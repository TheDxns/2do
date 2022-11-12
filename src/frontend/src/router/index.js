import Vue from 'vue'
import VueRouter from 'vue-router'
import Dashboard from "@/Dashboard";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Dashboard',
        component: Dashboard,
        meta: {
            title: "2DO - Dashboard"
        }
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms))
}

router.beforeEach(async (to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // We wait for Keycloak init, then we can call all methods safely
        while (router.app.$keycloak.createLoginUrl === null) {
            await sleep(100)
        }
        if (router.app.$keycloak.authenticated) {
            next()
        } else {
            const loginUrl = router.app.$keycloak.createLoginUrl()
            window.location.replace(loginUrl)
        }
    } else {
        document.title = to.meta.title;
        next()
    }
})

export default router