<template>
	<view>
		<view class="cu-bar search bg-white">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input type="text" placeholder="输入搜索的关键词" v-model="query" confirm-type="search" @confirm="searchArticles"></input>
			</view>
			<view class="action">
				<button class="cu-btn bg-gradual-green shadow-blur round" @click="searchArticles">搜索</button>
			</view>
		</view>

		<view class="button-container" style="margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
			<view class="button-wrapper">
				<button @click="navigateToProfileCenter" class="navigate-to-profile-center">
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
				<button @click="navigateToAiQuestion" class="navigate-to-ai-question">
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

		<view class="health-article-header"
			style="display: flex; justify-content: space-between; align-items: center; margin-top: 15px; width: 90%; margin-left: auto; margin-right: auto;">
			<view style="font-size: 20px; font-weight: bold;">健康资讯</view>
			<view @tap="goToAllPage" style="font-size: 12px; color: #3498db; cursor: pointer;">查看全部
				<text class="cuIcon-right" style="font-size: 14px; margin-left: 2px;"></text>
			</view>
		</view>

		<view class="cu-card article" :class="isCard ? 'no-card' : ''" v-for="(item, index) in healthArticles" :key="index">
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
import { queryArticles } from '@/api/system/advice'

export default {
	data() {
		return {
			isCard: false,
			query: '',
			healthArticles: []
		}
	},
	onLoad() {
		this.loadArticles()
	},
	methods: {
		getImagePath(imageName) {
			return imageName
		},
		navigateToProfileCenter() {
			uni.switchTab({
				url: '/pages/mine/index'
			})
		},
		navigateToAiQuestion() {
			uni.switchTab({
				url: '/pages/question/index'
			})
		},
		goToAllPage() {
			uni.navigateTo({
				url: '/pages/articles/all'
			})
		},
		loadArticles() {
			queryArticles().then((res) => {
				this.healthArticles = (res && res.data) || []
			})
		},
		searchArticles() {
			queryArticles({ param: this.query.trim() }).then((res) => {
				this.healthArticles = (res && res.data) || []
			})
		}
	}
}
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

	.rounded-button {
		background-image: linear-gradient(to top, #c1dfc4 0%, #deecdd 100%);
		border-radius: 10px;
		overflow: hidden;
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 5px;
	}

	.navigate-to-profile-center,
	.navigate-to-ai-question {
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

	.navigate-to-profile-center,
	.navigate-to-ai-question {
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
		margin-left: 8px;
	}

	text {
		display: block;
		margin-bottom: 5px;
	}
</style>
