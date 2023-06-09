<template>
  <HeadComponent />
  <div class="container">

    <div class="welcome-login">
      <h1>欢迎来到电影世界</h1>
      <!-- 切换登录和注册表单 -->
      <div class="toggle">
        <button @click="showLogin = 1" :class="{ active: showLogin === 1 }">密码登录</button>
        <button @click="showLogin = 2" :class="{ active: showLogin === 2 }">验证码登录</button>
        <button @click="showLogin = 3" :class="{ active: showLogin === 3 }">注册</button>
      </div>

      <!-- 密码登录表单 -->
      <form v-if="showLogin === 1" @submit.prevent="login" class="form">
        <div class="input-group">
          <label style="text-align: left;" for="login-telephone">手机号码</label>
          <input type="tel" id="login-telephone" v-model="loginData.telephone" pattern="^1[3-9]\d{9}$"
            title="请输入有效的中国大陆手机号码" required />
        </div>
        <div class="input-group">
          <label style="text-align: left;" for="login-password">密码</label>
          <input type="password" id="login-password" v-model="loginData.password" required />
        </div>
        <button type="submit" class="btn btn-primary">登录</button>
      </form>

      <!-- 验证码登录表单 -->
      <form v-if="showLogin === 2" @submit.prevent="loginWithCode" class="form">
        <div class="input-group">
          <label style="text-align: left;" for="loginWithCode-telephone">手机号码</label>
          <div style="display: flex; justify-content: space-between;">
            <input type="tel" id="loginWithCode-telephone" v-model="loginWithCodeData.telephone" pattern="^1[3-9]\d{9}$"
              title="请输入有效的中国大陆手机号码" style="width: 80%; margin-right: 5px;" required />
            <button type="button" class="btn btn-primary" style="width: 35%; margin-left: 10px;"
              @click="getCode('login_mode')" :disabled="countDown > 0">{{ countDown > 0 ? countDown + '秒后重新获取' : '获取验证码'
              }}</button>
          </div>
        </div>
        <div class="input-group">
          <label style="text-align: left;" for="loginWithCode-code">验证码</label>
          <input type="code" id="loginWithCode-code" v-model="loginWithCodeData.code" required />
        </div>
        <button type="submit" class="btn btn-primary">登录</button>
      </form>


      <!-- 注册表单 -->
      <form v-if="showLogin === 3" @submit.prevent="register" class="form">
        <div class="input-group">
          <label style="text-align: left;" for="register-username">用户名</label>
          <input type="text" id="register-username" v-model="registerData.username" required />
        </div>
        <div class="input-group">
          <label style="text-align: left;" for="register-password">密码</label>
          <input type="password" id="register-password" v-model="registerData.password" required />
        </div>
        <div class="input-group">
          <label style="text-align: left;" for="register-telephone">手机号码</label>
          <div style="display: flex; justify-content: space-between;">
            <input type="tel" id="register-telephone" v-model="registerData.telephone" pattern="^1[3-9]\d{9}$"
              title="请输入有效的中国大陆手机号码" style="width: 80%; margin-right: 5px;" required />
            <button type="button" class="btn btn-primary" style="width: 35%; margin-left: 10px;"
              @click="getCode('register_mode')" :disabled="countDown > 0">{{ countDown > 0 ? countDown + '秒后重新获取' :
                '获取验证码' }}</button>
          </div>
        </div>
        <div class="input-group">
          <label style="text-align: left;" for="register-password">验证码</label>
          <input type="code" id="register-code" v-model="registerData.code" required />
        </div>
        <button type="submit" class="btn btn-primary">注册</button>
      </form>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import HeadComponent from '@/components/HeadComponent.vue';
import { setToken } from '../token.js'
import authMixin from '../authMixin.js'
import {setUserType, getUserType, deleteUserType} from '../user.js'

export default {

  components: {
    HeadComponent,
  },
  data() {
    return {
      showLogin: 1,
      loginData: {
        telephone: '',
        password: '',
      },
      registerData: {
        username: '',
        password: '',
        telephone: '',
        code: '',
      },
      loginWithCodeData: {
        telephone: '',
        code: '',
      },
      countDown: 0,
    };
  },
  mixins: [authMixin],
  mounted() {
   deleteUserType();
  },
  methods: {
    // 密码登录函数
    async login() {
      try {
        let formData = new FormData();
        formData.append('telephone', this.loginData.telephone);
        formData.append('password', this.loginData.password);
        const response = await axios.post('http://localhost:8081/user/loginWithPassword', formData);
        if (response.data.success) {
          // 登录成功，在此处处理数据
          alert('登录成功');
          // 保存 token
          this.$store.dispatch('saveToken', response.data.data.token)
            .then(() => {
              axios.defaults.headers.common['Authorization'] = response.data.data.token;
              setToken(response.data.data.token);  // 保存 token 到 cookie
            });

          // let token = this.$store.getters.getToken;
          // console.log("Authorization header with token: ", token); // 打印即将设置的 token
          if (response.data.data.type === 1) {
            setUserType(1);
            console.log(getUserType());
            this.$router.push('/admin/home');
          } else {
            setUserType(0);
            this.$router.push('/');
          } 

        } else {
          // 处理登录失败
          alert(response.data.errorMsg);
        }
      } catch (error) {
        if (error.response) {
          if (error.response.status === 404) {
            alert('无法连接到后端');
          } else if (error.response.status === 403) {
            alert('无权限访问');
          } else if (error.response.status === 500) {
            alert('服务器错误');
          }
        } else {
          alert('错误：', error);
        }
      }
    },
    // 验证码登录函数
    async loginWithCode() {
      try {
        let formData = new FormData();
        formData.append('telephone', this.loginWithCodeData.telephone);
        formData.append('code', this.loginWithCodeData.code);
        const response = await axios.post('http://localhost:8081/user/loginWithCode', formData);
        if (response.data.success) {
          // 登录成功，在此处处理数据
          alert('登录成功');
          // 保存 token
          this.$store.dispatch('saveToken', response.data.data.token)
            .then(() => {
              axios.defaults.headers.common['Authorization'] = response.data.data.token;
            });
          if (response.data.data.type === 1) {
            setUserType(1);
            this.$router.push('/admin/home');
          } else {
            setUserType(0);
            this.$router.push('/');
          }
        } else {
          // 处理登录失败
          alert(response.data.errorMsg);
        }
      } catch (error) {
        if (error.response) {
          if (error.response.status === 404) {
            alert('无法连接到后端');
          } else if (error.response.status === 403) {
            alert('无权限访问');
          } else if (error.response.status === 500) {
            alert('服务器错误');
          }
        } else {
          alert('错误：', error);
        }
      }
    },
    // 注册函数
    async register() {
      try {
        let formData = new FormData();
        formData.append('username', this.registerData.username);
        formData.append('password', this.registerData.password);
        formData.append('telephone', this.registerData.telephone);
        formData.append('code', this.registerData.code);
        const response = await axios.post('http://localhost:8081/user/register', formData);
        if (response.data.success) {
          // 注册成功，在此处处理数据
          alert('注册成功');
        } else {
          // 处理注册失败
          alert(response.data.errorMsg);
        }
      } catch (error) {
        if (error.response) {
          if (error.response.status === 404) {
            alert('无法连接到后端');
          } else if (error.response.status === 403) {
            alert('无权限访问');
          } else if (error.response.status === 500) {
            alert('服务器错误');
          }
        } else {
          alert('错误：', error);
        }
      }
    },
    // 获取验证码函数
    async getCode(modeName) {
      try {
        let formData = new FormData();
        formData.append('telephone', modeName === 'login_mode' ? this.loginWithCodeData.telephone : this.registerData.telephone);
        formData.append('mode', modeName);
        const response = await axios.post('http://localhost:8081/user/code', formData);
        if (response.data.success) {
          // 获取验证码成功，在此处处理数据
          alert('获取验证码成功');
          this.countDown = 60;
          const timer = setInterval(() => {
            if (this.countDown > 0) {
              this.countDown--;
            } else {
              clearInterval(timer);
            }
          }, 1000);
        } else {
          // 处理获取验证码失败
          alert(response.data.errorMsg);
        }
      } catch (error) {
        if (error.response) {
          if (error.response.status === 404) {
            alert('无法连接到后端');
          } else if (error.response.status === 403) {
            alert('无权限访问');
          } else if (error.response.status === 500) {
            alert('服务器错误');
          }
        } else {
          alert('错误：', error);
        }
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