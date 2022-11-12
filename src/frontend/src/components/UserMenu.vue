<template>
      <v-menu
        bottom
        min-width="200px"
        rounded
        offset-x
        offset-y
      >
        <template v-slot:activator="{ on }">
          <v-row>
          <v-col
          cols="6">
          </v-col>
          <v-col
          cols="6">
          <v-btn
            class="ml-3 mt-10"
            icon
            x-large
            v-on="on"
          >
            <v-avatar
              color="green lighten-1"
              size="70"
            >
              <span class="white--text text-h4">{{ initials }}</span>
            </v-avatar>
          </v-btn>
        </v-col>
        </v-row>
        </template>
        <v-card>
          <v-list-item-content class="justify-center">
            <div class="mx-auto text-center">
              <h3 class="mt-2">{{ keycloakData.idTokenParsed.name }}</h3>
              <p class="text-caption mt-1">
                {{ keycloakData.idTokenParsed.email }}
              </p>
              <v-divider class="my-3"></v-divider>
              <v-btn
                  href="http://localhost:8180/auth/realms/Todo/account/#/personal-info"
                  target="_blank"
                  depressed
                  rounded
                  text
                >
                  Edytuj konto
              </v-btn>
              <v-divider class="my-3"></v-divider>
              <v-btn
                href="http://localhost:8180/auth/realms/Todo/account/#/security/signingin"
                target="_blank"
                depressed
                rounded
                text
              >
                Opcje zabezpieczeń
              </v-btn>
              <v-divider class="my-3"></v-divider>
              <v-btn
                @click="keycloakData.logout"
                depressed
                rounded
                text
              >
                Wyloguj się
              </v-btn>
            </div>
          </v-list-item-content>
        </v-card>
      </v-menu>
</template>

<script>
  export default {
    name: "UserMenu",
    props: ['keycloakData'],
     data() {
      return {
        dataLoading: false,
        initials: this.keycloakData.idTokenParsed.given_name.charAt(0).concat(this.keycloakData.idTokenParsed.family_name.charAt(0))
        }
    },
    methods: {
    },
    mounted() {
    }
  }
</script>