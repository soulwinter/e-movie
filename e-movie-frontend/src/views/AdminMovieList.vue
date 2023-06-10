<template>
  <HeadComponent />

  <!-- 搜索栏 -->
  <div class="search-bar">
    <input class="search-input" v-model="searchString" @input="searchRecommendation" type="text" placeholder="搜索电影" />
    <div class="dropdown" v-if="recommendations.length" v-click-outside="closeDropdown">
      <div v-for="item in recommendations" :key="item.id" class="dropdown-item" @click="navigateToMovie(item.id)">
        {{ item.title }}
      </div>
    </div>
  </div>

  <!-- Filter form -->
  <el-form class="filter-form">
    <!-- Row for the sliders -->
    <el-row :gutter="20">
      <!-- Column for the rating slider -->
      <el-col :span="12">
        <el-form-item label="评分范围" style="width: 100%;">
          <el-slider v-model="voteRange" :min="filterItems.voteAverage[0]" :max="filterItems.voteAverage[1]" range
            show-stops :disabled="isRatingUnlimited" />
          <el-checkbox v-model="isRatingUnlimited">评分不限</el-checkbox>
        </el-form-item>
      </el-col>

      <!-- Column for the release year slider -->
      <el-col :span="12">
        <el-form-item label="发布年份" style="width: 100%;">
          <el-slider v-model="releaseRange" :min="Number(filterItems.releaseDate[0])"
            :max="Number(filterItems.releaseDate[1])" :disabled="isDateUnlimited" />
          <el-checkbox v-model="isDateUnlimited">年份不限</el-checkbox>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- Other form items -->
    <el-form-item label="电影类型">
      <el-select v-model="selectedGenres" multiple placeholder="不限类型">
        <el-option v-for="genre in filterItems.genre" :key="genre" :label="genre" :value="genre">
        </el-option>
      </el-select>
    </el-form-item>

    <div class="flex-container">
      <el-form-item label="是否限制级">
        <el-checkbox v-model="isAdult">限制级</el-checkbox>
      </el-form-item>
    </div>
    <el-form-item>
      <el-button type="primary" @click="searchWithFilters">搜索</el-button>
      <el-button type="primary" @click="navigateToCreate()">创建新电影</el-button>
    </el-form-item>
  </el-form>


  <!-- 电影列表
    <div class="movie-list">
      <router-link v-for="movie in movies" :key="movie.id" :to="{ name: 'MovieDetails', params: { id: movie.id } }"
        class="movie-link">
        <div class="movie-card">
          <h1 class="movie-title">{{ movie.title }}</h1>
          <p class="movie-overview">{{ movie.overview }}</p>
          <div class="info-container">
            <div class="info-badge" :class="{ 'adult-badge': movie.adult, 'non-adult-badge': !movie.adult }">
              {{ movie.adult ? "成人分级" : "非成人分级" }}
            </div>
            <div class="info-badge">{{ movie.popularity }} 流行度</div>
            <div class="info-badge" v-if="movie.budget !== 0">${{ movie.budget }} 预算</div>
            <div class="info-badge" v-if="movie.revenue !== 0">${{ movie.revenue }} 票房收入</div>
          </div>
        </div>
      </router-link>
    </div> -->

  <table class="transport-table">
    <thead>
      <tr>
        <th>ID</th>
        <th>标题</th>
        <th>原标题</th>
        <th>语言</th>
        <th>摘要</th>
        <th>发布日期</th>
        <th>流行度</th>
        <th>预算</th>
        <th>票房</th>
        <th>时长</th>
        <th>状态</th>
        <th>投票平均分</th>
        <th>投票数</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item) in movies" :key="item.id">
        <td>{{ item.id }}</td>
        <td>{{ item.title ? (item.title.length > 30 ? item.title.substring(0, 30) + '...' : item.title) : '-' }}</td>
        <td>{{ item.originalTitle ? (item.originalTitle.length > 30 ? item.originalTitle.substring(0, 30) + '...' :
          item.originalTitle) : '-' }}</td>
        <td>{{ item.originalLanguage || '-' }}</td>
        <td>{{ item.overview ? (item.overview.length > 30 ? item.overview.substring(0, 30) + '...' : item.overview) : '-'
        }}</td>
        <td>{{ item.releaseDate || '-' }}</td>
        <td>{{ item.popularity || '-' }}</td>
        <td>{{ item.budget || '-' }}</td>
        <td>{{ item.revenue || '-' }}</td>
        <td>{{ item.runtime || '-' }}</td>
        <td>{{ item.status || '-' }}</td>
        <td>{{ item.voteAverage || '-' }}</td>
        <td>{{ item.voteCount || '-' }}</td>
        <td>
          <button class="start-transport" @click="navigateToMovie(item.id)">查看</button>
         
        </td>
        
      </tr>
    </tbody>
  </table>


  <button class="pagination-btn" @click="loadMoreMovies" v-if="!noMoreMovies">加载更多电影</button>
  <h1 class="centered-title" v-if="noMoreMovies">没有更多电影</h1>
</template>
  
<script>
import axios from 'axios';
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import HeadComponent from '@/components/HeadComponent.vue';
import { ElForm, ElFormItem, ElSlider, ElSelect, ElOption, ElCheckbox, ElButton } from 'element-plus';
import authMixin from '../authMixin.js'
import { getUserType } from '../user.js';


export default {
  name: 'MovieList',
  mixins: [authMixin],
  components: {
    HeadComponent,
    ElForm,
    ElFormItem,
    ElSlider,
    ElSelect,
    ElOption,
    ElCheckbox,
    ElButton
  },

  setup() {

    const movies = ref([]);
    const isRatingUnlimited = ref(true);
    const isDateUnlimited = ref(true);
    const noMoreMovies = ref(false);

    let currentPage = 1;
    const perPage = 10;

    const searchString = ref('');
    const recommendations = ref([]);
    const router = useRouter();

    // 筛选项
    const filterItems = ref({
      voteAverage: [0, 10],
      releaseDate: ['1931', '2016'],
      genre: [],
      adult: [false, true]
    });
    const voteRange = ref([0, 10]);
    const releaseRange = ref(2016);
    const selectedGenres = ref([]);
    const isAdult = ref(false);

    const filterOptions = reactive({
      voteAverageFrom: null,
      voteAverageTo: null,
      releaseDate: null,
      isAdult: null,
      genreList: [],
      movieInfoString: null,
    });

    const applyFilter = () => {
      filterOptions.voteAverageFrom = voteRange.value[0];
      filterOptions.voteAverageTo = voteRange.value[1];
      filterOptions.releaseDate = releaseRange.value;
      filterOptions.isAdult = isAdult.value;
      filterOptions.genreList = selectedGenres.value;
      filterOptions.movieInfoString = searchString.value;
      noMoreMovies.value = false;
      if (isDateUnlimited.value) {
        filterOptions.releaseDate = null;
      }
      if (isRatingUnlimited.value) {
        filterOptions.voteAverageFrom = null;
        filterOptions.voteAverageTo = null;
      }
    };


    const fetchMovies = async (page = 1, perPage = 10) => {
      if (!noMoreMovies.value) {
        try {
          const response = await axios.post('http://localhost:8081/movie/listInfo', {
            requestPage: page,
            movieNumberPerPage: perPage,
            ...filterOptions,
          });
          if (response.data.success) {
            if (response.data.data !== undefined) {
              movies.value = [...movies.value, ...response.data.data];
            } else {
              // 如果为空，则说明一个电影都没有
              console.log("no movies");
              noMoreMovies.value = true;
            }

            // TODO: 没有更多电影的逻辑暂时没有处理
          }
        } catch (error) {
          console.error('Error fetching movies:', error);
        }
      }

    };

    // 筛选
    const fetchFilterItems = async () => {
      try {
        const response = await axios.get('http://localhost:8081/movie/filterItem');
        if (response.data.success) {
          filterItems.value = response.data.data;
        }
      } catch (error) {
        console.error('Error fetching filter items:', error);
      }
    };

    // 搜索
    const searchWithFilters = async () => {
      applyFilter();
      try {
        const response = await axios.post('http://localhost:8081/movie/listInfo', {
          movieInfoString: searchString.value,
          requestPage: 1,
          movieNumberPerPage: 10,
          adult: filterOptions.isAdult,
          releaseDate: filterOptions.releaseDate,
          voteAverageFrom: filterOptions.voteAverageFrom,
          voteAverageTo: filterOptions.voteAverageTo,
          genreList: filterOptions.genreList
        });

        if (response.data.success) {
          if (response.data.data !== undefined) {
            movies.value = response.data.data;
          } else {
            // 如果为空，则说明一个电影都没有
            console.log("no movies");
            movies.value = [];
            noMoreMovies.value = true;
          }


        }
      } catch (error) {
        console.error('Error fetching movies with filters:', error);
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
      fetchMovies(currentPage, perPage, filterOptions);
    };

    const navigateToMovie = (id) => {
      router.push({ name: 'AdminMovieDetails', params: { id: id } });
    };
    
    const navigateToCreate = () => {
      router.push("/admin/create");
    }

    onMounted(() => {

      fetchMovies(currentPage, perPage);
      fetchFilterItems();
    });

    return {
      movies,
      loadMoreMovies,
      searchString,
      searchRecommendation,
      recommendations,
      navigateToMovie,
      filterItems,
      voteRange,
      releaseRange,
      selectedGenres,
      isAdult,
      searchWithFilters,
      applyFilter,
      noMoreMovies,
      isDateUnlimited,
      isRatingUnlimited,
      navigateToCreate
    };
  },
  mounted() {
    if (getUserType() != 1) {
      alert("您不是管理员，无法进入该页面。请检查您的权限。");
      const router = useRouter();
      router.push('/');
    }
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

    closeDropdown() {
      this.recommendations = [];
    },
  }
};
</script>
  
<style scoped>
.filter-form {
  max-width: 800px;
  width: 100%;
  /*  padding: 20px 30px;*/
  margin: 0 auto 20px;
  border-radius: 4px;
  position: relative;
}

/* Search Bar */
.search-bar {
  max-width: 800px;
  width: 100%;
  padding: 20px 30px;
  margin: 0 auto 100px;
  border-radius: 4px;
  position: relative;
}

.search-input {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  /* 灰色边框 */
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
  text-align: left;
  /* 让文字居左对齐 */
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

.transport-table {
  border-collapse: collapse;
  width: 100%;
}

.transport-table th,
.transport-table td {
  border: none;
  border-bottom: 3px solid #272343;
  text-align: center;
  padding: 8px;
  font-size: 18px;
}

.transport-table th {
  padding-bottom: 12px;
}

.bold {
  font-weight: bold;
}

.start-transport {
  background-color: #4285F4;
  border-radius: 5px;
  border: none;
  padding: 6px 12px;
  font-size: 18px;
  cursor: pointer;
  color: #ffffff;
}
</style>
  