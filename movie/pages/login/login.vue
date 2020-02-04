<template>
	<view>
		<!-- 导航 -->
		<cu-custom bgColor="bg-gradual-pink" :isBack="true">
			<block slot="content">登录</block>
			<block slot="backText">返回</block>
		</cu-custom>





		<!-- #ifdef MP-WEIXIN -->
		<button type="primary" open-type="getUserInfo" @getuserinfo="getUserInfo">微信登录</button>
		<!-- #endif -->
		<!-- #ifndef MP-WEIXIN -->
		<button type="primary" style="bottom: 0rpx;">除了微信登录</button>
		<!-- #endif -->

	</view>
</template
	export default {
		data() {
			return {

			}
		},
		onLoad() {

		},
		onReady() {

		},
		methods: {
			getUserInfo(res) {
				console.log(res);

				// 获取用户加密信息
				uni.login({
					provider: 'weixin',
					success: function(loginRes) {
						console.log('loginRes');
						console.log(loginRes);
						// 获取用户未加密信息
						uni.getUserInfo({
							provider: 'weixin',
							success: function(infoRes) {
								console.log('infoRes');
								console.log(infoRes);
								if (infoRes.iv) {
									console.log("登录成功");
									uni.setStorageSync('suid', 'suid');
									uni.setStorageSync('srand', 'srand');
									uni.navigateBack({
										url: "../index/index"
									})
								};
							}
						});

					}
				});


			}
		},
	}
</script>

<style scoped>

</style>
