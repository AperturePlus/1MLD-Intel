<template>
  <view class="container">
	  <view class="back-button" @click="goBack">
	      <text class="back-icon">| </text>
	      <text class="back-text">返回</text>
	  </view>
    <view class="title">举报</view>
    <!-- 资讯题目 -->
    <view class="form-item">
      <text class="label">题目：</text>
      <input class="input" v-model="form.title" placeholder="请输入题目" />
    </view>
	
	<view class="form-item">
	  <text class="label">举报内容</text>
	  <textarea class="textarea" v-model="form.content" placeholder="请输入内容"></textarea>
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

    <!-- 提交按钮 -->
    <view class="submit-wrapper">
      <button class="submit-btn" :loading="isSubmitting" @tap="submitForm">提交</button>
    </view>
  </view>
</template>

<script>
import upload from '@/utils/upload'
import { getUserProfile } from '@/api/system/user'
import { createReport } from '@/api/system/report'

export default {
	
  data() {
    return {
      form: {
		title:'',
        imageUrl: '',
		reporteenickname:'',
		reporternickname:'',
		content:'',
		state:'0',//未处理的举报
		type:'user_report',
		createtime:''
      },
      localImagePath: '', // 本地预览用
      localFilePath: '',  // 真实路径，用于上传
      isSubmitting: false
    }
  },
  
  onLoad(options) {
    if (options.nickname) {
      this.form.reporteenickname = decodeURIComponent(options.nickname)
    }
	this.getUser();
  },
  
  methods: {
	 
	  
	  getUser() {
        getUserProfile().then(response => {
          this.form.reporternickname = response.data.nickname
        })
      },
		
		getCurrentTime() {
		  const now = new Date()
		  const year = now.getFullYear()
		  const month = String(now.getMonth() + 1).padStart(2, '0')
		  const day = String(now.getDate()).padStart(2, '0')
		  const hours = String(now.getHours()).padStart(2, '0')
		  const minutes = String(now.getMinutes()).padStart(2, '0')
		  const seconds = String(now.getSeconds()).padStart(2, '0')
		  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
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
	
	goBack() {
	    uni.navigateBack({
	        delta: 1
	    });
	},
	
    removeImage() {
      this.localFilePath = ''
      this.localImagePath = ''
      this.form.imageUrl = ''
    },
	
    async uploadImage() {
      if (!this.localFilePath) {
        throw new Error('未选择图片')
      }
      return upload({
        url: '/upload/report',
        filePath: this.localFilePath,
        name: 'file'
      }).then((result) => (result.data && result.data.fileName) || result.fileName || '')
    },
	
	
    async submitForm() {
      if (!this.form.content || !this.form.title) {
        return uni.showToast({ title: '请填写完整信息', icon: 'none' })
      }
      if (!this.localFilePath) {
        return uni.showToast({ title: '请先选择图片', icon: 'none' })
      }
      try {
        const reporteeNickname = this.form.reporteenickname
        const reporterNickname = this.form.reporternickname
        const fileName = await this.uploadImage()
        this.form.imageUrl = fileName
        this.isSubmitting = true
        this.form.createtime = this.getCurrentTime()

        await createReport({
          ...this.form,
          imageUrl: fileName
        })

        uni.showToast({ title: '更新成功', icon: 'success' })

        this.form = {
			title:'',
			imageUrl: '',
      reporteenickname: reporteeNickname,
      reporternickname: reporterNickname,
			content:'',
			state:'0',
			type:'user_report',
			createtime:''
		}
        this.localImagePath = ''
        this.localFilePath = ''
      } catch (err) {
        uni.showToast({ title: '更新失败', icon: 'none' })
      } finally {
        this.isSubmitting = false
      }
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
  position: absolute;
  top: 0;
  right: 0;
  color: red;
  font-size: 36rpx;
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
.remove-tag {
  margin-left: 10rpx;
  color: red;
  font-weight: bold;
}
</style>
