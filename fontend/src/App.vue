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
      this.recording = true;
      this.audioChunks = [];
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      this.mediaRecorder = new MediaRecorder(stream);
      this.mediaRecorder.ondataavailable = e => {
        if (e.data.size > 0) this.audioChunks.push(e.data);
      };
      this.mediaRecorder.start();
    },
    async stopRecord() {
      this.recording = false;
      this.mediaRecorder.stop();
      this.mediaRecorder.onstop = async () => {
        const blob = new Blob(this.audioChunks, { type: 'audio/webm' });
        const formData = new FormData();
        formData.append('role', this.role);
        formData.append('skill', this.skill);
        formData.append('audio', blob, 'input.webm');

        const res = await axios.post('/api/chat/voice', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
        this.userText = res.data.userText;
        this.assistantText = res.data.assistantText;
        this.ttsAudio = 'data:audio/mp3;base64,' + res.data.ttsAudioBase64;
      }
    }
  }
}
</script>

<template>
  <div class="container">
    <h2>AI 角色扮演 Demo</h2>

    <div class="input-group">
      <input v-model="role" placeholder="角色名称(如苏格拉底)" />
      <input v-model="skill" placeholder="技能(如反问引导)" />
    </div>

    <div class="button-group">
      <button @click="startRecord" :disabled="recording" class="record-btn">
        <i class="icon" :class="{ 'recording-icon': recording }">🎙️</i>
        开始录音
      </button>
      <button @click="stopRecord" :disabled="!recording" class="stop-btn">
        <i class="icon">⏹️</i>
        停止录音并发送
      </button>
    </div>

    <div class="chat-container">
      <div class="message user-message">
        <span class="label">识别文本：</span>
        <p>{{ userText || '等待录音识别...' }}</p>
      </div>

      <div class="message ai-message">
        <span class="label">AI 回复：</span>
        <p>{{ assistantText || '等待AI响应...' }}</p>
      </div>
    </div>

    <div class="audio-player" v-if="ttsAudio">
      <span class="audio-label">AI语音：</span>
      <audio :src="ttsAudio" controls></audio>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 800px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #f5f7fa;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

h2 {
  color: #2c3e50;
  text-align: center;
  margin-bottom: 2rem;
  font-weight: 600;
}

.input-group {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

input {
  flex: 1;
  padding: 0.8rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.button-group {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

button {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.record-btn {
  background-color: #ef4444;
  color: white;
  flex: 1;
}

.record-btn:disabled {
  background-color: #f87171;
  cursor: not-allowed;
}

.stop-btn {
  background-color: #10b981;
  color: white;
  flex: 1;
}

.stop-btn:disabled {
  background-color: #34d399;
  cursor: not-allowed;
}

button:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.recording-icon {
  animation: pulse 1.5s infinite;
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.2); }
  100% { transform: scale(1); }
}

.chat-container {
  margin-bottom: 2rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.message {
  padding: 1rem;
  border-radius: 8px;
  line-height: 1.6;
}

.user-message {
  background-color: #e0f2fe;
  border-left: 4px solid #0ea5e9;
}

.ai-message {
  background-color: #f0fdf4;
  border-left: 4px solid #10b981;
}

.label {
  font-weight: 600;
  margin-right: 0.5rem;
  color: #1e293b;
}

.audio-player {
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