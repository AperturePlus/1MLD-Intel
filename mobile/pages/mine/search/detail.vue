<template>
  <view class="user-detail-page">
    <view v-if="user">
      <!-- 用户卡片展示 -->
      <view class="news-card">
        <image class="news-image" :src="getImagePath(user.imageUrl)" mode="aspectFill" />

        <view class="news-content">
          <text class="news-title">{{ user.nickname }}</text>
          <text class="news-tags">
            <text class="tag" v-for="(tag, tagIndex) in user.tags" :key="tagIndex">{{ tag }}</text>
          </text>
          <text class="news-author">年龄：{{ user.age }}</text>
          <text class="news-author">性别：{{ user.sex }}</text>
          <text class="news-author">学校：{{ user.school }}</text>
          <text class="news-desc">自我简介：{{ user.content }}</text>
        </view>
      </view>
      
      <!-- 关注按钮 -->
      <view class="follow-btn-container">
        <button class="follow-btn" @click="handleFollow">
          {{ isFollowing ? '已关注' : '关注' }}
        </button>
      </view>
    </view>

    <view v-else>
      <text>加载中...</text>
    </view>
  </view>
</template>

<script>
import config from '@/config'
import { getUserByNickname, getUserProfile } from '@/api/system/user'
import { addFollowRelation, checkFollowRelation, removeFollowRelation } from '@/api/system/follow'

export default {
  data() {
    return {
      user: null,
      currentUserNickname: '',
      currentUserImageUrl: '',
      isFollowing: false
    }
  },
  async onLoad(options) {
    const nickname = options.nickname ? decodeURIComponent(options.nickname) : ''
    if (!nickname) {
      return
    }
    await Promise.all([this.loadCurrentUser(), this.loadUser(nickname)])
    await this.loadFollowStatus()
  },
  methods: {
    getImagePath(filename) {
      return config.baseUrl + '/userinfo/' + filename
    },

    async loadCurrentUser() {
      const response = await getUserProfile()
      this.currentUserNickname = (response && response.data && response.data.nickname) || ''
      this.currentUserImageUrl = (response && response.data && response.data.imageUrl) || ''
    },

    async loadUser(nickname) {
      try {
        const res = await getUserByNickname(nickname)
        const userData = (res && res.data) || null
        if (!userData) {
          return
        }
        if (userData.tags && typeof userData.tags === 'string') {
          userData.tags = userData.tags.split(',').map(tag => tag.trim())
        } else {
          userData.tags = []
        }
        this.user = userData
      } catch (error) {
        console.error('获取用户失败：', error)
      }
    },

    async loadFollowStatus() {
      if (!this.user || !this.currentUserNickname || this.currentUserNickname === this.user.nickname) {
        this.isFollowing = false
        return
      }
      try {
        const response = await checkFollowRelation({
          follower: this.currentUserNickname,
          followee: this.user.nickname
        })
        this.isFollowing = Boolean(response && response.data && response.data.isFollowing)
      } catch (_error) {
        this.isFollowing = false
      }
    },
    
    handleFollow() {
      if (!this.user) {
        return
      }
      if (this.currentUserNickname === this.user.nickname) {
        uni.showToast({ title: '不能关注自己', icon: 'none' })
        return
      }
      if (this.isFollowing) {
        this.unfollowUser()
      } else {
        this.followUser()
      }
    },
    
    followUser() {
      uni.showLoading({ title: '处理中...' })
      addFollowRelation({
        follower: this.currentUserNickname,
        follower_image_url: this.currentUserImageUrl,
        followee: this.user.nickname,
        followee_image_url: this.user.imageUrl || ''
      })
        .then(() => {
        this.isFollowing = true
        uni.showToast({ title: '关注成功', icon: 'success' })
        })
        .catch(() => {
          uni.showToast({ title: '关注失败', icon: 'none' })
        })
        .finally(() => {
          uni.hideLoading()
        })
    },
    
    unfollowUser() {
      uni.showModal({
        title: '提示',
        content: '确定要取消关注吗？',
        success: (res) => {
          if (res.confirm) {
            uni.showLoading({ title: '处理中...' })
            removeFollowRelation({
              follower: this.currentUserNickname,
              followee: this.user.nickname
            })
              .then(() => {
                this.isFollowing = false
                uni.showToast({ title: '已取消关注', icon: 'success' })
              })
              .catch(() => {
                uni.showToast({ title: '取消失败', icon: 'none' })
              })
              .finally(() => {
                uni.hideLoading()
              })
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.user-detail-page {
  padding: 20px;
}

.news-card {
  background-color: #ffffff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.news-image {
  width: 100%;
  height: 180px;
  background-color: #f2f2f2;
}

.news-content {
  padding: 12px;
  font-size: 15px;
  font-weight: bold;
  color: #000000;
}

.news-title {
  font-size: 20px;
  color: #0d9ab9;
  margin-bottom: 6px;
  font-weight: bold;
}

.news-tags {
  margin-bottom: 8px;
}

.tag {
  display: inline-block;
  background-color: #e00a4a;
  padding: 6px 10px;
  border-radius: 12px;
  margin-right: 8px;
  font-size: 12px;
  color: #ffffff;
}

.news-author {
  font-size: 13px;
  color: #777;
  margin-bottom: 6px;
  display: block;
}

.news-desc {
  font-size: 14px;
  color: #6f6f00;
  line-height: 1.5;
}

/* 关注按钮样式 */
.follow-btn-container {
  padding: 0 20px;
}

.follow-btn {
  background-color: #07C160;
  color: white;
  border-radius: 5px;
  font-size: 16px;
  height: 45px;
  line-height: 45px;
  width: 100%;
  border: none;
}

.follow-btn:active {
  background-color: #06AD56;
}

/* 已关注状态样式 */
.follow-btn[disabled] {
  background-color: #EEEEEE;
  color: #999999;
}
</style>