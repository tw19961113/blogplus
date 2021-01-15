import axios from 'axios'
import router from '@/router'

// 创建axios实例
const service = axios.create({
  timeout: 30000 // 请求超时时间
})

// response拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res instanceof ArrayBuffer) {
      return Promise.reject(response)
    } else {
      return response
    }
  },
  error => {
    if (error) {
      // token无效，跳转到登录页，并且清除session里面的token
      localStorage.removeItem('token').then(() => {
        router.replace({
          name: 'login'
        })
      })
    }
    return Promise.reject(error)
  }
)
export default service
// 添加请求拦截器，在请求头中加token
service.interceptors.request.use(
  config => {
    if (sessionStorage.getItem('token')) {
      config.headers.Authorization = sessionStorage.getItem('token')
    }
    return config
  },
  error => {
    return Promise.reject(error)
  })
