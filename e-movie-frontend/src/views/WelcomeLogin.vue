<template>
  <div class="container">
    <div class="welcome-login">
      <h1>欢迎来到电影世界</h1>
      <!-- 切换登录和注册表单 -->
      <div class="toggle">
        <button @click="showLogin = true" :class="{ active: showLogin }">登录</button>
        <button @click="showLogin = false" :class="{ active: !showLogin }">注册</button>
      </div>
      
      <!-- 登录表单 -->
      <form v-if="showLogin" @submit.prevent="login" class="form">
        <div class="input-group">
          <label for="login-telephone">手机号码</label>
          <input type="tel" id="login-telephone" v-model="loginData.telephone" required />
        </div>
        <div class="input-group">
          <label for="login-password">密码</label>
          <input type="password" id="login-password" v-model="loginData.password" pattern="^1[3-9]\d{9}$" required />
        </div>
        <button type="submit" class="btn btn-primary">登录</button>
      </form>
      
      <!-- 注册表单 -->
      <form v-else @submit.prevent="register" class="form">
        <div class="input-group">
          <label for="register-username">用户名</label>
          <input type="text" id="register-username" v-model="registerData.username" required />
        </div>
        <div class="input-group">
          <label for="register-password">密码</label>
          <input type="password" id="register-password" v-model="registerData.password" required />
        </div>
        <div class="input-group"><label for="register-telephone">手机号码</label>
									<input type="tel" id="register-telephone" v-model="registerData.telephone" pattern="^1[3-9]\d{9}$" required />

        </div>
        <button type="submit" class="btn btn-primary">注册</button>
      </form>
    </div>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      showLogin: true,
      loginData: {
        telephone: '',
        password: '',
      },
      registerData: {
        username: '',
        password: '',
        telephone: '',
      },
    };
  },
  methods: {
    // 登录函数
    async login() {
      try {
        const response = await axios.post('http://localhost:8081/user/login', this.loginData);
        if (response.data.success) {
          // 登录成功，在此处处理数据
          console.log('登录成功');
        } else {
          // 处理登录失败
          console.error(response.data.errorMsg);
        }
      } catch (error) {
        console.error('错误：', error);
      }
    },
    // 注册函数
    async register() {
      try {
        const response = await axios.post('http://localhost:8081/user/register', this.registerData);
        if (response.data.success) {
          // 注册成功，在此处处理数据
          console.log('注册成功');
        } else {
          // 处理注册失败
          console.error(response.data.errorMsg);
        }
      } catch (error) {
        console.error('错误：', error);
      }
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.welcome-login {
  background-color: #fff;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.toggle {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.toggle button {
  flex: 1;
  background-color: transparent;
  border: none;
  border-bottom: 2px solid transparent;
  cursor: pointer;
  font-size: 1rem;
  padding: 0.5rem 0;
}

.toggle button.active {
  border-bottom: 2px solid #2196f3;
}

.form {
  display: flex;
  flex-direction: column;
}

.input-group {
  margin-bottom: 1rem;
}

.input-group label {
  display: block;
  margin-bottom: 0.25rem;
}

.input-group input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.btn {
  background-color: #2196f3;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 0.5rem;
  cursor: pointer;
}

.btn:hover {
  background-color: #0d8bf2;
}

.btn:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(33, 150, 243, 0.4);
}

.btn:active {
  background-color: #0d8bf2;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* ADDITIONAL CSS FOR ANIMATION */
.toggle {
  transition: all 0.3s ease-in-out;
}

.toggle button {
  transition: all 0.3s ease-in-out;
}

.toggle button.active {
  transform: translateY(-5px);
}

.welcome-login h1 {
  font-size: 3rem;
  margin-bottom: 2rem;
  text-align: center;
  color: #2196f3;
}
</style>
