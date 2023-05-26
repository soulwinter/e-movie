<template>
  <HeadComponent />

  <!-- 搜索栏 -->
  <div class="search-bar">
    <input
      class="search-input"
      v-model="searchString"
      @input="searchRecommendation"
      type="text"
      placeholder="搜索电影"
    />
    <div class="dropdown" v-if="recommendations.length">
      <div
        v-for="item in recommendations"
        :key="item.id"
        class="dropdown-item"
        @click="navigateToMovie(item.id)"
      >
        {{ item.title }}
      </div>
    </div>
  </div>

  <!-- 电影列表 -->
  <div class="movie-list">
    <router-link
      v-for="movie in movies"
      :key="movie.id"
      :to="{ name: 'MovieDetails', params: { id: movie.id } }"
      class="movie-link"
    >
      <div class="movie-card">
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
    </router-link>
  </div>

  <button class="pagination-btn" @click="loadMoreMovies">加载更多电影</button>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
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

    const searchString = ref('');
    const recommendations = ref([]);
    const router = useRouter();

//    const fetchMovies = async (page = 1, perPage = 10) => {
//      try {
//        const response = await axios.get(`http://localhost:8081/movie/listInfo/${page}/${perPage}`);
//        if (response.data.success) {
//          movies.value = [...movies.value, ...response.data.data];
//        }
//      } catch (error) {
//        console.error('Error fetching movies:', error);
//      }
//    };
    const fetchMovies = async (page = 1, perPage = 10) => {
      try {
        const response = await axios.post('http://localhost:8081/movie/listInfo', {
          requestPage: page,
          movieNumberPerPage: perPage
        });
        if (response.data.success) {
          movies.value = [...movies.value, ...response.data.data];
        }
      } catch (error) {
        console.error('Error fetching movies:', error);
      }
    };



    const searchRecommendation = async () => {
      try {
        const response = await axios.post('http://localhost:8081/movie/searchRecommendation', {
          movieInfoString: searchString.value
        });
        if (response.data.success) {
          recommendations.value = response.data.data;
        }
      } catch (error) {
        console.error('Error fetching search recommendations:', error);
      }
    };


    const loadMoreMovies = () => {
      currentPage++;
      fetchMovies(currentPage, perPage);
    };

    const navigateToMovie = (id) => {
      router.push({ name: 'MovieDetails', params: { id: id } });
    };

    onMounted(() => {
      fetchMovies(currentPage, perPage);
    });

    return {
      movies,
      loadMoreMovies,
      searchString,
      searchRecommendation,
      recommendations,
      navigateToMovie
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
/* Search Bar */
.search-bar {
  max-width: 800px;
  width: 100%;
  padding: 20px 30px;
  margin: 0 auto 100px;  /* 空出300px */
  border-radius: 4px;
  position: relative;
}

.search-input {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;  /* 灰色边框 */
}

.dropdown {
  position: absolute;
  width: 100%;
  top: 100%;
  left: 0;
  background: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  z-index: 9999;
}

.dropdown-item {
  padding: 10px;
  cursor: pointer;
  text-align: left;  /* 让文字居左对齐 */
}

.dropdown-item:hover {
  background-color: #f2f2f2;
}

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
  .movie-link {
    text-decoration: none; /* 取消下划线 */
    color: inherit;
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
