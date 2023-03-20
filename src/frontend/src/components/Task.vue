<template>
<v-container>
  <v-expansion-panel>
        <v-expansion-panel-header @click="getPermittedUsers()">
          <span v-if= "task.priority === 'MINOR'">{{task.title}}</span>
          <span v-else style="color:red;">{{task.title}}</span>
          <span style="color:grey;" v-if="task.deadline != null">({{task.deadline | formatDate}})</span>
          <v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer>
          <v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer>
          <v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer><v-spacer></v-spacer>
    
        <span @click.stop="setTaskAsDone">Oznacz jako wykonane</span> <span v-if="task.priority === 'MINOR'" @click.stop="toggleImportant">Zwiększ priorytet</span> <span v-else @click.stop="toggleImportant">Zmiejsz priorytet</span> <span style="color: red;" @click.stop="deleteTask">Usuń</span>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
        
        <v-text-field
          class="ml-8 mt-5"
          style="width:500px;"
          label="Tytuł"
          placeholder="Tytuł zadania"
          v-model="newTaskTitle"
        ></v-text-field>
          <v-row>
            <v-col cols="3">
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
                  label="Data"
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
            </v-col>
            <v-col cols="6">
              <vue-timepicker placeholder="Godzina" class="mt-4"></vue-timepicker>
            </v-col>
          </v-row>
        <v-text-field v-if="this.currentListId == null"
            class="ml-8 mt-5"
            v-model="currentUsersFullName"
            label="Zadanie przypisane do:"
            readonly
            style="width:300px;"
        ></v-text-field>
        <v-select v-else
          v-model="responsible"
          class="ml-8 mt-5"
          :items="permittedUsersNames"
          style="width:300px;"
          label="Zadanie przypisane do:"
          dense
        ></v-select>
           <v-textarea
          outlined
          name="input-7-4"
          label="Opis zadania"
          v-model="task.description"
          style="width:50%;"
          class="ml-7 mt-5"
        ></v-textarea>
        <a class="ml-7" href="#" @click="updateTask" style="text-decoration:none; color:black;" onmouseover="this.style.color='grey';" onmouseout="this.style.color='black';">Zaktualizuj</a>
        </v-expansion-panel-content>
      </v-expansion-panel>
</v-container>  
</template>

<script>
import VueTimepicker from 'vue2-timepicker/src/vue-timepicker.vue'

export default {
  name: 'Task',
  props: ['task', 'keycloakData', 'permittedUsers', 'currentListId'],
  data() {
    return {
      creatorName: this.keycloakData.idTokenParsed.given_name.concat(" ").concat(this.keycloakData.idTokenParsed.family_name),
      menu: false,
      newDeadline: '',
      newTaskTitle: this.task.title,
      responsible: this.task.responsible
    }
  },
  components: {
    VueTimepicker
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
    },
    permittedUsersNames() {
      let allUsernames = [this.task.creator.name];
      allUsernames = allUsernames.concat(this.permittedUsers.map(user => user.name));
      return allUsernames;
    },
    currentUsersFullName() {
      return this.keycloakData.idTokenParsed.given_name + " " + this.keycloakData.idTokenParsed.family_name;
    }
  },
  methods: {
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
        body:JSON.stringify({
          id:this.task.id, title:this.newTaskTitle, taskListId: this.task.taskList.id, description:this.task.description,
          priority:this.task.priority, creatorId: this.task.creator.keycloakId, status: this.task.status,
          deadline: new Date(this.newDeadline), responsibleId: this.findUserIdByName(this.responsible) })
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
    },
    findUserIdByName(responsible) {
      const user = this.permittedUsers.find(user => user.name === responsible);
      if (user) {
        return user.id;
      }
      return null;
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