import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080/api'
})

// 请求拦截器：自动加 token
instance.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
})

export default instance