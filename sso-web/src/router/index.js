import {createRouter, createWebHistory} from 'vue-router'
import Signin from '@/views/Signin.vue'
import Signup from "@/views/Signup";
import Password from "@/views/setup/Password";
import Home from "@/views/Home";
import Profile from "@/views/setup/Profile";
import Account from "@/views/setup/Account";
import NotFound from "@/views/error/NotFound";

const routes = [
    {
        path: '/signin.html',
        name: 'Signin',
        component: Signin
    },
    {
        path: '/signup.html',
        name: 'Signup',
        component: Signup
    },
    {
        path: '/password.html',
        name: 'Password',
        component: Password
    },
    {
        path: '/',
        name: 'Home',
        redirect: '/profile.html',
        component: Home,
        children: [
            {
                path: '/profile.html',
                name: 'Profile',
                component: Profile
            },
            {
                path: '/account.html',
                name: 'Account',
                component: Account
            }
        ]
    },
    {
        path: '/:catchAll(.*)',
        name: '404',
        component: NotFound
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
