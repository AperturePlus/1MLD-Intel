<template>
  <view class="container">
    <view class="back-button" @click="goBack">
      <text class="back-icon">◁</text>
      <text class="back-text">返回</text>
    </view>
    <view class="title">修改个人信息</view>

    <view class="form-item">
      <text class="label">上传头像：</text>
      <view class="images">
        <view v-if="localImagePath">
          <image :src="localImagePath" class="uploaded-img" mode="aspectFill" />
          <text class="remove-tag" @click="removeImage">×</text>
        </view>
        <button class="btn" v-if="!localImagePath" @tap="chooseImage">选择图片</button>
      </view>
    </view>

    <view class="form-item">
      <text class="label">电话号码：</text>
      <input class="input" v-model="form.phone" placeholder="请输入电话号码" />
    </view>

    <view class="form-item">
      <text class="label">性别：</text>
      <input class="input" v-model="form.sex" placeholder="请输入性别" />
    </view>

    <view class="form-item">
      <text class="label">年龄：</text>
      <input class="input" v-model="form.age" placeholder="请输入年龄" />
    </view>

    <view class="form-item">
      <text class="label">院校/单位：</text>
      <input class="input" v-model="form.school" placeholder="请输入院校/单位" />
    </view>

    <view class="form-item">
      <text class="label">帖子标签：</text>
      <view class="tag-input-wrapper">
        <input class="input" v-model="tagInput" placeholder="输入标签（最多5个）" />
        <button class="btn" @click="addTag">添加标签</button>
      </view>
      <view class="tags">
        <view class="tag" v-for="(tag, index) in form.tags" :key="index">
          {{ tag }} <text class="remove-tag" @click="removeTag(index)">×</text>
        </view>
      </view>
    </view>

    <view class="form-item">
      <text class="label">个人简介</text>
      <textarea class="textarea" v-model="form.content" placeholder="请输入个人简介"></textarea>
    </view>

    <view class="submit-wrapper">
      <button class="submit-btn" :loading="isSubmitting" @tap="submitForm">提交</button>
    </view>
  </view>
</template>

<script>
import upload from '@/utils/upload'
import { getUserProfile, updateUserProfile } from '@/api/system/user'

export default {
  data() {
    return {
      form: {
        phone: '',
        sex: '',
        age: '',
        school: '',
        imageUrl: '',
        tags: [],
        nickname: '',
        content: ''
      },
      tagInput: '',
      localImagePath: '',
      localFilePath: '',
      isSubmitting: false
    }
  },
  onLoad() {
    this.loadCurrentUser()
  },
  methods: {
    loadCurrentUser() {
      getUserProfile().then((response) => {
        const data = (response && response.data) || {}
        this.form.nickname = data.nickname || ''
        this.form.phone = data.phone || ''
        this.form.sex = data.sex || ''
        this.form.age = data.age || ''
        this.form.school = data.school || ''
        this.form.content = data.content || ''
        this.form.imageUrl = data.imageUrl || ''
        this.form.tags = data.tags ? String(data.tags).split(',').map((item) => item.trim()).filter(Boolean) : []
      })
    },
    chooseImage() {
      uni.chooseImage({
        count: 1,
        success: (res) => {
          this.localFilePath = res.tempFilePaths[0]
          this.localImagePath = this.localFilePath
        }
      })
    },
    addTag() {
      const tag = this.tagInput.trim()
      if (!tag) return
      if (this.form.tags.includes(tag)) {
        uni.showToast({ title: '标签已存在', icon: 'none' })
      } else if (this.form.tags.length >= 5) {
        uni.showToast({ title: '最多添加5个标签', icon: 'none' })
      } else {
        this.form.tags.push(tag)
      }
      this.tagInput = ''
    },
    removeTag(index) {
      this.form.tags.splice(index, 1)
    },
    goBack() {
      uni.navigateBack({
        delta: 1
      })
    },
    removeImage() {
      this.localFilePath = ''
      this.localImagePath = ''
      this.form.imageUrl = ''
    },
    uploadImage() {
      if (!this.localFilePath) {
        return Promise.resolve(this.form.imageUrl)
      }
      return upload({
        url: '/upload/userinfo',
        filePath: this.localFilePath,
        name: 'file'
      }).then((result) => (result.data && result.data.fileName) || result.fileName || '')
    },
    submitForm() {
      if (!this.form.phone || !this.form.sex || !this.form.age || !this.form.school) {
        uni.showToast({ title: '请填写完整信息', icon: 'none' })
        return
      }

      this.isSubmitting = true
      this.uploadImage()
        .then((fileName) => {
          this.form.imageUrl = fileName
          const payload = {
            ...this.form,
            tags: this.form.tags.join(',')
          }
          return updateUserProfile(payload)
        })
        .then(() => {
          uni.showToast({ title: '更新成功', icon: 'success' })
          this.localImagePath = ''
          this.localFilePath = ''
        })
        .finally(() => {
          this.isSubmitting = false
        })
    }
  }
}
</script>

<style scoped>
.input {
  height: 80rpx;
  line-height: 80rpx;
  background: #ffffffcc;
  border-radius: 20rpx;
  padding: 0 20rpx;
  font-size: 28rpx;
  border: 1px solid #ccc;
}
.btn {
  background: linear-gradient(to right, #4dd0e1, #f48fb1);
  color: #fff;
  padding: 10rpx 30rpx;
  border-radius: 20rpx;
  font-size: 28rpx;
  margin-left: 20rpx;
}
.container {
  padding: 20rpx;
  background: linear-gradient(to bottom right, #e0f7fa, #fce4ec);
  border-radius: 20rpx;
}
.title {
  font-size: 42rpx;
  text-align: center;
  margin-bottom: 40rpx;
  color: #5a9bd6;
  font-weight: bold;
}
.form-item {
  margin-bottom: 30rpx;
}
.label {
  font-size: 28rpx;
  margin-bottom: 10rpx;
  color: #333;
}
.textarea {
  width: 100%;
  height: 160rpx;
  border: 2rpx solid #dcdcdc;
  border-radius: 16rpx;
  padding: 20rpx;
  font-size: 28rpx;
  background-color: #fff;
}
.uploaded-img {
  width: 200rpx;
  height: 200rpx;
  border-radius: 16rpx;
}
.remove-tag {
  margin-left: 10rpx;
  color: red;
  font-weight: bold;
}
.submit-wrapper {
  margin-top: 40rpx;
}
.submit-btn {
  background: linear-gradient(to right, #4dd0e1, #f48fb1);
  text-align: center;
  padding: 20rpx;
  border-radius: 32rpx;
  color: white;
  font-size: 32rpx;
  font-weight: bold;
}
.tag-list {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10rpx;
}
.tag {
  background-color: #cde7fa;
  color: #333;
  border-radius: 20rpx;
  padding: 10rpx 20rpx;
  margin: 5rpx;
  display: flex;
  align-items: center;
}
</style>
