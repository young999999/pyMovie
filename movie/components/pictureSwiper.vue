<template>
	<view>

		<!-- 图片轮播 -->
		<swiper class="screen-swiper round-dot" :indicator-dots="true" :circular="true" :autoplay="true" interval="2000"
		 duration="500">
			<swiper-item v-for="(item,index) in 3" :key="index">
				<!-- 				<image :src="'https://ossweb-img.qq.com/images/lol/web201310/skin/big3900'+index+ '.jpg'" mode="aspectFill"></image>
 -->
				<image :src="'../static/swiper/swiper'+(index+1)+ '.png'" mode="aspectFill" style="padding: 10rpx 20rpx 20rpx 10rpx ;"></image>

			</swiper-item>
		</swiper>


	</view>
</template>

<script>
	export default {
		data() {
			return {
				swiperList: ['']

			};
		}
	}
</script>

<style scoped>

</style>
