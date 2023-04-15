<template>
    <HeadComponent />
    <div class="movie-list">
      <div class="movie-card" v-for="movie in movies" :key="movie.id">
        <h1 class="movie-title">{{ movie.title }}</h1>
        <p class="movie-overview">{{ movie.overview }}</p>
        <div class="info-container">
          <div
            class="info-badge"
            :class="{ 'adult-badge': movie.adult, 'non-adult-badge': !movie.adult }"
          >
            {{ movie.adult ? "成人分级" : "非成人分级" }}
          </div>
          <div class="info-badge">{{ movie.popularity }} 流行度</div>
          <div class="info-badge" v-if="movie.budget !== 0">${{ movie.budget }} 预算</div>
          <div class="info-badge" v-if="movie.revenue !== 0">${{ movie.revenue }} 票房收入</div>
        </div>
      </div>
      
    </div>
    <button class="pagination-btn" @click="loadMoreMovies">加载更多电影</button>
  </template>
  
  <script>
  import axios from 'axios';
  import { ref, onMounted } from 'vue';
  import HeadComponent from '@/components/HeadComponent.vue';
  
  export default {
    name: 'MovieList',
    components: {
        HeadComponent,
    },
    setup() {
      const movies = ref([]);
      let currentPage = 1;
      const perPage = 10;
  
      const fetchMovies = async (page = 1, perPage = 10) => {
        try {
          const response = await axios.get(`http://localhost:8081/movie/listInfo/${page}/${perPage}`);
          if (response.data.success) {
            movies.value = [...movies.value, ...response.data.data];
          }
        } catch (error) {
          console.error('Error fetching movies:', error);
        }
      };
  
      const loadMoreMovies = () => {
        currentPage++;
        fetchMovies(currentPage, perPage);
      };
  
      onMounted(() => {
        fetchMovies(currentPage, perPage);
      });
  
      return {
        movies,
        loadMoreMovies
      };
    },
    mounted() {
      window.addEventListener("scroll", this.scrollHandler);
    },
    beforeUnmount() {
      window.removeEventListener("scroll", this.scrollHandler);
    },
    methods: {
        scrollHandler() {
            const { scrollTop, scrollHeight, clientHeight } = document.documentElement;
            if (scrollTop + clientHeight >= scrollHeight - 300) {
                this.loadMoreMovies();
            }
      },
    }
  };
  </script>
  
  <style scoped>
  .movie-list {
    column-count: 3;
    column-gap: 70px;
    max-width: 1280px;
    margin: 0 auto;
  }

  .movie-card {
    background-color: #fff;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 20px;
    max-width: 400px;
    width: 100%;
    transition: transform 0.3s ease-in-out;
    break-inside: avoid;
  }

  .movie-card:hover {
    transform: scale(1.05);
  }
  
  .movie-title {
    font-size: 30px;
    font-weight: bold;
    color: #000;
    margin-bottom: 10px;
  }
  
  .movie-overview {
    font-size: 15px;
    color: #777;
    text-align: left;
    margin-bottom: 10px;
  }
  
  .info-container {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
  }
  
  .info-badge {
    border: 1px solid #ccc;
    border-radius: 12px;
    padding: 4px 8px;
    font-size: 12px;
    color: #000;
  }
  
  .adult-badge {
    background-color: #f44336;
  }
  
  .non-adult-badge {
    background-color: #4caf50;
  }

  .pagination-btn {
    background-color: #4285F4;
    color: #fff;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    margin-top: 20px;
  }

  .pagination-btn:hover {
    background-color: #3079ED;
  }
</style>
  