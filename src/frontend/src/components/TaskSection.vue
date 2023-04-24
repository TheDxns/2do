<template>
  <div @keyup.enter="addTask">
    <p class="text-h5 text--primary mx-2 mt-7">
      Obecnie wybrana lista: {{currentListName}}
    </p>
    <div v-if="this.currentListName !== 'Wszystkie zadania' && this.currentListName !== 'Ukończone' && this.currentListName !== 'Priorytetowe'">
      <v-menu
        v-model="listSharingMenu"
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
          v-bind="attrs" v-on="on"
            class="mb-5"
            text
            @click="getPermittedUsers()"
            >
              Udostępnij
            </v-btn>
        </template>
        <v-card @keyup.enter="shareCustomList()">
          <v-list>
            <v-list-item>
              <b>Udostępnianie listy zadań</b>
            </v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list>
            <v-list-item>
              <v-text-field
                v-model="username"
                label="Podaj nazwę użytkownika"
                style="width: 75%;"
                class="mt-3"
              ></v-text-field>
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                  <v-list-item-title><span class="text-caption">Osoby z dostępem:</span></v-list-item-title>
                  <v-list-item-title v-if="permittedUsers.length === 0"><span style="color:grey">Nie udostępniono</span></v-list-item-title>
                  <v-list-item-title v-else v-for="permittedUser in permittedUsers" :key="permittedUser" v-bind:permittedUser="permittedUser"><li class="ml-1">{{permittedUser.name}} ({{permittedUser.username}})&ensp;<a @click="removeAccess(permittedUser.username)" style="color:red;">x</a></li></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
          </v-list>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn
              text
              @click="listSharingMenu = false"
            >
              Wróc
            </v-btn>
            <v-btn
              class="green--text text--darken-1"
              text
              @click="shareCustomList()"
            >
              Udostępnij
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-menu>
      <v-menu
        v-model="listEditMenu"
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
          v-bind="attrs" v-on="on"
            class="mb-5"
            text
            @click="updateListTitle()"
            >
              Edytuj
            </v-btn>
        </template>
        <v-card
        @keyup.enter="updateCustomList()">
          <v-list>
            <v-list-item>
              <b>Edycja listy zadań</b>
            </v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list>
            <v-list-item>
              <v-text-field
                label="Tytuł"
                placeholder="Tytuł listy"
                v-model=newListName
              ></v-text-field>
            </v-list-item>
          </v-list>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              text
              @click="listEditMenu = false"
            >
              Wróc
            </v-btn>
            <v-btn
              text
              class="green--text text--darken-1"
              @click="updateCustomList()"

            >
              Aktualizuj
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-menu>
      <v-btn
        class="mb-5"
        text
        color="error"
        @click="deleteTaskList()">
          Usuń listę
      </v-btn>
    </div>
    <div v-if="this.currentListName !== 'Ukończone'">
      <v-text-field
        v-model="newTaskTitle"
        class="ml-3"
        label="Dodaj nowe zadanie"
        outlined
        style="width: 25%;"
        placeholder="Zatwierdź zadanie przyciskiem Enter"
      ></v-text-field>
      <p class="text-h6 text--primary ml-3">
        Zadania do wykonania:
      </p>
    </div>
    <div v-else> 
      <v-text-field
        v-model="newTaskTitle"
        label="Dodaj nowe zadanie"
        class="ml-3"
        outlined
        disabled
        style="width: 25%;"
        placeholder="Zatwierdź zadanie przyciskiem Enter"
      ></v-text-field>
      <p class="text-h6 text--primary ml-3">
        Wykonane zadania:
      </p>
    </div>
    <v-expansion-panels focusable v-if="this.currentListName === 'Wszystkie zadania'">
      <Task v-for="task in allTasks" :key="task.title" v-bind:task="task" :keycloakData="keycloakData" @dataUpdate="getAllTasks"/>
    </v-expansion-panels>
    <v-expansion-panels focusable v-if="this.currentListName === 'Priorytetowe'">
      <Task v-for="task in importantTasks" :key="task.title" v-bind:task="task" :keycloakData="keycloakData" @dataUpdate="getAllTasks"/>
    </v-expansion-panels>
    <v-expansion-panels focusable v-if="this.currentListName === 'Ukończone'">
      <Task v-for="task in doneTasks" :key="task.title" v-bind:task="task" :keycloakData="keycloakData" @dataUpdate="getAllTasks"/>
    </v-expansion-panels>
    <v-expansion-panels focusable v-if="this.currentListId != null">
      <Task v-for="task in customListTasks" :key="task.title" v-bind:task="task" :keycloakData="keycloakData" @loadUsersRequired="getPermittedUsers" @dataUpdate="getAllTasks" :permittedUsers="permittedUsers" :currentListId="currentListId"/>
    </v-expansion-panels>
  </div>
</template>

<script>
import Task from '@/components/Task.vue'
  export default {
    name: 'TaskSection',
     props: ['currentListName', 'currentListId', 'keycloakData'],
    data() {
      return {
        allTasks: [],
        importantTasks: [],
        doneTasks: [],
        customListTasks: [],
        newTaskTitle: '',
        listSharingMenu: false,
        listEditMenu: false,
        username: '',
        newListName: this.currentListName,
        allUsers: '',
        permittedUsers: []
      }
    },
    components: {
      Task
    },
    created() {
      this.getUnfinishedTasks();
      this.getImportantTasks();
      this.getDoneTasks();
      this.getAllUsers();
    },
      methods: {
        getAllTasks() {
          this.getUnfinishedTasks();
          this.getImportantTasks();
          this.getDoneTasks();
          this.getCustomListTasks();
        },
        getUnfinishedTasks() {
          fetch("http://localhost:9000/api/tasks/unfinished/user/" + this.$keycloak.idTokenParsed.sub)
            .then((response) => response.json())
            .then((data) => {
              data.sort(function compare(a, b) {
                  let dateA = new Date(a.deadline);
                  let dateB = new Date(b.deadline);
                  if (a.deadline == null) {
                    dateA = new Date("2100-01-01");
                  }
                  if (b.deadline == null) {
                    dateB = new Date("2100-01-02");
                  }
                  console.log(dateA + " | " + dateB)
                  return dateA - dateB;
              });
              this.allTasks = data;
            })
        },
        getImportantTasks() {
          fetch("http://localhost:9000/api/tasks/important/user/" + this.$keycloak.idTokenParsed.sub)
            .then((response) => response.json())
            .then((data) => {
                data.sort(function compare(a, b) {
                  let dateA = new Date(a.deadline);
                  let dateB = new Date(b.deadline);
                  if (a.deadline == null) {
                    dateA = new Date("2100-01-01");
                  }
                  if (b.deadline == null) {
                    dateB = new Date("2100-01-02");
                  }
                  console.log(dateA + " | " + dateB)
                  return dateA - dateB;
                });
                this.importantTasks = data;
            })
        },
        getDoneTasks() {
          fetch("http://localhost:9000/api/tasks/done/user/" + this.$keycloak.idTokenParsed.sub)
            .then((response) => response.json())
            .then((data) => {
                data.sort(function compare(a, b) {
                  let dateA = new Date(a.deadline);
                  let dateB = new Date(b.deadline);
                  if (a.deadline == null) {
                    dateA = new Date("2100-01-01");
                  }
                  if (b.deadline == null) {
                    dateB = new Date("2100-01-02");
                  }
                  console.log(dateA + " | " + dateB)
                  return dateA - dateB;
                });
                this.doneTasks = data;
            })
        },
        getCustomListTasks() {
          setTimeout(() => {  
          if (this.currentListId != null) {
            fetch("http://localhost:9000/api/tasks/unfinished/custom/" + this.currentListId)
              .then((response) => response.json())
              .then((data) => {
                  data.sort(function compare(a, b) {
                    let dateA = new Date(a.deadline);
                    let dateB = new Date(b.deadline);
                    if (a.deadline == null) {
                      dateA = new Date("2100-01-01");
                    }
                    if (b.deadline == null) {
                      dateB = new Date("2100-01-02");
                    }
                    console.log(dateA + " | " + dateB)
                    return dateA - dateB;
                  });
                  this.customListTasks = data;
              })}
              }, 100);
        },
        getAllUsers() {
          fetch("http://localhost:9000/api/users/username")
              .then((response) => response.json())
              .then((data) => {
                  this.allUsers = data;
              })
        },
        addTask() {
          if (this.currentListId == null) {
            this.addStandardTask();
          } else {
            this.addTaskToCustomList();
          }
        },
        addStandardTask() {
          let taskPriority;
          if (this.currentListName === "Priorytetowe") {
            taskPriority = "MAJOR";
          } else {
            taskPriority = "MINOR";
          }
          fetch("http://localhost:9000/api/tasks", {
                  method: 'post',
                  headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                  body:JSON.stringify({title:this.newTaskTitle, creatorId: this.$keycloak.idTokenParsed.sub, status:"WAITING", priority: taskPriority})
                }).then(response => response.text())
                  .then((response) => {
                      console.log(response);
                      this.newTaskTitle="";
                      this.getUnfinishedTasks();
                      this.getImportantTasks();
                      this.getDoneTasks();
                      this.getCustomListTasks();
                  })
                  .catch(err => console.log(err));
        },
        addTaskToCustomList() {
          let taskPriority;
          if (this.currentListName === "Priorytetowe") {
            taskPriority = "MAJOR";
          } else {
            taskPriority = "MINOR";
          }
          fetch("http://localhost:9000/api/tasks/" + this.currentListId, {
                  method: 'post',
                  headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                  body:JSON.stringify({title:this.newTaskTitle, creatorId: this.$keycloak.idTokenParsed.sub, status:"WAITING", priority: taskPriority})
                }).then(response => response.text())
                  .then((response) => {
                      console.log(response);
                      this.newTaskTitle="";
                      this.getUnfinishedTasks();
                      this.getImportantTasks();
                      this.getDoneTasks();
                      this.getCustomListTasks();
                  })
                  .catch(err => console.log(err));
        },
        deleteTaskList() {
          fetch("http://localhost:9000/api/lists/" + this.currentListId, { method: 'delete' })
            .then(response => response.text())
            .then((response) => {
                console.log(response.status);
                document.location.replace("/");
            })
            .catch(err => console.log(err));
        },
      shareCustomList() {
        if (this.allUsers.includes(this.username)) {
          if (this.permittedUsers.some(user => this.allUsers.includes(user.username))) {
            window.alert("Podany użytkownik już ma dostęp do listy.")
          } else if(this.keycloakData.idTokenParsed.preferred_username === this.username) {
            window.alert("Podany użytkownik jest właścicielem listy.")
          } else {
          fetch("http://localhost:9000/api/lists/access/" + this.currentListId + "/" + this.username, { method: 'PATCH',
          headers: {
              'Accept': '*',
              'Host': ''
            }})
            .then((response) => {
                console.log(response.status);
                window.alert("Udostępniono listę.");
                this.getPermittedUsers();
            })
            .catch(err => console.log(err));
          }
        } else {
          window.alert("Nie istnieje użytkownik o podanej nazwie.");
        }
        },
        updateCustomList() {
          fetch("http://localhost:9000/api/lists/" + this.currentListId, {
        method: 'put',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body:JSON.stringify({title:this.newListName})
      }).then(response => response.text())
        .then((response) => {
            console.log(response);
            this.$emit('dataUpdate')
            setTimeout(() => { 
            this.currentListName = this.newListName;
            }, 500);
        })
        .catch(err => console.log(err));
        },
        updateListTitle() {
          this.newListName = this.currentListName;
        },
        getPermittedUsers() {
          fetch("http://localhost:9000/api/lists/access/" + this.currentListId)
          .then((response) => response.json())
              .then((data) => {
                  this.permittedUsers = data;
              })
        },
        removeAccess(username) {
          fetch("http://localhost:9000/api/lists/access/remove/" + this.currentListId + "/" + username, { method: 'PATCH',
          headers: {
            'Accept': '*',
            'Host': ''
          }})
          .then((response) => {
              console.log(response.status);
              window.alert("Usunięto dostęp.");
              this.getPermittedUsers();
          })
          .catch(err => console.log(err));
        }
      }
  }
</script>

