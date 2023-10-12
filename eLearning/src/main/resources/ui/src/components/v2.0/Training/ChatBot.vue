<template>
    <Teleport to="body">
        <div class="w-full h-full overflow-hidden z-[100px]">
            <form @submit.prevent="onSubmit" v-if="isActive" :class="currentAnimation" class="z-[100px] w-[350px] h-[500px] animate__animated animate__fadeInUp rounded-2xl bg-white drop-shadow-xl shadow-lg border !border-grayToneStroke fixed bottom-9 right-4">
                <div class="w-full h-[90px] border !border-red rounded-t-2xl bg-pakDarkBlue text-white flex flex-col gap-1 px-4 py-2">
                    <div class="w-full flex flex-row items-center justify-between">
                        <span class="w-full font-prototype text-xl text-center capitalize">{{ translator('chatbot.title') }}</span>
                        <button @click="closeChat" class="bg-pakDarkBlue">
                            <WindowClose :size="30" class="text-white"/>
                        </button>
                    </div>
                    <input required type="email" v-model.trim="userMail"  name="" id="" :placeholder="translator('request.email.placeholder')" class="w-full h-[35px] px-2 bg-white rounded-lg outline-none">
                </div>
                <div class="w-full h-[calc(100%-90px)] bg-[url('@/assets/images/chatBotBackground.png')] bg-no-repeat bg-cover rounded-b-2xl flex flex-col justify-end">
                    <div class="h-full px-2 py-2 flex flex-col gap-2 overflow-y-auto text-sm">
                        <div class="w-full flex flex-row justify-start pr-7">
                            <span class=" w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold">{{ translator('chatbot.greetings') }} {{ username.toUpperCase() }}, {{ translator('request.form.label') }}</span>
                        </div>
                        <div class="w-full flex flex-row justify-start pr-7">
                            <span class=" w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold">{{ translator('chatbot.welcome.msg1') }}</span>
                        </div>
                        <div class="w-full flex flex-row justify-start pr-7">
                            <span class=" w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold">{{ translator('chatbot.welcome.msg2') }}</span>
                        </div>
                        <div v-for="(request, index) in displayRequestList" :key="index" class="w-full flex flex-col gap-2">
                            <div  class="w-full flex flex-col gap-1 justify-end items-end pl-7">
                                <span :class="request.status==='ERROR'?'text-[#EA4335]':'text-black'" class="w-fit h-fit p-2 break-words rounded-md bg-white text-left drop-shadow-md shadow font-centuryGothic font-bold">{{ request.message }}</span>
                                <span v-if="request.status==='SENT'" class="text-[#34A853] text-right font-bold">{{ translator('chatbot.msgSent.notif') }}</span>
                                <span v-else class="text-[#EA4335] text-right font-bold">{{ translator('chatbot.msgError.notif') }}</span>
                            </div>
                            <div class="w-full flex flex-row justify-start pr-7">
                                <span v-if="request.status==='SENT'" class=" w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold">{{ translator('request.sent.succeed') }}</span>
                                <span v-else class="w-fit h-fit p-2 rounded-md bg-pakBlue text-white text-left drop-shadow-md shadow font-centuryGothic font-bold">{{ translator('request.sent.failed') }}</span>
                            </div>
                        </div>
                    </div>
                    <div class="w-full h-[60px] flex flex-row px-4 py-1 gap-1">
                        <textarea id="chat" required rows="1" v-model.trim="requestMsg" class="block p-2.5 w-full h-auto outline-none text-sm text-gray-900 bg-white rounded-lg border !border-grayToneStroke focus:ring-blue-500 focus:!border-pakBlue dark:bg-gray-800 dark:!border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" :placeholder="translator('request.textarea.placeholder')"></textarea>
                        <button type="submit" class="text-pakYellow flex items-center justify-center px-2 py-2 rounded-lg hover:!bg-pakYellow hover:text-white">
                            <Send :size="30" class=""/>
                        </button>
                    </div>
                    <div class="w-full rounded-b-2xl bg-pakDarkBlue px-4 py-2">
                        <img src="@/assets/images/pakLogoWhite.png" alt="PAK Logo with White Variant" class="w-[200px]">
                    </div>
                </div> 
            </form>
            <button @click="closeChat" :class="isActive?activeStyle:defaultStyle" class="aspect-square p-3 fixed bottom-4 right-4 drop-shadow-lg shadow rounded-full flex justify-center items-center border-4 border-pakBlue hover:!text-pakDarkBlue hover:bg-white hover:!scale-110 ease-in-out duration-300">
                <ChatProcessing :size="30" class=""/>
            </button>
        </div>
    </Teleport>
</template>

<script lang="ts">
    import store from '@/store';
    import ChatProcessing from 'vue-material-design-icons/ChatProcessing.vue'
    import WindowClose from 'vue-material-design-icons/WindowClose.vue'
    import Send from 'vue-material-design-icons/Send.vue'
    import 'animate.css';
    import AxiosService from '@/services/axiosService';
    import {getGenderLabel} from '@/hooks/myHooks'

    const axiosService = new AxiosService();
    export default {
        name:"ChatBot",
        components:{
            ChatProcessing,
            WindowClose,
            Send,
        },
        async mounted() {
            this.session = store.getters.getCurrentSession;
        },
        data(){
            return{
                isActive: false,
                activeStyle: '!text-pakDarkBlue bg-white',
                defaultStyle: '!text-white bg-pakDarkBlue',
                animateInclass:'animate__animated animate__fadeInUp',
                animateOutClass:'animate__animated animate__fadeOutDown',
                currentAnimation:'',
                senderEmail: "",
                requestMsg:"",
                requestList:[],

                // Integration Data
                session: null,
                translateHeaders: null,
            }
        },
        methods:{
            async onSubmit(){
                if(this.requestMsg!==""){
                    let reqMsg
                    try {
                        const request = {
                            sender:this.userMail,
                            message:this.requestMsg
                        }
                        await axiosService.sendRequest(request)
                        reqMsg = {
                            message: this.requestMsg,
                            status: "SENT"
                        }
                    } catch (error) {
                        console.log(error)
                        reqMsg = {
                            message: this.requestMsg,
                            status: "ERROR"
                        }
                    }
                    this.requestList.push(reqMsg)
                    this.requestMsg=""
                }
            },
            closeChat(){
                if(this.isActive){
                    this.currentAnimation = this.animateOutClass
                    setTimeout(()=>{
                        this.isActive = false
                    },500)
                }
                else{
                    this.currentAnimation = ""
                    setTimeout(()=>{
                        this.isActive = true
                    },10)
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
            this.translateHeaders = await axiosService.translate(['chatbot.title', 'chatbot.greetings', 'request.form.label', 'chatbot.welcome.msg1', 'chatbot.welcome.msg2', 'request.textarea.placeholder', 'request.email.placeholder','chatbot.msgSent.notif', 'chatbot.msgError.notif', 'request.sent.succeed', 'request.sent.failed']);
        },
        computed:{
            displayRequestList(){
                return this.requestList
            },
            username() {
                const name = this.session != null ? this.session.fullname : ""
                const gender = this.session != null ? this.session.gender : null
                const fullName = gender!=null? `${getGenderLabel(gender)} ${name}` : ""
                return fullName.trim()
            },
            useremail() {
                const email = this.session !== null ? this.session.email : "";
                return email.trim()
            },
            userMail:{
                get(){
                    return this.senderEmail!==""? this.senderEmail : this.useremail
                },
                set(value:string){
                    this.senderEmail = value
                }
            },
        }
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
    ::-webkit-scrollbar {
        width: 10px;
    }

    /* Track */
    ::-webkit-scrollbar-track {
        @apply bg-grayToneStroke rounded-xl;
    }
    
    /* Handle */
    ::-webkit-scrollbar-thumb {
        @apply bg-pakDarkBlue rounded-xl;
    }

    /* Handle on hover */
    ::-webkit-scrollbar-thumb:hover {
        @apply bg-pakBlue
    }
</style>