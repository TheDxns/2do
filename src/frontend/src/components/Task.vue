<template>
<v-container>
  <v-expansion-panel>
        <v-expansion-panel-header @click="getPermittedUsers()">
          <span v-if= "task.prioritized === false">{{task.title}}</span>
          <span v-else style="color:red;">{{task.title}}</span>
          <span style="color:grey;" v-if="task.deadline != null">({{task.deadline | formatDate}})</span>
          <v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer>
          <v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer>
          <v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer>
    
        <span @click.stop="setTaskAsDone">Oznacz jako wykonane</span> <span v-if="task.prioritized === false" @click.stop="toggleImportant">Zwiększ priorytet</span> <span v-else @click.stop="toggleImportant">Zmiejsz priorytet</span> <span style="color: red;" @click.stop="deleteTask">Usuń</span>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
        
        <v-text-field
          class="ml-8 mt-5"
          style="width:500px;"
          label="Tytuł"
          placeholder="Tytuł zadania"
          v-model="newTaskTitle"
        ></v-text-field>
        <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :nudge-right="40"
        transition="scale-transition"
        offset-y
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            class=""
            v-model="deadline"
            label="Termin wykonania"
            prepend-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
            style="width:300px;"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="newDeadline"
          @input="menu = false"
          :first-day-of-week="1"
          locale="pl"
        ></v-date-picker>
      </v-menu>
        <v-text-field v-if="this.currentListId == null"
            class="ml-8 mt-5"
            v-model="this.keycloakData.idTokenParsed.preferred_username"
            label="Zadanie przypisane do:"
            readonly
            style="width:300px;"
        ></v-text-field>
        <v-select v-else
          v-model="task.responsible"
          class="ml-8 mt-5"
          :items="permittedUsers"
          style="width:300px;"
          label="Zadanie przypisane do:"
          dense
        ></v-select>
           <v-textarea
          outlined
          name="input-7-4"
          label="Opis zadania"
          v-model="task.content"
          style="width:50%;"
          class="ml-7 mt-5"
        ></v-textarea>
        <a class="ml-7" href="#" @click="updateTask" style="text-decoration:none; color:black;" onmouseover="this.style.color='grey';" onmouseout="this.style.color='black';">Zaktualizuj</a>
        </v-expansion-panel-content>
      </v-expansion-panel>
</v-container>  
</template>

<script>
export default {
  name: 'Task',
  props: ['task', 'keycloakData', 'permittedUsers', 'currentListId'],
  data() {
    return {
      creatorName: this.keycloakData.idTokenParsed.given_name.concat(" ").concat(this.keycloakData.idTokenParsed.family_name),
      menu: false,
      newDeadline: '',
      newTaskTitle: this.task.title
    }
  },
  computed: {
    deadline: function() {
      if (this.newDeadline === '' && this.task.deadline == null) {
        return null;
      } else if (this.newDeadline === '') {
        let date = new Date(this.task.deadline);
        date.setDate(date.getDate() + 1);
        return date.toISOString().substring(0, 10);
      } else {
        return this.newDeadline;
      } 
    }
  },
  methods: {
    getTaskList() {
      fetch("/api/lists/"+ this.list.id)
              .then((response) => response.json())
              .then((data) => {
                  this.tasks = data;
              })
    },
    deleteTask() {
      fetch("/api/tasks/" + this.task.id, { method: 'delete' })
        .then(response => response.text())
        .then((response) => {
            console.log(response.status);
            this.$emit('dataUpdate')
        })
        .catch(err => console.log(err));
    },
    setTaskAsDone() {
      fetch("/api/tasks/finish/" + this.task.id, { method: 'PATCH',
      headers: {
          'Accept': '*',
          'Host': ''
        }})
        .then((response) => {
            console.log(response.status);
            this.$emit('dataUpdate')
        })
        .catch(err => console.log(err));
    },
    toggleImportant() {
      fetch("/api/tasks/prioritize/" + this.task.id, { method: 'PATCH' })
        .then((response) => {
            console.log(response.status);
            this.$emit('dataUpdate')
        })
        .catch(err => console.log(err));
    },
    updateTask() {
      fetch("/api/tasks/"+ this.task.id, {
        method: 'put',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body:JSON.stringify({id:this.task.id, title:this.newTaskTitle, taskList: this.task.taskList, content:this.task.content, prioritized:this.task.prioritized, responsible: this.task.responsible, creatorId: this.task.creatorId, deadline: new Date(this.newDeadline)})
      }).then(response => response.text())
        .then((response) => {
            console.log(response);
            this.$emit('dataUpdate')
            window.alert("Zaktualizowano zadanie");
        })
        .catch(err => console.log(err));
    },
    getPermittedUsers() {
      this.$emit('loadUsersRequired');
    }
  },
  filters: {
    formatDate: function(date) {
        let newDate = new Date(date);
        return newDate.toLocaleDateString("pl-PL", {
        year: "numeric",
        month: "long",
        day: "numeric" });
    }
  }
}
</script>