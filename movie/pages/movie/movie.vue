<template>
	<view>
		<!-- 		导航 -->
		<cu-custom bgColor="bg-gradual-pink" :isBack="false">
			<block slot="content">{{movieName}}</block>
			<block slot="backText">返回</block>
		</cu-custom>






		<!-- 搜索框 -->
		<view class="cu-bar search bg-white">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<!-- @blur 是当元素失去焦点时所触发的事件
					 @focus当元素获得焦点时,发生focus事件 -->
				<input v-model="InputBottom" :adjust-position="false" type="text" placeholder="请输入电影或电视剧名称" confirm-type="search"></input>

			</view>
			<view class="action">
				<button class="cu-btn bg-green shadow-blur round" @click="searchVideo">搜索</button>
			</view>


		</view>
		<view class="text-pink" v-if="InputBottom">搜索“{{InputBottom}}”相关影视</view>


		<view v-if="judge" class="text-red text-xl">未找到“{{InputBottom}}”相关影视</view>


		<view v-else class="outter bg-white" v-for="(item ,index) of data" :key="index" :item="item">

			<image class="movieLeft" :src="item.poster" @click="gotoVideo(item)"></image>

			<view class="movieRight ">

				<view class="movieBtn text-blue  text-xl" @click="gotoVideo(item)">
					{{item.movieName}}
				</view>

				<view class="movieInfor ">
					主演：<br>
					{{item.actor}}：<br>


				</view>
			</view>

		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				judge: "", //判断查询内容是否存在

				InputBottom: "锦衣之下",
				data: "",
				movieName: "",
				movieCategory: "",
				movieUrl: "",
				item: "",
				movieCollection: []
			};
		},
		onShow() {


		},
		methods: {

			// InputBlur(e) {
			// 	this.InputBottom = e.detail.value;
			// 	console.log(this.InputBottom);
			// },

			gotoVideo(data) {

				uni.navigateTo({
					// url: "../../components/video?movieData=" + data  
					url: "../../components/myvideo?movieName="+data.movieName
												 +"&kycollectionm3u8=" + data.mc.kycollectionm3u8+"&kycollectionmp4="+data.mc.kycollectionmp4
												 +"&okcollectionm3u8="+data.mc.okcollectionm3u8+"&okcollectionmp4="+data.mc.okcollectionmp4
												 +"&two09collectionm3u8="+data.mc.two09collectionm3u8+"&two09collectionmp4="+data.mc.two09collectionmp4
												 +"&zdcollectionm3u8="+data.mc.zdcollectionm3u8+"&zdcollectionmp4="+data.mc.zdcollectionmp4
					// url: "../../components/myvideo?movieCollection=" + data.movieCollection + "&movieName=" + data.movieName+"&movieCollectionMp4=" + data.movieCollectionMp4
				})
			},
			searchVideo() {

				uni.request({
					url: 'http://findingway.xyz:8088/movie/' + this.InputBottom, //仅为示例，并非真实接口地址。
					// url: 'http://192.168.1.111:8087/movie/' + this.InputBottom, //仅为示例，并非真实接口地址。

					method: 'GET',

					success: (res) => {
						console.log("查找影视“" + this.InputBottom + "”成功");
						let data = res.data;
						console.log(data);

						
						
						let len = data.length;


						if (len < 1) {
							this.judge = true;

						} else {
							this.judge = false;
							this.content = "";
							this.data = data;

						}

					},
					fail: (error) => {
						console.log("查找影视“" + this.InputBottom + "”失败");
						this.judge = true;

					}
				});
			},
		}
	}
</script>

<style scoped>
	.outter {
		display: flex;
		margin: 10rpx;
		padding: 20rpx;
		width: 100%;
		flex-direction: row;
	}

	.movieLeft {
		flex: 3;
		margin-right: 10rpx;
		height: 300rpx;
	}

	.movieRight {
		display: flex;
		flex: 5;
		flex-direction: column;
	}

	.movieBtn {
		flex: 1;
		height: 100rpx;
	}

	.movieInfor {
		flex: 3;
	}
</style>
