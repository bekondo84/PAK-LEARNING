"use strict";(self["webpackChunkui"]=self["webpackChunkui"]||[]).push([[1546],{1546:function(e,t,r){r.r(t),r.d(t,{default:function(){return p}});var i=r(3396);const n=["src","type"];function a(e,t,r,a,o,c){return e.isVideo?((0,i.wg)(),(0,i.iD)("video",{key:0,controls:"",class:"media-player",src:e.mediaUrl,type:e.mediaType},null,8,n)):(0,i.kq)("",!0)}var o=r(3373),c=r(6520),s=function(e,t,r,i){var n,a=arguments.length,o=a<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,r):i;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)o=Reflect.decorate(e,t,r,i);else for(var c=e.length-1;c>=0;c--)(n=e[c])&&(o=(a<3?n(o):a>3?n(t,r,o):n(t,r))||o);return a>3&&o&&Object.defineProperty(t,r,o),o};const l=new o.Z;let d=class extends c.w3{};d=s([(0,c.Ei)({props:{item:Object,format:String},components:{Overlay:(0,i.RC)((()=>r.e(3720).then(r.bind(r,3720))))},data:function(){return{mediaUrl:null,overlay:!1}},methods:{async loadResource(){if(null!=this.item){const e=await l.getResourceAsStream(this.item.pk,this.item.file);this.mediaUrl=window.URL.createObjectURL(new Blob([e]))}}},computed:{isVideo(){return"MP4"==this.format},mediaType(){if("MP4"==this.format)return"video/mp4"}},watch:{async item(){await this.loadResource()}},async mounted(){await this.loadResource()}})],d);var u=d,f=r(89);const m=(0,f.Z)(u,[["render",a],["__scopeId","data-v-16ca7d41"]]);var p=m}}]);
//# sourceMappingURL=1546.f08b578f.js.map