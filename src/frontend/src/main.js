import Vue from 'vue'
import App from './App.vue'
import vuetify from '@/plugins/vuetify'
import router from './router'
import VueKeycloakJs from '@dsb-norge/vue-keycloak-js'

Vue.config.productionTip = false

Vue.use(VueKeycloakJs, {
  init: {
    onLoad: 'login-required',
    checkLoginFrame: false
  },
  config: {
    url: 'http://localhost:8180/auth/',
    realm: 'Todo',
    clientId: 'todo-client'
  },
  onReady (keycloak) {
    console.log(keycloak);

    new Vue({
      vuetify,
      router,
      render: h => h(App, {
        props: {
          'keycloakData': keycloak
        }
      }),
      }).$mount('#app')
  }
})