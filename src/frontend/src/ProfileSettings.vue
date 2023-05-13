<template>
  <v-row>
      <v-col cols=3
      class="ml-16">
        <v-text-field
            v-model=name
            label="Name"
        ></v-text-field>
        <v-text-field
            v-model=surname
            label="Surname"
        ></v-text-field>
        <v-text-field
            v-model=email
            label="E-mail address"
        ></v-text-field>
        <v-text-field
            v-model=username
            label="Username"
        ></v-text-field>

        <v-text-field
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.min]"
            :type="show ? 'text' : 'password'"
            name="input-10-2"
            label="Hasło"
            hint="At least 8 characters"
            v-model=password
            class="input-group--focused"
            @click:append="show = !show"
        ></v-text-field>
      </v-col>
  </v-row>
</template>
<script>
export default {
  name: 'ProfileSettings',
  props: ['keycloakData'],
   data() {
      return {
        userId: '',
        username: '',
        password: '',
        show: false,
        name: '',
        surname: '',
        email: '',
        rules: {
          required: value => !!value || 'Required.',
          min: v => v.length >= 8 || 'Min 8 characters'
        },

      }
    },
    components: {
    },
    mounted() {
      this.mapKeycloakData();
    },
    methods: {
      getUserId() {
        let splitUrl = window.location.href.split('/');
        this.userId = splitUrl[splitUrl.length-1];
      },
      mapKeycloakData() {
        this.name = this.keycloakData.idTokenParsed.given_name;
        this.surname = this.keycloakData.idTokenParsed.family_name;
        this.email = this.keycloakData.idTokenParsed.email;
        this.username = this.keycloakData.idTokenParsed.preferred_username;
      },
      getUser() {
        fetch()
      }
    }
}
</script>