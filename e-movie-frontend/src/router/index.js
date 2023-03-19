import { createRouter, createWebHistory } from 'vue-router';
import Login from "@/views/Login"

const routes = [
  // Add your routes here
    {
    path: '/',
    name: 'Login',
    component: Login
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;