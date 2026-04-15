<template>
  <view class="user-management">
    <view class="search-bar">
      <uni-search-bar
        placeholder="搜索用户昵称"
        radius="100"
        @confirm="handleSearch"
        cancelButton="none"
        v-model="searchKeyword"
      />
    </view>

    <view class="user-list" v-if="userList.length > 0">
      <view
        class="user-card"
        v-for="(user, index) in userList"
        :key="user.id"
      >
        <image
          class="user-avatar"
          :src="getImagePath(user.imageUrl)"
        />
        <view class="user-info">
          <text class="user-name">{{ user.nickname }}</text>
          <text class="user-phone">{{ user.phone }}</text>
          <text
            class="user-status"
            :class="{ banned: user.role === '2' }"
          >
            {{ user.role === '2' ? '已封禁' : '正常' }}
          </text>
        </view>
        <button
          class="ban-btn"
          @click="handleBanToggle(user.id, index)"
          :class="{ 'unban-btn': user.role === '2' }"
        >
          {{ user.role === '2' ? '解封' : '封禁' }}
        </button>
      </view>
    </view>
    <view v-else class="no-data">暂无用户数据</view>
  </view>
</template>

<script>
import config from '@/config'
import { banUserById, listManageableUsers, unbanUserById } from '@/api/system/admin'

export default {
  data() {
    return {
      userList: [],
      searchKeyword: ''
    }
  },
  mounted() {
    this.fetchUserList()
  },
  methods: {
    fetchUserList() {
      listManageableUsers(this.searchKeyword.trim()).then((res) => {
        this.userList = (res && res.data) || []
      })
    },
    handleSearch() {
      this.fetchUserList()
    },
    getImagePath(filename) {
      return `${config.baseUrl}/userinfo/${filename}`
    },
    handleBanToggle(userId, index) {
      const user = this.userList[index]
      const action = user.role === '2' ? 'unban' : 'ban'
      const confirmMsg = action === 'ban' ? '确定要封禁该用户吗？' : '确定要解封该用户吗？'
      uni.showModal({
        title: '确认操作',
        content: confirmMsg,
        success: (res) => {
          if (!res.confirm) {
            return
          }
          const request = action === 'ban' ? banUserById(userId) : unbanUserById(userId)
          request.then(() => {
            uni.showToast({
              title: action === 'ban' ? '已封禁' : '已解封',
              icon: 'success'
            })
            this.userList[index].role = action === 'ban' ? '2' : '0'
          })
        }
      })
    }
  }
}
</script>

<style>
.user-management {
  padding: 20rpx;
}

.search-bar {
  margin-bottom: 20rpx;
}

.user-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.user-card {
  display: flex;
  align-items: center;
  padding: 15rpx;
  border: 1px solid #ddd;
  border-radius: 10rpx;
}

.user-avatar {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  margin-right: 20rpx;
}

.user-info {
  flex: 1;
}

.user-name {
  font-weight: bold;
  font-size: 28rpx;
}

.user-phone {
  color: #666;
  margin-top: 6rpx;
}

.user-status {
  margin-top: 6rpx;
  font-weight: 600;
}

.user-status.banned {
  color: red;
}

.ban-btn {
  padding: 10rpx 20rpx;
  border-radius: 20rpx;
  background-color: #4caf50;
  color: white;
  font-weight: bold;
}

.ban-btn.unban-btn {
  background-color: #f44336;
}

.no-data {
  text-align: center;
  color: #888;
  margin-top: 50rpx;
}
</style>
