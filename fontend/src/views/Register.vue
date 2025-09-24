<script setup>
import { reactive, ref } from 'vue'
import { register } from '@/api/user'

const form = reactive({
  username: '',
  email: '',
  password: ''
})
const error = ref('')

const handleRegister = async () => {
  try {
    await register(form)
    alert('注册成功')
    // 可以跳转到登录页
  } catch (err) {
    error.value = err.response?.data?.message || '注册失败'
  }
}
</script>

<template>
  <div class="register">
    <h2>注册</h2>
    <form @submit.prevent="handleRegister">
      <input v-model="form.username" placeholder="用户名" required />
      <input v-model="form.email" placeholder="邮箱" type="email" required />
      <input v-model="form.password" placeholder="密码" type="password" required />
      <button type="submit">注册</button>
    </form>
    <p v-if="error">{{ error }}</p>
  </div>
</template>

<style scoped>
.register {
  max-width: 300px;
  margin: auto;
}
</style>