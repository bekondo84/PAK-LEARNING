"use strict";(self["webpackChunkui"]=self["webpackChunkui"]||[]).push([[4623],{9310:function(e,t,s){s.d(t,{Ol:function(){return o},mJ:function(){return a},nW:function(){return d},xI:function(){return u}});const a=e=>{const t=e?.trim();if(void 0===t||""===t)return"";const s=t.trimStart().toUpperCase().split(" ");return`${s[0][0]}${1===s.length?"":s[s.length-1][0]}`},i=["January","February","March","April","May","June","July","August","September","October","November","December"],r=["Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"],l=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],n=["Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"],o=(e,t,s)=>{if(null==e||""===e)switch(s){case"fr":return"Aucune Date";case"en":return"No Date";default:return"Aucune Date"}const a=e.split(t)[0].split("/"),o=e.split(t)[1],u=new Date(`${a[2]}-${a[1]}-${a[0]}`);if("fr"===s){const e=`${n[u.getDay()]} ${u.getDate()} ${r[u.getMonth()]} ${u.getFullYear()} à ${o}`;return e}if("en"===s){const e=`${l[u.getDay()]} ${u.getDate()} ${i[u.getMonth()]} ${u.getFullYear()} at ${o}`;return e}},u=(e,t,s,a)=>{const i=e.split(s)[0].split("/"),r=t.split(s)[0].split("/"),l=new Date(`${i[2]}-${i[1]}-${i[0]}`),n=new Date(`${r[2]}-${r[1]}-${r[0]}`),o=n.getTime()-l.getTime(),u=Math.ceil(o/864e5);if(u>1)return u+("fr"===a?"j":"d");const d=Math.ceil(o/36e5);if(d>1)return d+"h";const c=Math.ceil(o/6e4);return c>1?c+"mins":Math.ceil(o/1e3)+"s"},d=e=>{switch(e){case"M":return"M.";case"F":return"Mme.";default:return"M."}}},6442:function(e,t,s){s.d(t,{Z:function(){return ue}});var a=s(3396),i=s(9242),r=s(7139),l=s(7774);const n=e=>((0,a.dD)("data-v-3516f70d"),e=e(),(0,a.Cn)(),e),o={class:"w-full h-full overflow-hidden z-[100px]"},u={class:"w-full h-[90px] border !border-red rounded-t-2xl bg-pakDarkBlue text-white flex flex-col gap-1 px-4 py-2"},d={class:"w-full flex flex-row items-center justify-between"},c={class:"w-full font-prototype text-xl text-center capitalize"},h=["placeholder"],f={class:"w-full h-[calc(100%-90px)] bg-[url('@/assets/images/chatBotBackground.png')] bg-no-repeat bg-cover rounded-b-2xl flex flex-col justify-end"},p={class:"h-full px-2 py-2 flex flex-col gap-2 overflow-y-auto text-sm"},g={class:"w-full flex flex-row justify-start pr-7"},w={class:"w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold"},m={class:"w-full flex flex-row justify-start pr-7"},b={class:"w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold"},x={class:"w-full flex flex-row justify-start pr-7"},y={class:"w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold"},k={class:"w-full flex flex-col gap-1 justify-end items-end pl-7"},v={key:0,class:"text-[#34A853] text-right font-bold"},_={key:1,class:"text-[#EA4335] text-right font-bold"},C={class:"w-full flex flex-row justify-start pr-7"},D={key:0,class:"w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold"},M={key:1,class:"w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold"},q={class:"w-full h-[60px] flex flex-row px-4 py-1 gap-1"},S=["placeholder"],z={type:"submit",class:"text-pakYellow flex items-center justify-center px-2 py-2 rounded-lg hover:!bg-pakYellow hover:text-white"},$=n((()=>(0,a._)("div",{class:"w-full rounded-b-2xl bg-pakDarkBlue px-4 py-2"},[(0,a._)("img",{src:l,alt:"PAK Logo with White Variant",class:"w-[200px]"})],-1)));function L(e,t,s,l,n,L){const A=(0,a.up)("WindowClose"),B=(0,a.up)("Send"),V=(0,a.up)("ChatProcessing");return(0,a.wg)(),(0,a.j4)(a.lR,{to:"body"},[(0,a._)("div",o,[n.isActive?((0,a.wg)(),(0,a.iD)("form",{key:0,onSubmit:t[3]||(t[3]=(0,i.iM)(((...e)=>L.onSubmit&&L.onSubmit(...e)),["prevent"])),class:(0,r.C_)([n.currentAnimation,"z-[100px] w-[350px] h-[500px] animate__animated animate__fadeInUp rounded-2xl bg-white drop-shadow-xl shadow-lg border !border-grayToneStroke fixed bottom-9 right-4"])},[(0,a._)("div",u,[(0,a._)("div",d,[(0,a._)("span",c,(0,r.zw)(L.translator("chatbot.title")),1),(0,a._)("button",{onClick:t[0]||(t[0]=(...e)=>L.closeChat&&L.closeChat(...e)),class:"bg-pakDarkBlue"},[(0,a.Wm)(A,{size:30,class:"text-white"})])]),(0,a.wy)((0,a._)("input",{required:"",type:"email","onUpdate:modelValue":t[1]||(t[1]=e=>L.userMail=e),name:"",id:"",placeholder:L.translator("request.email.placeholder"),class:"w-full h-[35px] px-2 bg-white rounded-lg outline-none"},null,8,h),[[i.nr,L.userMail,void 0,{trim:!0}]])]),(0,a._)("div",f,[(0,a._)("div",p,[(0,a._)("div",g,[(0,a._)("span",w,(0,r.zw)(L.translator("chatbot.greetings"))+" "+(0,r.zw)(L.username.toUpperCase())+", "+(0,r.zw)(L.translator("request.form.label")),1)]),(0,a._)("div",m,[(0,a._)("span",b,(0,r.zw)(L.translator("chatbot.welcome.msg1")),1)]),(0,a._)("div",x,[(0,a._)("span",y,(0,r.zw)(L.translator("chatbot.welcome.msg2")),1)]),((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(L.displayRequestList,((e,t)=>((0,a.wg)(),(0,a.iD)("div",{key:t,class:"w-full flex flex-col gap-2"},[(0,a._)("div",k,[(0,a._)("span",{class:(0,r.C_)(["ERROR"===e.status?"text-[#EA4335]":"text-black","w-fit h-fit p-2 break-words rounded-md bg-white text-left drop-shadow-md shadow font-centuryGothic font-bold"])},(0,r.zw)(e.message),3),"SENT"===e.status?((0,a.wg)(),(0,a.iD)("span",v,(0,r.zw)(L.translator("chatbot.msgSent.notif")),1)):((0,a.wg)(),(0,a.iD)("span",_,(0,r.zw)(L.translator("chatbot.msgError.notif")),1))]),(0,a._)("div",C,["SENT"===e.status?((0,a.wg)(),(0,a.iD)("span",D,(0,r.zw)(L.translator("request.sent.succeed")),1)):((0,a.wg)(),(0,a.iD)("span",M,(0,r.zw)(L.translator("request.sent.failed")),1))])])))),128))]),(0,a._)("div",q,[(0,a.wy)((0,a._)("textarea",{id:"chat",required:"",rows:"1","onUpdate:modelValue":t[2]||(t[2]=e=>n.requestMsg=e),class:"block p-2.5 w-full h-auto outline-none text-sm text-gray-900 bg-white rounded-lg border !border-grayToneStroke focus:ring-blue-500 focus:!border-pakBlue dark:bg-gray-800 dark:!border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500",placeholder:L.translator("request.textarea.placeholder")},null,8,S),[[i.nr,n.requestMsg,void 0,{trim:!0}]]),(0,a._)("button",z,[(0,a.Wm)(B,{size:30,class:""})])]),$])],34)):(0,a.kq)("",!0),(0,a._)("button",{onClick:t[4]||(t[4]=(...e)=>L.closeChat&&L.closeChat(...e)),class:(0,r.C_)([n.isActive?n.activeStyle:n.defaultStyle,"aspect-square p-3 fixed bottom-4 right-4 drop-shadow-lg shadow rounded-full flex justify-center items-center border-4 border-pakBlue hover:!text-pakDarkBlue hover:bg-white hover:!scale-110 ease-in-out duration-300"])},[(0,a.Wm)(V,{size:30,class:""})],2)])])}s(7658);var A=s(7627);const B=["aria-hidden","aria-label"],V=["fill","width","height"],H={d:"M12,3C17.5,3 22,6.58 22,11C22,15.42 17.5,19 12,19C10.76,19 9.57,18.82 8.47,18.5C5.55,21 2,21 2,21C4.33,18.67 4.7,17.1 4.75,16.5C3.05,15.07 2,13.13 2,11C2,6.58 6.5,3 12,3M17,12V10H15V12H17M13,12V10H11V12H13M9,12V10H7V12H9Z"},E={key:0};function j(e,t,s,i,l,n){return(0,a.wg)(),(0,a.iD)("span",(0,a.dG)(e.$attrs,{"aria-hidden":!s.title,"aria-label":s.title,class:"material-design-icon chat-processing-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,a.wg)(),(0,a.iD)("svg",{fill:s.fillColor,class:"material-design-icon__svg",width:s.size,height:s.size,viewBox:"0 0 24 24"},[(0,a._)("path",H,[s.title?((0,a.wg)(),(0,a.iD)("title",E,(0,r.zw)(s.title),1)):(0,a.kq)("",!0)])],8,V))],16,B)}var T={name:"ChatProcessingIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}},W=s(89);const R=(0,W.Z)(T,[["render",j]]);var Z=R;const G=["aria-hidden","aria-label"],N=["fill","width","height"],I={d:"M13.46,12L19,17.54V19H17.54L12,13.46L6.46,19H5V17.54L10.54,12L5,6.46V5H6.46L12,10.54L17.54,5H19V6.46L13.46,12Z"},J={key:0};function O(e,t,s,i,l,n){return(0,a.wg)(),(0,a.iD)("span",(0,a.dG)(e.$attrs,{"aria-hidden":!s.title,"aria-label":s.title,class:"material-design-icon window-close-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,a.wg)(),(0,a.iD)("svg",{fill:s.fillColor,class:"material-design-icon__svg",width:s.size,height:s.size,viewBox:"0 0 24 24"},[(0,a._)("path",I,[s.title?((0,a.wg)(),(0,a.iD)("title",J,(0,r.zw)(s.title),1)):(0,a.kq)("",!0)])],8,N))],16,G)}var F={name:"WindowCloseIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const U=(0,W.Z)(F,[["render",O]]);var Y=U;const P=["aria-hidden","aria-label"],K=["fill","width","height"],Q={d:"M2,21L23,12L2,3V10L17,12L2,14V21Z"},X={key:0};function ee(e,t,s,i,l,n){return(0,a.wg)(),(0,a.iD)("span",(0,a.dG)(e.$attrs,{"aria-hidden":!s.title,"aria-label":s.title,class:"material-design-icon send-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,a.wg)(),(0,a.iD)("svg",{fill:s.fillColor,class:"material-design-icon__svg",width:s.size,height:s.size,viewBox:"0 0 24 24"},[(0,a._)("path",Q,[s.title?((0,a.wg)(),(0,a.iD)("title",X,(0,r.zw)(s.title),1)):(0,a.kq)("",!0)])],8,K))],16,P)}var te={name:"SendIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const se=(0,W.Z)(te,[["render",ee]]);var ae=se,ie=s(3373),re=s(9310);const le=new ie.Z;var ne={name:"ChatBot",components:{ChatProcessing:Z,WindowClose:Y,Send:ae},async mounted(){this.session=A.Z.getters.getCurrentSession},data(){return{isActive:!1,activeStyle:"!text-pakDarkBlue bg-white",defaultStyle:"!text-white bg-pakDarkBlue",animateInclass:"animate__animated animate__fadeInUp",animateOutClass:"animate__animated animate__fadeOutDown",currentAnimation:"",senderEmail:"",requestMsg:"",requestList:[],session:null,translateHeaders:null}},methods:{async onSubmit(){if(""!==this.requestMsg){let t;try{const e={sender:this.userMail,message:this.requestMsg};await le.sendRequest(e),t={message:this.requestMsg,status:"SENT"}}catch(e){console.log(e),t={message:this.requestMsg,status:"ERROR"}}this.requestList.push(t),this.requestMsg=""}},closeChat(){this.isActive?(this.currentAnimation=this.animateOutClass,setTimeout((()=>{this.isActive=!1}),500)):(this.currentAnimation="",setTimeout((()=>{this.isActive=!0}),10))},translator(e){return null!=this.translateHeaders?this.translateHeaders[e]:e}},async created(){this.translateHeaders=await le.translate(["chatbot.title","chatbot.greetings","request.form.label","chatbot.welcome.msg1","chatbot.welcome.msg2","request.textarea.placeholder","request.email.placeholder","chatbot.msgSent.notif","chatbot.msgError.notif","request.sent.succeed","request.sent.failed"])},computed:{displayRequestList(){return this.requestList},username(){const e=null!=this.session?this.session.fullname:"",t=null!=this.session?this.session.gender:null,s=null!=t?`${(0,re.nW)(t)} ${e}`:"";return s.trim()},useremail(){const e=null!==this.session?this.session.email:"";return e.trim()},userMail:{get(){return""!==this.senderEmail?this.senderEmail:this.useremail},set(e){this.senderEmail=e}}}};const oe=(0,W.Z)(ne,[["render",L],["__scopeId","data-v-3516f70d"]]);var ue=oe},1365:function(e,t,s){e.exports=s.p+"e-learning/img/pakLogo2.39a93591.svg"},7774:function(e,t,s){e.exports=s.p+"e-learning/img/pakLogoWhite.ca3e1a68.png"}}]);
//# sourceMappingURL=4623.aa6cf852.js.map