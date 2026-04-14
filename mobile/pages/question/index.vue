<template>
  <view class="container">
    <view class="cu-chat">
      <view v-for="(message, index) in messages" :key="index">
        <view v-if="message.role !== 'user'" class="cu-item" style="padding-bottom: 5rpx;">
          <view class="cu-avatar radius" style="background-image: url(/static/images/image/smart-qa.png)"></view>
          <view class="main">
            <view class="content shadow">
              <text>{{ message.content }}</text>
            </view>
          </view>
        </view>
        <view v-else class="cu-item self" style="padding-bottom: 5rpx;">
          <view class="main">
            <view class="content shadow bg-green">
              <text>{{ message.content }}</text>
            </view>
          </view>
          <view class="cu-avatar radius" style="background-image: url(/static/images/image/smart-recommendation.png)"></view>
        </view>
      </view>
    </view>

    <view class="cu-bar foot input" :style="{ 'padding-bottom': inputBottom + 'px' }">
      <view class="action">
        <text class="cuIcon-sound text-grey"></text>
      </view>
      <input class="solid-bottom" :adjust-position="false" v-model="inputMessage" maxlength="300" cursor-spacing="10" @focus="onInputFocus" @blur="onInputBlur"></input>
      <view class="action">
        <text class="cuIcon-emojifill text-grey"></text>
      </view>
      <button class="cu-btn bg-green shadow" @click="sendMessage">发送</button>
    </view>
  </view>
</template>

<script>
import { sendChatMessage } from '@/api/chat'

export default {
  data() {
    return {
      inputBottom: 0,
      messages: [
        {
          content: '您好，我是数智肝循 AI 助手，专注于遗传代谢性肝病的健康咨询。您可以向我提问关于饮食管理、检查指标、基因检测、药物治疗等方面的问题。',
          role: 'assistant'
        }
      ],
      inputMessage: ''
    }
  },
  methods: {
    onInputFocus(e) {
      this.inputBottom = e.detail.height
    },
    onInputBlur() {
      this.inputBottom = 0
    },
    sendMessage() {
      const content = this.inputMessage.trim()
      if (!content) {
        return
      }
      this.messages.push({
        content,
        role: 'user'
      })
      this.inputMessage = ''

      const loadingMessage = {
        content: '回复生成中...',
        role: 'system'
      }
      this.messages.push(loadingMessage)

      sendChatMessage(
        this.messages
          .filter((item) => item.role === 'user' || item.role === 'assistant')
          .map((item) => ({ role: item.role, content: item.content }))
      )
        .then((res) => {
          const reply = (res && res.data && res.data.reply) || '系统暂未返回内容。'
          const loadingIndex = this.messages.findIndex((item) => item.content === '回复生成中...')
          if (loadingIndex >= 0) {
            this.messages.splice(loadingIndex, 1, {
              role: 'assistant',
              content: reply
            })
          } else {
            this.messages.push({ role: 'assistant', content: reply })
          }
        })
        .catch(() => {
          const loadingIndex = this.messages.findIndex((item) => item.content === '回复生成中...')
          if (loadingIndex >= 0) {
            this.messages.splice(loadingIndex, 1, {
              role: 'assistant',
              content: '当前服务不可用，请稍后重试。'
            })
          }
        })
    }
  }
}
</script>

<style>
  page {
    background-image: linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%);
  }
  .page {
    padding-bottom: 100upx;
  }
  .container {
    display: flex;
    flex-direction: column;
    height: 90vh;
  }
  .system {
    animation: fadeIn 0.5s ease forwards;
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(-20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
</style>
