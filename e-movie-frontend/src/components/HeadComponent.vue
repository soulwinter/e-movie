<template>
  <header class="header">
    <h1 class="title">e-movie Movie Information Retrieval System</h1>
    <router-link v-if="!isLoginPage" to="/login" class="logout-button">登出</router-link>
  </header>
</template>

  
<script>
export default {
  name: 'HeadComponent',
  data() {
    return {
      isLoginPage: false,
    };
  },
  created() {
    this.isLoginPage = this.$route.path === '/login';
  },
};
</script>
  
<style scoped>
.header {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #2196f3;
  height: 60px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.title {
  font-family: 'Roboto', sans-serif;
  font-size: 24px;
  font-weight: bold;
  color: #ffffff;
  margin: 0;
}

.logout-button {
  color: #ffffff;
  border: none;
  background: none;
  font-family: 'Roboto', sans-serif;
  font-size: 16px;
  cursor: pointer;
  margin-left: 10px;
  /* display: none; */
}
</style>
  