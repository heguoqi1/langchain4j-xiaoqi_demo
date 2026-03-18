<template>
  <div class="chat-container">
    <div class="chat-header">智能小奇</div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div v-if="messages.length === 0" class="welcome-message">
        <p>👋 你好！我是智能小奇</p>
        <p>有什么我可以帮助你的吗？</p>
      </div>
      
      <div v-for="(message, index) in messages" :key="index" 
           :class="['message', message.role]">
        <div class="message-content">
          {{ message.content }}
        </div>
      </div>
      
      <div v-if="isTyping" class="message assistant">
        <div class="message-content typing">
          小奇正在思考...
        </div>
      </div>
    </div>
    
    <div class="chat-input">
      <input 
        v-model="userInput" 
        @keyup.enter="sendMessage"
        placeholder="请输入你的问题..."
        :disabled="isTyping"
      />
      <button 
        @click="sendMessage" 
        :disabled="isTyping || !userInput.trim()"
      >
        发送
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted } from 'vue'
import axios from 'axios'

const userInput = ref('')
const messages = ref([])
const isTyping = ref(false)
const messagesContainer = ref(null)
const memoryId = ref(Date.now())

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

const sendMessage = async () => {
  if (!userInput.value.trim() || isTyping.value) return
  
  const userMessage = userInput.value.trim()
  messages.value.push({
    role: 'user',
    content: userMessage
  })
  
  userInput.value = ''
  isTyping.value = true
  scrollToBottom()
  
  try {
    const response = await fetch('/xiaoqi/chat', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        memoryId: memoryId.value,
        userMassage: userMessage
      })
    })
    
    if (!response.ok) {
      throw new Error('网络请求失败')
    }
    
    const reader = response.body.getReader()
    const decoder = new TextDecoder()
    let assistantMessage = ''
    
    messages.value.push({
      role: 'assistant',
      content: ''
    })
    
    while (true) {
      const { done, value } = await reader.read()
      if (done) break
      
      const chunk = decoder.decode(value)
      assistantMessage += chunk
      messages.value[messages.value.length - 1].content = assistantMessage
      scrollToBottom()
    }
    
  } catch (error) {
    console.error('发送消息失败:', error)
    messages.value.push({
      role: 'assistant',
      content: '抱歉，我遇到了一些问题。请稍后再试。'
    })
    scrollToBottom()
  } finally {
    isTyping.value = false
  }
}

onMounted(() => {
  scrollToBottom()
})
</script>
