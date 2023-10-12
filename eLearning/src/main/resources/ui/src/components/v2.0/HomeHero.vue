<template>
    <div class="w-full px-[120px] py-16 bg-lightBlue flex flex-row gap-8 justify-center items-center">
        <div class="w-full h-full flex flex-col gap-8">
            <div class="flex flex-col gap-2">
                <span class="font-bold font-myriadBold text-[40px] leading-none">{{ translator('welcome.message') }}</span>
                <span class="font-prototype text-pakBlue font-bold text-[40px] uppercase leading-none tracking-wider animate-typing overflow-hidden whitespace-nowrap">{{ username }}</span>
                <span class="font-bold font-myriadBold text-[40px] text-justify leading-none">{{ translator('application.center.name') }}</span>
            </div>
            <div class="w-full flex flex-col gap-2">
                <span class="w-fit font-centuryGothic font-bold text-[24px] animate-typing overflow-hidden whitespace-nowrap border-r-4 border-r-pakBlue">{{ translator('hero.hint.title') }}</span>
                <span class="font-centuryGothic font-bold text-[20px] text-justify">{{ translator('hero.hint.message') }}</span>
            </div>
        </div>
        <img v-if="heroType === 'Available'" src='@/assets/images/homeHero.png' alt="PAK E-learning Home Hero Image" class="w-[30%] animate__animated animate__fadeIn"/>
        <img v-if="heroType === 'Subscribed'" src='@/assets/images/subscribed.png' alt="PAK E-learning Home Hero Image" class="w-[30%] animate__animated animate__fadeIn"/>
        <img v-if="heroType === 'Ongoing'" src='@/assets/images/ongoing.png' alt="PAK E-learning Home Hero Image" class="w-[30%] animate__animated animate__fadeIn"/>
        <img v-if="heroType === 'Completed'" src='@/assets/images/completed.png' alt="PAK E-learning Home Hero Image" class="w-[30%] animate__animated animate__fadeIn"/>
    </div>
</template>

<script lang="ts">
    import 'animate.css';
    import store from '@/store';
    import AxiosService from '@/services/axiosService';
    import {getGenderLabel} from '@/hooks/myHooks'
    const axiosService = new AxiosService();
    export default {
        name: 'HomeHero',
        props:{
            heroType: {
                type:String,
                default: 'Available'
            },
        },
        data(){
            return{
                // Integration Data
                session: null,
                translateHeaders: null,
            }
        },
        methods:{
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async mounted() {
            this.session = store.getters.getCurrentSession;
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['welcome.message', 'application.center.name', 'hero.hint.title', 'hero.hint.message']);
        },
        computed: {
            username() {
                const name = this.session != null ? this.session.fullname : "unkown"
                const gender = this.session !== null ? this.session.gender : null
                const fullName = gender!=null? `${getGenderLabel(gender)} ${name}` : ""
                return fullName.trim()
            },
        },
    }
</script>

<style scoped>

</style>