<template>
    <div class="w-full h-[250px] relative">
        <div v-if="trainingImage===''" role="status" class="animate-pulse w-full h-[250px] flex items-center justify-center bg-gray-300 dark:bg-gray-700">
            <svg class="w-10 aspect-square rounded-[12px] text-gray-200 dark:text-gray-600" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18">
                <path d="M18 0H2a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2Zm-5.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3Zm4.376 10.481A1 1 0 0 1 16 15H4a1 1 0 0 1-.895-1.447l3.5-7A1 1 0 0 1 7.468 6a.965.965 0 0 1 .9.5l2.775 4.757 1.546-1.887a1 1 0 0 1 1.618.1l2.541 4a1 1 0 0 1 .028 1.011Z"/>
            </svg>
        </div>
        <img v-else :src="trainingImage" alt="" class="w-full h-[250px] absolute bg-cover bg-center">
        <div class="absolute left-0 top-0 right-0 bottom-0 px-[120px] bg-[#0000005c] flex flex-col gap-2 items-center justify-center">
            <span class="w-full uppercase text-center text-white text-bold font-myriadBold text-3xl drop-shadow-lg">{{ trainingName }}</span>
            <span class="w-full uppercase text-center text-pakYellow text-bold font-prototype text-2xl drop-shadow-lg">{{ themeName }}</span>
            <span  v-if="!showLesson && !isTrainingActivity" class="uppercase text-center text-white text-bold font-myriadBold text-xl drop-shadow-lg">{{ translator('training.welcome.msg') }}</span>
            <div v-if="showLesson" class="w-full flex flex-row gap-1 items-center justify-center">
                <span  class="uppercase text-center text-white text-bold font-myriadBold text-xl drop-shadow-lg">{{ translator('lesson.btn.label') }} {{ lessonDetails?.sequence }} :</span>
                <span class="uppercase text-center text-white text-bold font-myriadBold text-xl drop-shadow-lg">{{ lessonDetails?.name }}</span>
            </div>
            <div v-if="isTrainingActivity" class="w-full flex flex-row gap-1 items-center justify-center">
                <span  class="uppercase text-center text-white text-bold font-myriadBold text-xl drop-shadow-lg">{{ translator('summary.evaluation.title') }} {{ testIndex }} :</span>
                <span class="uppercase text-center text-white text-bold font-myriadBold text-xl drop-shadow-lg">{{ testTitle }}</span>
            </div>
        </div>
        <div class="w-fit mr-[120px] py-2 px-4 absolute right-0 bottom-0 rounded-t-2xl flex flex-col gap-1 items-center bg-white">
            <span class="text-center text-sm flex font-bold">{{ translator('main.trainer.label') }}</span>
            <span class="w-[40px] aspect-square bg-pakBlue rounded-full flex justify-center items-center text-white text-base font-bold">{{ mainTrainerInitials }}</span>
            <span class="text-center font-myriadBold flex text-zinc-800 text-lg font-bold leading-snug tracking-tight">{{ trainers.length > 0 ? trainers[0] :translator('noTrainer.label') }}</span>
        </div>
        <button @click="routeToHome" class="absolute bottom-2.5 ml-[120px] flex flex-row items-center gap-2 py-1 px-2 rounded-lg drop-shadow-lg text-white hover:bg-white hover:!text-pakDarkBlue">
            <Home :size="20" class=""/>
            <span class="text-base text-center text-bold font-prototype">{{ translator('return.home.btn') }}</span>
        </button>
    </div>
</template>

<script lang="ts">
    import Home from 'vue-material-design-icons/Home.vue'
    import {nameInitials} from '@/hooks/myHooks'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';
    import { baseURL } from '@/data/config';


    const axiosService = new AxiosService();
    export default {
        name:'TrainingHeader',
        components:{
            Home
        },
        props:{
            showLesson:{
                type: Boolean,
                default: false
            },
            isTrainingActivity:{
                type:Boolean,
                default: false
            },
            testIndex:{
                type: Number,
                default: 1
            },
            testTitle: String,
        },
        data(){
            return{
                id: this.$route.params.id, // Training code

                // Integrated data
                trainingDetails: null,
                lessonDetails: {
                    name: '',
                    sequence: ''
                },
                translateHeaders: null,
            }
        },
        methods:{
            routeToHome(){
                this.$router.push({name: "HomePage"})
            },
            async getTraining(id: string) {
                try {
                    this.trainingDetails = await axiosService.getTraining(id);
                } catch (error) {
                    this.error = true ;
                }
            },
            async getLesson(id:string){
                try{
                    const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                    let temp = await axiosService.leconResult(id);
                    this.lessonDetails = {
                        name: temp.activity.name[lang].value,
                        sequence: temp.activity.sequence
                    }
                }catch(error){
                    this.error = true
                }
            },
            getInitials(value:string){
                return nameInitials(value)
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async mounted() {
            const trainingId = this.$route.params.id;
            if (trainingId != null) {
                await this.getTraining(trainingId);
            }
            const lessonId = this.$route.params.lessonId
            if(lessonId != null){
                await this.getLesson(lessonId)
            }
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['training.welcome.msg', 'lesson.btn.label', 'summary.evaluation.title', 'main.trainer.label', 'return.home.btn', 'noTrainer.label']);
        },
        computed: {
            trainingName() {
                return this.trainingDetails != null ? this.trainingDetails.name : "";
            },
            themeName(){
                return this.trainingDetails!=null ? this.trainingDetails.theme.name: ""
            },
            trainingImage(){
                return (this.trainingDetails!=null && this.trainingDetails.image!=null) ? baseURL+this.trainingDetails.image:""
            },
            trainers(){
                return this.trainingDetails!=null ? this.trainingDetails.involves.map((member:any)=>{
                    if(member.involveType === "TEACHER"){
                        return member.concern.name
                    }
                }): []
            },
            mainTrainerInitials(){
                return this.trainers.length !== 0?nameInitials(this.trainers[0]) : nameInitials(this.translator('noTrainer.label'))
            }
        },
    }
</script>

<style scoped>
</style>