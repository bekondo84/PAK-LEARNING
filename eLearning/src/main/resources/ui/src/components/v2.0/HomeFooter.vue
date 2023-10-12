<template>
    <div class="w-full px-[120px] py-8 bg-pakDarkBlue flex flex-row justify-center items-start">
        <div class="w-full flex flex-col justify-between items-start gap-14">
            <div class="w-full flex flex-col">
                <h1 class="w-full text-left text-pakBlue text-3xl font-normal font-prototype">PAK E-Learning</h1>
                <span class="w-full text-left uppercase font-bold font-myriadBold text-white text-3xl">Centre de Formation du PAK</span>
            </div>
            <div class="w-full flex flex-col">
                <span class="text-white font-centuryGothic text-[20px] font-bold">{{ translator('social.contacts.title') }}</span>
                <div class="flex flex-row relative gap-2">
                    <SocialMediaIcon text="+237 222 462 100" type="Contact"/>
                    <SocialMediaIcon text="contact@pak.cm" type="Email"/>
                    <SocialMediaIcon text="@portofkrib" type="Facebook"/>
                    <SocialMediaIcon text="kribiport" type="Linkedin"/>
                    <SocialMediaIcon text="PortofKribi" type="Twitter"/>
                </div>
            </div>
            <img src="@/assets/images/pakLogoWhite.png" alt="PAK Logo with White Variant" class="w-[300px]">
        </div>
        <form @submit.prevent="onSubmit" class="w-[800px] h-full flex flex-col justify-start items-end gap-2">
            <span class="w-full text-left font-semibold font-myriadBold text-white text-2xl">{{ translator('request.form.label') }}</span>
            <textarea required v-model.trim="requestMsg" name="" id="" cols="30" rows="8" :placeholder="translator('request.textarea.placeholder')" :class="sendState==='SENT'?'!text-[#34A853] font-bold':(sendState==='ERROR'?'!text-[#EA4335] font-bold':'')" class="bg-white rounded-[16px] w-full p-4 outline-none"/>
            <div class="w-full flex gap-2 bg-white py-2 pl-4 pr-2 rounded-[16px]">
                <input required type="email" v-model.trim="senderEmail"  name="" id="" :placeholder="translator('request.email.placeholder')" class="bg-white w-full outline-none">
                <button type="submit" class="rounded-[10px] py-2 px-8 bg-pakYellow text-white font-bold text-center">{{ translator('request.send.btn') }}</button>
            </div>
        </form>
    </div>
</template>

<script lang="ts">
    import SocialMediaIcon from '@/components/v2.0/SocialMediaIcon.vue'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name: 'HomeFooter',
        components:{
            SocialMediaIcon,
        },
        data(){
            return{
                requestMsg: "",
                senderEmail: "",
                translateHeaders: null,
                session: null,
                sendState:"",
            }
        },
        methods:{
            async onSubmit(){
                const temp = this.requestMsg
                const request = {
                        sender:this.senderEmail,
                        message:this.requestMsg
                    }
                if(temp != ""){
                    try {
                        await axiosService.sendRequest(request)
                        this.sendState = "SENT"
                        this.requestMsg = this.translator('chatbot.msgSent.notif')
                    } catch (error) {
                        console.log(error)
                        this.sendState = "ERROR"
                        this.requestMsg = this.translator('chatbot.msgError.notif')
                    }
                    setTimeout(() => {
                        if(this.sendState === "ERROR"){
                            this.requestMsg = temp
                        }else{
                            this.requestMsg = ""
                        }
                        this.sendState = ""
                    }, 2000);
                }
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['social.contacts.title', 'request.form.label', 'request.textarea.placeholder', 'request.email.placeholder', 'request.send.btn', 'chatbot.msgSent.notif', 'chatbot.msgError.notif']);
            this.session = store.getters.getCurrentSession
            let email = this.session != null ? this.session.email : ""
            this.senderEmail = email.trim()
        },
    }
</script>

<style scoped>

</style>