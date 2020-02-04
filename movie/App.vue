<script>
	import Vue from 'vue'
	global.isLogin=function(){
		try{
			var suid=uni.getStorageSync('suid');
			var srand=uni.getStorageSync('srand');
		}catch(e){
			//TODO handle the exception
		}
		if (suid==''||srand=='') {
			return false;
		} else{
			return [suid,srand];
		}
	}
	
	export default {
		onLaunch: function() {
			console.log('App Launch');


			uni.getSystemInfo({
				success: function(e) {
					// #ifndef MP
					Vue.prototype.StatusBar = e.statusBarHeight;
					if (e.platform == 'android') {
						Vue.prototype.CustomBar = e.statusBarHeight + 50;
					} else {
						Vue.prototype.CustomBar = e.statusBarHeight + 45;
					};
					// #endif
					// #ifdef MP-WEIXIN
					Vue.prototype.StatusBar = e.statusBarHeight;
					let custom = wx.getMenuButtonBoundingClientRect();
					Vue.prototype.Custom = custom;
					Vue.prototype.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
					// #endif       
					// #ifdef MP-ALIPAY
					Vue.prototype.StatusBar = e.statusBarHeight;
					Vue.prototype.CustomBar = e.statusBarHeight + e.titleBarHeight;
					// #endif
				}
			})
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/* #ifndef APP-NVUE */
		@import "colorui/main.css";
	@import "colorui/icon.css";
	/* #endif */
	/*每个页面公共css */

</style>
