<template>
  <view class="pwd-retrieve-container">
	<uni-forms ref="form" :value="user" labelWidth="100px">
	  <uni-forms-item name="newHeight" label="身高">
	    <uni-easyinput type="text" v-model="user.newHeight" placeholder="请输入身高（cm）" />
	  </uni-forms-item>
	  <uni-forms-item name="newWeight" label="体重">
	    <uni-easyinput type="text" v-model="user.newWeight" placeholder="请输入体重（kg）" />
	  </uni-forms-item>
	  <uni-forms-item name="newGFR" label="肾小球滤过率">
	    <uni-easyinput type="text" v-model="user.newGFR" placeholder="请输入GFR（mL/min）" />
	  </uni-forms-item>
	  <uni-forms-item name="newComplications" label="并发症情况">
	    <uni-easyinput type="text" v-model="user.newComplications" placeholder="请描述当前并发症情况" />
	  </uni-forms-item>
	  <button type="primary" @click="submit">提交</button>
	</uni-forms>
  </view>
</template>

<script>
  import { updateDietPreference } from '@/api/system/user'

  export default {
    data() {
      return {
        user: {
          newHeight: '',
          newWeight: '',
          newGFR: '',
          newComplications: ''
        },
        rules: {
          newHeight: {
            rules: [{
              required: true,
              errorMessage: '身高不能为空'
            }]
          },
          newWeight: {
            rules: [{
                required: true,
                errorMessage: '体重不能为空'
              }
            ]
          },
          newGFR: {
            rules: [{
                required: true,
                errorMessage: 'GFR 不能为空'
              }
            ]
          },
          newComplications: {
            rules: [{
              required: true,
              errorMessage: '并发症情况不能为空'
            }]
          }
        }
      }
    },
    onReady() {
      this.$refs.form.setRules(this.rules)
    },
    methods: {
      submit() {
        this.$refs.form.validate().then(res => {
          updateDietPreference({
            height: this.user.newHeight,
            weight: this.user.newWeight,
            gfr: this.user.newGFR,
            complications: this.user.newComplications
          }).then(response => {
            this.$modal.msgSuccess("修改成功")
          })
        })
      }
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }

  .pwd-retrieve-container {
    padding-top: 36rpx;
    padding: 15px;
  }
</style>
