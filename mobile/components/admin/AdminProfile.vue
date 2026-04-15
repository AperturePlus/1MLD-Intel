<template>
  <view class="admin-profile">
    <view class="profile-header">
      <image class="profile-avatar" :src="userInfo.avatar" mode="aspectFill" />
      <view class="profile-info">
        <text class="profile-name">{{ userInfo.name }}</text>
        <text class="profile-phone">{{ userInfo.phone || '未绑定手机号' }}</text>
      </view>
    </view>

    <view class="profile-actions">
      <button class="logout-btn" @click="handleLogout">退出登录</button>
    </view>
  </view>
</template>

<script>
import config from '@/config'
import { getUserProfile } from '@/api/system/user'

const defaultAvatar = '/static/images/default-avatar.png'

export default {
  data() {
    return {
      userInfo: {
        name: '管理员',
        phone: '',
        avatar: defaultAvatar
      }
    }
  },
  mounted() {
    this.loadProfile()
  },
  methods: {
    resolveAvatar(data) {
      const rawAvatar = data.avatar || ''
      if (rawAvatar) {
        return String(rawAvatar).startsWith('http') ? rawAvatar : `${config.baseUrl}${rawAvatar}`
      }
      if (data.imageUrl) {
        return `${config.baseUrl}/userinfo/${data.imageUrl}`
      }
      return defaultAvatar
    },
    loadProfile() {
      getUserProfile()
        .then((response) => {
          const data = (response && response.data) || {}
          this.userInfo = {
            name: data.nickname || data.userName || '管理员',
            phone: data.phone || '',
            avatar: this.resolveAvatar(data)
          }
        })
        .catch(() => {
          this.userInfo = {
            name: '管理员',
            phone: '',
            avatar: defaultAvatar
          }
        })
    },
    handleLogout() {
      uni.showModal({
        title: '提示',
        content: '确定要退出登录吗？',
        success: (res) => {
          if (!res.confirm) {
            return
          }
          uni.showLoading({ title: '退出中...' })
          this.$store
            .dispatch('LogOut')
            .then(() => {
              uni.showToast({ title: '已退出登录', icon: 'success' })
              uni.reLaunch({
                url: '/pages/login'
              })
            })
            .finally(() => {
              uni.hideLoading()
            })
        }
      })
    }
  }
}
</script>

<style scoped>
.admin-profile {
  padding: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  background-color: #fff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.profile-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-right: 20px;
}

.profile-info {
  display: flex;
  flex-direction: column;
}

.profile-name {
  font-size: 18px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.profile-phone {
  font-size: 15px;
  color: #666;
}

.profile-actions {
  background-color: #fff;
  border-radius: 12px;
  padding: 0 15px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.logout-btn {
  margin-top: 30px;
  background-color: #fef0f0;
  color: #f56c6c;
  height: 44px;
  line-height: 44px;
  font-size: 16px;
  border-radius: 8px;
}
</style>