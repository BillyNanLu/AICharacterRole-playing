<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const roles = ref([])
const router = useRouter()

onMounted(async () => {
  try {
    const res = await axios.get('/api/roles')
    roles.value = res.data
  } catch (err) {
    ElMessage.error('加载角色失败')
  }
})

const selectRole = (role) => {
  localStorage.setItem('roleId', role.id)
  router.push('/chat')
}
</script>

<template>
  <div>
    <h2>选择角色</h2>
    <el-row :gutter="20">
      <el-col v-for="role in roles" :key="role.id" :span="6">
        <el-card @click="selectRole(role)">
          <img :src="role.avatar" alt="" style="width:100px;height:100px;" />
          <div>{{ role.name }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>

</style>