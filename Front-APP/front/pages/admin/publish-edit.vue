<template>
  <view class="container">
    <view class="back-button" @tap="goBack">
      <text class="back-icon">◁</text>
      <text class="back-text">返回</text>
    </view>
    <view class="title">编辑资讯</view>

    <view class="form-item">
      <text class="label">资讯题目：</text>
      <input class="input" v-model="form.title" placeholder="请输入题目" />
    </view>

    <view class="form-item">
      <text class="label">资讯内容：</text>
      <textarea class="textarea" v-model="form.solution" placeholder="请输入内容"></textarea>
    </view>

    <view class="form-item">
      <text class="label">上传图片：</text>
      <view class="images">
        <view v-if="localImagePath">
          <image :src="localImagePath" class="uploaded-img" mode="aspectFill" />
          <text class="remove-tag" @click="removeImage">×</text>
        </view>
        <button class="btn" v-if="!localImagePath" @tap="chooseImage">选择图片</button>
      </view>
    </view>

    <view class="submit-wrapper">
      <button class="submit-btn" :loading="isSubmitting" @tap="submitForm">更新资讯</button>
    </view>
  </view>
</template>

<script>
import upload from '@/utils/upload'
import { updateArticle } from '@/api/system/advice'

export default {
  data() {
    return {
      form: {
        id: '',
        title: '',
        solution: '',
        imageUrl: ''
      },
      localImagePath: '',
      localFilePath: '',
      isSubmitting: false
    }
  },
  onLoad(options) {
    if (options.id) {
      this.form.id = options.id
    }
  },
  methods: {
    chooseImage() {
      uni.chooseImage({
        count: 1,
        success: (res) => {
          this.localFilePath = res.tempFilePaths[0]
          this.localImagePath = this.localFilePath
        }
      })
    },
    removeImage() {
      this.localFilePath = ''
      this.localImagePath = ''
      this.form.imageUrl = ''
    },
    uploadImage() {
      if (!this.localFilePath) {
        return Promise.reject(new Error('请选择图片'))
      }
      return upload({
        url: '/upload/advice',
        filePath: this.localFilePath,
        name: 'file'
      }).then((result) => (result.data && result.data.fileName) || result.fileName || '')
    },
    goBack() {
      uni.navigateBack({
        delta: 1
      })
    },
    submitForm() {
      if (!this.form.title || !this.form.solution) {
        uni.showToast({ title: '请填写完整信息', icon: 'none' })
        return
      }
      if (!this.localFilePath) {
        uni.showToast({ title: '请选择图片', icon: 'none' })
        return
      }

      this.isSubmitting = true
      this.uploadImage()
        .then((fileName) => {
          this.form.imageUrl = fileName
          return updateArticle(this.form)
        })
        .then(() => {
          uni.showToast({ title: '更新成功', icon: 'success' })
          uni.navigateBack()
        })
        .finally(() => {
          this.isSubmitting = false
        })
    }
  }
}
</script>

<style scoped>
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
  display: block;
}

.input {
  height: 80rpx;
  line-height: 80rpx;
  background: #ffffffcc;
  border-radius: 20rpx;
  padding: 0 20rpx;
  font-size: 28rpx;
  border: 1px solid #ccc;
  width: 100%;
  box-sizing: border-box;
}

.textarea {
  width: 100%;
  height: 160rpx;
  border: 2rpx solid #dcdcdc;
  border-radius: 16rpx;
  padding: 20rpx;
  font-size: 28rpx;
  background-color: #fff;
  box-sizing: border-box;
}

.images {
  position: relative;
}

.uploaded-img {
  width: 200rpx;
  height: 200rpx;
  border-radius: 16rpx;
}

.remove-tag {
  position: absolute;
  top: 0;
  right: 0;
  background: red;
  color: white;
  width: 40rpx;
  height: 40rpx;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: translate(50%, -50%);
}

.btn {
  background: linear-gradient(to right, #4dd0e1, #f48fb1);
  color: #fff;
  padding: 10rpx 30rpx;
  border-radius: 20rpx;
  font-size: 28rpx;
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
  width: 100%;
}
</style>
