import Cookies from 'js-cookie'

// 保存 token 到 cookie
export const setToken = (token) => {
  Cookies.set('Token', token)
}

// 从 cookie 获取 token
export const getToken = () => {
  const token = Cookies.get('Token')
  if (token) return token
  else return false
}
