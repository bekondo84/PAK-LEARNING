"use strict";(self["webpackChunkui"]=self["webpackChunkui"]||[]).push([[6604],{6604:function(e,t,n){n.r(t),n.d(t,{default:function(){return D}});var a=n(3396),o=n(7139),s=n(9242);const l={class:"pdf-viewer"},i={class:"app-header"},d={key:0},c={key:1},p=["disabled"],u=["disabled"],r={class:"right"},g={class:"app-content"};function f(e,t,n,f,h,w){const b=(0,a.up)("vue-pdf-embed");return(0,a.wg)(),(0,a.iD)("div",l,[(0,a._)("div",i,[e.isLoading?((0,a.wg)(),(0,a.iD)(a.HY,{key:0},[(0,a.Uk)(" Loading... ")],64)):((0,a.wg)(),(0,a.iD)(a.HY,{key:1},[e.showAllPages?((0,a.wg)(),(0,a.iD)("span",d,(0,o.zw)(e.pageCount)+" page(s) ",1)):((0,a.wg)(),(0,a.iD)("span",c,[(0,a._)("button",{disabled:e.page<=1,onClick:t[0]||(t[0]=t=>e.page--)},"❮",8,p),(0,a.Uk)(" "+(0,o.zw)(e.page)+" / "+(0,o.zw)(e.pageCount)+" ",1),(0,a._)("button",{disabled:e.page>=e.pageCount,onClick:t[1]||(t[1]=t=>e.page++)},"❯",8,u)])),(0,a._)("label",r,[(0,a.wy)((0,a._)("input",{"onUpdate:modelValue":t[2]||(t[2]=t=>e.showAllPages=t),type:"checkbox"},null,512),[[s.e8,e.showAllPages]]),(0,a.Uk)(" Show all pages ")])],64))]),(0,a._)("div",g,[(0,a.Wm)(b,{ref:"pdfRef",source:e.pdfSource,page:e.page,onRendered:e.handleDocumentRender},null,8,["source","page","onRendered"])])])}var h=n(6520),w=n(3627),b=n.n(w),k=n(3373),m=function(e,t,n,a){var o,s=arguments.length,l=s<3?t:null===a?a=Object.getOwnPropertyDescriptor(t,n):a;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)l=Reflect.decorate(e,t,n,a);else for(var i=e.length-1;i>=0;i--)(o=e[i])&&(l=(s<3?o(l):s>3?o(t,n,l):o(t,n))||l);return s>3&&l&&Object.defineProperty(t,n,l),l};const v=new k.Z;let R=class extends h.w3{};R=m([(0,h.Ei)({props:{item:Object},components:{VuePdfEmbed:b()},data:function(){return{isLoading:!0,page:null,pageCount:1,pdfSource:null,showAllPages:!0}},watch:{showAllPages(){this.page=this.showAllPages?null:1}},methods:{handleDocumentRender(e){console.log(e),this.isLoading=!1,this.pageCount=this.$refs.pdfRef.pageCount}},async mounted(){if(null!=this.item){const e=await v.getResourceAsStream(this.item.pk,this.item.file);this.pdfSource=window.URL.createObjectURL(new Blob([e]))}}})],R);var y=R,C=n(89);const P=(0,C.Z)(y,[["render",f],["__scopeId","data-v-6518b8d4"]]);var D=P}}]);
//# sourceMappingURL=6604.2b59277a.js.map