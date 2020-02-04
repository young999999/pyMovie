<template>
	<view>
		<!-- 搜索框 -->
		<view class="cu-bar search bg-white">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
<!-- 				@blur 是当元素失去焦点时所触发的事件
				@focus当元素获得焦点时,发生focus事件 -->
				<input  @focus="InputFocus" @blur="InputBlur" :adjust-position="false" type="text" placeholder="搜索图片、文章、视频"
				 confirm-type="search"></input>
			</view>
			<view class="action">
				<button class="cu-btn bg-green shadow-blur round">搜索</button>
			</view>
		
		</view>
		
	</view>

</template>

<script>
	export default {
		data() {
			return {
				InputBottom: 0
			};
		},
		methods: {
			InputFocus(e) {
				//输入框得到焦点
			},
			InputBlur(e) {
				
				//console.log(e.detail.value);//e.detail.value=在输入框失去焦点时输入框中的值
				this.InputBottom=e.detail.value;
				console.log(this.InputBottom);
			}
		}
	}
</script>

<style scoped>

</style>
