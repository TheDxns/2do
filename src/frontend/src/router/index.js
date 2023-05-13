import Vue from 'vue'
import VueRouter from 'vue-router'
import Dashboard from "@/Dashboard";
import ProfileSettings from "@/ProfileSettings";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Dashboard',
        component: Dashboard,
        meta: {
            title: "2DO - Dashboard"
        }
    },
    {
        path: '/profile-settings/:id',
        name: 'Profile settings',
        component: ProfileSettings,
        meta: {
            title: "2DO - Profile settings"
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