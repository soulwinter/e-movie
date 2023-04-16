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
    </div>
  </template>
  
  

<script>
export default {
  name: "MovieDetails",
  data() {
    return {
      movieDetails: null,
    };
  },
  async mounted() {
    const id = this.$route.params.id;
    const response = await fetch(`http://localhost:8081/movie/detailInfo/${id}`);
    const data = await response.json();

    if (data.success) {
      this.movieDetails = data.data;
    }
  },
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
</style>
