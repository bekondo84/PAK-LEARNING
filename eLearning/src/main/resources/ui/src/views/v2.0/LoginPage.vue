<template>
    <div class="w-screen h-screen overflow-hidden bg-whiteTone relative">
        <div class="w-[12%] aspect-square left-[-70px] top-[-74px] absolute animate__animated animate__bounceInUp bg-pakYellow rounded-full"></div>
        <div class="w-[25%] aspect-square right-[-150px] bottom-[-200px] absolute animate__animated animate__bounceInDown bg-pakDarkBlue rounded-full"></div>
        
        <div class="w-full h-full flex flex-row justify-center items-center">
            <div class="w-[80%] h-[600px] p-2 bg-white rounded-[32px] shadow-lg drop-shadow-xl flex flex-row gap-2">
                <div class="w-full h-full rounded-[32px] relative">
                    <img src="@/assets/images/loginHero.jpg" alt="" class="bg-cover w-full h-full rounded-[32px]">
                    <span class="absolute bottom-4 left-5 text-white text-[24px] font-bold uppercase drop-shadow-xl">{{ config.title }}</span>
                </div>
                <form @submit.prevent="onSubmit"  class="w-full h-full rounded-[32px] flex flex-col justify-evenly items-center p-3">
                    <img src="@/assets/images/pakLogo1.svg" alt="" class="w-[150px]">
                    <h1 class="text-black text-[32px] font-normal leading-10 font-prototype">PAK E-Learning</h1>
                    
                    <div v-if="config.loginMsge===''" role="status" class="max-w-sm animate-pulse">
                        <div class="w-[300px] h-5 bg-gray-200 rounded-full dark:bg-gray-700 mb-4"></div>
                    </div>
                    <span v-else class="text-center text-black text-xl font-centuryGothic leading-[34px]">{{ config.loginMsge }}</span>
                    
                    <span v-if="haserror" class="font-centuryGothic font-bold text-xl text-[#F54646] text-center">{{ config.errorMsge }}</span>
                    <InputField
                        :label="config.loginLabel"
                        v-model="username"
                        type="text"
                    />
                    <InputField
                        :label="config.passwordLabel"
                        v-model="password"
                        type="password"
                    />
                    <v-btn
                        :disabled="!isFormValid"
                        :loading="loading"
                        class="w-[200px] !rounded-full !capitalize !text-xl !text-white !bg-pakYellow hover:!scale-110 disabled:!bg-grayTone1 disabled:!text-white"
                        size="large"
                        type="submit"
                        variant="elevated"
                        >
                        {{ config.connexionBtnLabel }}
                    </v-btn>
                </form>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import InputField from '@/components/v2.0/FormElement/InputField.vue'
    import {useRouter} from 'vue-router';
    import { defineComponent } from 'vue';
    import AxiosService from '@/services/axiosService';
    import 'animate.css';
    const axiosService = new AxiosService();

    export default defineComponent({
        name: 'LoginPage',
        setup(){
            const router = useRouter();
            return {
                router,
            };
        },
        components:{
            InputField
        },
        async mounted() {
            this.config = Object.assign({}, await axiosService.getLoginComponent());
        },
        data(){
            return {
                username: "",
                password: "",
                loading: false,
                haserror: false,
                config: {
                    title:"",
                    loginMsge:"",
                    errorMsge:"",
                    loginLabel:"",
                    passwordLabel:"",
                    connexionBtnLabel:""
                },
            }
        },
        methods:{
            async onSubmit(){
                if (!this.isFormValid) return
                this.loading = true
                try {
                    await axiosService.authenticate(this.username, this.password);
                    this.$router.push({name: 'HomePage'});
                } catch (error) {
                    this.haserror = true;
                }
                this.loading = false
            }
        },
        computed:{
            isFormValid(){
                return this.username!=="" && this.password!=""
            }
        },
    })
</script>

<style scoped>
</style>