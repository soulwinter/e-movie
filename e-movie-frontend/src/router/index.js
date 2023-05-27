import { createRouter, createWebHistory } from 'vue-router';
import WelcomeLogin from "@/views/WelcomeLogin"
// import Home from "@/views/HomePage"
import MovieList from '@/views/MovieList.vue';
import MovieDetails from "@/views/MovieDetails.vue";

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
  {
    path: "/movie/:id",
    name: "MovieDetails",
    component: MovieDetails,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 如果路由中保存了滚动位置则使用它，否则滚动到页面顶部
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  },
});

export default router;
