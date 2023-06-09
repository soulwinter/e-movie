<template>
    <div class="movie-details-container">
        <div v-if="movieDetails">
            <div v-if="!isEditable">
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
                        class="tag genre-tag"
                        @click="deleteGenre(index)"
                    >
                        {{ genre }}<span class="close-icon">×</span>
                    </span>
                    <button @click="addGenre()" class="pagination-btn">添加类型</button>
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
                        class="tag keyword-tag"
                        @click="deleteKeyword(index)"
                    >
                        {{ keyword }}<span class="close-icon">×</span>
                    </span>
                    <button @click="addKeyword()" class="pagination-btn">添加标签</button>
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

    async deleteGenre(index) {
      try {
        const response = await axios.get(`http://localhost:8081/movie/deleteGenre`, {
          params: {
            id: this.$route.params.id,
            Genre: this.movieDetails.genre[index]
          }
        });
        if (response.data.success) {
          this.movieDetails.genre.splice(index, 1);
        }
      } catch (error) {
        console.error(error);
      }
    },
    async addGenre() {
      // 假设用户输入的值为 newValue
      const newValue = prompt('请输入新的类型');
      try {
        const response = await axios.get(`http://localhost:8081/movie/addGenre`, {
          params: {
            id: this.$route.params.id,
            Genre: newValue
          }
        });
        if (response.data.success) {
          this.movieDetails.genre.push(newValue);
        }
      } catch (error) {
        console.error(error);
      }
    },
    async deleteKeyword(index) {
      try {
        const response = await axios.get(`http://localhost:8081/movie/deleteKeyWord`, {
          params: {
            id: this.$route.params.id,
            KeyWord: this.movieDetails.keyword[index]
          }
        });
        if (response.data.success) {
          this.movieDetails.keyword.splice(index, 1);
        }
      } catch (error) {
        console.error(error);
      }
    },
    async addKeyword() {
      // 假设用户输入的值为 newValue
      const newValue = prompt('请输入新的关键词');
      try {
        const response = await axios.get(`http://localhost:8081/movie/addKeyWord`, {
          params: {
            id: this.$route.params.id,
            KeyWord: newValue
          }
        });
        if (response.data.success) {
          this.movieDetails.keyword.push(newValue);
        }
      } catch (error) {
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
/* 此处仅列出与新需求相关的部分样式 */

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

.pagination-btn {
  background-color: #4285F4;
  color: #fff;
  border: none;
  border-radius: 10px;
  /* padding: 10px 20px; */
  font-size: 13px;
  cursor: pointer;
  /* margin-top: 20px; */
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
  padding: 5px 10px;
  font-size: 10px;
  cursor: pointer;
  margin-top: 20px;
  padding-left: 10px;
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

.tag.genre-tag,
.tag.keyword-tag {
  animation: shake 0.82s cubic-bezier(.36,.07,.19,.97) both;
  transform: translate3d(0, 0, 0);
  backface-visibility: hidden;
  perspective: 1000px;
  cursor: pointer;
}

.close-icon {
  display: inline-block;
  padding-left: 8px;
}

.input {
        flex-grow: 1;
        background: transparent;
        border: none;
        border-bottom: 3px solid #272343;
        font-size: 20px;
        outline: none;
        margin: 10px 0;
        display: block;
    }
    
    .flex-container {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 10px;
    }

    .modern-switch {
        position: relative;
        display: inline-block;
        width: 60px;
        height: 34px;
    }

    .modern-switch input { 
        opacity: 0;
        width: 0;
        height: 0;
    }

    .modern-switch input:checked + .slider {
        background-color: #2196F3;
    }

    .modern-switch input:focus + .slider {
        box-shadow: 0 0 1px #2196F3;
    }

    .modern-switch input:checked + .slider:before {
        transform: translateX(26px);
    }

    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        transition: .4s;
    }

    .slider:before {
        position: absolute;
        content: "";
        height: 26px;
        width: 26px;
        left: 4px;
        bottom: 4px;
        background-color: white;
        transition: .4s;
    }

@keyframes shake {
  10%, 90% {
    transform: translate3d(-1px, 0, 0);
  }
  
  20%, 80% {
    transform: translate3d(2px, 0, 0);
  }

  30%, 50%, 70% {
    transform: translate3d(-4px, 0, 0);
  }

  40%, 60% {
    transform: translate3d(4px, 0, 0);
  }
}
</style>