<template>
	<view>
		<!-- 		导航 -->
		<cu-custom bgColor="bg-gradual-pink" :isBack="true">
			<block slot="content">{{movieName}}</block>
			<block slot="backText">返回</block>
		</cu-custom>


		<view class="outter ">

			<video class="mVideo " :src="movieUrl"  controls></video>


			<scroll-view class="movieNumOut " scroll-y="true">
				<view class="movieNum">
					<view v-for="(item ,index) of movieNumber" :key="index">
						<button class="movieNumBut text-xs" @click="getUrl(index)">第{{index+1}}集</button>
					</view>
				</view>
			</scroll-view>

		</view>
		<button size="mini" class="text-red shadow">线路切换：</button>
		<button size="mini" type="primary" @click="lineSwitching(1)">m3u8</button>
		<button size="mini" type="primary" @click="lineSwitching(2)">mp4</button>
		<view>{{movieUrl}}</view>


	</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {

				movieName: "",
				movieUrl: "",
				movieCollection: "",
				movieCollectionMp4: "",
				lineSwitch: "",
				movieCategory: "",
				movieNumber: "",



			};
		},


		onShow() {

		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数

			this.movieName = option.movieName;



			this.movieCollectionMp4 = option.movieCollectionMp4;
			// console.log(option.movieCollectionMp4);
			this.movieNumber = this.movieCollectionMp4.toString().split(",").length


			this.movieCollection = option.movieCollection;
			// console.log(option.movieCollection);
			this.movieNumber = this.movieCollection.toString().split(",").length

			this.lineSwitch = this.movieCollection;
		},
		methods: {
			getUrl(index) {
				// console.log(this.lineSwitch);
				this.movieUrl = this.lineSwitch.toString().split(",")[index].split("$")[1];

				console.log(this.movieUrl);

			},
			lineSwitching(data) {
				if (data === 1) {
					this.lineSwitch = this.movieCollection;
				} else {
					this.lineSwitch = this.movieCollectionMp4;
				}
			},
		}
	}
</script>

<style scoped>
	.outter {
		display: flex;
		/* 指定了弹性容器中子元素的排列方式:纵向，横向，纵向反向，横纵向反向， */
		flex-direction: row;
		margin-top: 10rpx;
		margin-bottom: 10rpx;
		width: 100%;
		height: 500rpx;
	}

	.mVideo {
		flex: 7;

		margin-left: 10rpx;
		margin-right: 10rpx;
		height: 500rpx;

	}

	.movieNumOut {
		flex: 2;
		/* width: 300rpx; */
		height: 500rpx;
	}

	.movieNum {

		display: flex;
		flex-wrap: wrap;
	}

	.movieNumBut {
		flex: 1;
		width: 140rpx;
		margin-left: 10rpx;
		margin-bottom: 5rpx;
		border: 1px solid black;
	}
</style>
