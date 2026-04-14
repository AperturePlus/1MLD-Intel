<template>
	<view>
		<view class="cu-bar search bg-white">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input type="text" placeholder="输入搜索的关键词" v-model="query" confirm-type="search" @confirm="search"></input>
			</view>
			<view class="action">
				<button class="cu-btn bg-gradual-green shadow-blur round" @click="search">搜索</button>
			</view>
		</view>
		
		<view class="button-container" style="margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		    <view class="button-wrapper">
		    	<button @click="navigateToQuestion" class="navigateToQuestion">
		    		<view class="rounded-button">
						<image src="https://api.iconify.design/fluent-emoji-flat/clipboard.svg" class="icon1" mode="aspectFit"></image>
		    		</view>
		    		<view class="right-column">
		    			<text style="font-size: 16px; line-height: 1.2;">个人中心</text>
		    			<text style="font-size: 10px; line-height: 1.2;">填写个人指标</text>
		    		</view>
		    	</button>
		    </view>
		    <view class="button-wrapper">
		    	<button @click="navigateToRecommend" class="navicateToRecommend">
		    		<view class="rounded-button">
						<image src="https://api.iconify.design/fluent-emoji-flat/robot.svg" class="icon1" mode="aspectFit"></image>
		    		</view>
		    		<view class="right-column">
		    			<text style="font-size: 16px; line-height: 1.2;">AI分析</text>
		    			<text style="font-size: 10px; line-height: 1.2;">为您答疑解惑</text>
		    		</view>
		    	</button>
		    </view>
		</view>

		<view class="jiankangzixun"
			style="display: flex; justify-content: space-between; align-items: center; margin-top: 15px; width: 90%; margin-left: auto; margin-right: auto;">
			<view style="font-size: 20px;font-weight: bold;">健康资讯</view>
			<view @tap="goToAllPage" style="font-size: 12px; color: #3498db; cursor: pointer;">查看全部
				<text class="cuIcon-right" style="font-size: 14px; margin-left: 2px;"></text>
			</view>
		</view>
		
		<view class="cu-card article" :class="isCard?'no-card':''" v-for="(item, index) in healthNews" :key="index">
			<view class="cu-item shadow">
				<view class="title">
					<view class="text-cut">{{ item.title }}</view>
				</view>
				<view class="content" style="margin-top: 3px;">
					<view class="desc">
						<view class="text-content">{{ item.solution }}</view>
					</view>
					<image :src="getImagePath(item.imageUrl)" style="width: 80px; height: 60px; border-radius: 10px; margin-left: 10px;"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isCard: false,
				query: '',
				healthNews: [],
				mockAdviceList: [
					{
						title: '肝豆状核变性（Wilson病）的日常饮食指南',
						solution: '限制铜的摄入是关键。日常应尽量避免食用富含高铜的食物，例如动物内脏、坚果、巧克力、蘑菇和贝类等，并定期复查。',
						imageUrl: 'https://picsum.photos/seed/liver1/160/120'
					},
					{
						title: '糖原累积病(GSD)患者如何科学预防低血糖？',
						solution: '建议采取少食多餐的策略，夜间可遵医嘱使用生玉米淀粉来缓慢释放葡萄糖，维持血糖稳定，避免空腹剧烈运动导致能量耗竭。',
						imageUrl: 'https://picsum.photos/seed/liver2/160/120'
					},
					{
						title: '认识α1-抗胰蛋白酶缺乏症及其肝脏保护',
						solution: '首要任务是绝对戒烟，并避免接触二手烟和环境粉尘。日常需定期进行肝脏超声检查，尽早监控肝纤维化或肝硬化的发生。',
						imageUrl: 'https://picsum.photos/seed/liver3/160/120'
					},
					{
						title: '血色病（Hemochromatosis）患者的铁超载管理',
						solution: '定期进行静脉放血是减少体内过量铁沉积的有效方法。饮食上应限制维生素C的过量补充（因其会促进铁吸收），并避免生食海鲜。',
						imageUrl: 'https://picsum.photos/seed/liver4/160/120'
					}
				]
			}
		},
		onLoad() {
			this.loadData()
		},
		methods: {
			getImagePath(imageName) {
				return imageName;
			},
			navigateToQuestion() {
				uni.switchTab({
					url: "/pages/mine/index",
				});
			},
			navigateToRecommend() {
				uni.switchTab({
					url: "/pages/question/index",
				});
			},
			goToAllPage() {
				uni.navigateTo({
					url: "/pages/zixun/all",
				});
			},
			loadData() {
				this.healthNews = this.mockAdviceList;
			},
			search() {
				if (!this.query.trim()) {
					this.healthNews = this.mockAdviceList;
					return;
				}
				
				const keyword = this.query.trim().toLowerCase();
				this.healthNews = this.mockAdviceList.filter(item => {
					return item.title.toLowerCase().includes(keyword) || 
						   item.solution.toLowerCase().includes(keyword);
				});
			}
		},
	};
</script>

<style>
	.button-container {
		margin-top: 20px;
		display: flex;
		justify-content: space-between;
		margin-top: 10px;
	}

	.button-wrapper {
		flex: 1;
		margin-right: 10px;
	}

	/* 给图标容器加一点内边距，让图标看起来更精致 */
	.rounded-button {
		background-image: linear-gradient(to top, #c1dfc4 0%, #deecdd 100%);
		border-radius: 10px;
		overflow: hidden;
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 5px; 
	}

	.navigateToQuestion,
	.navicateToRecommend {
		width: 100%;
		background-image: linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%);
		padding: 10px;
		border-radius: 10px;
		display: flex;
	}

	.rounded-container {
		background-color: #ffffff;
		border-radius: 10px;
		overflow: hidden;
		display: flex;
	}

	.container {
		padding: 30px;
	}

	.right1 img.icon2 {
		display: block;
		margin-left: auto;
		margin-right: auto;
		width: 80px;
		height: 60px;
		border-radius: 10px;
	}

	.left1 {
		border: none;
		border-radius: 20px;
		width: 70%;
		padding: 2px;
		background-color: transparent;
		margin-left: 10px;
	}

	.icon {
		width: 20px;
		height: 20px;
		vertical-align: middle;
	}

	.rounded-input {
		width: 100%;
		height: 30px;
		flex: 1;
		padding: 5px;
		font-size: 14px;
		border: 1px solid transparent;
		box-sizing: border-box;
	}

	.navigateToQuestion,
	.navicateToRecommend {
		display: flex;
		align-items: center;
	}

	.icon1 {
		width: 35px;
		height: 35px;
		border-radius: 8px;
	}

	.right-column {
		flex: 1;
		margin-left: 8px; /* 给文字和图标之间增加一点呼吸感 */
	}

	text {
		display: block;
		margin-bottom: 5px;
	}
</style>