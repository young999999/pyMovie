(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["common/main"],{"2a7b":function(t,e,n){"use strict";n.r(e);var o=n("9eac"),u=n.n(o);for(var a in o)"default"!==a&&function(t){n.d(e,t,function(){return o[t]})}(a);e["default"]=u.a},"31d2":function(t,e,n){"use strict";var o=n("4460"),u=n.n(o);u.a},4460:function(t,e,n){},"4df3":function(t,e,n){"use strict";(function(t){n("5278"),n("921b");var e=u(n("66fd")),o=u(n("ef0a"));function u(t){return t&&t.__esModule?t:{default:t}}function a(t){for(var e=1;e<arguments.length;e++){var n=null!=arguments[e]?arguments[e]:{},o=Object.keys(n);"function"===typeof Object.getOwnPropertySymbols&&(o=o.concat(Object.getOwnPropertySymbols(n).filter(function(t){return Object.getOwnPropertyDescriptor(n,t).enumerable}))),o.forEach(function(e){r(t,e,n[e])})}return t}function r(t,e,n){return e in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}var c=function(){return n.e("colorui/components/cu-custom").then(n.bind(null,"8b4c"))};e.default.component("cu-custom",c),e.default.config.productionTip=!1,o.default.mpType="app";var f=new e.default(a({},o.default));t(f).$mount()}).call(this,n("6e42")["createApp"])},"9eac":function(t,e,n){"use strict";(function(t,o,u){Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var a=r(n("66fd"));function r(t){return t&&t.__esModule?t:{default:t}}o.isLogin=function(){try{var e=t.getStorageSync("suid"),n=t.getStorageSync("srand")}catch(o){}return""!=e&&""!=n&&[e,n]};var c={onLaunch:function(){console.log(u("App Launch"," at App.vue:19")),t.getSystemInfo({success:function(t){a.default.prototype.StatusBar=t.statusBarHeight,"android"==t.platform?a.default.prototype.CustomBar=t.statusBarHeight+50:a.default.prototype.CustomBar=t.statusBarHeight+45}})},onShow:function(){console.log(u("App Show"," at App.vue:46"))},onHide:function(){console.log(u("App Hide"," at App.vue:49"))}};e.default=c}).call(this,n("6e42")["default"],n("c8ba"),n("0de9")["default"])},ef0a:function(t,e,n){"use strict";n.r(e);var o=n("2a7b");for(var u in o)"default"!==u&&function(t){n.d(e,t,function(){return o[t]})}(u);n("31d2");var a,r,c=n("2877"),f=Object(c["a"])(o["default"],a,r,!1,null,null,null);e["default"]=f.exports}},[["4df3","common/runtime","common/vendor"]]]);