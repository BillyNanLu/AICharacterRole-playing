<template>
  <div class="register">
    <el-card class="box-card">
      <h2>注册</h2>
      <el-form :model="form" @submit.prevent="handleRegister" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
          <el-button type="text" @click="toLogin">去登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { registerUser } from '@/api/user'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = reactive({
  username: '',
  email: '',
  password: ''
})

const handleRegister = async () => {
  try {
    await registerUser(form)
    ElMessage.success('注册成功')
    router.push('/login')
  } catch (err) {
    ElMessage.error(err.response?.data?.message || '注册失败')
  }
}

const toLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.register {
  width: 400px;
  margin: 100px auto;
}
</style>