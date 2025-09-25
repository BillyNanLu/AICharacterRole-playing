<template>
  <div class="login">
    <el-card class="box-card">
      <h2>登录</h2>
      <el-form :model="form" @submit.prevent="handleLogin" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
          <el-button type="text" @click="toRegister">去注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { loginUser } from '@/api/user'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  try {
    const res = await loginUser(form)
    localStorage.setItem('username', res.data.username)
    ElMessage.success('登录成功')
    router.push('/home')
  } catch (err) {
    ElMessage.error(err.response?.data?.message || '登录失败')
  }
}

const toRegister = () => {
  router.push('/register')
}
</script>

<style scoped>
.login {
  width: 400px;
  margin: 100px auto;
}
</style>