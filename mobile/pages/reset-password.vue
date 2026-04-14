<template>
  <view class="reset-password-container">
    <view class="logo-content align-center justify-center flex">
      <text class="title">重置密码</text>
    </view>
    <view class="back-button" @click="goBack">
      <text class="back-icon">◁</text>
      <text class="back-text">返回</text>
    </view>
    <view class="form-content">
      <view class="input-item flex align-center">
        <view class="iconfont icon-password icon"></view>
        <input v-model="resetForm.newPassword" type="password" class="input" placeholder="请输入新密码" maxlength="20" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-password icon"></view>
        <input v-model="resetForm.confirmPassword" type="password" class="input" placeholder="请输入确认密码" maxlength="20" />
      </view>
      <view class="action-btn">
        <button @click="handleResetPassword" class="login-btn cu-btn block bg-blue lg round">重置密码</button>
      </view>
    </view>
  </view>
</template>

<script>
import { resetPasswordByPhone } from '@/api/auth'

export default {
  data() {
    return {
      phone: '',
      code: '',
      resetForm: {
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  onLoad(options) {
    this.phone = (options && options.phone) || ''
    this.code = (options && options.code) || ''
  },
  methods: {
    goBack() {
      uni.navigateBack({
        delta: 1
      })
    },
    handleResetPassword() {
      if (this.resetForm.newPassword === '') {
        this.$modal.msgError('请输入新密码')
        return
      }
      if (this.resetForm.confirmPassword === '') {
        this.$modal.msgError('请输入确认密码')
        return
      }
      if (this.resetForm.newPassword !== this.resetForm.confirmPassword) {
        this.$modal.msgError('两次输入的密码不一致')
        return
      }
      this.$modal.loading('重置密码中...')
      resetPasswordByPhone({
        phone: this.phone,
        code: this.code,
        newPassword: this.resetForm.newPassword,
        confirmPassword: this.resetForm.confirmPassword
      })
        .then(() => {
          this.$modal.msgSuccess('密码重置成功')
          this.$tab.reLaunch('/pages/login')
        })
        .catch(() => {
          this.$modal.msgError('密码重置失败，请重试')
        })
        .finally(() => {
          this.$modal.closeLoading()
        })
    }
  }
}
</script>

<style lang="scss">
page {
  background-image: linear-gradient(to top, #f3cbee 0%, #ace0f9 100%);
}

.reset-password-container {
  width: 100%;

  .back-button {
    display: flex;
    align-items: center;
    cursor: pointer;

    .back-icon {
      font-size: 24px;
      margin-right: 6rpx;
      color: #333;
    }

    .back-text {
      font-size: 16px;
      color: #333;
    }
  }

  .logo-content {
    width: 100%;
    font-size: 21px;
    text-align: center;
    padding-top: 15%;
  }

  .form-content {
    text-align: center;
    margin: 20px auto;
    margin-top: 15%;
    width: 80%;

    .input-item {
      margin: 20px auto;
      background-color: #f5f6f7;
      height: 45px;
      border-radius: 20px;

      .icon {
        font-size: 38rpx;
        margin-left: 10px;
        color: #999;
      }

      .input {
        width: 100%;
        font-size: 14px;
        line-height: 20px;
        text-align: left;
        padding-left: 15px;
      }
    }

    .login-btn {
      margin-top: 40px;
      height: 45px;
    }
  }
}
</style>
