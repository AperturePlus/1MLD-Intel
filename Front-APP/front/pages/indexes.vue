<template>
  <view class="container">
    <view class="header">
      <text class="title">遗传代谢性肝病筛查评估</text>
      <text class="subtitle">请准确填写以下临床及检验指标，以便系统进行精准的 AI 辅助分析评估。</text>
    </view>

    <view class="form-wrapper">
      <view class="form-card">
        <view class="card-title">
          <text class="title-icon">|</text>基本信息
        </view>
        <view class="form-item">
          <text class="label">患者姓名 <text class="required">*</text></text>
          <input class="input-box" v-model="formData.patientName" placeholder="请输入真实姓名" placeholder-class="placeholder-style" />
        </view>
        <view class="form-item">
          <text class="label">联系方式 <text class="required">*</text></text>
          <input class="input-box" type="number" maxlength="11" v-model="formData.phone" placeholder="请输入手机号码" placeholder-class="placeholder-style" />
        </view>
        <view class="form-item">
          <text class="label">性别 <text class="required">*</text></text>
          <radio-group class="radio-group" @change="radioChange($event, 'gender')">
            <label class="radio-label"><radio value="1" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.gender === '1'" /> 男</label>
            <label class="radio-label"><radio value="2" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.gender === '2'" /> 女</label>
          </radio-group>
        </view>
        <view class="form-item">
          <text class="label">年龄 <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="number" maxlength="3" v-model="formData.age" placeholder="请输入年龄" placeholder-class="placeholder-style" />
            <text class="unit">岁</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">身高 <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.height" placeholder="例如: 175" placeholder-class="placeholder-style" />
            <text class="unit">cm</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">体重 <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.weight" placeholder="例如: 65.5" placeholder-class="placeholder-style" />
            <text class="unit">kg</text>
          </view>
        </view>
      </view>

      <view class="form-card">
        <view class="card-title">
          <text class="title-icon">|</text>常规肝脏与生化功能
          <text class="title-tips">(必填，请参照近期报告单)</text>
        </view>
        <view class="form-item">
          <text class="label">谷丙转氨酶 (ALT) <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.alt" placeholder="例如: 35" placeholder-class="placeholder-style" />
            <text class="unit">U/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">谷草转氨酶 (AST) <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.ast" placeholder="例如: 40" placeholder-class="placeholder-style" />
            <text class="unit">U/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">总胆红素 (TBIL) <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.bilirubin" placeholder="例如: 15.2" placeholder-class="placeholder-style" />
            <text class="unit">μmol/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">白蛋白 (ALB) <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.albumin" placeholder="例如: 42" placeholder-class="placeholder-style" />
            <text class="unit">g/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">谷氨酰转移酶 (GGT) <text class="required">*</text></text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.ggt" placeholder="例如: 45" placeholder-class="placeholder-style" />
            <text class="unit">U/L</text>
          </view>
        </view>
      </view>

      <view class="form-card">
        <view class="card-title">
          <text class="title-icon">|</text>特异性代谢标志物
          <text class="title-tips">(选填，有相关检验则填写)</text>
        </view>
        <view class="form-item">
          <text class="label">血清铜蓝蛋白 (CER)</text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.ceruloplasmin" placeholder="筛查肝豆状核变性" placeholder-class="placeholder-style" />
            <text class="unit">g/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">24小时尿铜</text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.urinaryCopper" placeholder="筛查肝豆状核变性" placeholder-class="placeholder-style" />
            <text class="unit">μg/24h</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">血清铁蛋白 (Ferritin)</text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.ferritin" placeholder="筛查血色病" placeholder-class="placeholder-style" />
            <text class="unit">μg/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">空腹血糖 (GLU)</text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.glucose" placeholder="筛查糖原累积病" placeholder-class="placeholder-style" />
            <text class="unit">mmol/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">血氨 (Ammonia)</text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.ammonia" placeholder="筛查尿素循环障碍等" placeholder-class="placeholder-style" />
            <text class="unit">μmol/L</text>
          </view>
        </view>
        <view class="form-item">
          <text class="label">甲胎蛋白 (AFP)</text>
          <view class="input-group">
            <input class="input-box flex-1" type="digit" v-model="formData.afp" placeholder="筛查酪氨酸血症等" placeholder-class="placeholder-style" />
            <text class="unit">ng/mL</text>
          </view>
        </view>
      </view>

      <view class="form-card">
        <view class="card-title">
          <text class="title-icon">|</text>临床症状与辅助检查
        </view>
        <view class="form-item">
          <text class="label">是否出现黄疸(皮肤/巩膜发黄)？</text>
          <radio-group class="radio-group" @change="radioChange($event, 'jaundice')">
            <label class="radio-label"><radio value="1" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.jaundice === '1'" /> 是</label>
            <label class="radio-label"><radio value="0" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.jaundice === '0'" /> 否</label>
          </radio-group>
        </view>
        <view class="form-item">
          <text class="label">影像学(B超等)是否提示肝/脾肿大？</text>
          <radio-group class="radio-group" @change="radioChange($event, 'hepatosplenomegaly')">
            <label class="radio-label"><radio value="1" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.hepatosplenomegaly === '1'" /> 是</label>
            <label class="radio-label"><radio value="0" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.hepatosplenomegaly === '0'" /> 否</label>
          </radio-group>
        </view>
        <view class="form-item">
          <text class="label">是否有神经/精神系统异常(如震颤、肌张力异常)？</text>
          <radio-group class="radio-group" @change="radioChange($event, 'neuroSymptoms')">
            <label class="radio-label"><radio value="1" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.neuroSymptoms === '1'" /> 是</label>
            <label class="radio-label"><radio value="0" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.neuroSymptoms === '0'" /> 否</label>
          </radio-group>
        </view>
        <view class="form-item">
          <text class="label">眼科裂隙灯检查是否发现 K-F 角膜环？</text>
          <radio-group class="radio-group" @change="radioChange($event, 'kfRing')">
            <label class="radio-label"><radio value="1" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.kfRing === '1'" /> 是</label>
            <label class="radio-label"><radio value="0" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.kfRing === '0'" /> 否</label>
            <label class="radio-label"><radio value="-1" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.kfRing === '-1'" /> 未检查</label>
          </radio-group>
        </view>
        <view class="form-item">
          <text class="label">是否已进行基因靶向测序或全外显子测序？</text>
          <radio-group class="radio-group" @change="radioChange($event, 'hasGeneticData')">
            <label class="radio-label"><radio value="1" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.hasGeneticData === '1'" /> 已测序</label>
            <label class="radio-label"><radio value="0" color="#2b85e4" style="transform:scale(0.8)" :checked="formData.hasGeneticData === '0'" /> 未测序</label>
          </radio-group>
        </view>
      </view>

      <view class="consent-section">
        <label class="consent-label" @click="toggleConsent">
          <radio :checked="formData.dataConsent" color="#2b85e4" style="transform:scale(0.7)" />
          <text class="consent-text">我已阅读并同意<text class="link">《知情同意书》</text>，授权系统对上述数据进行隐私脱敏处理并用于辅助风险分析。</text>
        </label>
      </view>

      <button class="submit-btn" :class="{ 'btn-disabled': !isFormValid }" @click="submitForm">
        提交评估
      </button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        // 1. 基本信息
        patientName: '',
        phone: '',
        gender: '1',
        age: '',     
        height: '',  
        weight: '',  
        
        // 2. 核心肝脏与生化功能 (必填)
        alt: '',
        ast: '',       // 新增: 谷草转氨酶
        bilirubin: '',
        albumin: '',   // 新增: 白蛋白
        ggt: '',       // 新增: 谷氨酰转移酶
        
        // 3. 特异性代谢标志物 (选填)
        ceruloplasmin: '', 
        urinaryCopper: '', // 新增: 24h尿铜
        ferritin: '',      // 新增: 铁蛋白
        glucose: '',       // 新增: 空腹血糖
        ammonia: '',       // 新增: 血氨
        afp: '',           // 新增: 甲胎蛋白
        
        // 4. 临床症状与体征
        jaundice: '0',
        hepatosplenomegaly: '0', // 修改: 明确为肝脾肿大
        neuroSymptoms: '0',      // 新增: 神经系统症状 (针对肝豆)
        kfRing: '-1',            // 新增: K-F环 (-1代表未检查)
        hasGeneticData: '0',
        
        // 5. 合规
        dataConsent: false
      }
    };
  },
  computed: {
    // 校验逻辑：仅校验基本信息与常规肝功，代谢特异性指标允许留空
    isFormValid() {
      return this.formData.patientName.trim() !== '' && 
             this.formData.phone.trim().length === 11 &&
             this.formData.age !== '' &&
             this.formData.height !== '' &&
             this.formData.weight !== '' &&
             this.formData.alt !== '' &&
             this.formData.ast !== '' &&       // 校验新增的必填项
             this.formData.bilirubin !== '' &&
             this.formData.albumin !== '' &&   // 校验新增的必填项
             this.formData.ggt !== '' &&       // 校验新增的必填项
             this.formData.dataConsent;
    }
  },
  methods: {
    radioChange(e, field) {
      this.formData[field] = e.detail.value;
    },
    toggleConsent() {
      this.formData.dataConsent = !this.formData.dataConsent;
    },
    submitForm() {
      if (!this.formData.dataConsent) {
        uni.showToast({ title: '请先勾选知情同意书', icon: 'none' });
        return;
      }
      if (!this.isFormValid) {
        uni.showToast({ title: '请完整填写带星号(*)的必填指标及信息', icon: 'none' });
        return;
      }

      uni.showLoading({ title: '正在处理脱敏...' });
      
      setTimeout(() => {
        uni.hideLoading();
        console.log('前端完成初步脱敏打包准备流转的数据:', this.formData);
        uni.showToast({ title: '数据提交成功', icon: 'success' });
      }, 1500);
    }
  }
};
</script>

<style scoped>
/* 原有的样式完全可以复用，无需改动，保持了 UI 的美观一致性 */
.container {
  min-height: 100vh;
  background-color: #f4f6f9;
  padding-bottom: 40rpx;
}

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

.form-wrapper {
  padding: 0 30rpx;
  margin-top: -40rpx;
}

.form-card {
  background-color: #ffffff;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.04);
}

.card-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333333;
  margin-bottom: 30rpx;
  display: flex;
  align-items: center;
}

.title-icon {
  color: #2b85e4;
  font-weight: 900;
  margin-right: 12rpx;
  font-size: 28rpx;
}

.title-tips {
  font-size: 22rpx;
  color: #999999;
  font-weight: normal;
  margin-left: 10rpx;
}

.form-item {
  margin-bottom: 36rpx;
}

.form-item:last-child {
  margin-bottom: 0;
}

.label {
  display: block;
  font-size: 28rpx;
  color: #333333;
  margin-bottom: 16rpx;
}

.required {
  color: #fa3534;
  margin-left: 6rpx;
}

.input-box {
  background-color: #f8f9fa;
  border: 1px solid #ebedf0;
  border-radius: 12rpx;
  padding: 0 24rpx;
  height: 80rpx;
  font-size: 28rpx;
  color: #333;
  transition: all 0.3s;
}

.input-box:focus {
  border-color: #2b85e4;
  background-color: #ffffff;
}

.placeholder-style {
  color: #c0c4cc;
}

.input-group {
  display: flex;
  align-items: center;
  background-color: #f8f9fa;
  border: 1px solid #ebedf0;
  border-radius: 12rpx;
  padding-right: 24rpx;
}

.input-group .input-box {
  border: none;
  background-color: transparent;
}

.flex-1 {
  flex: 1;
}

.unit {
  font-size: 26rpx;
  color: #666666;
  margin-left: 10rpx;
}

.radio-group {
  display: flex;
  gap: 40rpx;
}

.radio-label {
  display: flex;
  align-items: center;
  font-size: 28rpx;
  color: #333333;
}

.consent-section {
  padding: 0 10rpx 30rpx;
}

.consent-label {
  display: flex;
  align-items: flex-start;
}

.consent-text {
  font-size: 24rpx;
  color: #666666;
  line-height: 1.6;
  margin-left: 8rpx;
  flex: 1;
}

.link {
  color: #2b85e4;
}

.submit-btn {
  background: linear-gradient(to right, #2b85e4, #3c9cff);
  color: #ffffff;
  border-radius: 40rpx;
  font-size: 32rpx;
  font-weight: bold;
  box-shadow: 0 8rpx 20rpx rgba(43, 133, 228, 0.3);
  border: none;
}

.submit-btn::after {
  border: none;
}

.btn-disabled {
  background: #c8c9cc;
  box-shadow: none;
  color: #f2f3f5;
}
</style>