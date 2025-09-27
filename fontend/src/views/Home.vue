<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

// 这里你可以从路由参数或者 localStorage 获取当前会话 ID
// 先写死一个conversationId做测试
const conversationId = 1

const role = ref('')
const skill = ref('')
const recording = ref(false)
const mediaRecorder = ref(null)
const audioChunks = ref([])

const userText = ref('')
const assistantText = ref('')
const ttsAudio = ref('')

// 历史消息
const messages = ref([])

// 拉取历史消息
const fetchMessages = async () => {
  try {
    const res = await axios.get(`/api/messages/${conversationId}`)
    messages.value = res.data
  } catch (e) {
    ElMessage.error('获取消息失败')
  }
}

onMounted(() => {
  fetchMessages()
})

const startRecord = async () => {
  recording.value = true
  audioChunks.value = []
  const stream = await navigator.mediaDevices.getUserMedia({ audio: true })
  mediaRecorder.value = new MediaRecorder(stream)
  mediaRecorder.value.ondataavailable = e => {
    if (e.data.size > 0) audioChunks.value.push(e.data)
  }
  mediaRecorder.value.start()
}

const stopRecord = async () => {
  recording.value = false
  mediaRecorder.value.stop()
  mediaRecorder.value.onstop = async () => {
    const blob = new Blob(audioChunks.value, { type: 'audio/webm' })
    const formData = new FormData()
    formData.append('role', role.value)
    formData.append('skill', skill.value)
    formData.append('audio', blob, 'input.webm')
    formData.append('conversationId', conversationId)

    try {
      const res = await axios.post('/api/chat/voice', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      })
      // 后端返回识别文本+AI文本+tts音频
      userText.value = res.data.userText
      assistantText.value = res.data.assistantText
      ttsAudio.value = 'data:audio/mp3;base64,' + res.data.ttsAudioBase64

      // 把消息存数据库
      await axios.post('/api/messages', {
        conversationId,
        sender: 'user',
        content: userText.value
      })
      await axios.post('/api/messages', {
        conversationId,
        sender: 'ai',
        content: assistantText.value
      })

      // 刷新消息列表
      fetchMessages()
    } catch (e) {
      ElMessage.error('发送失败')
    }
  }
}
</script>

<template>
  <el-card class="container">
    <h2 style="text-align:center;margin-bottom:1rem;">AI 角色扮演 Demo</h2>

    <el-form :inline="true" class="input-group">
      <el-form-item>
        <el-input v-model="role" placeholder="角色名称(如苏格拉底)" clearable />
      </el-form-item>
      <el-form-item>
        <el-input v-model="skill" placeholder="技能(如反问引导)" clearable />
      </el-form-item>
    </el-form>

    <div class="button-group">
      <el-button type="danger" :disabled="recording" @click="startRecord">
        🎙️ 开始录音
      </el-button>
      <el-button type="success" :disabled="!recording" @click="stopRecord">
        ⏹️ 停止录音并发送
      </el-button>
    </div>

    <!-- 历史消息列表 -->
    <div class="messages">
      <div
          v-for="msg in messages"
          :key="msg.id"
          :class="['bubble', msg.sender === 'user' ? 'user' : 'ai']"
      >
        <strong>{{ msg.sender === 'user' ? '我' : 'AI' }}：</strong>
        {{ msg.content }}
      </div>
    </div>

    <div class="audio-player" v-if="ttsAudio">
      <span class="audio-label">AI语音：</span>
      <audio :src="ttsAudio" controls></audio>
    </div>
  </el-card>
</template>

<style scoped>
.container {
  max-width: 800px;
  margin: 2rem auto;
}

.input-group {
  margin-bottom: 1rem;
}

.button-group {
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-bottom: 1rem;
}

.messages {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  max-height: 300px;
  overflow-y: auto;
  margin-bottom: 1rem;
}

.bubble {
  padding: 0.5rem 1rem;
  border-radius: 12px;
  max-width: 70%;
}

.user {
  background-color: #e0f2fe;
  align-self: flex-end;
}

.ai {
  background-color: #f0fdf4;
  align-self: flex-start;
}

.audio-player {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  background-color: #f8fafc;
  border-radius: 8px;
  border: 1px solid #e2e8f0;
}

.audio-label {
  font-weight: 600;
  color: #334155;
}

audio {
  flex: 1;
  outline: none;
}
</style>