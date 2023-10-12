<template>
    <div class="w-full sticky z-10 top-0 left-0 drop-shadow-md px-[120px] py-3 bg-white flex flex-row items-center">
        <img src="@/assets/images/pakLogo2.svg" alt="" class="w-[300px]">
        <h1 class="w-full flex justify-center items-center text-center text-black text-[32px] font-normal leading-10 font-prototype">PAK E-Learning</h1>
        <div class="w-[600px] h-full flex flex-row gap-1 items-center">
            <div v-if="userInitals===''" role="status" class="animate-pulse">
                <div class="w-[60px] aspect-square bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="w-[80px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white font-agressive text-xl">{{ userInitals }}</span>
            <div v-if="username===''" role="status" class="animate-pulse">
                <div class="w-[200px] h-6 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="w-full flex justify-center items-center text-black text-2xl font-normal font-prototype">{{ username }}</span>
            <div class="h-full relative flex items-center">
                <button v-if="!dropDownVisible" class="" @click="dropDownVisible = !dropDownVisible">
                    <ChevronDown :size="32"/>
                </button>
                <button v-if="dropDownVisible" class="" @click="dropDownVisible = !dropDownVisible">
                    <ChevronUp :size="32"/>
                </button>
                <div v-if="dropDownVisible" class="w-[300px] p-3 drop-shadow-2xl shadow rounded-[8px] bg-white absolute bottom-[-150px] right-0 flex flex-col gap-2">
                    <button @click="routeToProfile" class="flex flex-row justify-center px-4 py-2.5 text-pakBlue items-center gap-2 hover:text-pakDarkBlue">
                        <AccountCircle :size="24" class=""/>
                        <span class="flex font-myriadBold text-lg">{{ translator('view.profile.btn') }}</span>
                    </button>
                    <v-btn
                        class="w-full !text-pakDarkBlue border !border-pakDarkBlue px-4 py-2 flex flex-row rounded-[8px] items-center hover:!text-white hover:bg-pakDarkBlue"
                        :loading="loading"
                        size="large"
                        type="submit"
                        variant="flat"
                        @click="disconnect"
                    >
                        <Logout :size="24" class="-rotate-180"/>
                        <span class="flex font-myriadBold text-lg ml-4">{{ translator('logout.btn') }}</span>
                    </v-btn>
            </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { defineComponent } from "vue";
    import store from '@/store';
    import { nameInitials } from '@/hooks/myHooks'

    import ChevronDown from 'vue-material-design-icons/ChevronDown.vue'
    import ChevronUp from 'vue-material-design-icons/ChevronUp.vue'
    import AccountCircle from 'vue-material-design-icons/AccountCircle.vue'
    import Logout from 'vue-material-design-icons/Logout.vue'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default defineComponent({
        name: 'HomeHeader',
        components:{
            ChevronDown,
            ChevronUp,
            AccountCircle,
            Logout,
        },
        data(){
            return{
                dropDownVisible: false,
                loading:false,

                // Integration Data
                session: null,
                translateHeaders: null,
            }
        },
        methods:{
            routeToProfile(){
                this.$router.push({name: 'ProfilePage'});
            },
            disconnect(){
                this.loading = true
                setTimeout(() => {
                    this.loading = false
                    this.$router.push({name: 'LoginPage'});
                }, 1000);
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async mounted() {
            this.session = store.getters.getCurrentSession;
            // console.log(this.session)
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['view.profile.btn', 'logout.btn']);
            this.emitter.on("authorization-error", ()=>{this.$router.push({name: 'LoginPage'})});
        },
        computed: {
            username() {
                const name = this.session != null ? this.session.fullname : ""
                return name
            },
            usermail() {
                const email = this.session != null ? this.session.email : "";
                return email.trim()
            },
            userInitals(){
                return nameInitials(this.username)
            },
        },
    })
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
</style>