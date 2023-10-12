"use strict";(self["webpackChunkui"]=self["webpackChunkui"]||[]).push([[6910],{7845:function(e,t,i){i.d(t,{Z:function(){return p}});var a=i(3396),n=i(7139);const r=["aria-hidden","aria-label"],s=["fill","width","height"],l={d:"M14,14H7V16H14M19,19H5V8H19M19,3H18V1H16V3H8V1H6V3H5C3.89,3 3,3.9 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V5A2,2 0 0,0 19,3M17,10H7V12H17V10Z"},o={key:0};function c(e,t,i,c,u,g){return(0,a.wg)(),(0,a.iD)("span",(0,a.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon calendar-text-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,a.wg)(),(0,a.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,a._)("path",l,[i.title?((0,a.wg)(),(0,a.iD)("title",o,(0,n.zw)(i.title),1)):(0,a.kq)("",!0)])],8,s))],16,r)}var u={name:"CalendarTextIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}},g=i(89);const d=(0,g.Z)(u,[["render",c]]);var p=d},1198:function(e,t,i){i.d(t,{Z:function(){return p}});var a=i(3396),n=i(7139);const r=["aria-hidden","aria-label"],s=["fill","width","height"],l={d:"M8.59,16.58L13.17,12L8.59,7.41L10,6L16,12L10,18L8.59,16.58Z"},o={key:0};function c(e,t,i,c,u,g){return(0,a.wg)(),(0,a.iD)("span",(0,a.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon chevron-right-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,a.wg)(),(0,a.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,a._)("path",l,[i.title?((0,a.wg)(),(0,a.iD)("title",o,(0,n.zw)(i.title),1)):(0,a.kq)("",!0)])],8,s))],16,r)}var u={name:"ChevronRightIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}},g=i(89);const d=(0,g.Z)(u,[["render",c]]);var p=d},1635:function(e,t,i){i.d(t,{Z:function(){return d}});var a=i(3396),n=i(7139);const r={class:"w-full flex flex-row gap-4 items-center"},s={class:"w-full h-fit rounded-[16px] bg-[#F2F2F2] p-1.5 flex justify-start items-center"},l={class:"w-[180px] font-bold flex"};function o(e,t,i,o,c,u){return(0,a.wg)(),(0,a.iD)("div",r,[(0,a._)("div",s,[(0,a._)("div",{class:(0,n.C_)(["h-[10px] bg-[#34A853] flex rounded-[16px]",u.progressWidth])},null,2)]),(0,a._)("span",l,(0,n.zw)(i.value)+"% "+(0,n.zw)(i.label),1)])}var c={name:"ProgressBar",props:{value:{type:Number,default:0},label:{type:String,default:"de Progrès"}},computed:{progressWidth(){const e=this.value;return 100===e?"w-full":e>=70?"w-["+e+"%]":e>=65&&e<70?"w-2/3":e>=60&&e<65?"w-3/5":e>=55&&e<60?"w-7/12":e>=50&&e<55?"w-1/2":e>=40&&e<50?"w-2/5":e>=30&&e<40?"w-1/3":e>=25&&e<30?"w-1/4":e>=20&&e<25?"w-1/5":e>=15&&e<20?"w-1/6":e>=8&&e<15?"w-1/12":"w-[10px]"}}},u=i(89);const g=(0,u.Z)(c,[["render",o]]);var d=g},3380:function(e,t,i){i.r(t),i.d(t,{default:function(){return de}});var a=i(3396),n=i(7139),r=i.p+"e-learning/img/NoOngoingTraining.67a20044.png";const s={class:"w-full px-[120px] py-4 flex flex-col gap-4"},l={key:1,class:"w-full min-h-[600px] flex flex-col gap-4 animate__animated animate__fadeIn"},o={key:2,class:"w-full min-h-[600px] py-8 flex flex-col justify-center items-center gap-4 animate__animated animate__fadeIn font-myriadBold text-2xl"},c=(0,a._)("img",{src:r,alt:"PAK E-learning no Ongoing Training Image",class:"w-[50%]"},null,-1),u={class:"text-center px-20"};function g(e,t,i,r,g,d){const p=(0,a.up)("HomeHero"),f=(0,a.up)("HomeNavBar"),h=(0,a.up)("SearchInput"),m=(0,a.up)("OngoingCard");return(0,a.wg)(),(0,a.iD)(a.HY,null,[(0,a.Wm)(p,{heroType:"Ongoing"}),(0,a._)("div",s,[(0,a.Wm)(f,{title:d.translator("my.learning.inprogress.title")},null,8,["title"]),d.apiList.length>0?((0,a.wg)(),(0,a.j4)(h,{key:0,modelValue:g.searchValue,"onUpdate:modelValue":t[0]||(t[0]=e=>g.searchValue=e),onSetNewSearchValue:d.sortCourse,placeholder:d.translator("my.learning.inprogress.search")},null,8,["modelValue","onSetNewSearchValue","placeholder"])):(0,a.kq)("",!0),d.apiList.length>0?((0,a.wg)(),(0,a.iD)("div",l,[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(d.displayList,((e,t)=>((0,a.wg)(),(0,a.j4)(m,{key:t,courseDetails:e,index:t},null,8,["courseDetails","index"])))),128))])):((0,a.wg)(),(0,a.iD)("div",o,[c,(0,a._)("span",u,(0,n.zw)(d.translator("my.learning.noInprogress.msg1")),1)]))])],64)}i(7658);var d=i(6455),p=i(3260);const f={key:0,role:"status",class:"animate-pulse w-[70%] h-[250px] rounded-[12px] flex items-center justify-center bg-gray-300 dark:bg-gray-700"},h=(0,a._)("svg",{class:"w-10 aspect-square text-gray-200 dark:text-gray-600","aria-hidden":"true",xmlns:"http://www.w3.org/2000/svg",fill:"currentColor",viewBox:"0 0 20 18"},[(0,a._)("path",{d:"M18 0H2a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2Zm-5.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3Zm4.376 10.481A1 1 0 0 1 16 15H4a1 1 0 0 1-.895-1.447l3.5-7A1 1 0 0 1 7.468 6a.965.965 0 0 1 .9.5l2.775 4.757 1.546-1.887a1 1 0 0 1 1.618.1l2.541 4a1 1 0 0 1 .028 1.011Z"})],-1),m=[h],x=["src","alt"],w={class:"w-full flex flex-col items-start justify-between gap-2.5 pr-2"},y={class:"py-1 file:w-full flex flex-col items-start gap-1"},v={class:"text-zinc-950 text-[17px] capitalize font-centuryGothic text-left font-bold"},b={class:"text-pakYellow text-[16px] font-medium capitalize text-left"},C={class:"py-1 flex flex-row items-center justify-center gap-5"},_={class:"flex flex-row gap-1 items-end justify-center pr-5 border-r border-r-grayTone2"},k={class:"text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize"},T={class:"text-textDarkTone text-[14px] flex justify-center font-centuryGothic items-center text-center font-bold"},z={class:"flex flex-row gap-1 items-end justify-center"},D={class:"text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize"},L={class:"text-textDarkTone text-[14px] font-centuryGothic text-center font-bold"},A={class:"w-full flex flex-col items-start"},H={class:"text-[18px] text-grayTone2 font-bold"},Z={class:"flex flex-row gap-1 items-end justify-center"},V={class:"text-textDarkTone text-[14px] font-centuryGothic text-left font-bold"},N={class:"w-full flex flex-row justify-between"},S={class:"py-1 w-full flex flex-row items-start gap-2.5"},B={class:"w-[40px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white text-base font-bold"},I={class:"flex flex-col items-start justify-between"},j={class:"text-zinc-800 text-md font-bold leading-snug tracking-tight text-left"},O={class:"text-slate-500 text-[16px] font-normal leading-[18px] tracking-tight"},M={class:"flex font-bold font-myriadBold"};function W(e,t,i,r,s,l){const o=(0,a.up)("CalendarText"),c=(0,a.up)("BookMultiple"),u=(0,a.up)("ProgressBar"),g=(0,a.up)("ArrowRightBoldCircle"),d=(0,a.up)("ChevronRight"),p=(0,a.up)("v-btn");return(0,a.wg)(),(0,a.iD)("button",{onClick:t[0]||(t[0]=(...e)=>l.routeToTraining&&l.routeToTraining(...e)),class:"w-full h-fit p-2.5 gap-5 bg-white rounded-[12px] drop-shadow-md flex flex-row hover:scale-105 ease-in-out duration-300"},[""===i.courseDetails.image?((0,a.wg)(),(0,a.iD)("div",f,m)):((0,a.wg)(),(0,a.iD)("img",{key:1,src:i.courseDetails.image,alt:i.courseDetails.name,class:"w-[70%] h-[250px] rounded-[12px]"},null,8,x)),(0,a._)("div",w,[(0,a._)("div",y,[(0,a._)("span",v,(0,n.zw)(i.courseDetails.name),1),(0,a._)("span",b,(0,n.zw)(i.courseDetails.themeName),1)]),(0,a._)("div",C,[(0,a._)("div",_,[(0,a.Wm)(o,{size:20,class:"text-grayTone2"}),(0,a._)("span",k,(0,n.zw)(l.translator("card.startAt.label"))+" :",1),(0,a._)("span",T,(0,n.zw)(i.courseDetails.startAt),1)]),(0,a._)("div",z,[(0,a.Wm)(c,{size:20,class:"text-grayTone2"}),(0,a._)("span",D,(0,n.zw)(l.translator("lesson.summary.title"))+" :",1),(0,a._)("span",L,(0,n.zw)(i.courseDetails.finishedActivities)+" "+(0,n.zw)(l.translator("card.lessons.on.label"))+" "+(0,n.zw)(i.courseDetails.totalActivities)+" "+(0,n.zw)(l.translator("lessons.done.label")),1)])]),(0,a.Wm)(u,{value:i.courseDetails.progress},null,8,["value"]),(0,a._)("div",A,[(0,a._)("span",H,(0,n.zw)(l.translator("next.Activity.label")),1),(0,a._)("div",Z,[(0,a.Wm)(g,{size:20,class:"text-grayTone2"}),(0,a._)("span",V,(0,n.zw)(i.courseDetails.nextActivity),1)])]),(0,a._)("div",N,[(0,a._)("div",S,[(0,a._)("span",B,(0,n.zw)(l.trainerInitials),1),(0,a._)("div",I,[(0,a._)("span",j,(0,n.zw)(i.courseDetails.trainer),1),(0,a._)("span",O,(0,n.zw)(l.translator("main.trainer.label")),1)])]),(0,a.Wm)(p,{class:"flex flex-row !px-2 py-2.5 !rounded-[10px] !bg-pakBlue justify-evenly text-white items-center gap-2 hover:!bg-pakDarkBlue",loading:s.loading,size:"large",type:"submit",variant:"flat",onClick:l.routeToTraining},{default:(0,a.w5)((()=>[(0,a._)("span",M,(0,n.zw)(l.translator("continue.btn")),1),(0,a.Wm)(d,{size:24,class:""})])),_:1},8,["loading","onClick"])])])])}var G=i(622),R=i(7845),$=i(1198);const q=["aria-hidden","aria-label"],E=["fill","width","height"],P={d:"M2,12A10,10 0 0,1 12,2A10,10 0 0,1 22,12A10,10 0 0,1 12,22A10,10 0 0,1 2,12M17,12L12,7V10H8V14H12V17L17,12Z"},F={key:0};function Y(e,t,i,r,s,l){return(0,a.wg)(),(0,a.iD)("span",(0,a.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon arrow-right-bold-circle-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,a.wg)(),(0,a.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,a._)("path",P,[i.title?((0,a.wg)(),(0,a.iD)("title",F,(0,n.zw)(i.title),1)):(0,a.kq)("",!0)])],8,E))],16,q)}var K={name:"ArrowRightBoldCircleIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}},J=i(89);const U=(0,J.Z)(K,[["render",Y]]);var Q=U,X=i(1635),ee=i(9310),te=i(3373);const ie=new te.Z;var ae={name:"OngoingCard",components:{BookMultiple:G.Z,CalendarText:R.Z,ChevronRight:$.Z,ArrowRightBoldCircle:Q,ProgressBar:X.Z},props:["courseDetails","index"],data(){return{loading:!1,translateHeaders:null}},methods:{routeToTraining(){this.loading=!0,setTimeout((()=>{this.loading=!1,this.$router.push({name:"TrainingIndex",params:{type:"ongoing",id:this.courseDetails.code}})}),1e3)},translator(e){return null!=this.translateHeaders?this.translateHeaders[e]:e}},async created(){this.translateHeaders=await ie.translate(["card.startAt.label","lesson.summary.title","card.lessons.on.label","lessons.done.label","next.Activity.label","main.trainer.label","continue.btn"])},computed:{trainerInitials(){let e=(0,ee.mJ)(this.courseDetails.trainer);return e}}};const ne=(0,J.Z)(ae,[["render",W]]);var re=ne,se=i(3697),le=i(7627),oe=i(2653);const ce=new te.Z;var ue={name:"OngoingCourse",components:{HomeHero:d.Z,HomeNavBar:p.Z,OngoingCard:re,SearchInput:se.Z},data(){return{searchValue:"",courseList:[],apiTraining:[],displayTrainingList:[],error:!1,translateHeaders:null}},methods:{sortCourse(e){if(""!==e){let t=[...this.apiList];t=this.apiList.filter((t=>t.name.toLowerCase().trim().includes(e.toLowerCase().trim())||t.themeName.toLowerCase().trim().includes(e.toLowerCase().trim())||t.trainer.toLowerCase().trim().includes(e.toLowerCase().trim()))),t.length>0&&(this.displayList=t)}else this.displayList=this.apiList},async getNextLeconActivity(e){const t=null!=le.Z.getters.getCurrentSession?le.Z.getters.getCurrentSession.language:"fr";let i=null;for(let a=0;a<e.length;a++){const n=e[a];if("LECON"===n.type){const e=await ce.leconResult(n.code);i=""!=e&&"DONE"!=e.state.code?e.currentIndex.index.name[t].value:null}}return i},async getNextTestActivity(e){const t=null!=le.Z.getters.getCurrentSession?le.Z.getters.getCurrentSession.language:"fr";let i=null;for(let a=0;a<e.length;a++){const n=e[a];if("TEST"===n.type){const e=await ce.testResult(n.code);i=""!=e&&"DONE"!=e.state?e.activity.name[t].value:null}}return i},async getNextActivity(e,t){let i=null;return e.length>0&&t!=e.length&&(i=await this.getNextLeconActivity(e),null==i&&(i=await this.getNextTestActivity(e))),null!=i?i:e[0].name},errorHandler(){this.error=!0},async constructList(){const e=null!=le.Z.getters.getCurrentSession?le.Z.getters.getCurrentSession.language:"fr";let t=[];for(let i=0;i<this.apiTraining.length;i++){const a=this.apiTraining[i];let n=this.translator("noTrainer.label");0!==a.training.involves.length&&(n=a.training.involves[0].concern.name);const r=await this.getNextActivity(a.training.activities,a.nbreOfCompleteActivities),s={image:oe.v+a.training.image,name:a.training.name,code:a.training.code,themeName:a.training.theme.name,trainer:n,finishedActivities:a.nbreOfCompleteActivities,totalActivities:a.training.activities.length,progress:a.training.skill,startAt:(0,ee.Ol)(a.startingDate," ",e),nextActivity:null!=r?r:""};t.push(s)}this.courseList=t,this.displayList=t},async getItems(){try{this.apiTraining.splice(0,this.apiTraining.length);const e=await ce.registerTrainings();this.apiTraining.push(...e),null!=this.apiTraining&&this.constructList()}catch(e){this.error=!0}},translator(e){return null!=this.translateHeaders?this.translateHeaders[e]:e}},async mounted(){await this.getItems()},async created(){this.translateHeaders=await ce.translate(["my.learning.inprogress.title","my.learning.inprogress.search","noTrainer.label","my.learning.noInprogress.msg1"])},computed:{displayList:{get(){return this.displayTrainingList},set(e){this.displayTrainingList=e}},apiList(){return this.courseList}}};const ge=(0,J.Z)(ue,[["render",g]]);var de=ge}}]);
//# sourceMappingURL=6910.5988c410.js.map