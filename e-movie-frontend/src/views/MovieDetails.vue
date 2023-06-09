<template>
    <div class="movie-details-container">
      <div v-if="movieDetails">
        <h1 style="font-size: 40px; font-weight: bold;">
          {{ movieDetails.basic.title }}
        </h1>
        <p style="font-size: 20px;">
          {{ movieDetails.basic.overview }}
        </p>
        <div class="info-tags">
          <span :class="{'tag': true, 'adult-tag': movieDetails.basic.adult === 1}">
            {{ movieDetails.basic.adult === 1 ? 'Adult' : '非成人分级' }}
          </span>
          <span v-if="movieDetails.basic.budget !== 0" class="tag">
            预算: ${{ movieDetails.basic.budget }}
          </span>
          <span class="tag">流行度: {{ movieDetails.basic.popularity }}</span>

          
          <span v-if="movieDetails.basic.revenue !== 0" class="tag">
            票房: ${{ movieDetails.basic.revenue }}
          </span>
          <span class="tag">时长: {{ movieDetails.basic.runtime }} 分钟</span>
          <span class="tag">当前状态: {{ movieDetails.basic.status === 0 ? '已完成' : '拍摄中'}}</span>
        </div>
        <div>
          <h3>类型</h3>
          <span
            v-for="(genre, index) in movieDetails.genre"
            :key="index"
            class="tag"
          >
            {{ genre }}
          </span>
        </div>
        <div>
          <h3>国家</h3>
          <span
            v-for="(country, index) in movieDetails.country"
            :key="index"
            class="tag"
          >
            {{ country }}
          </span>
        </div>
        <div>
          <h3>标签</h3>
          <span
            v-for="(keyword, index) in movieDetails.keyword"
            :key="index"
            class="tag"
          >
            {{ keyword }}
          </span>
        </div>
      </div>
      <div>

    <div v-if="movieDetails && movieDetails.similarMovies && movieDetails.similarMovies.length">
      <h2 style="margin-top: 30px;">这部电影的观众也喜欢</h2>
      <div class="movie-list">
          <router-link
            v-for="(similarMovie, index) in movieDetails.similarMovies"
            :key="index"
            :to="{ name: 'MovieDetails', params: { id: similarMovie.id } }"
            class="movie-link"
          >
            <div class="movie-card">
              <h1 class="movie-title">{{ similarMovie.title }}</h1>
              <p class="movie-overview">{{ similarMovie.overview }}</p>
              <div class="info-container">
                <div class="info-badge" :class="{ 'adult-badge': similarMovie.adult, 'non-adult-badge': !similarMovie.adult }">
                  {{ similarMovie.adult ? "成人分级" : "非成人分级" }}
                </div>
                <div class="info-badge">{{ similarMovie.popularity }} 流行度</div>
                <div class="info-badge" v-if="similarMovie.budget !== 0">${{ similarMovie.budget }} 预算</div>
                <div class="info-badge" v-if="similarMovie.revenue !== 0">${{ similarMovie.revenue }} 票房收入</div>
              </div>
            </div>
          </router-link>
        </div>
    </div>
  </div>
    </div>
  </template>
  
  

<script>
import axios from 'axios';
import authMixin from '../authMixin.js'

export default {
  name: "MovieDetails",
  data() {
    return {
      movieDetails: null,
    };
  },
  mixins: [authMixin],
  watch: {
    '$route'() {
      this.fetchMovieDetails();
    },
  },
  methods: {
    async fetchMovieDetails() {
      const id = this.$route.params.id;
      try {
        const response = await axios.get(`http://localhost:8081/movie/detailInfo/${id}`);
        const data = response.data;
        if (data.success) {
          this.movieDetails = data.data;
        }
      } catch(error) {
        console.error(error);
      }
    },
},
  mounted() {
  this.fetchMovieDetails();
}
};
</script>
<style scoped>

.movie-details-container {
  text-align: left;
  padding: 0 40px; /* 左右两侧留出40px的间隙 */
}



.tag {
  display: inline-block;
  background-color: #f2f2f2;
  border-radius: 12px;
  padding: 4px 8px;
  margin: 4px;
  font-size: 14px;
}

.adult-tag {
  background-color: red;
}

.info-tags {
  margin-bottom: 20px;
}

.similar-movies-container {
    display: flex;
    flex-wrap: wrap;
    gap: 30px;
    justify-content: space-between;
    margin-top: 20px;
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
/*  min-width: 200px;*/
}

.movie-link {
  text-decoration: none;
  /* 取消下划线 */
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

.centered-title {
  text-align: center;
  font-weight: bold;
  font-size: 24px;
  color: gray;
}

.flex-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.movie-list {
  column-count: 3;
  column-gap: 70px;
  max-width: 1280px;
  margin: 0 auto;
}

</style>
