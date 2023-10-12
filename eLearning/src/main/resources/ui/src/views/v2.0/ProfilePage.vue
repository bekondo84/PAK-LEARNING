<template>
    <div class="w-screen h-screen flex flex-col overflow-hidden">
        <div class="w-full h-[95px] sticky z-10 top-0 left-0 right-0 drop-shadow-md py-3 bg-white flex flex-row justify-center items-center gap-8">
            <img src="@/assets/images/pakLogo2.svg" alt="" class="w-[300px]">
            <h1 class="text-center text-black text-[32px] font-normal leading-10 font-prototype">PAK E-Learning</h1>
        </div>
        <div class="w-full h-full flex justify-center items-center relative z-0">
            <img src="@/assets/images/pakStar.svg" alt="pak start Logo" class="w-full aspect-video animate__animated animate__bounceInDown absolute top-[-30px] left-[-150px] z-0">
            <img src="@/assets/images/pakLeaf.svg" alt="pak start Logo" class="w-full aspect-video animate__animated animate__bounceInUp absolute z-0 bottom-[-350px] right-[-150px]">


            <div class="w-[80%] h-[85%] absolute z-0 p-4 bg-white rounded-[32px] shadow drop-shadow-2xl flex flex-col gap-2.5">
                <span class="w-full font-prototype text-[40px] text-pakDarkBlue uppercase text-center">{{ translator('profile.title') }}</span>
                <div class="w-full h-full flex flex-row gap-2.5">
                    <div class="w-[70%] h-full flex justify-center items-center">
                        <span class="w-[70%] aspect-square bg-[#165081] rounded-full flex justify-center items-center text-white font-agressive text-[130px]">
                            {{ !isModifiyPassword?userInitals:"" }}
                            <AccountLock v-if="isModifiyPassword" :size="250" class=""/>
                        </span>
                    </div>

                    <div v-if="!isModifyProfile && !isModifiyPassword" class="w-full h-full pb-2 flex flex-col items-center justify-between gap-2">
                        <div class="w-full flex flex-col gap-1 items-start font-centuryGothic">
                            <span class="w-full text-grayTone2 text-2xl">{{ translator('username.label') }}</span>
                            <span class="w-full font-bold text-4xl">{{ apiProfile.name }}</span>
                        </div>
                        <div class="w-full flex flex-col gap-1 items-start font-centuryGothic">
                            <span class="w-full text-grayTone2 text-2xl">{{ translator('usersurname.label') }}</span>
                            <span class="w-full font-bold text-4xl">{{ apiProfile.surname }}</span>
                        </div>
                        <div class="w-full flex flex-col gap-1 items-start font-centuryGothic">
                            <span class="w-full text-grayTone2 text-2xl">{{ translator('useremail.label') }}</span>
                            <span class="w-full font-semibold text-3xl">{{ apiProfile.email }}</span>
                        </div>
                        <div class="w-full flex flex-col gap-1 items-start font-centuryGothic">
                            <span class="w-full text-grayTone2 text-2xl">{{ translator('userLanguage.label') }}</span>
                            <span class="w-full font-semibold text-3xl">{{ apiProfile.language.label }}</span>
                        </div>
                        <div class="w-full flex flex-row pr-8 justify-between gap-2">
                            <button @click="initialiseUpdateParams" class="flex flex-row justify-center px-4 py-2 text-pakYellow border border-pakYellow rounded-[32px] items-center gap-2 hover:text-white hover:bg-pakYellow">
                                <AccountEdit :size="28" class=""/>
                                <span class="text-xl">{{ translator('update.btn') }}</span>
                            </button>
                            <button @click="initialisePassword" class="flex flex-row justify-center px-4 py-2 text-pakBlue border border-pakBlue rounded-[32px] items-center gap-2 hover:text-white hover:bg-pakBlue">
                                <EyeOutline :size="28" class=""/>
                                <span class="text-xl">{{ translator('change.password.btn') }}</span>
                            </button>
                            <button @click="routeToHome" class="flex flex-row items-center gap-2 py-1 px-2 text-pakDarkBlue hover:!text-pakBlue">
                                <Home :size="28" class=""/>
                                <span class="text-center text-bold font-prototype text-xl">{{ translator('return.home.btn') }}</span>
                            </button>
                        </div>
                    </div>

                    <form @submit.prevent="" v-if="isModifiyPassword" class="w-full h-full pb-2 flex flex-col items-center justify-between gap-5">
                        <span class="w-full text-center font-centuryGothic font-bold text-3xl capitalize">{{ translator('change.password.btn') }}</span>
                        <PasswordField
                            :label="translator('old.password.label')"
                            v-model="registerRequest.oldPassword"
                        />
                        <PasswordField
                            :label="translator('new.password.label')"
                            v-model="registerRequest.password"
                        />
                        <div class="w-full flex flex-row pr-8 gap-4 justify-end">
                            <button @click="isModifiyPassword = false" class="flex flex-row justify-center px-4 py-2 text-[#EA4335] border border-[#EA4335] rounded-lg items-center gap-2 hover:text-white hover:bg-[#EA4335]">
                                <span class="text-xl">{{ translator('cancel.btn') }}</span>
                            </button>
                            <v-btn
                                class="flex flex-row justify-center px-4 py-2 text-[#34A853] border !border-[#34A853] rounded-lg items-center gap-2 hover:text-white hover:!bg-[#34A853]"
                                :loading="updateLoading"
                                :disabled="!isPasswordValid"
                                size="large"
                                type="submit"
                                variant="flat"
                                @click="resetPassword"
                            >
                                <AccountLock :size="28" class=""/>
                                <span class="text-xl">{{ translator('update.btn') }}</span>
                            </v-btn>
                        </div>
                    </form>

                    <form @submit.prevent="" v-if="isModifyProfile" class="w-full h-full pb-2 flex flex-col items-center justify-between gap-2">
                        <span class="w-full text-center font-centuryGothic font-bold text-3xl capitalize">{{ translator('update.profile.title') }}</span>
                        <InputField
                            :label="translator('username.label')"
                            v-model="userProfile.name"
                            type="text"
                        />
                        <InputField
                            :label="translator('usersurname.label')"
                            v-model="userProfile.surname"
                            type="text"
                        />
                        <InputField
                            :label="translator('useremail.label')"
                            v-model="userProfile.email"
                            type="email"
                        />
                        <DropDownField
                            :label="translator('userLanguage.label')"
                            v-model="userProfile.language"
                            :optionList="languageArray"
                        />
                        <div class="w-full flex flex-row pr-8 gap-4 justify-end">
                            <button @click="isModifyProfile = false" class="flex flex-row justify-center px-4 py-2 text-[#EA4335] border border-[#EA4335] rounded-lg items-center gap-2 hover:text-white hover:bg-[#EA4335]">
                                <span class="text-xl">{{ translator('cancel.btn') }}</span>
                            </button>
                            <v-btn
                                class="flex flex-row justify-center px-4 py-2 text-[#34A853] border !border-[#34A853] rounded-lg items-center gap-2 hover:text-white hover:!bg-[#34A853]"
                                :loading="updateLoading"
                                :disabled="!isFormValid"
                                size="large"
                                type="submit"
                                variant="flat"
                                @click="updateProfile"
                            >
                                <AccountCheck :size="28" class=""/>
                                <span class="text-xl">{{ translator('validate.btn') }}</span>
                            </v-btn>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        <ChatBot/>
    </div>
</template>

<script lang="ts">
    import 'animate.css';
    import ChatBot from '@/components/v2.0/Training/ChatBot.vue'
    import AccountEdit from 'vue-material-design-icons/AccountEdit.vue'
    import AccountCheck from 'vue-material-design-icons/AccountCheck.vue'
    import Home from 'vue-material-design-icons/Home.vue'
    import InputField from '@/components/v2.0/FormElement/InputField.vue'
    import EyeOutline from 'vue-material-design-icons/EyeOutline.vue'
    import AccountLock from 'vue-material-design-icons/AccountLock.vue'
    import PasswordField from '@/components/v2.0/FormElement/PasswordField.vue'
    import DropDownField from '@/components/v2.0/FormElement/DropDownField.vue'
    import { nameInitials } from '@/hooks/myHooks'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"ProfilePage",
        components:{
            ChatBot,
            AccountEdit,
            AccountCheck,
            Home,
            AccountLock,
            InputField,
            DropDownField,
            PasswordField,
            EyeOutline,
        },
        data(){
            return {
                // Values by default
                apiProfile:{
                    name: "",
                    surname: "",
                    email: "",
                    language: {
                        value: "",
                        label: ""
                    }
                },
                languageArray:['Français', 'English'],

                // Values on update
                userProfile:{
                    username:"",
                    name: "",
                    surname: "",
                    fullname:"",
                    email: "",
                    gender:null,
                    avatar:"",
                    language: ""
                },
                registerRequest:{
                    username:"",
                    password:"",
                    oldPassword:"",
                },
                isModifyProfile: false,
                isModifiyPassword: false,
                updateLoading: false,
                translateHeaders: null,

            }
        },
        methods:{
            routeToHome(){
                this.$router.push({name: "HomePage"})
            },
            initialiseProfile(){
                const session = store.getters.getCurrentSession;
                // console.log(session)
                const lang = store.getters.getCurrentSession.language;
                this.apiProfile.name = (session != null && session.name != null) ? session.name : "Unkown"
                this.apiProfile.surname = (session != null && session.surname != null) ? session.surname : ""
                this.apiProfile.email = (session != null && session.email != null) ? session.email : ""
                this.apiProfile.language = {
                        value: lang,
                        label: this.getLanguageLabel(lang)
                    }
            },
            initialisePassword(){
                const session = store.getters.getCurrentSession
                this.registerRequest.username = session.username
                this.isModifiyPassword=true
            },
            initialiseUpdateParams(){
                const session = store.getters.getCurrentSession
                this.userProfile={
                    username:session.username,
                    name: this.apiProfile.name,
                    surname: this.apiProfile.surname,
                    fullname:session.fullname,
                    email: this.apiProfile.email,
                    gender:session.gender,
                    avatar:session.avatar,
                    language: this.apiProfile.language.label
                }
                this.isModifyProfile = true
            },
            async updateProfile(){
                this.updateLoading = true
                try{
                    this.userProfile.language = this.getLanguageValue(this.userProfile.language)
                    await axiosService.updateProfile(this.userProfile)
                    this.apiProfile.name = this.userProfile.name
                    this.apiProfile.surname = this.userProfile.surname
                    this.apiProfile.email = this.userProfile.email
                    this.apiProfile.language = {
                        value: this.userProfile.language,
                        label: this.getLanguageLabel(this.userProfile.language)
                    }
                }catch(error){
                    console.log(error)
                }
                this.updateLoading = false
                this.isModifyProfile = false
            },
            getLanguageValue(label: string){
                switch(label){
                    case 'Français':
                        return 'fr'
                    case 'English':
                        return 'en'
                    default: return 'fr'
                }
            },
            getLanguageLabel(value: string){
                switch(value){
                    case 'fr':
                        return 'Français'
                    case 'en':
                        return 'English'
                    default: return 'Français'
                }
            },
            async resetPassword(){
                this.updateLoading = true
                try{
                    await axiosService.resetPassword(this.registerRequest)
                }catch(error){
                    console.log(error)
                }
                this.updateLoading = false
                this.isModifiyPassword = false
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async mounted() {
            this.initialiseProfile()
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['profile.title', 'username.label', 'usersurname.label', 'useremail.label', 'userLanguage.label', 'update.btn', 'cancel.btn', 'validate.btn', 'return.home.btn', 'change.password.btn', 'new.password.label', 'old.password.label', 'update.profile.title']);
        },
        computed:{
            userInitals(){
                const value = this.isModifyProfile? `${this.userProfile.name} ${this.userProfile.surname!=null?this.userProfile.surname:""}` : `${this.apiProfile.name} ${this.apiProfile.surname!=null?this.apiProfile.surname:""}`
                return nameInitials(value)
            },
            isFormValid(){
                return (this.userProfile.name!=="" && this.userProfile.surname!=="" && this.userProfile.email!=="" && this.userProfile.language!=="")
            },
            isPasswordValid(){
                return (this.registerRequest.password !== "" && this.registerRequest.oldPassword !== "")
            }
        }
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;

    ::-webkit-scrollbar {
        width: 15px;
        @apply mt-2 mb-2
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