import { createRouter, createWebHistory } from 'vue-router';
import WelcomeLogin from "@/views/WelcomeLogin"
// import Home from "@/views/HomePage"
import MovieList from '@/views/MovieList.vue';

const routes = [
  // Add your routes here
    {
    path: '/',
    name: 'Login',
    component: WelcomeLogin
  },
  {
    path: '/home',
    name: 'MovieList',
    component: MovieList
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
