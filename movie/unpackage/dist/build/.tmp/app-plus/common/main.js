(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["common/main"],{4103:function(t,e,n){},"5fdb":function(t,e,n){"use strict";(function(t,o,u){Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var r=a(n("66fd"));function a(t){return t&&t.__esModule?t:{default:t}}o.isLogin=function(){try{var e=t.getStorageSync("suid"),n=t.getStorageSync("srand")}catch(o){}return""!=e&&""!=n&&[e,n]};var c={onLaunch:function(){console.log(u("App Launch"," at App.vue:19")),t.getSystemInfo({success:function(t){r.default.prototype.StatusBar=t.statusBarHeight,"android"==t.platform?r.default.prototype.CustomBar=t.statusBarHeight+50:r.default.prototype.CustomBar=t.statusBarHeight+45}})},onShow:function(){console.log(u("App Show"," at App.vue:46"))},onHide:function(){console.log(u("App Hide"," at App.vue:49"))}};e.default=c}).call(this,n("6e42")["default"],n("c8ba"),n("0de9")["default"])},"62e8":function(t,e,n){"use strict";n.r(e);var o=n("759f");for(var u in o)"default"!==u&&function(t){n.d(e,t,function(){return o[t]})}(u);n("e750");var r,a,c=n("2877"),f=Object(c["a"])(o["default"],r,a,!1,null,null,null);e["default"]=f.exports},"759f":function(t,e,n){"use strict";n.r(e);var o=n("5fdb"),u=n.n(o);for(var r in o)"default"!==r&&function(t){n.d(e,t,function(){return o[t]})}(r);e["default"]=u.a},b956:function(t,e,n){"use strict";(function(t){n("87bd"),n("921b");var e=u(n("66fd")),o=u(n("62e8"));function u(t){return t&&t.__esModule?t:{default:t}}function r(t){for(var e=1;e<arguments.length;e++){var n=null!=arguments[e]?arguments[e]:{},o=Object.keys(n);"function"===typeof Object.getOwnPropertySymbols&&(o=o.concat(Object.getOwnPropertySymbols(n).filter(function(t){return Object.getOwnPropertyDescriptor(n,t).enumerable}))),o.forEach(function(e){a(t,e,n[e])})}return t}function a(t,e,n){return e in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}var c=function(){return n.e("colorui/components/cu-custom").then(n.bind(null,"8166"))};e.default.component("cu-custom",c),e.default.config.productionTip=!1,o.default.mpType="app";var f=new e.default(r({},o.default));t(f).$mount()}).call(this,n("6e42")["createApp"])},e750:function(t,e,n){"use strict";var o=n("4103"),u=n.n(o);u.a}},[["b956","common/runtime","common/vendor"]]]);