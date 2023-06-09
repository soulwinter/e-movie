import { createRouter, createWebHistory } from 'vue-router';
import WelcomeLogin from "@/views/WelcomeLogin"
// import Home from "@/views/HomePage"
import MovieList from '@/views/MovieList.vue';
import MovieDetails from "@/views/MovieDetails.vue";
import AdminMovieList from "@/views/AdminMovieList.vue";
import AdminMovieDetails from "@/views/AdminMovieDetails.vue";
import AdminCreateNewMovieVue from '@/views/AdminCreateNewMovie.vue';

const routes = [
  // Add your routes here
  {
    path: '/login',
    name: 'Login',
    meta: {title: '登录 e-movie',},
    component: WelcomeLogin
  },
  {
    path: '/',
    name: 'MovieList',
    meta: {title: '电影列表 ｜ e-movie',},
    component: MovieList
  },
  {
    path: "/movie/:id",
    name: "MovieDetails",
    meta: {title: '电影详情 ｜ e-movie',},
    component: MovieDetails,
  },
  {
    path: "/admin/home",
    name: "AdminMovieList",
    meta: {title: '电影列表 ｜ e-movie 管理',},
    component: AdminMovieList,
  },
  {
    path: "/admin/movie/:id",
    name: "AdminMovieDetails",
    meta: {title: '电影详情 ｜ e-movie 管理',},
    component: AdminMovieDetails,
  },
  {
    path: "/admin/create",
    name: "AdminCreateNewMovie",
    meta: {title: '添加电影 ｜ e-movie 管理',},
    component: AdminCreateNewMovieVue,
  }
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

router.beforeEach((to, from, next) => {
  // 根据路由设置标签页名称
  document.title = to.meta.title || 'e-movie';
  next();
});

export default router;
