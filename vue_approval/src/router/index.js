import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from "@/views/LoginPage.vue";
import HomePage from "@/views/HomePage.vue"
import ProjectSavePage from "@/views/project/ProjectSavePage";
import ProjectPage from "@/views/project/ProjectPage";
import ProjectApprovalPage from "@/views/project/ProjectApprovalPage";
import AppealPage from "@/views/appeal/AppealPage";
import AppealList from "@/views/appeal/AppealList";

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        component: LoginPage
    },
    {
        path: '/',
        component: HomePage,
        children: [
            {
                path: '/project/add',
                component: ProjectSavePage
            },
            {
                path: '/project/approval',
                component: ProjectApprovalPage
            },
            {
                path: '/project/page',
                component: ProjectPage
            },
            {
                path: '/appealPage',
                component: AppealPage
            },
            {
                path: '/appealList',
                component: AppealList
            }
        ]
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    if (to.path == '/login') {
        next()
        return
    }
    const user = localStorage.getItem('user');
    if (!user) {
        next({path: '/login'});
    } else {
        next();
    }
});

export default router