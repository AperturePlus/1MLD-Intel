<template>
  <view class="page-container">
    <view class="cu-bar search bg-white fixed-top">
      <view class="search-form round">
        <text class="cuIcon-search"></text>
        <input type="text" placeholder="输入搜索的关键词" confirm-type="search" v-model="query" @confirm="searchArticles" />
      </view>
      <view class="action">
        <button class="cu-btn bg-gradual-green shadow-blur round" @click="searchArticles">搜索</button>
      </view>
    </view>

    <view style="height: 60px;"></view>

    <view class="article-card shadow" v-for="(item, index) in articleList" :key="index"
      style="background-color: #ffffff; width: 92%; margin: 15px auto; display: flex; justify-content: space-between; align-items: center; border-radius: 12px;">
      <view class="left1" style="width: 100%; padding: 15px;">
        <view>
          <view style="font-size: 16px; font-weight: bold; color: #333; margin-bottom: 8px;">{{ item.title }}</view>
          <view style="font-size: 13px; color: #666; line-height: 1.5;">{{ item.solution }}</view>
        </view>
      </view>
    </view>

    <view v-if="articleList.length === 0" style="text-align: center; margin-top: 50px; color: #999;">
      <text>暂无相关资讯</text>
    </view>
  </view>
</template>

<script>
import { listArticles, queryArticles } from '@/api/system/advice'

export default {
  data() {
    return {
      query: '',
      articleList: []
    }
  },
  onLoad() {
    this.searchArticles()
  },
  methods: {
    searchArticles() {
      const keyword = this.query.trim()
      listArticles({ param: keyword }).then((res) => {
        this.articleList = (res && res.data) || []
      })
    }
  }
}
</script>

<style>
page {
  background-color: #f5f6f7;
}

.fixed-top {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 999;
}

.shadow {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}
</style>
