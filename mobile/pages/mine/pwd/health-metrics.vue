<template>
  <view class="pwd-retrieve-container">
	<uni-forms ref="form" :value="user" labelWidth="80px">
	  <uni-forms-item name="newHeight" label="新">
	    <uni-easyinput type="height" v-model="user.newHeight" placeholder="请输入新身高" />
	  </uni-forms-item>
	  <uni-forms-item name="newWeight" label="新">
	    <uni-easyinput type="weight" v-model="user.newWeight" placeholder="请输入新体重" />
	  </uni-forms-item>
	  <uni-forms-item name="newGFR" label="新">
	    <uni-easyinput type="GFR" v-model="user.newGFR" placeholder="请输入GFR" />
	  </uni-forms-item>
	  <uni-forms-item name="newComplications" label="新">
	    <uni-easyinput type="complications" v-model="user.newComplications" placeholder="请输入新并发症情况" />
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
