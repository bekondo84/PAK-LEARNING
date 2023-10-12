"use strict";(self["webpackChunkui"]=self["webpackChunkui"]||[]).push([[5986],{9310:function(e,t,i){i.d(t,{Ol:function(){return o},mJ:function(){return l},nW:function(){return d},xI:function(){return c}});const l=e=>{const t=e?.trim();if(void 0===t||""===t)return"";const i=t.trimStart().toUpperCase().split(" ");return`${i[0][0]}${1===i.length?"":i[i.length-1][0]}`},r=["January","February","March","April","May","June","July","August","September","October","November","December"],a=["Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"],n=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],s=["Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"],o=(e,t,i)=>{if(null==e||""===e)switch(i){case"fr":return"Aucune Date";case"en":return"No Date";default:return"Aucune Date"}const l=e.split(t)[0].split("/"),o=e.split(t)[1],c=new Date(`${l[2]}-${l[1]}-${l[0]}`);if("fr"===i){const e=`${s[c.getDay()]} ${c.getDate()} ${a[c.getMonth()]} ${c.getFullYear()} à ${o}`;return e}if("en"===i){const e=`${n[c.getDay()]} ${c.getDate()} ${r[c.getMonth()]} ${c.getFullYear()} at ${o}`;return e}},c=(e,t,i,l)=>{const r=e.split(i)[0].split("/"),a=t.split(i)[0].split("/"),n=new Date(`${r[2]}-${r[1]}-${r[0]}`),s=new Date(`${a[2]}-${a[1]}-${a[0]}`),o=s.getTime()-n.getTime(),c=Math.ceil(o/864e5);if(c>1)return c+("fr"===l?"j":"d");const d=Math.ceil(o/36e5);if(d>1)return d+"h";const u=Math.ceil(o/6e4);return u>1?u+"mins":Math.ceil(o/1e3)+"s"},d=e=>{switch(e){case"M":return"M.";case"F":return"Mme.";default:return"M."}}},569:function(e,t,i){i.d(t,{Z:function(){return ce}});var l=i(3396),r=i(7139),a=i(1365);const n=e=>((0,l.dD)("data-v-c40fdfc4"),e=e(),(0,l.Cn)(),e),s={class:"w-full sticky z-10 top-0 left-0 drop-shadow-md px-[120px] py-3 bg-white flex flex-row items-center"},o=n((()=>(0,l._)("img",{src:a,alt:"",class:"w-[300px]"},null,-1))),c=n((()=>(0,l._)("h1",{class:"w-full flex justify-center items-center text-center text-black text-[32px] font-normal leading-10 font-prototype"},"PAK E-Learning",-1))),d={class:"w-[600px] h-full flex flex-row gap-1 items-center"},u={key:0,role:"status",class:"animate-pulse"},p=n((()=>(0,l._)("div",{class:"w-[60px] aspect-square bg-gray-200 rounded-full dark:bg-gray-700"},null,-1))),g=[p],h={key:1,class:"w-[80px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white font-agressive text-xl"},f={key:2,role:"status",class:"animate-pulse"},m=n((()=>(0,l._)("div",{class:"w-[200px] h-6 bg-gray-200 rounded-full dark:bg-gray-700"},null,-1))),w=[m],b={key:3,class:"w-full flex justify-center items-center text-black text-2xl font-normal font-prototype"},x={class:"h-full relative flex items-center"},k={key:2,class:"w-[300px] p-3 drop-shadow-2xl shadow rounded-[8px] bg-white absolute bottom-[-150px] right-0 flex flex-col gap-2"},v={class:"flex font-myriadBold text-lg"},y={class:"flex font-myriadBold text-lg ml-4"};function C(e,t,i,a,n,p){const m=(0,l.up)("ChevronDown"),C=(0,l.up)("ChevronUp"),A=(0,l.up)("AccountCircle"),D=(0,l.up)("Logout"),z=(0,l.up)("v-btn");return(0,l.wg)(),(0,l.iD)("div",s,[o,c,(0,l._)("div",d,[""===e.userInitals?((0,l.wg)(),(0,l.iD)("div",u,g)):((0,l.wg)(),(0,l.iD)("span",h,(0,r.zw)(e.userInitals),1)),""===e.username?((0,l.wg)(),(0,l.iD)("div",f,w)):((0,l.wg)(),(0,l.iD)("span",b,(0,r.zw)(e.username),1)),(0,l._)("div",x,[e.dropDownVisible?(0,l.kq)("",!0):((0,l.wg)(),(0,l.iD)("button",{key:0,class:"",onClick:t[0]||(t[0]=t=>e.dropDownVisible=!e.dropDownVisible)},[(0,l.Wm)(m,{size:32})])),e.dropDownVisible?((0,l.wg)(),(0,l.iD)("button",{key:1,class:"",onClick:t[1]||(t[1]=t=>e.dropDownVisible=!e.dropDownVisible)},[(0,l.Wm)(C,{size:32})])):(0,l.kq)("",!0),e.dropDownVisible?((0,l.wg)(),(0,l.iD)("div",k,[(0,l._)("button",{onClick:t[2]||(t[2]=(...t)=>e.routeToProfile&&e.routeToProfile(...t)),class:"flex flex-row justify-center px-4 py-2.5 text-pakBlue items-center gap-2 hover:text-pakDarkBlue"},[(0,l.Wm)(A,{size:24,class:""}),(0,l._)("span",v,(0,r.zw)(e.translator("view.profile.btn")),1)]),(0,l.Wm)(z,{class:"w-full !text-pakDarkBlue border !border-pakDarkBlue px-4 py-2 flex flex-row rounded-[8px] items-center hover:!text-white hover:bg-pakDarkBlue",loading:e.loading,size:"large",type:"submit",variant:"flat",onClick:e.disconnect},{default:(0,l.w5)((()=>[(0,l.Wm)(D,{size:24,class:"-rotate-180"}),(0,l._)("span",y,(0,r.zw)(e.translator("logout.btn")),1)])),_:1},8,["loading","onClick"])])):(0,l.kq)("",!0)])])])}i(7658);var A=i(7627),D=i(9310);const z=["aria-hidden","aria-label"],_=["fill","width","height"],H={d:"M7.41,8.58L12,13.17L16.59,8.58L18,10L12,16L6,10L7.41,8.58Z"},q={key:0};function S(e,t,i,a,n,s){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon chevron-down-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",H,[i.title?((0,l.wg)(),(0,l.iD)("title",q,(0,r.zw)(i.title),1)):(0,l.kq)("",!0)])],8,_))],16,z)}var M={name:"ChevronDownIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}},B=i(89);const L=(0,B.Z)(M,[["render",S]]);var V=L;const I=["aria-hidden","aria-label"],W=["fill","width","height"],Z={d:"M7.41,15.41L12,10.83L16.59,15.41L18,14L12,8L6,14L7.41,15.41Z"},E={key:0};function P(e,t,i,a,n,s){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon chevron-up-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",Z,[i.title?((0,l.wg)(),(0,l.iD)("title",E,(0,r.zw)(i.title),1)):(0,l.kq)("",!0)])],8,W))],16,I)}var $={name:"ChevronUpIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const F=(0,B.Z)($,[["render",P]]);var N=F;const R=["aria-hidden","aria-label"],O=["fill","width","height"],T={d:"M12,19.2C9.5,19.2 7.29,17.92 6,16C6.03,14 10,12.9 12,12.9C14,12.9 17.97,14 18,16C16.71,17.92 14.5,19.2 12,19.2M12,5A3,3 0 0,1 15,8A3,3 0 0,1 12,11A3,3 0 0,1 9,8A3,3 0 0,1 12,5M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12C22,6.47 17.5,2 12,2Z"},U={key:0};function G(e,t,i,a,n,s){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon account-circle-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",T,[i.title?((0,l.wg)(),(0,l.iD)("title",U,(0,r.zw)(i.title),1)):(0,l.kq)("",!0)])],8,O))],16,R)}var Y={name:"AccountCircleIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const J=(0,B.Z)(Y,[["render",G]]);var K=J;const Q=["aria-hidden","aria-label"],X=["fill","width","height"],j={d:"M16,17V14H9V10H16V7L21,12L16,17M14,2A2,2 0 0,1 16,4V6H14V4H5V20H14V18H16V20A2,2 0 0,1 14,22H5A2,2 0 0,1 3,20V4A2,2 0 0,1 5,2H14Z"},ee={key:0};function te(e,t,i,a,n,s){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon logout-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",j,[i.title?((0,l.wg)(),(0,l.iD)("title",ee,(0,r.zw)(i.title),1)):(0,l.kq)("",!0)])],8,X))],16,Q)}var ie={name:"LogoutIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const le=(0,B.Z)(ie,[["render",te]]);var re=le,ae=i(3373);const ne=new ae.Z;var se=(0,l.aZ)({name:"HomeHeader",components:{ChevronDown:V,ChevronUp:N,AccountCircle:K,Logout:re},data(){return{dropDownVisible:!1,loading:!1,session:null,translateHeaders:null}},methods:{routeToProfile(){this.$router.push({name:"ProfilePage"})},disconnect(){this.loading=!0,setTimeout((()=>{this.loading=!1,this.$router.push({name:"LoginPage"})}),1e3)},translator(e){return null!=this.translateHeaders?this.translateHeaders[e]:e}},async mounted(){this.session=A.Z.getters.getCurrentSession},async created(){this.translateHeaders=await ne.translate(["view.profile.btn","logout.btn"]),this.emitter.on("authorization-error",(()=>{this.$router.push({name:"LoginPage"})}))},computed:{username(){const e=null!=this.session?this.session.fullname:"";return e},usermail(){const e=null!=this.session?this.session.email:"";return e.trim()},userInitals(){return(0,D.mJ)(this.username)}}});const oe=(0,B.Z)(se,[["render",C],["__scopeId","data-v-c40fdfc4"]]);var ce=oe},5986:function(e,t,i){i.r(t),i.d(t,{default:function(){return Re}});var l=i(3396);const r={class:"w-screen h-screen flex flex-col relative"};function a(e,t,i,a,n,s){const o=(0,l.up)("HomeHeader"),c=(0,l.up)("router-view"),d=(0,l.up)("HomeFooter");return(0,l.wg)(),(0,l.iD)("div",r,[(0,l.Wm)(o),(0,l.Wm)(c),(0,l.Wm)(d)])}var n=i(569),s=i(7139),o=i(9242),c=i(7774);const d={class:"w-full px-[120px] py-8 bg-pakDarkBlue flex flex-row justify-center items-start"},u={class:"w-full flex flex-col justify-between items-start gap-14"},p=(0,l._)("div",{class:"w-full flex flex-col"},[(0,l._)("h1",{class:"w-full text-left text-pakBlue text-3xl font-normal font-prototype"},"PAK E-Learning"),(0,l._)("span",{class:"w-full text-left uppercase font-bold font-myriadBold text-white text-3xl"},"Centre de Formation du PAK")],-1),g={class:"w-full flex flex-col"},h={class:"text-white font-centuryGothic text-[20px] font-bold"},f={class:"flex flex-row relative gap-2"},m=(0,l._)("img",{src:c,alt:"PAK Logo with White Variant",class:"w-[300px]"},null,-1),w={class:"w-full text-left font-semibold font-myriadBold text-white text-2xl"},b=["placeholder"],x={class:"w-full flex gap-2 bg-white py-2 pl-4 pr-2 rounded-[16px]"},k=["placeholder"],v={type:"submit",class:"rounded-[10px] py-2 px-8 bg-pakYellow text-white font-bold text-center"};function y(e,t,i,r,a,n){const c=(0,l.up)("SocialMediaIcon");return(0,l.wg)(),(0,l.iD)("div",d,[(0,l._)("div",u,[p,(0,l._)("div",g,[(0,l._)("span",h,(0,s.zw)(n.translator("social.contacts.title")),1),(0,l._)("div",f,[(0,l.Wm)(c,{text:"+237 222 462 100",type:"Contact"}),(0,l.Wm)(c,{text:"contact@pak.cm",type:"Email"}),(0,l.Wm)(c,{text:"@portofkrib",type:"Facebook"}),(0,l.Wm)(c,{text:"kribiport",type:"Linkedin"}),(0,l.Wm)(c,{text:"PortofKribi",type:"Twitter"})])]),m]),(0,l._)("form",{onSubmit:t[2]||(t[2]=(0,o.iM)(((...e)=>n.onSubmit&&n.onSubmit(...e)),["prevent"])),class:"w-[800px] h-full flex flex-col justify-start items-end gap-2"},[(0,l._)("span",w,(0,s.zw)(n.translator("request.form.label")),1),(0,l.wy)((0,l._)("textarea",{required:"","onUpdate:modelValue":t[0]||(t[0]=e=>a.requestMsg=e),name:"",id:"",cols:"30",rows:"8",placeholder:n.translator("request.textarea.placeholder"),class:(0,s.C_)(["SENT"===a.sendState?"!text-[#34A853] font-bold":"ERROR"===a.sendState?"!text-[#EA4335] font-bold":"","bg-white rounded-[16px] w-full p-4 outline-none"])},null,10,b),[[o.nr,a.requestMsg,void 0,{trim:!0}]]),(0,l._)("div",x,[(0,l.wy)((0,l._)("input",{required:"",type:"email","onUpdate:modelValue":t[1]||(t[1]=e=>a.senderEmail=e),name:"",id:"",placeholder:n.translator("request.email.placeholder"),class:"bg-white w-full outline-none"},null,8,k),[[o.nr,a.senderEmail,void 0,{trim:!0}]]),(0,l._)("button",v,(0,s.zw)(n.translator("request.send.btn")),1)])],32)])}var C="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAQgSURBVHgB7VZbaBxVGP7OzM5tJ1uJNnGleUhJqkZUKOIFrCAKYmmFWi8QLxQCahVsQBRE8+BLrSI+tKitiFIFKVW8gLXW9qG2KHh5SFt6w0AKAU1KErqX7Mzu7Fz6nzM7u5N0uzu0hfYhH5yd4cw5//fN9///mQUWsYirDBbdjL80+BgDe1Vb/bip3bfKZqpWxRWEX6kolcP7dWf/nlnfND/t27Zzb13A+Oa3bpSOj47Ctm9iqor02idhrFkPphu4IggClHZ/ifLBffDnikBmSSnI9vQv/+CTqRR/rlt5vQqWDWhhUKmg9N3XcE4cQeaVNyAv7cblwJudxtz2D+GcPFaf813XhM80fi/xH3u6EHhyygPjhgRiVE8fR25kGJU/D+NSwffm3t5E5EfrcSFLyPt+cGZyUqwRDtg05FIRkqIg5bqQPDdUnz+H/NYtME4chTk4BCltJiL2C3lYv/wA68fdNeIQVTmFyZKFfHkG5rIVDQERPMqPk1KgdGWhzUyJ3PEA9oE9wpGODRuh3r6yJXl17DSKX+2AO3aqMUnO2td3YWJ8DOWyDdlI1x9JCwNIroPggYdhrX0agaqFImi4E2dQ2PourL3f0yv6FzLTmvKhA8htfhPuvyfr+0Ax3NVPIH/3KjgVW1Q9i21LNXsLlaz2Vt6LYs9ymD/tgkzkwtp8DnM7t1OBHkOG3JC7s+H87AwKn29DdfRvBJ7XCNTTi8pTG+Bml4H9cZCIiZoFYam1EsBhGAaCvhUoPP8yOiifypG/iCkM7vzzO3IT48hsfA1MoqL66H3402djNsoI7rkfDrnoa4awWZKYIGYBC4W0E8CRTvNcdaG07hlo/bfC+PlbgPcxwZv6H/n3RsR9UC43NmWWwFv/HNw77uKs9Rwz1qDl1+iIaSmgIQIoUUC3tx/mN1+AUTEJYtuet5bdfBuqz76I4LrOeXkWppAALoYMEPeJHBAkVEg8HRwWbSwODcM49CuU3/ZRyTvhImpfPLIO7oOPUgGpF5DXBdZcaFuEcfIIXAQPYFkW7IfWwO0fgL7rszDY0CZ45A4WBI9DonREx1wiAXHyCLqui/ky5ZwTVodHoJgZBNTXFyOOEH/z9l3QhDxC5AQXonR01IO3g7A/7MIkbRi0DNTZ2QnHccRIComF7Uefu+RtuBCyLIs0cBFRYbqum2gvi86BGr0xX0C5bQCVqluhamcx/7gY7kISEazWhuF9Y76tA3wjJ+Jv3+yZpmni2k6EVGtBcQ4goQDeOpycX1uBu8PhN/tIxcSKIcqrYYGILN/QXWIpZTq+gdvNT8F25HER3KU6UbNRo6Z6+M8cuLPE9wkHBrZ8PHvqhcFB8mmIycrSlJH2KaDkxb9sCcDFXnSPbqhqb5/Oqt45+mOy45bX35nBIhZxLeA8PjN4M9AhUy8AAAAASUVORK5CYII=";const A={key:0,class:"group rounded-full relative"},D={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden"},z={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block"},_={class:"w-[180px] absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-2 text-xl"},H={key:1,class:"group rounded-full relative"},q={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden"},S=(0,l._)("button",{class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block"},[(0,l._)("img",{src:C,alt:"",class:"w-[28px] aspect-square"})],-1),M={class:"absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl"},B={key:2,class:"group rounded-full relative"},L={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden"},V={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block"},I={class:"absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl"},W={key:3,class:"group rounded-full relative"},Z={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden"},E={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block"},P={class:"absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl"},$={key:4,class:"group rounded-full relative"},F={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden"},N={class:"p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block"},R={class:"absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl"};function O(e,t,i,r,a,n){const o=(0,l.up)("Phone"),c=(0,l.up)("EmailOutline"),d=(0,l.up)("Facebook"),u=(0,l.up)("Linkedin"),p=(0,l.up)("Twitter");return(0,l.wg)(),(0,l.iD)(l.HY,null,["Contact"===i.type?((0,l.wg)(),(0,l.iD)("div",A,[(0,l._)("button",D,[(0,l.Wm)(o,{size:28,class:"text-white"})]),(0,l._)("button",z,[(0,l.Wm)(o,{size:28,class:"text-pakYellow"})]),(0,l._)("span",_,(0,s.zw)(i.text),1)])):(0,l.kq)("",!0),"Email"===i.type?((0,l.wg)(),(0,l.iD)("div",H,[(0,l._)("button",q,[(0,l.Wm)(c,{size:28,class:"text-white"})]),S,(0,l._)("span",M,(0,s.zw)(i.text),1)])):(0,l.kq)("",!0),"Facebook"===i.type?((0,l.wg)(),(0,l.iD)("div",B,[(0,l._)("button",L,[(0,l.Wm)(d,{size:28,class:"text-white"})]),(0,l._)("button",V,[(0,l.Wm)(d,{size:28,class:"text-pakDarkBlue"})]),(0,l._)("span",I,(0,s.zw)(i.text),1)])):(0,l.kq)("",!0),"Linkedin"===i.type?((0,l.wg)(),(0,l.iD)("div",W,[(0,l._)("button",Z,[(0,l.Wm)(u,{size:28,class:"text-white"})]),(0,l._)("button",E,[(0,l.Wm)(u,{size:28,class:"text-pakDarkBlue"})]),(0,l._)("span",P,(0,s.zw)(i.text),1)])):(0,l.kq)("",!0),"Twitter"===i.type?((0,l.wg)(),(0,l.iD)("div",$,[(0,l._)("button",F,[(0,l.Wm)(p,{size:28,class:"text-white"})]),(0,l._)("button",N,[(0,l.Wm)(p,{size:28,class:"text-pakDarkBlue"})]),(0,l._)("span",R,(0,s.zw)(i.text),1)])):(0,l.kq)("",!0)],64)}const T=["aria-hidden","aria-label"],U=["fill","width","height"],G={d:"M6.62,10.79C8.06,13.62 10.38,15.94 13.21,17.38L15.41,15.18C15.69,14.9 16.08,14.82 16.43,14.93C17.55,15.3 18.75,15.5 20,15.5A1,1 0 0,1 21,16.5V20A1,1 0 0,1 20,21A17,17 0 0,1 3,4A1,1 0 0,1 4,3H7.5A1,1 0 0,1 8.5,4C8.5,5.25 8.7,6.45 9.07,7.57C9.18,7.92 9.1,8.31 8.82,8.59L6.62,10.79Z"},Y={key:0};function J(e,t,i,r,a,n){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon phone-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",G,[i.title?((0,l.wg)(),(0,l.iD)("title",Y,(0,s.zw)(i.title),1)):(0,l.kq)("",!0)])],8,U))],16,T)}var K={name:"PhoneIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}},Q=i(89);const X=(0,Q.Z)(K,[["render",J]]);var j=X;const ee=["aria-hidden","aria-label"],te=["fill","width","height"],ie={d:"M22 6C22 4.9 21.1 4 20 4H4C2.9 4 2 4.9 2 6V18C2 19.1 2.9 20 4 20H20C21.1 20 22 19.1 22 18V6M20 6L12 11L4 6H20M20 18H4V8L12 13L20 8V18Z"},le={key:0};function re(e,t,i,r,a,n){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon email-outline-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",ie,[i.title?((0,l.wg)(),(0,l.iD)("title",le,(0,s.zw)(i.title),1)):(0,l.kq)("",!0)])],8,te))],16,ee)}var ae={name:"EmailOutlineIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const ne=(0,Q.Z)(ae,[["render",re]]);var se=ne;const oe=["aria-hidden","aria-label"],ce=["fill","width","height"],de={d:"M12 2.04C6.5 2.04 2 6.53 2 12.06C2 17.06 5.66 21.21 10.44 21.96V14.96H7.9V12.06H10.44V9.85C10.44 7.34 11.93 5.96 14.22 5.96C15.31 5.96 16.45 6.15 16.45 6.15V8.62H15.19C13.95 8.62 13.56 9.39 13.56 10.18V12.06H16.34L15.89 14.96H13.56V21.96A10 10 0 0 0 22 12.06C22 6.53 17.5 2.04 12 2.04Z"},ue={key:0};function pe(e,t,i,r,a,n){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon facebook-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",de,[i.title?((0,l.wg)(),(0,l.iD)("title",ue,(0,s.zw)(i.title),1)):(0,l.kq)("",!0)])],8,ce))],16,oe)}var ge={name:"FacebookIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const he=(0,Q.Z)(ge,[["render",pe]]);var fe=he;const me=["aria-hidden","aria-label"],we=["fill","width","height"],be={d:"M19 3A2 2 0 0 1 21 5V19A2 2 0 0 1 19 21H5A2 2 0 0 1 3 19V5A2 2 0 0 1 5 3H19M18.5 18.5V13.2A3.26 3.26 0 0 0 15.24 9.94C14.39 9.94 13.4 10.46 12.92 11.24V10.13H10.13V18.5H12.92V13.57C12.92 12.8 13.54 12.17 14.31 12.17A1.4 1.4 0 0 1 15.71 13.57V18.5H18.5M6.88 8.56A1.68 1.68 0 0 0 8.56 6.88C8.56 5.95 7.81 5.19 6.88 5.19A1.69 1.69 0 0 0 5.19 6.88C5.19 7.81 5.95 8.56 6.88 8.56M8.27 18.5V10.13H5.5V18.5H8.27Z"},xe={key:0};function ke(e,t,i,r,a,n){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon linkedin-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",be,[i.title?((0,l.wg)(),(0,l.iD)("title",xe,(0,s.zw)(i.title),1)):(0,l.kq)("",!0)])],8,we))],16,me)}var ve={name:"LinkedinIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const ye=(0,Q.Z)(ve,[["render",ke]]);var Ce=ye;const Ae=["aria-hidden","aria-label"],De=["fill","width","height"],ze={d:"M22.46,6C21.69,6.35 20.86,6.58 20,6.69C20.88,6.16 21.56,5.32 21.88,4.31C21.05,4.81 20.13,5.16 19.16,5.36C18.37,4.5 17.26,4 16,4C13.65,4 11.73,5.92 11.73,8.29C11.73,8.63 11.77,8.96 11.84,9.27C8.28,9.09 5.11,7.38 3,4.79C2.63,5.42 2.42,6.16 2.42,6.94C2.42,8.43 3.17,9.75 4.33,10.5C3.62,10.5 2.96,10.3 2.38,10C2.38,10 2.38,10 2.38,10.03C2.38,12.11 3.86,13.85 5.82,14.24C5.46,14.34 5.08,14.39 4.69,14.39C4.42,14.39 4.15,14.36 3.89,14.31C4.43,16 6,17.26 7.89,17.29C6.43,18.45 4.58,19.13 2.56,19.13C2.22,19.13 1.88,19.11 1.54,19.07C3.44,20.29 5.7,21 8.12,21C16,21 20.33,14.46 20.33,8.79C20.33,8.6 20.33,8.42 20.32,8.23C21.16,7.63 21.88,6.87 22.46,6Z"},_e={key:0};function He(e,t,i,r,a,n){return(0,l.wg)(),(0,l.iD)("span",(0,l.dG)(e.$attrs,{"aria-hidden":!i.title,"aria-label":i.title,class:"material-design-icon twitter-icon",role:"img",onClick:t[0]||(t[0]=t=>e.$emit("click",t))}),[((0,l.wg)(),(0,l.iD)("svg",{fill:i.fillColor,class:"material-design-icon__svg",width:i.size,height:i.size,viewBox:"0 0 24 24"},[(0,l._)("path",ze,[i.title?((0,l.wg)(),(0,l.iD)("title",_e,(0,s.zw)(i.title),1)):(0,l.kq)("",!0)])],8,De))],16,Ae)}var qe={name:"TwitterIcon",emits:["click"],props:{title:{type:String},fillColor:{type:String,default:"currentColor"},size:{type:Number,default:24}}};const Se=(0,Q.Z)(qe,[["render",He]]);var Me=Se,Be={name:"SocialMediaIcon",props:{type:String,text:String},components:{Phone:j,EmailOutline:se,Facebook:fe,Linkedin:Ce,Twitter:Me},setup(){return{}},data(){return{}}};const Le=(0,Q.Z)(Be,[["render",O]]);var Ve=Le,Ie=i(7627),We=i(3373);const Ze=new We.Z;var Ee={name:"HomeFooter",components:{SocialMediaIcon:Ve},data(){return{requestMsg:"",senderEmail:"",translateHeaders:null,session:null,sendState:""}},methods:{async onSubmit(){const e=this.requestMsg,t={sender:this.senderEmail,message:this.requestMsg};if(""!=e){try{await Ze.sendRequest(t),this.sendState="SENT",this.requestMsg=this.translator("chatbot.msgSent.notif")}catch(i){console.log(i),this.sendState="ERROR",this.requestMsg=this.translator("chatbot.msgError.notif")}setTimeout((()=>{"ERROR"===this.sendState?this.requestMsg=e:this.requestMsg="",this.sendState=""}),2e3)}},translator(e){return null!=this.translateHeaders?this.translateHeaders[e]:e}},async created(){this.translateHeaders=await Ze.translate(["social.contacts.title","request.form.label","request.textarea.placeholder","request.email.placeholder","request.send.btn","chatbot.msgSent.notif","chatbot.msgError.notif"]),this.session=Ie.Z.getters.getCurrentSession;let e=null!=this.session?this.session.email:"";this.senderEmail=e.trim()}};const Pe=(0,Q.Z)(Ee,[["render",y]]);var $e=Pe,Fe={name:"HomePage",components:{HomeHeader:n.Z,HomeFooter:$e}};const Ne=(0,Q.Z)(Fe,[["render",a]]);var Re=Ne},1365:function(e,t,i){e.exports=i.p+"e-learning/img/pakLogo2.39a93591.svg"},7774:function(e,t,i){e.exports=i.p+"e-learning/img/pakLogoWhite.ca3e1a68.png"}}]);
//# sourceMappingURL=5986.c07856df.js.map