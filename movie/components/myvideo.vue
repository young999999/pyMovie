<template>
	<view>
		<!-- 		导航 -->
		<cu-custom bgColor="bg-gradual-pink" :isBack="true">
			<block slot="content">{{movieName}}</block>
			<block slot="backText">返回</block>
		</cu-custom>


		<view class="outter ">

			<video class="mVideo " :src="movieUrl" controls></video>

			<scroll-view class="movieNumOut " scroll-y="true">
				<view class="movieNum">
					<view v-for="(item ,index) of movieNumber" :key="index">
						<button class="movieNumBut text-xs bg-red" @click="getUrl(index)" :class="{ liBackground:changeLeftBackground == index}">第{{index+1}}集</button>
					</view>
				</view>
			</scroll-view>

		</view>


		<view class="text-red">当前线路：{{currentLine}}</view>

		<view class="collapse">

			<view class="collapse-content">
				<button class="collapse-content-btn bg-pink text-sm" @click="collapseItemBtn(2,0,0)">酷云资源</button>
				<view class="collapse-content-item">
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'ky','m3u8')">m3u8</button>
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'ky','mp4')">mp4</button>
				</view>
			</view>

			<view class="collapse-content">
				<button class="collapse-content-btn bg-pink text-sm" @click="collapseItemBtn(2,0,0)">OK资源</button>
				<view class="collapse-content-item">
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'ok','m3u8')">m3u8</button>
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'ok','mp4')">mp4</button>
				</view>
			</view>

			<view class="collapse-content">
				<button class="collapse-content-btn bg-pink text-sm" @click="collapseItemBtn(2,0,0)">最大资源</button>
				<view class="collapse-content-item">
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'zd','m3u8')">m3u8</button>
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'zd','mp4')">mp4</button>
				</view>
			</view>

			<view class="collapse-content">
				<button class="collapse-content-btn bg-pink text-sm" @click="collapseItemBtn(2,0,0)">209资源</button>
				<view class="collapse-content-item">
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'209','m3u8')">m3u8</button>
					<button class="collapse-item-btn bg-pink" v-show="collapseitembtn" @click="collapseItemBtn(2,'209','mp4')">mp4</button>
				</view>
			</view>

		</view>



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
				changeLeftBackground: "k",
				collapseitembtn: false,
				movieName: "",
				movieUrl: "",
				kycollectionm3u8: "",
				kycollectionmp4: "",
				okcollectionm3u8: "",
				okcollectionmp4: "",
				two09collectionm3u8: "",
				two09collectionmp4: "",
				zdcollectionm3u8: "",
				zdcollectionmp4: "",
				lineSwitch: "",
				currentLine: "酷云mp4",
				movieCategory: "",
				movieNumber: "",
			};
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			this.movieName = option.movieName;

			this.kycollectionm3u8 = option.kycollectionm3u8;
			this.kycollectionmp4 = option.kycollectionmp4;
			this.okcollectionm3u8 = option.okcollectionm3u8;
			this.okcollectionmp4 = option.okcollectionmp4;
			this.two09collectionm3u8 = option.two09collectionm3u8;
			this.two09collectionmp4 = option.two09collectionmp4;
			this.zdcollectionm3u8 = option.zdcollectionm3u8;
			this.zdcollectionmp4 = option.zdcollectionmp4;



			this.movieNumber = this.kycollectionmp4.toString().split(",").length
			this.lineSwitch = this.kycollectionmp4;
		},
		methods: {
			getUrl(index) {
				// console.log(this.lineSwitch);
				this.movieUrl = this.lineSwitch.toString().split(",")[index].split("$")[1];
				this.changeLeftBackground = index;
				console.log(this.movieUrl);

			},
			collapseItemBtn(data, line, pattern) {

				if (data == 1) {
					this.collapseitembtn = true
				} else {
					this.collapseitembtn = !this.collapseitembtn;
				}

				if (line == "ky") {
					if (pattern == "m3u8") {
						this.lineSwitch = this.kycollectionm3u8
						this.movieNumber = this.kycollectionm3u8.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "酷云资源m3u8";
					} else {
						this.lineSwitch = this.kycollectionmp4
						this.movieNumber = this.kycollectionmp4.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "酷云资源mp4";
					}
				}
				if (line == "ok") {
					if (pattern == "m3u8") {
						this.lineSwitch = this.okcollectionm3u8
						this.movieNumber = this.okcollectionm3u8.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "OK资源m3u8";
					} else {
						this.lineSwitch = this.okcollectionmp4
						this.movieNumber = this.okcollectionmp4.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "OK资源mp4";
					}
				}
				if (line == "zd") {
					if (pattern == "m3u8") {
						this.lineSwitch = this.zdcollectionm3u8
						this.movieNumber = this.zdcollectionm3u8.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "最大资源m3u8";
					} else {
						this.lineSwitch = this.zdcollectionmp4
						this.movieNumber = this.zdcollectionmp4.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "最大资源m3u8";
					}
				}
				if (line == "209") {
					if (pattern == "m3u8") {
						this.lineSwitch = this.two09collectionm3u8
						this.movieNumber = this.two09collectionm3u8.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "209资源m3u8";
					} else {
						this.lineSwitch = this.two09collectionmp4
						this.movieNumber = this.two09collectionmp4.toString().split(",").length
						if (this.lineSwitch == "") this.movieNumber = 0
						this.currentLine = "208资源mp4";
					}
				}


			}
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

	.liBackground {
		background-color: #E03997;
	}

	.collapse {
		display: flex;
		flex-direction: row;
	}

	.collapse-content {
		width: 24%;
		display: flex;
		flex-direction: column;
		margin-left: 10rpx;
	}

	.collapse-content-btn {
		width: 100%;
	}

	.collapse-content-item {
		display: flex;
		flex-direction: row;
	}

	.collapse-item-btn {
		/* width: 200rpx; */

		flex: 1;
		height: 50rpx;
		text-align: center;
		font-size: 25rpx;

	}
</style>
