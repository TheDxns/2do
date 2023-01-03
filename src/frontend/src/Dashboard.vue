<template>
  <v-row>
      <v-col cols=2>
          <v-row>
            <v-col
              cols:12
            >
              <ul class="mt-8">
                <li style="overflow:hidden;">
                  <v-card
                    class="mx-auto mb-5 d-flex flex-column"
                    max-width=100%
                    min-width=100%
                    min-height="20"
                    max-height="50"
                    outlined
                    tile
                    link
                    @click="currentListName='Wszystkie zadania', currentListId = null"
                  >
                    <v-card-text class="font-weight-light" style="max-height: 50px; overflow:hidden;">
                      <p class="text-h5 text--primary mx-2">
                        Wszystkie zadania
                      </p>
                    </v-card-text>
                  </v-card>
                </li>
                <li style="overflow: hidden;">
                  <v-card
                    class="mx-auto mb-5 d-flex flex-column"
                    max-width=100%
                    min-width=100%
                    min-height="20"
                    max-height="50"
                    outlined
                    tile
                    link
                    @click="currentListName='Priorytetowe', currentListId=null"
                  >
                    <v-card-text class="font-weight-light" style="max-height: 50px; overflow: hidden;">
                      <p class="text-h5 text--primary mx-2">
                        Priorytetowe
                      </p>
                    </v-card-text>
                  </v-card>
                </li>
                <li style="overflow: hidden;">
                  <v-card
                    class="mx-auto mb-5 d-flex flex-column"
                    max-width=100%
                    min-width=100%
                    min-height="20"
                    max-height="50"
                    outlined
                    tile
                    link
                    @click="currentListName='Ukończone', currentListId=null"
                  >
                    <v-card-text class="font-weight-light" style="max-height: 50px; overflow:hidden;">
                      <p class="text-h5 text--primary mx-2">
                        Ukończone
                      </p>
                    </v-card-text>
                  </v-card>
                </li>
                <li v-for="list in lists" :key="list.title" v-bind:list="list" style="overflow: hidden;">
                  <v-card
                    class="mx-auto mb-5 d-flex flex-column"
                    max-width=100%
                    min-width=100%
                    min-height="20"
                    max-height="50"
                    outlined
                    tile
                    link
                    @click="currentListName=list.title, currentListId=list.id, $refs.taskSectionComponent.getCustomListTasks()"
                  >
                    <v-card-text class="font-weight-light" style="max-height: 50px; overflow:hidden;">
                      <p class="text-h5 green--text text--darken-1 mx-2">
                        {{list.title}}
                      </p>
                    </v-card-text>
                  </v-card>
                </li>
                <li style="overflow:hidden;">
                  <v-card
                    class="mx-auto mb-5 d-flex flex-column"
                    max-width=100%
                    min-width=100%
                    min-height="20"
                    max-height="50"
                    outlined
                    tile
                    link
                  >
                    <v-card-text class="font-weight-light" style="max-height: 50px; overflow:hidden;">
                      <p class="text-h5  text--primary mx-2">
                        <v-menu
                          v-model="listCreationMenu"
                          :close-on-content-click="false"
                          :nudge-width="200"
                          offset-x
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <span class="green--text text--darken-1" v-bind="attrs" v-on="on">+ Utwórz nową listę</span>
                          </template>

                          <v-card
                          @keyup.enter="createNewCustomList()">
                            <v-list>
                              <v-list-item>
                                <b>Tworzenie nowej listy zadań</b>
                              </v-list-item>
                            </v-list>

                            <v-divider></v-divider>

                            <v-list>
                              <v-list-item>
                                <v-text-field
                                  v-model="newTaskListTitle"
                                  label="Dodaj nową listę"
                                  outlined
                                  style="width: 75%;"
                                  class="mt-3"
                                ></v-text-field>
                              </v-list-item>
                            </v-list>

                            <v-card-actions>
                              <v-spacer></v-spacer>

                              <v-btn
                                text
                                @click="listCreationMenu = false"
                              >
                                Wróc
                              </v-btn>
                              <v-btn
                                class="green--text text--darken-1"
                                text
                                @click="createNewCustomList()"
                                
                              >
                                Utwórz
                              </v-btn>
                            </v-card-actions>
                          </v-card>
                        </v-menu>
                      </p>
                    </v-card-text>
                  </v-card>
                </li>
              </ul>
            </v-col>
          </v-row>
      </v-col>
      <v-col cols=10>
          <TaskSection :currentListName="currentListName" :currentListId="currentListId" :keycloakData="keycloakData" ref="taskSectionComponent" @dataUpdate="getLists"/>
      </v-col>
  </v-row>
</template>
<script>
import TaskSection from '@/components/TaskSection.vue'
export default {
  name: 'Dashboard',
  props: ['keycloakData'],
   data() {
      return {
        users: [],
        lists: [],
        listCreationMenu: false,
        currentListName: 'Wszystkie zadania',
        currentListId: null,
        newTaskListTitle: ''
      }
    },
    components: {
      TaskSection
    },
    mounted() {
      this.getLists();
    },
    methods: {
      getLists() {
        fetch("/api/lists/user/" + this.$keycloak.idTokenParsed.sub)
          .then((response) => response.json())
          .then((data) => {
            this.lists = data;
          })
      },
      createNewCustomList() {
        this.users.push(this.$keycloak.idTokenParsed.sub);
          fetch("/api/lists", {
                  method: 'post',
                  headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                  body:JSON.stringify({title: this.newTaskListTitle, users: this.users})
                }).then(response => response.text())
                  .then((response) => {
                      console.log(response);
                      this.newTaskListTitle = "";
                      this.getLists();
                  })
                  .catch(err => console.log(err));
      }
  }
}
</script>