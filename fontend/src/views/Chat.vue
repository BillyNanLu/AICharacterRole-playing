<template>
  <div class="chat-container">
    <el-card class="chat-card">
      <h2>AI 角色扮演对话</h2>
      <!-- 消息列表 -->
      <div class="messages">
        <div v-for="msg in messages" :key="msg.id" class="message-item">
          <strong>{{ msg.sender }}：</strong> {{ msg.content }}
        </div>
      </div>

      <!-- 输入框 -->
      <div class="input-area">
        <el-input
            v-model="content"
            type="textarea"
            placeholder="输入消息..."
            rows="3"
            class="input"
        />
        <div class="btn">
          <el-button type="primary" @click="sendMessage">发送</el-button>
          <el-button type="success" @click="loadMessages">刷新</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

// 这里先固定一个会话ID，实际你可以通过路由传参获取
const conversationId = 1
const messages = ref([])
const content = ref('')

const loadMessages = async () => {
  try {
    const res = await axios.get(`http://localhost:8080/api/messages/${conversationId}`)
    messages.value = res.data
  } catch (err) {
    ElMessage.error('加载消息失败')
  }
}

const sendMessage = async () => {
  if (!content.value) {
    ElMessage.warning('不能发送空消息')
    return
  }
  try {
    await axios.post('http://localhost:8080/api/messages', {
      conversation: { id: conversationId },
      sender: localStorage.getItem('username') || '用户',
      content: content.value
    })
    content.value = ''
    await loadMessages()
  } catch (err) {
    ElMessage.error('发送失败')
  }
}

onMounted(() => {
  loadMessages()
})
</script>

<style scoped>
.chat-container {
  width: 600px;
  margin: 50px auto;
}
.chat-card {
  padding: 20px;
}
.messages {
  height: 300px;
  overflow-y: auto;
  border: 1px solid #ebeef5;
  padding: 10px;
  margin-bottom: 20px;
}
.message-item {
  padding: 5px 0;
  border-bottom: 1px solid #f2f2f2;
}
.input-area {
  display: flex;
  flex-direction: column;
}
.input {
  margin-bottom: 10px;
}
.btn {
  text-align: right;
}
</style>