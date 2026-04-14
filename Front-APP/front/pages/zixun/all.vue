<template>
  <view class="page-container">
    <view class="cu-bar search bg-white fixed-top">
      <view class="search-form round">
        <text class="cuIcon-search"></text>
        <input type="text" placeholder="输入搜索的关键词" confirm-type="search" v-model="param" @confirm="search"/>
      </view>
      <view class="action">
        <button class="cu-btn bg-gradual-green shadow-blur round" @click="search">搜索</button>
      </view>
    </view>

    <view style="height: 60px;"></view>

    <view class="zixun shadow" v-for="(item, index) in zixunlist" :key="index"
          style="background-color: #ffffff; width: 92%; margin: 15px auto; display: flex; justify-content: space-between; align-items: center; border-radius: 12px;">
      <view class="left1" style="width: 100%; padding: 15px;">
        <view>
          <view style="font-size: 16px; font-weight: bold; color: #333; margin-bottom: 8px;">{{ item.title }}</view>
          <view style="font-size: 13px; color: #666; line-height: 1.5;">{{ item.solution }}</view>
        </view>
      </view>
    </view>
    
    <view v-if="zixunlist.length === 0" style="text-align: center; margin-top: 50px; color: #999;">
      <text>暂无相关资讯</text>
    </view>

  </view>
</template>

<script>
// 移除了后端的 import { listAdvice } from "@/api/system/advice.js"

export default {
  data() {
    return {
      param: "",
      zixunlist: [],
      // 3. 扩充了纯前端的遗传代谢性肝病资讯库
      mockAdviceList: [
        {
          title: '肝豆状核变性（Wilson病）的日常饮食指南',
          solution: '限制铜的摄入是关键。日常应尽量避免食用富含高铜的食物，例如动物内脏、坚果、巧克力、蘑菇和贝类等，并定期复查血清铜蓝蛋白。'
        },
        {
          title: '糖原累积病(GSD)患者如何科学预防低血糖？',
          solution: '建议采取少食多餐的策略，夜间可遵医嘱使用生玉米淀粉来缓慢释放葡萄糖，维持血糖稳定，绝对避免空腹剧烈运动导致能量耗竭。'
        },
        {
          title: '认识α1-抗胰蛋白酶缺乏症及其肝脏保护',
          solution: '首要任务是绝对戒烟，并避免接触二手烟和环境粉尘。日常需定期进行肝脏超声检查，尽早监控肝纤维化或肝硬化的发生，避免饮酒。'
        },
        {
          title: '血色病（Hemochromatosis）患者的铁超载管理',
          solution: '定期进行静脉放血是减少体内过量铁沉积的有效方法。饮食上应限制维生素C的过量补充（因其会促进铁吸收），并绝对避免生食海鲜以免感染。'
        },
        {
          title: '希特林蛋白缺乏症（NICCD）婴儿期的喂养建议',
          solution: '对于患有NICCD的婴儿，推荐使用强化中链甘油三酯（MCT）的深度水解蛋白配方奶粉，同时应限制乳糖和蔗糖的摄入，定期监测肝功能指标。'
        },
        {
          title: '酪氨酸血症I型的饮食控制与随访',
          solution: '确诊后应尽早使用尼替西农（NTBC）治疗，同时必须配合严格的低酪氨酸和低苯丙氨酸饮食。每半年需进行肝脏影像学和甲胎蛋白(AFP)复查。'
        },
        {
          title: '戈谢病（Gaucher Disease）的骨骼与肝脾肿大管理',
          solution: '酶替代疗法（ERT）是目前的标准治疗方案，可有效缩小肝脾体积并改善骨骼受累。患者日常应避免剧烈碰撞运动，以防脾脏破裂或病理性骨折。'
        },
        {
          title: '尼曼-匹克病（Niemann-Pick）的日常护理注意事项',
          solution: '目前主要以对症和支持治疗为主。对于伴有肝脾肿大的患者，饮食应以清淡、易消化为主，注意预防呼吸道感染，保持呼吸道通畅。'
        }
      ]
    };
  },
  onLoad() {
    this.search();
  },
  methods: {
    search() {
      // 纯前端本地搜索过滤逻辑
      if (!this.param.trim()) {
        this.zixunlist = this.mockAdviceList;
        return;
      }
      
      const keyword = this.param.trim().toLowerCase();
      this.zixunlist = this.mockAdviceList.filter(item => {
        return item.title.toLowerCase().includes(keyword) || 
               item.solution.toLowerCase().includes(keyword);
      });
    }
  }
};
</script>

<style>
/* 设定页面背景色 */
page {
  background-color: #f5f6f7;
}

/* 让搜索栏固定在顶部，体验更好 */
.fixed-top {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 999;
}

/* 资讯卡片的阴影效果 */
.shadow {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

/* 删除了原先与该页面无关的冗余代码 (如 rounded-button, icon1 等) */
</style>