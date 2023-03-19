import { createRouter, createWebHistory } from 'vue-router';
import WelcomeLogin from "@/views/WelcomeLogin"

const routes = [
  // Add your routes here
    {
    path: '/',
    name: 'Login',
    component: WelcomeLogin
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;