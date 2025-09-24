import axios from 'axios'

const API_BASE = 'http://localhost:8080/api/users'

export function registerUser(data) {
    return axios.post(`${API_BASE}/register`, data)
}

export function loginUser(data) {
    return axios.post(`${API_BASE}/login`, data)
}