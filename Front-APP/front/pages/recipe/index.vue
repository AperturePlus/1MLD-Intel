<template>
  <view class="container">
    <view class="header">
      <text class="title">AI 智能营养处方</text>
      <text class="subtitle">基于您的临床指标与遗传代谢性肝病特征，AI 已为您生成今日专属的低铜护肝食谱。</text>
    </view>

    <view class="content-wrapper">
      <view class="summary-card">
        <view class="summary-header">
          <view class="iconfont icon-ai"></view>
          <text class="summary-title">AI 膳食评估原则</text>
        </view>
        <view class="summary-text">
          <text class="dot">•</text> 控制植物蛋白，补充高生物价优质蛋白（如蛋清、瘦肉）。
        </view>
        <view class="summary-text">
          <text class="dot">•</text> 采用麦淀粉/藕粉替代部分主食，提供充足热量防营养不良。
        </view>
        <view class="summary-text">
          <text class="dot">•</text> 严格限制高钾（如香蕉、坚果）及高磷（如蛋黄、内脏）食物。
        </view>
      </view>

      <view class="meal-card">
        <view class="card-title">
          <view class="title-left">
            <text class="title-icon">|</text> 营养早餐
          </view>
          <text class="calories-total">约 450 kcal</text>
        </view>
        
        <view class="food-item" v-for="(item, index) in breakfast" :key="index">
          <image class="food-img" :src="item.img" mode="aspectFill"></image>
          <view class="food-info">
            <view class="food-name">{{ item.name }}</view>
            <view class="food-desc">{{ item.desc }}</view>
            <view class="tags">
              <text class="tag" v-for="(tag, tIndex) in item.tags" :key="tIndex">{{ tag }}</text>
            </view>
          </view>
        </view>
      </view>

      <view class="meal-card">
        <view class="card-title">
          <view class="title-left">
            <text class="title-icon">|</text> 活力午餐
          </view>
          <text class="calories-total">约 600 kcal</text>
        </view>
        
        <view class="food-item" v-for="(item, index) in lunch" :key="index">
          <image class="food-img" :src="item.img" mode="aspectFill"></image>
          <view class="food-info">
            <view class="food-name">{{ item.name }}</view>
            <view class="food-desc">{{ item.desc }}</view>
            <view class="tags">
              <text class="tag blue-tag" v-for="(tag, tIndex) in item.tags" :key="tIndex">{{ tag }}</text>
            </view>
          </view>
        </view>
      </view>

      <view class="meal-card">
        <view class="card-title">
          <view class="title-left">
            <text class="title-icon">|</text> 轻盈晚餐
          </view>
          <text class="calories-total">约 500 kcal</text>
        </view>
        
        <view class="food-item" v-for="(item, index) in dinner" :key="index">
          <image class="food-img" :src="item.img" mode="aspectFill"></image>
          <view class="food-info">
            <view class="food-name">{{ item.name }}</view>
            <view class="food-desc">{{ item.desc }}</view>
            <view class="tags">
              <text class="tag green-tag" v-for="(tag, tIndex) in item.tags" :key="tIndex">{{ tag }}</text>
            </view>
          </view>
        </view>
      </view>

      <view class="footer-tips">
        <text class="tips-text">⚠️ 医疗免责声明：本食谱由 AI 基于医学指南生成，仅供参考。具体执行请结合您的主治医师或临床营养师的当面指导。</text>
      </view>
    </view>
  </view>
</template>

<script>
import { fetchDailyRecipe } from '@/api/recipe'

export default {
  data() {
    return {
      breakfast: [],
      lunch: [],
      dinner: []
    };
  },
  onLoad() {
    this.loadRecipe()
  },
  methods: {
    loadRecipe() {
      fetchDailyRecipe().then((res) => {
        const data = (res && res.data) || {}
        this.breakfast = data.breakfast || []
        this.lunch = data.lunch || []
        this.dinner = data.dinner || []
      })
    }
  }
};
</script>

<style scoped>
/* 全局背景 */
.container {
  min-height: 100vh;
  background-color: #f4f6f9;
  padding-bottom: 60rpx;
}

/* 头部样式（与表单页保持一致） */
.header {
  background: linear-gradient(135deg, #2b85e4 0%, #005eaa 100%);
  padding: 60rpx 40rpx 80rpx;
  border-bottom-left-radius: 40rpx;
  border-bottom-right-radius: 40rpx;
}

.header .title {
  display: block;
  font-size: 44rpx;
  font-weight: bold;
  color: #ffffff;
  margin-bottom: 16rpx;
}

.header .subtitle {
  display: block;
  font-size: 26rpx;
  color: rgba(255, 255, 255, 0.85);
  line-height: 1.5;
}

/* 内容包装器（向上偏移产生悬浮层叠效果） */
.content-wrapper {
  padding: 0 30rpx;
  margin-top: -40rpx;
}

/* AI 摘要卡片（特殊样式） */
.summary-card {
  background: linear-gradient(to right, #ffffff, #f0f7ff);
  border: 1px solid #d6eaff;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 4rpx 16rpx rgba(43, 133, 228, 0.08);
}

.summary-header {
  display: flex;
  align-items: center;
  margin-bottom: 16rpx;
}

.summary-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #2b85e4;
}

.summary-text {
  font-size: 26rpx;
  color: #555555;
  line-height: 1.6;
  margin-top: 8rpx;
  display: flex;
}

.dot {
  color: #2b85e4;
  margin-right: 12rpx;
  font-weight: bold;
}

/* 菜谱卡片 */
.meal-card {
  background-color: #ffffff;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.04);
}

/* 卡片标题行 */
.card-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
  padding-bottom: 20rpx;
  border-bottom: 1px dashed #eeeeee;
}

.title-left {
  font-size: 32rpx;
  font-weight: bold;
  color: #333333;
  display: flex;
  align-items: center;
}

.title-icon {
  color: #2b85e4;
  font-weight: 900;
  margin-right: 12rpx;
  font-size: 28rpx;
}

.calories-total {
  font-size: 26rpx;
  color: #fa3534; /* 红色醒目提示热量 */
  font-weight: bold;
  background-color: #fef0f0;
  padding: 4rpx 16rpx;
  border-radius: 20rpx;
}

/* 食物列表项布局 */
.food-item {
  display: flex;
  margin-bottom: 30rpx;
}

.food-item:last-child {
  margin-bottom: 0;
}

/* 食物图片 */
.food-img {
  width: 160rpx;
  height: 160rpx;
  border-radius: 16rpx;
  margin-right: 24rpx;
  background-color: #f5f5f5;
  flex-shrink: 0;
}

/* 食物信息 */
.food-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.food-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #333333;
}

.food-desc {
  font-size: 24rpx;
  color: #888888;
  line-height: 1.5;
  margin: 10rpx 0;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* 限制两行 */
  overflow: hidden;
}

/* 标签组 */
.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 12rpx;
}

.tag {
  font-size: 20rpx;
  padding: 4rpx 12rpx;
  border-radius: 8rpx;
  background-color: #fff5e6;
  color: #ff9900;
}

.blue-tag {
  background-color: #e6f3ff;
  color: #2b85e4;
}

.green-tag {
  background-color: #eafff0;
  color: #19be6b;
}

/* 底部免责声明 */
.footer-tips {
  padding: 20rpx 10rpx;
  text-align: center;
}

.tips-text {
  font-size: 22rpx;
  color: #aaaaaa;
  line-height: 1.6;
}
</style>