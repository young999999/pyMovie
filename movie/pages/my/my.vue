<template>
	<view>
		<!-- 导航 -->
		<cu-custom bgColor="bg-gradual-pink" :isBack="false">
			<block slot="content">我的</block>
			<block slot="backText">返回</block>
		</cu-custom>
		<button type="primary" @click="tologin">登录</button>
		<button type="primary" @click="tovideo">video</button>




	</view>

</template>

<script>
	export default {

		data() {
			return {
				title: '快陪练',
				extra: '教育科技公司',
				note: '拓展钢琴陪练业务',
				thumbnail: require('../../static/logo.png'),
				isFull: true
			}
		},
		methods: {
			tologin() {
				console.log("从my页面进入login页面");
				uni.navigateTo({
					url: "../login/login"
				})
			},
			tovideo() {
				console.log("从my页面进入video页面");
				uni.navigateTo({
					url: "../../components/myvideo"
				})
			},
			
		}
	}
</script>

<style>

</style>
