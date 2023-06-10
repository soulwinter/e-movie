import { getToken } from './token.js'
import axios from 'axios'

export default {
  created() {
    const token = getToken(); // 从 cookie 中获取 token
    if (token) {
      axios.defaults.headers.common['Authorization'] = token; // 设置默认头部
    } else {
      // 处理没有 token 的情况，比如重定向到登录页面
      this.$router.push('/login')
    }
  }
}
