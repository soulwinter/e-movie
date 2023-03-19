import { createRouter, createWebHistory } from 'vue-router';
import WelcomeLogin from "@/views/WelcomeLogin"
import Home from "@/views/HomePage"

const routes = [
  // Add your routes here
    {
    path: '/',
    name: 'Login',
    component: WelcomeLogin
  },
  {
    path: '/home',
    name: 'HomePage',
    component: Home
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
