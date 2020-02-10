<template>
	<view>
		<!-- 导航 -->
		<cu-custom bgColor="bg-gradual-pink" :isBack="false">
			<block slot="content">我的</block>
			<block slot="backText">返回</block>
		</cu-custom>
		<button type="primary" @click="tologin">登录</button>
		<button type="primary" @click="tovideo">video</button>


		<!-- 		<view>kuy:m3u8</view>
		<video src="http://guihua.feifei-kuyun.com/20200128/29953_31d33d8b/index.m3u8" controls></video>
		<view>kuy:mp4</view>
		<video src="http://kuyun.xiazaikuyun.com/20200128/29953_31d33d8b/爱情公寓5第26集.mp4" controls></video>
		
		
		<view>zuida:m3u8</view>
		<video src="https://you-ku.qingyu-zuida.com/20200129/7043_f5e0ee19/index.m3u8" controls></video>
		<view>zuida:mp4</view>
		<video src="http://zuida.downzuida.com/2001/A情公寓5-26.mp4" controls></video>

		<view>ok:m3u8</view>
		<video src="https://youku.cdn4-okzy.com/20200129/4115_b07fbecb/index.m3u8" controls></video>
		<view>ok:mp4</view>
		<video src="http://down.okdown6.com/20200129/4115_b07fbecb/爱情公寓5_26.mp4" controls></video>
		<view>209:m3u8</view>
		<video src="https://tv.youkutv.cc/2020/01/31/at27u4eSsXm2PehC/playlist.m3u8" controls></video> -->


<!-- 		<uni-collapse >
			<uni-collapse-item title="NBA">
				<uni-list>
					<button type="primary">意见栏</button>
					<button type="primary">意见栏</button>
					<button type="primary">意见栏</button>
					<button type="primary">意见栏</button>
				</uni-list>
					
				
			</uni-collapse-item>
		</uni-collapse> -->

		<!-- 		手风琴效果-->
		<uni-collapse>
			
			<uni-collapse-item title="棒球" style="width: 200rpx;">
				<view style="padding: 30rpx;">意见栏</view>
			</uni-collapse-item>
			
			<uni-collapse-item title="足球" style="width: 200rpx;">
				<view style="padding: 30rpx;">中超</view>
			</uni-collapse-item>
			
			<uni-collapse-item title="篮球" style="width: 200rpx;">
				<view style="padding: 30rpx;">NBA</view>
			</uni-collapse-item>
			
			<uni-collapse-item title="乒乓球" style="width: 200rpx;">
				<view style="padding: 30rpx;">意见栏</view>
			</uni-collapse-item>
			
		</uni-collapse>


		<uni-badge text="1"></uni-badge>
	</view>

</template>

<script>
	import uniCollapse from '@/components/uni-collapse/uni-collapse.vue'
	import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue'
	export default {
		components: {
			uniCollapse,
			uniCollapseItem
		},
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
					url: "../../components/video"
				})
			},
		}
	}
</script>

<style>

</style>
