<template>
  <view class="container">
    <view class="header-bg" :class="riskColorClass"></view>

    <view class="score-card shadow-card">
      <view class="score-header">
        <text class="score-title">AI 综合预估风险概率</text>
        <text class="score-date">{{ currentDate }}</text>
      </view>
      
      <view class="score-display">
        <view class="score-circle" :class="riskColorClass">
          <text class="score-value">{{ resultData.probability }}</text>
          <text class="score-unit">%</text>
        </view>
      </view>

      <view class="risk-level-box">
        <text class="risk-label">风险评级：</text>
        <text class="risk-tag" :class="riskColorClass">{{ resultData.riskLevel }}</text>
      </view>
      <text class="risk-desc">{{ resultData.riskDescription }}</text>
    </view>

    <view class="analysis-card shadow-card">
      <view class="card-title">
        <text class="icon-bar" :class="riskColorClass"></text>
        核心指标异常贡献度
      </view>
      <text class="sub-title">基于 AI 算法解析，以下指标对本次风险评估影响最大：</text>
      
      <view class="chart-container">
        <view class="bar-item" v-for="(item, index) in resultData.keyFactors" :key="index">
          <view class="bar-info">
            <text class="bar-name">{{ item.name }} ({{ item.value }})</text>
            <text class="bar-percent">{{ item.contribution }}%</text>
          </view>
          <view class="progress-track">
            <view class="progress-fill" :style="{ width: item.contribution + '%', backgroundColor: item.color }"></view>
          </view>
          <text class="bar-remark">{{ item.remark }}</text>
        </view>
      </view>
    </view>

    <view class="disease-card shadow-card" v-if="resultData.suspectedDisease">
      <view class="card-title">
        <text class="icon-bar" :class="riskColorClass"></text>
        高可疑疾病分型
      </view>
      <view class="disease-info">
        <view class="info-row">
          <text class="info-label">疑似病种：</text>
          <text class="info-value highlight">{{ resultData.suspectedDisease.name }}</text>
        </view>
        <view class="info-row" v-if="resultData.suspectedDisease.gene">
          <text class="info-label">关键变异基因：</text>
          <text class="info-value">{{ resultData.suspectedDisease.gene }}</text>
        </view>
      </view>
    </view>

    <view class="advice-card shadow-card">
      <view class="card-title">
        <text class="icon-bar" :class="riskColorClass"></text>
        健康建议与临床指导
      </view>
      <view class="advice-list">
        <view class="advice-item" v-for="(advice, index) in resultData.advices" :key="index">
          <text class="advice-index">{{ index + 1 }}</text>
          <text class="advice-text">{{ advice }}</text>
        </view>
      </view>
    </view>

    <view class="disclaimer">
      <text class="disclaimer-text">免责声明：本报告由数智肝循 AI 引擎基于 XGBoost 算法生成。结果仅供参考，不作为最终临床诊断依据。如有身体不适，请务必前往正规医疗机构就诊。</text>
    </view>

    <view class="action-footer">
      <button class="btn-outline" @click="goBack">返回首页</button>
      <button class="btn-primary" :class="riskColorClass" @click="bookAppointment">预约专科复诊</button>
    </view>
  </view>
</template>

<script>
import { fetchAssessmentResult } from '@/api/assessment'

export default {
  data() {
    return {
      currentDate: '',
      resultData: {
        probability: 0,
        riskLevel: '',
        riskDescription: '',
        suspectedDisease: null,
        keyFactors: [],
        advices: []
      }
    };
  },
  computed: {
    riskColorClass() {
      if (this.resultData.riskLevel === '高风险') return 'theme-danger';
      if (this.resultData.riskLevel === '中风险') return 'theme-warning';
      return 'theme-safe';
    }
  },
  onLoad() {
    this.initDate();
    this.loadAssessmentResult();
  },
  methods: {
    initDate() {
      const date = new Date();
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      this.currentDate = `${year}-${month}-${day}`
    },
    loadAssessmentResult() {
      fetchAssessmentResult().then((res) => {
        this.resultData = (res && res.data) || this.resultData;
      });
    },
    goBack() {
      uni.switchTab({
        url: '/pages/index'
      });
    },
    bookAppointment() {
      uni.showToast({
        title: '预约入口开发中',
        icon: 'none'
      });
    }
  }
};
</script>

<style scoped>
.container {
  min-height: 100vh;
  background-color: #f5f7fa;
  padding-bottom: 140rpx; /* 为底部悬浮按钮留出空间 */
  position: relative;
}

/* 顶部动态背景色 */
.header-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 300rpx;
  border-bottom-left-radius: 40rpx;
  border-bottom-right-radius: 40rpx;
  z-index: 0;
}
.header-bg.theme-danger { background: linear-gradient(135deg, #fa3534 0%, #c93532 100%); }
.header-bg.theme-warning { background: linear-gradient(135deg, #ff9900 0%, #e68a00 100%); }
.header-bg.theme-safe { background: linear-gradient(135deg, #19be6b 0%, #159e59 100%); }

/* 卡片通用阴影和布局 */
.shadow-card {
  position: relative;
  z-index: 1;
  background-color: #ffffff;
  border-radius: 20rpx;
  margin: 0 30rpx 30rpx;
  padding: 40rpx 30rpx;
  box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.05);
}

/* --- 核心风险评分卡片 --- */
.score-card {
  margin-top: 40rpx;
  text-align: center;
}
.score-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40rpx;
}
.score-title { font-size: 30rpx; color: #666; font-weight: bold; }
.score-date { font-size: 24rpx; color: #999; }

.score-display {
  display: flex;
  justify-content: center;
  margin-bottom: 30rpx;
}
.score-circle {
  width: 240rpx;
  height: 240rpx;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 12rpx solid #f0f0f0; /* 默认底圈 */
  box-shadow: 0 0 20rpx rgba(0,0,0,0.05) inset;
}
/* 动态边框色 */
.score-circle.theme-danger { border-color: #fa3534; color: #fa3534; }
.score-circle.theme-warning { border-color: #ff9900; color: #ff9900; }
.score-circle.theme-safe { border-color: #19be6b; color: #19be6b; }

.score-value { font-size: 72rpx; font-weight: 900; }
.score-unit { font-size: 36rpx; margin-top: 20rpx; margin-left: 4rpx; }

.risk-level-box { margin-bottom: 16rpx; }
.risk-label { font-size: 30rpx; color: #333; }
.risk-tag {
  font-size: 28rpx;
  font-weight: bold;
  padding: 6rpx 20rpx;
  border-radius: 8rpx;
}
.risk-tag.theme-danger { background-color: rgba(250, 53, 52, 0.1); color: #fa3534; }
.risk-tag.theme-warning { background-color: rgba(255, 153, 0, 0.1); color: #ff9900; }
.risk-tag.theme-safe { background-color: rgba(25, 190, 107, 0.1); color: #19be6b; }

.risk-desc {
  font-size: 26rpx;
  color: #666;
  line-height: 1.6;
  text-align: left;
  display: block;
  margin-top: 20rpx;
  background-color: #f8f9fa;
  padding: 20rpx;
  border-radius: 12rpx;
}

/* --- 卡片通用标题 --- */
.card-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
  display: flex;
  align-items: center;
}
.icon-bar {
  width: 8rpx;
  height: 32rpx;
  border-radius: 4rpx;
  margin-right: 16rpx;
}
.icon-bar.theme-danger { background-color: #fa3534; }
.icon-bar.theme-warning { background-color: #ff9900; }
.icon-bar.theme-safe { background-color: #19be6b; }
.sub-title { font-size: 24rpx; color: #999; margin-bottom: 30rpx; display: block; }

/* --- 条形图表区域 --- */
.chart-container { margin-top: 20rpx; }
.bar-item { margin-bottom: 36rpx; }
.bar-item:last-child { margin-bottom: 0; }
.bar-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12rpx;
}
.bar-name { font-size: 26rpx; color: #333; font-weight: 500; }
.bar-percent { font-size: 26rpx; font-weight: bold; color: #666; }

.progress-track {
  width: 100%;
  height: 16rpx;
  background-color: #ebedf0;
  border-radius: 8rpx;
  overflow: hidden;
  margin-bottom: 12rpx;
}
.progress-fill {
  height: 100%;
  border-radius: 8rpx;
  transition: width 1s ease-in-out;
}
.bar-remark { font-size: 22rpx; color: #999; }

/* --- 疑似病种 --- */
.disease-info { background-color: rgba(250, 53, 52, 0.05); padding: 24rpx; border-radius: 12rpx; }
.info-row { display: flex; margin-bottom: 12rpx; }
.info-row:last-child { margin-bottom: 0; }
.info-label { font-size: 28rpx; color: #666; width: 180rpx; }
.info-value { font-size: 28rpx; color: #333; font-weight: 500; flex: 1; }
.info-value.highlight { color: #fa3534; font-weight: bold; font-size: 30rpx; }

/* --- 健康建议 --- */
.advice-list { padding-top: 10rpx; }
.advice-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 24rpx;
}
.advice-item:last-child { margin-bottom: 0; }
.advice-index {
  width: 36rpx;
  height: 36rpx;
  background-color: #e6f1fc;
  color: #2b85e4;
  font-size: 22rpx;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  margin-right: 16rpx;
  flex-shrink: 0;
  margin-top: 4rpx;
}
.advice-text { font-size: 26rpx; color: #333; line-height: 1.6; }

/* --- 免责声明 --- */
.disclaimer { padding: 0 40rpx 40rpx; }
.disclaimer-text { font-size: 22rpx; color: #c0c4cc; line-height: 1.5; text-align: justify; }

/* --- 底部固定操作栏 --- */
.action-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 120rpx;
  background-color: #ffffff;
  box-shadow: 0 -4rpx 16rpx rgba(0,0,0,0.05);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30rpx;
  box-sizing: border-box;
  z-index: 99;
}
.btn-outline {
  width: 30%;
  height: 80rpx;
  line-height: 80rpx;
  font-size: 28rpx;
  color: #666;
  background-color: #f8f9fa;
  border-radius: 40rpx;
}
.btn-outline::after { border: none; }

.btn-primary {
  width: 65%;
  height: 80rpx;
  line-height: 80rpx;
  font-size: 30rpx;
  color: #fff;
  font-weight: bold;
  border-radius: 40rpx;
}
.btn-primary::after { border: none; }
.btn-primary.theme-danger { background-color: #fa3534; box-shadow: 0 8rpx 16rpx rgba(250, 53, 52, 0.3); }
.btn-primary.theme-warning { background-color: #ff9900; box-shadow: 0 8rpx 16rpx rgba(255, 153, 0, 0.3); }
.btn-primary.theme-safe { background-color: #19be6b; box-shadow: 0 8rpx 16rpx rgba(25, 190, 107, 0.3); }
</style>
