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
      <v-snackbar v-model="showSnackbar" :color="color" :timeout="timeout">{{ message }}</v-snackbar>
      <v-row class="mt-16">
          <Footer/>
      </v-row>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'
import WebSocketService from '@/services/WebSocketService'

export default {
  name: 'App',
  props: ['keycloakData'],
  components: {
    Navbar,
    Footer
  },
  data() {
    return {
      showSnackbar: false,
      message: '',
      color: '',
      timeout: 3000,
      notifications: []
    }
  },
  methods: {
    showNotification(message, color) {
      this.message = message
      this.color = color
      this.showSnackbar = true
    },
    notificationHandler(payload) {
      this.showNotification(payload.message, payload.color);
    },
    onConnected(stompClient) {
      WebSocketService.subscribe(stompClient, '/topic/notifications', this.onNotification.bind(this));
    },
    onNotification(notification) {
      this.notifications.push(JSON.parse(notification));
      this.notificationHandler(notification);
    }
  },
  mounted() {
    WebSocketService.connect(this.onConnected.bind(this));
  }
}
</script>