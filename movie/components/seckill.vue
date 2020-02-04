<template>


	<view>

		<view class="outer bg-white">

			<!-- 头部 -->
			<view class="head">
				<!-- 头部的头部 -->
				<view class="head-head">
					<!-- <view>头部部分的头部</view> -->

				</view>
				<!-- 头部的内容 -->
				<view class="head-content">
					<view class="head-item1 text-center text-red">限时秒杀</view>
					<view class="head-item2 text-center">8点场 </view>
					<view class="head-item3 text-center bg-black text-white">07:13:56</view>
					<view class="head-item4 text-right" style="margin-right: 30rpx;">></view>
				</view>
			</view>


			<!-- 内容部分-->
			<view class="content">
				<view class="content-head">
					<!-- <view>内容部分的头部</view> -->
				</view>
				<!-- 内容部分的内容 -->
			</view>
			<scroll-view class="content-floor-list " scroll-x="true">
				<view class="content-floor-wrapper">
					<view v-for="(item, index) in 6" :key="index" class="content-floor-item">
						<image class="content-floor-img" :src="src"></image>
						<view class="text-cut">恒源祥2019春季长袖白色t恤新款春装</view>
						<view class="text-red">￥{{179+index*3}}</view>
					</view>
				</view>
			</scroll-view>
		</view>

		<!-- 底部 -->
		<view class="footer">
			<!-- 底部部分的头部 -->
			<view class="footer-head">
				<!-- <view>底部部分</view> -->
			</view>
		</view>


	</view>
	</view>
</template>

<script scoped>
	export default {
		data() {
			return {
				src: "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1620020012,789258862&fm=26&gp=0.jpg"
			};
		},

		onLoad() {

		},

		
		onReady() {

		},
		methods: {

		},

	}
</script>

<style scoped>
	.outer {
		display: flex;
		/* 指定了弹性容器中子元素的排列方式:纵向，横向，纵向反向，横纵向反向， */
		flex-direction: column;
		width: 100%;
		/* background-color: #ED1C24; */
	}

	.head {
		flex: 1;
		/* background-color: #A5673F; */
		padding: 10rpx;
	}

	.head-head {
		width: 50%;
		/* background-color: #DD524D; */
	}

	.head-content {
		display: flex;
		/* background-color: yellow; */
	}

	.head-item1 {
		width: 20px;
		height: 20px;
		flex: 1;
	}

	.head-item2 {
		width: 20px;
		height: 20px;
		flex: 1;
	}

	.head-item3 {
		width: 20px;
		height: 20px;
		flex: 1;
	}

	.head-item4 {
		width: 20px;
		height: 20px;
		flex: 1;
	}



	.content {
		flex: 1;
		display: flex;

		padding: 10rpx;
		/* background-color: #39B54A; */
		flex-direction: column;


	}

	.content-head {

		/* background-color: #EC008C; */
		flex: 1;
	}


	.content-floor-list {
		flex: 1;
		white-space: nowrap;/* 设置如何处理元素内的空白 */
	}

	.content-floor-wrapper {
		display: flex;
		/* align-items: flex-start; */
	}

	.content-floor-item {
		width: 150rpx;
		margin-right: 20rpx;
	}

	.content-floor-img {

		/* background-color: blue; */
		flex: 1;

		width: 150rpx;
		height: 150rpx;
		padding: 10rpx;
		margin: 10rpx 30rpx 10rpx 10rpx;

	}



	.footer {
		flex: 1;
		padding: 10rpx;
		/* background-color: #FBBD08; */
	}

	.footer-head {
		width: 50%;
		/* background-color: #0000FF; */
	}
</style>
