<template>
    <div class="movie-details-container">
      <div v-if="movieDetails">
        <div>
          <input style="font-size: 40px; font-weight: bold;" class="input" v-model="editableTitle" placeholder="电影名"
            type="text">
          <textarea style="font-size: 20px; width: 100%;" class="input"
            v-model="editableOverview" placeholder="电影简介" ></textarea>
          <div class="info-tags">
            <span :class="{ 'tag': true, 'adult-tag': (editableAdult === 1) }">
                <input type="checkbox" id="adult-checkbox" v-model="editableAdult">
                <label for="adult-checkbox">{{ editableAdult ? '限制级' : '非限制级' }}</label>
            </span>
            <span class="tag">
              预算: $
              <input v-model="editableBudget" style="width:100px;" class="input" type="number">
            </span>
            <span class="tag">
              流行度:
              <input v-model="editablePopularity" style="width:100px;" class="input" type="number">
            </span>
            <span class="tag">
              票房: $
              <input v-model="editableRevenue" style="width:100px;" class="input" type="number">
            </span>
            <span class="tag">
              时长:
              <input v-model="editableRuntime" style="width:60px;" class="input" type="number">
              分钟
            </span>
            <span class="tag">
    
              <div>
                <input type="checkbox" id="status-checkbox" v-model="editableStatus">
                <label for="status-checkbox">{{ editableStatus ? '已完成' : '拍摄中' }}</label>
              </div>
            </span>
          </div>
  
          <button @click="updateMovieDetails" class="pagination-btn">
            新建
          </button>
        </div>
  
      </div>
    </div>
  </template>
  <script>
  import axios from 'axios';
  import authMixin from '../authMixin.js'
  
  export default {
    name: "AdminCreateNewMovie",
    data() {
      return {
        movieDetails: null,
        isEditing: false,
        editableTitle: '',
        editableOverview: '',
        editableRuntime: -1,
        editableRevenue: -1,
        editableBudget: -1,
        editablePopularity: -1,
        editableAdult: false,
        editableStatus: false,
      };
    },
    mixins: [authMixin],
    methods: {
      async initNewMovie() {
        this.movieDetails= { basic: {} };
      },

      async updateMovieDetails() {
  
          // 转换数据
          this.movieDetails.basic.adult = this.editableAdult ? 1 : 0;
          this.movieDetails.basic.status = this.editableStatus ? 0 : 1;
          this.movieDetails.basic.title = this.editableTitle;
          this.movieDetails.basic.overview = this.editableOverview;
          this.movieDetails.basic.runtime = this.editableRuntime;
          this.movieDetails.basic.revenue = this.editableRevenue;
          this.movieDetails.basic.budget = this.editableBudget;
          this.movieDetails.basic.popularity = this.editablePopularity;
  
          // 去除不符合条件的属性
          if (this.movieDetails.basic.runtime <= 0) delete this.movieDetails.basic.runtime;
          if (this.movieDetails.basic.revenue <= 0) delete this.movieDetails.basic.revenue;
          if (this.movieDetails.basic.budget <= 0) delete this.movieDetails.basic.budget;
  
          // 发送请求
          try {
            const response = await axios.post('http://localhost:8081/movie/newMovie', this.movieDetails.basic);
  
            // 检查响应
            if (response.data.success) {
              alert('电影信息更新成功！');
            } else {
              alert('电影信息更新失败，请稍后再试！');
            }
          } catch (error) {
            console.error('电影信息更新时发生错误:', error);
            alert('电影信息更新失败，可能是网络问题或服务器错误。');
          }
        
  
      },
  
      
    },
    mounted() {
      this.initNewMovie();
    }
  };
  </script>
  <style scoped>
  /* 此处仅列出与新需求相关的部分样式 */
  
  .movie-details-container {
    text-align: left;
    padding: 0 40px;
    /* 左右两侧留出40px的间隙 */
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
    animation: shake 0.82s cubic-bezier(.36, .07, .19, .97) both;
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
    /* font-size: 20px; */
    outline: none;
    margin: 10px 0;
    /* display: block; */
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
  
  .modern-switch input:checked+.slider {
    background-color: #2196F3;
  }
  
  .modern-switch input:focus+.slider {
    box-shadow: 0 0 1px #2196F3;
  }
  
  .modern-switch input:checked+.slider:before {
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
  
    10%,
    90% {
      transform: translate3d(-1px, 0, 0);
    }
  
    20%,
    80% {
      transform: translate3d(2px, 0, 0);
    }
  
    30%,
    50%,
    70% {
      transform: translate3d(-4px, 0, 0);
    }
  
    40%,
    60% {
      transform: translate3d(4px, 0, 0);
    }
  }
  </style>