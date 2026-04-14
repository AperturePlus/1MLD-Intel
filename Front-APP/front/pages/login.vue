<template>
	<view class="normal-login-container">
		<image class="bg-image" src="/static/images/login.png" mode="aspectFill"></image>
		
		<view class="logo-content align-center justify-center flex-direction flex">
			<text class="title" style="font-weight: bold; font-size: 24px;">数智肝循</text>
			<text class="subtitle" style="font-size: 16px; margin-top: 5px;">遗传代谢性肝病管理平台</text>
		</view>
		
		<view class="login-form-content">
			<view class="input-item flex align-center">
				<view class="iconfont icon-user icon"></view>
				<input v-model="loginForm.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="loginForm.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
			</view>
			<view class="input-item flex align-center" style="width: 60%;margin: 0px;" v-if="captchaEnabled">
				<view class="iconfont icon-code icon"></view>
				<input v-model="loginForm.code" type="text" class="input" placeholder="请输入验证码" maxlength="4" />
				<view class="login-code">
					<image :src="codeUrl" @click="getCode" class="login-code-img"></image>
				</view>
			</view>
			<view class="action-btn">
				<button @click="handleLogin" class="login-btn cu-btn block bg-blue lg round">登录</button>
			</view>
			
			<view class="reg text-center" v-if="register">
				<text class="text-white">没有账号？</text>
				<text @click="handleUserRegister" class="text-blue underline-text">立即注册</text>
			</view>
			
			<view class="appeal text-center">
				<text class="text-white">账号被封禁？</text>
				<text @click="handleAppeal" class="text-blue underline-text">点击申诉</text>
			</view>

			<view class="forget text-center">
				<text @click="handleForgetPassword" class="text-blue underline-text">找回密码</text>
			</view>
		</view>

		<view class="bottom-footer flex flex-direction align-center">
			<text @click="handleAdminLogin" class="admin-link">管理员入口</text>
			<text class="copyright">© 2026 四川大学华西临床医学院IMLD课题组</text>
			<text class="copyright">All rights reserved.</text>
		</view>
	</view>
</template>

<script>
	// 引入 setToken 方法
	import { setToken } from '@/utils/auth'

	export default {
		data() {
			return {
				codeUrl: "",
				actualCode: "", // 用于存储前端生成的真实验证码以便比对
				captchaEnabled: true,
				register: true,
				globalConfig: getApp().globalData ? getApp().globalData.config : {},
				loginForm: {
					username: "",
					password: "",
					code: "",
					role: "0"
				}
			}
		},
		created() {
			this.getCode()
		},
		methods: {
			handleAdminLogin() {
				this.$tab.navigateTo(`/pages/login_admin`)
			},
			
			handleDoctorLogin() {
				this.$tab.navigateTo(`/pages/login_doctor`)
			},
			
			handleUserRegister() {
				this.$tab.redirectTo(`/pages/register`)
			},
			handlePrivacy() {
				let site = this.globalConfig.appInfo.agreements[0]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			handleUserAgrement() {
				let site = this.globalConfig.appInfo.agreements[1]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			
			// 纯前端生成 SVG 验证码
			getCode() {
				const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
				let code = '';
				for (let i = 0; i < 4; i++) {
					code += chars[Math.floor(Math.random() * chars.length)];
				}
				this.actualCode = code; 

				let svg = `<svg xmlns="http://www.w3.org/2000/svg" width="100" height="38" viewBox="0 0 100 38">
					<rect width="100" height="38" fill="#e0e0e0" />`; 
				
				for (let i = 0; i < 4; i++) {
					const x = 15 + i * 20;
					const y = 25 + Math.random() * 5;
					const angle = Math.random() * 40 - 20; 
					const r = Math.floor(Math.random() * 150);
					const g = Math.floor(Math.random() * 150);
					const b = Math.floor(Math.random() * 150);
					
					svg += `<text x="${x}" y="${y}" fill="rgb(${r},${g},${b})" font-size="22" font-weight="bold" transform="rotate(${angle} ${x} ${y})">${code[i]}</text>`;
				}
				
				for (let i = 0; i < 4; i++) {
					svg += `<line x1="${Math.random()*100}" y1="${Math.random()*38}" x2="${Math.random()*100}" y2="${Math.random()*38}" stroke="#999" stroke-width="1.5" />`;
				}
				svg += `</svg>`;

				this.codeUrl = 'data:image/svg+xml;utf8,' + encodeURIComponent(svg);
			},
			
			async handleLogin() {
				if (this.loginForm.username === "") {
					this.$modal.msgError("请输入您的账号")
				} else if (this.loginForm.password === "") {
					this.$modal.msgError("请输入您的密码")
				} else if (this.loginForm.code === "" && this.captchaEnabled) {
					this.$modal.msgError("请输入验证码")
				} else {
					this.$modal.loading("登录中，请耐心等待...")
					this.pwdLogin()
				}
			},
			
			// 纯前端拦截与账号密码校验
			async pwdLogin() {
				setTimeout(() => {
					// 1. 校验验证码 (忽略大小写)
					if (this.captchaEnabled && this.loginForm.code.toLowerCase() !== this.actualCode.toLowerCase()) {
						this.$modal.closeLoading()
						this.$modal.msgError("验证码错误")
						this.getCode() // 验证码错误刷新
						return
					}

					// 2. 校验账号密码
					if (this.loginForm.username === 'admin' && this.loginForm.password === '123456') {
						this.$modal.closeLoading()
						this.$modal.msgSuccess("登录成功")
						
						// 3. 写入假的 Token 以通过路由拦截器
						setToken('mock-frontend-token-123456')
						
						this.loginSuccess()
					} else {
						this.$modal.closeLoading()
						this.$modal.msgError("账号或密码错误")
						this.getCode() 
					}
				}, 600) 
			},
			
			loginSuccess() {
				setTimeout(() => {
					this.$tab.reLaunch('/pages/index')
				}, 500)
			},
			
			handleAppeal() {
				this.$tab.navigateTo(`/pages/appeal`)
			},
			handleForgetPassword() {
				this.$tab.navigateTo(`/pages/forget_password`)
			}
		}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
		background-color: #f5f6f7; 
	}

	.normal-login-container {
		width: 100%;
		min-height: 100vh;
		position: relative; 
		z-index: 1;
		padding-bottom: 120px; 

		.bg-image {
			position: fixed; 
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			z-index: -1; 
		}

		.logo-content {
			width: 100%;
			font-size: 21px;
			text-align: center;
			padding-top: 15%;
			
			color: #ffffff;
			text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.4);

			image {
				border-radius: 4px;
			}

			.title {
				margin-left: 10px;
			}
		}

		.login-form-content {
			text-align: center;
			margin: 20px auto;
			margin-top: 15%;
			width: 80%;

			.input-item {
				margin: 20px auto;
				background-color: rgba(255, 255, 255, 0.8); 
				height: 45px;
				border-radius: 20px;
				box-shadow: 0 4px 12px rgba(0,0,0,0.05); 

				.icon {
					font-size: 38rpx;
					margin-left: 10px;
					color: #fafafa;
				}

				.input {
					width: 100%;
					font-size: 14px;
					line-height: 20px;
					text-align: left;
					padding-left: 15px;
				}
			}

			.login-btn {
				margin-top: 40px;
				height: 45px;
				box-shadow: 0 4px 12px rgba(32, 214, 255, 0.3); 
			}

			.reg, .appeal, .forget {
				margin-top: 15px;
				
				.underline-text {
					text-decoration: underline;
				}
			}

			.text-center {
				text-align: center;
			}

			.xieyi {
				color: #333;
				margin-top: 20px;
			}

			.login-code {
				height: 38px;
				float: right;

				.login-code-img {
					height: 38px;
					position: absolute;
					margin-left: 10px;
					width: 200rpx;
				}
			}
		}

		.bottom-footer {
			position: absolute;
			bottom: 40rpx;
			width: 100%;
			text-align: center;
			
			.admin-link {
				color: #ffffff;
				font-size: 14px;
				text-decoration: underline;
				margin-bottom: 20rpx; 
				opacity: 0.9;
			}

			.copyright {
				color: #ffffff;
				font-size: 12px;
				line-height: 1.6;
				opacity: 0.6; 
			}
		}
	}
</style>