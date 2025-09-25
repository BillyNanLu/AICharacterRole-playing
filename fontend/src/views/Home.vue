<script>
import axios from 'axios'

export default {
  data() {
    return {
      role: '',
      skill: '',
      recording: false,
      mediaRecorder: null,
      audioChunks: [],
      userText: '',
      assistantText: '',
      ttsAudio: ''
    }
  },
  methods: {
    async startRecord() {
      this.recording = true
      this.audioChunks = []
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true })
      this.mediaRecorder = new MediaRecorder(stream)
      this.mediaRecorder.ondataavailable = e => {
        if (e.data.size > 0) this.audioChunks.push(e.data)
      }
      this.mediaRecorder.start()
    },
    async stopRecord() {
      this.recording = false
      this.mediaRecorder.stop()
      this.mediaRecorder.onstop = async () => {
        const blob = new Blob(this.audioChunks, { type: 'audio/webm' })
        const formData = new FormData()
        formData.append('role', this.role)
        formData.append('skill', this.skill)
        formData.append('audio', blob, 'input.webm')

        const res = await axios.post('/api/chat/voice', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        })
        this.userText = res.data.userText
        this.assistantText = res.data.assistantText
        this.ttsAudio = 'data:audio/mp3;base64,' + res.data.ttsAudioBase64
      }
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
        <el-icon><i class="el-icon-microphone"></i></el-icon> 开始录音
      </el-button>
      <el-button type="success" :disabled="!recording" @click="stopRecord">
        <el-icon><i class="el-icon-video-pause"></i></el-icon> 停止录音并发送
      </el-button>
    </div>

    <el-card shadow="hover" class="chat-card">
      <p><strong>识别文本：</strong>{{ userText || '等待录音识别...' }}</p>
    </el-card>

    <el-card shadow="hover" class="chat-card">
      <p><strong>AI 回复：</strong>{{ assistantText || '等待AI响应...' }}</p>
    </el-card>

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

.chat-card {
  margin-top: 1rem;
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