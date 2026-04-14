<template>
  <view class="container">
    <view class="header">
      <view class="user-info-box">
        <view class="avatar-wrap">
          <image class="avatar" :src="userProfile.avatar || '/static/images/default-avatar.png'" mode="aspectFill"></image>
        </view>
        <view class="info-text">
          <view class="name-line">
            <text class="user-name">{{ userProfile.name || '未登录用户' }}</text>
            <text class="role-tag" v-if="userProfile.role === 'patient'">认证患者</text>
          </view>
          <text class="phone">{{ maskPhone(userProfile.phone) || '登录同步云端数据' }}</text>
        </view>
      </view>
    </view>

    <view class="content-wrapper">
      <view class="quick-action-card flex justify-around">
        <view class="action-item" @click="navigateTo('/pages/assessment-form')">
          <text class="action-num">12</text>
          <text class="action-label">健康档案</text>
        </view>
        <view class="action-item" @click="navigateTo('/pages/assessment-result')">
          <text class="action-num">3</text>
          <text class="action-label">评估记录</text>
        </view>
        <view class="action-item" @click="navigateTo('/pages/question/index')">
          <text class="action-num">1</text>
          <text class="action-label">问诊记录</text>
        </view>
        <view class="action-item" @click="navigateTo('/pages/articles/all')">
          <text class="action-num">8</text>
          <text class="action-label">我的收藏</text>
        </view>
      </view>

      <view class="menu-list-card">
        <view class="menu-item" @click="navigateTo('/pages/mine/info/index')">
          <view class="menu-left">
            <text class="iconfont icon-user menu-icon" style="color: #2b85e4;"></text>
            <text class="menu-text">个人资料</text>
          </view>
          <view class="menu-right">
            <text class="arrow"></text>
          </view>
        </view>

        <view class="menu-item" @click="navigateTo('/pages/mine/settings/index')">
          <view class="menu-left">
            <text class="iconfont icon-setting menu-icon" style="color: #19be6b;"></text>
            <text class="menu-text">隐私与安全</text>
          </view>
          <view class="menu-right">
            <text class="arrow"></text>
          </view>
        </view>

        <view class="menu-item" @click="handleAbout">
          <view class="menu-left">
            <text class="iconfont icon-info menu-icon" style="color: #ff9900;"></text>
            <text class="menu-text">关于平台</text>
          </view>
          <view class="menu-right">
            <text class="version-text">v1.0.0</text>
            <text class="arrow"></text>
          </view>
        </view>
      </view>

      <view class="logout-section">
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </view>

      <view class="footer-copyright">
        <text class="copyright-text">© 2026 四川大学华西临床医学院IMLD课题组</text>
      </view>
    </view>
  </view>
</template>

<script>
import { getUserProfile } from '@/api/system/user'

export default {
  data() {
    return {
      userProfile: {
        name: '',
        phone: '',
        avatar: '',
        role: 'patient'
      }
    }
  },
  onLoad() {
    this.loadUserProfile()
  },
  methods: {
    loadUserProfile() {
      getUserProfile().then((response) => {
        const data = (response && response.data) || {}
        this.userProfile = {
          name: data.nickname || data.userName || '未登录用户',
          phone: data.phone || '',
          avatar: data.avatar || '/static/images/default-avatar.png',
          role: 'patient'
        }
      })
    },
    maskPhone(phone) {
      if (!phone) return ''
      return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
    },
    navigateTo(url) {
      if (!url) return
      uni.navigateTo({
        url,
        fail: () => {
          uni.showToast({ title: '功能开发中', icon: 'none' })
        }
      })
    },
    handleAbout() {
      uni.showModal({
        title: '关于数智肝循',
        content: '遗传代谢性肝病管理平台由四川大学华西临床医学院IMLD课题组研发，致力于提供精准的AI辅助诊断与全病程管理服务。',
        showCancel: false,
        confirmText: '了解',
        confirmColor: '#2b85e4'
      })
    },
    handleLogout() {
      uni.showModal({
        title: '提示',
        content: '确定要退出当前账号吗？',
        confirmColor: '#fa3534',
        success: (res) => {
          if (!res.confirm) {
            return
          }
          uni.showLoading({ title: '退出中...' })
          this.$store.dispatch('LogOut').then(() => {
            uni.hideLoading()
            uni.showToast({ title: '已退出登录', icon: 'success' })
            uni.reLaunch({
              url: '/pages/login'
            })
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  min-height: 100vh;
  background-color: #f4f6f9;
}

.header {
  background: linear-gradient(135deg, #2b85e4 0%, #005eaa 100%);
  padding: 100rpx 40rpx 120rpx;
  border-bottom-left-radius: 40rpx;
  border-bottom-right-radius: 40rpx;
}

.user-info-box {
  display: flex;
  align-items: center;
}

.avatar-wrap {
  width: 120rpx;
  height: 120rpx;
  border-radius: 50%;
  border: 4rpx solid rgba(255, 255, 255, 0.4);
  overflow: hidden;
  margin-right: 30rpx;
  flex-shrink: 0;
}

.avatar {
  width: 100%;
  height: 100%;
  background-color: #e1f0ff;
}

.info-text {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.name-line {
  display: flex;
  align-items: center;
  margin-bottom: 12rpx;
}

.user-name {
  font-size: 40rpx;
  font-weight: bold;
  color: #ffffff;
  margin-right: 16rpx;
}

.role-tag {
  font-size: 20rpx;
  color: #2b85e4;
  background-color: #ffffff;
  padding: 4rpx 12rpx;
  border-radius: 20rpx;
  font-weight: bold;
}

.phone {
  font-size: 26rpx;
  color: rgba(255, 255, 255, 0.85);
}

.content-wrapper {
  padding: 0 30rpx;
  margin-top: -60rpx;
}

.quick-action-card {
  background-color: #ffffff;
  border-radius: 20rpx;
  padding: 40rpx 20rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.04);
  display: flex;
  text-align: center;
}

.action-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.action-num {
  font-size: 40rpx;
  font-weight: bold;
  color: #333333;
  margin-bottom: 10rpx;
}

.action-label {
  font-size: 24rpx;
  color: #666666;
}

.menu-list-card {
  background-color: #ffffff;
  border-radius: 20rpx;
  padding: 0 30rpx;
  margin-bottom: 40rpx;
  box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.04);
}

.menu-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 36rpx 0;
  border-bottom: 1px solid #f5f5f5;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-left {
  display: flex;
  align-items: center;
}

.menu-icon {
  font-size: 36rpx;
  margin-right: 20rpx;
}

.menu-text {
  font-size: 30rpx;
  color: #333333;
}

.menu-right {
  display: flex;
  align-items: center;
}

.version-text {
  font-size: 24rpx;
  color: #999999;
  margin-right: 10rpx;
}

.arrow {
  width: 14rpx;
  height: 14rpx;
  border-top: 2px solid #cccccc;
  border-right: 2px solid #cccccc;
  transform: rotate(45deg);
}

.logout-section {
  margin-bottom: 40rpx;
}

.logout-btn {
  background-color: #ffffff;
  color: #fa3534;
  font-size: 32rpx;
  font-weight: bold;
  height: 90rpx;
  line-height: 90rpx;
  border-radius: 20rpx;
  box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.02);
  border: none;
}

.logout-btn::after {
  border: none;
}

.footer-copyright {
  text-align: center;
  padding-bottom: 40rpx;
}

.copyright-text {
  font-size: 22rpx;
  color: #bbbbbb;
}
</style>
