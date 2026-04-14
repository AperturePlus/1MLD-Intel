import request from '@/utils/request'

export interface ChatMessage {
  role: 'system' | 'user' | 'assistant'
  content: string
}

export function sendChatMessage(messages: ChatMessage[]) {
  return request({
    url: '/api/chat',
    method: 'post',
    data: {
      model: 'qwen-plus',
      messages
    }
  })
}

