<script setup>
import { ref } from 'vue'
import { login } from '@/api/user'

const username = ref('')
const password = ref('')
const error = ref('')

const handleLogin = async () => {
  try {
    const res = await login(username.value, password.value)
    alert('登录成功')
    // 可以把 res.data 存到 localStorage 作为用户信息
  } catch (err) {
    error.value = err.response?.data?.message || '登录失败'
  }
}
</script>

<template>
  <div class="login">
    <h2>登录</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="username" placeholder="用户名" required />
      <input v-model="password" placeholder="密码" type="password" required />
      <button type="submit">登录</button>
    </form>
    <p v-if="error">{{ error }}</p>
  </div>
</template>

<style scoped>
.login {
  max-width: 300px;
  margin: auto;
}
</style>