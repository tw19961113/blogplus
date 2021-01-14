import axios from 'axios'
import router from '@/router'

// 创建axios实例
const service = axios.create({
  timeout: 30000 // 请求超时时间
})

// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res instanceof ArrayBuffer) {
      return Promise.reject(response)
    }
    if (res.status === 401) {
      // token无效，跳转到登录页，并且清除session里面的token
      localStorage.removeItem('token').then(() => {
        router.replace({
          name: 'login'
        })
      })
    } else {
      return response
    }
  },
  error => {
    return Promise.reject(error)
  }
)
export default service
// 添加请求拦截器，在请求头中加token
service.interceptors.request.use(
  config => {
    if (localStorage.getItem('token')) {
      config.headers.Authorization = localStorage.getItem('token')
    }
    return config
  },
  error => {
    return Promise.reject(error)
  })
