<template>
  <v-app>
    <v-main>
      <v-row>
        <v-col>
          <Navbar :keycloakData="keycloakData"/>
        </v-col>
      </v-row>
      <v-row>
        <v-col
        cols:12>
          <router-view :keycloakData="keycloakData"/>
        </v-col>
      </v-row>
      <v-row class="mt-16 mb-16">
      </v-row>
      <v-row class="mt-16">
          <Footer/>
      </v-row>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'
export default {
  name: 'App',
  props: ['keycloakData'],
  components: {
    Navbar,
    Footer
  }
}
</script>