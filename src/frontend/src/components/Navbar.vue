<template>
  <nav>
    <v-toolbar dark extended>
      <v-row justify="start">
        <v-col>
          <a href="/" style="text-decoration: none;">
            <v-toolbar-title class="headline text-uppercase grey--text mt-14 px-5">
              <v-img :src="require('../assets/logo.png')" max-height="90" max-width="90"  contain class="mb-3" />
            </v-toolbar-title>
            </a>
        </v-col>
      <v-spacer></v-spacer>
      <v-col
      cols="2" class="mt-9">
        <UserMenu :keycloakData="keycloakData"/>
      </v-col>
    </v-row>
  </v-toolbar>
</nav>
</template>
<script>
import UserMenu from '@/components/UserMenu.vue'
export default {
  name: 'Navbar',
  props: ['keycloakData'],
  components: {
    UserMenu
  }
}
</script>