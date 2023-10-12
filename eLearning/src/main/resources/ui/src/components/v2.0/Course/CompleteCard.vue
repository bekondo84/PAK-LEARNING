<template>
    <button @click="routeToTraining" class="w-full h-fit p-2.5 gap-5 bg-white rounded-[12px] drop-shadow-md flex flex-row hover:scale-105 ease-in-out duration-300">
        <div v-if="courseDetails.image===''" role="status" class="animate-pulse w-[70%] h-[250px] rounded-[12px] flex items-center justify-center bg-gray-300 dark:bg-gray-700">
            <svg class="w-10 aspect-square text-gray-200 dark:text-gray-600" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18">
                <path d="M18 0H2a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2Zm-5.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3Zm4.376 10.481A1 1 0 0 1 16 15H4a1 1 0 0 1-.895-1.447l3.5-7A1 1 0 0 1 7.468 6a.965.965 0 0 1 .9.5l2.775 4.757 1.546-1.887a1 1 0 0 1 1.618.1l2.541 4a1 1 0 0 1 .028 1.011Z"/>
            </svg>
        </div>
        <img v-else :src="courseDetails.image" :alt="courseDetails.name" class="w-[70%] h-[250px] rounded-[12px]">
        <div class="w-full flex flex-col items-start justify-between gap-2.5 pr-2">
            <div class="py-1 file:w-full flex flex-col items-start gap-1">
                <span class="text-zinc-950 text-[16PX] capitalize font-centuryGothic text-left font-bold">{{ courseDetails.name }}</span>
                <span class="text-pakYellow text-[15px] font-medium capitalize text-left">{{ courseDetails.themeName }}</span>
            </div>
            <div class="py-1 flex flex-row items-center justify-between gap-2">
                <div class="flex flex-row gap-1 items-center justify-start pr-4 border-r border-r-grayTone2">
                    <CalendarCheck :size="20" class="text-grayTone2"/>
                    <span class="text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize">{{ translator('card.endAt.label') }} :</span>
                    <span class="text-textDarkTone text-[14px] flex justify-center font-centuryGothic items-center text-center font-bold">{{ courseDetails.endAt }}</span>
                </div>
                <div class="flex flex-row gap-1 items-center justify-start">
                    <ClockTimeFour :size="20" class="text-grayTone2"/>
                    <span class="text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize">{{ translator('duration.label') }} :</span>
                    <span class="text-textDarkTone text-[14px] flex justify-center font-centuryGothic items-center text-center font-bold">{{ courseDetails.duration }}</span>
                </div>
            </div>
            <div class="flex flex-row gap-1 items-center justify-start">
                <BookMultiple :size="20" class="text-grayTone2"/>
                <span class="text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize">{{ translator('lesson.summary.title') }} :</span>
                <span class="text-textDarkTone text-[14px] font-centuryGothic text-center font-bold">{{ courseDetails.finishedActivities }} {{ translator('card.lessons.on.label') }} {{ courseDetails.totalActivities }} {{ translator('lessons.done.label') }}</span>
                <CheckCircle :size="24" class="text-[#34A853]"/>
            </div>
            <ProgressBar :value="courseDetails.progress" label="de Succès"/>
            <span class="w-full text-justify text-zinc-800 text-md font-bold font-centuryGothic">{{ translator('congratulation.message') }}</span>
            <div class="w-full flex flex-row justify-between">
                <div class="py-1 w-full flex flex-row items-start gap-2.5">
                    <span class="w-[40px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white text-base font-bold">{{ trainerInitials }}</span>
                    <div class="flex flex-col items-start justify-between">
                        <span class="text-zinc-800 text-md font-bold leading-snug tracking-tight text-left">{{ courseDetails.trainer }}</span>
                        <span class="text-slate-500 text-[16px] font-normal leading-[18px] tracking-tight">{{ translator('main.trainer.label') }}</span>
                    </div>
                </div>
                <button @click="routeToTraining" class="w-[200px] flex flex-row px-4 py-2.5 rounded-[10px] bg-pakBlue justify-evenly text-white items-center gap-2 hover:text-white hover:bg-pakDarkBlue">
                    <Menu :size="24" class=""/>
                    <span class="flex font-bold font-myriadBold">{{ translator('view.training.result.btn') }}</span>
                </button>
            </div>
        </div>
    </button>
</template>

<script lang="ts">
    import BookMultiple from 'vue-material-design-icons/BookMultiple.vue'
    import CalendarCheck from 'vue-material-design-icons/CalendarCheck.vue'
    import CheckCircle from 'vue-material-design-icons/CheckCircle.vue'
    import Menu from 'vue-material-design-icons/Menu.vue'
    import ProgressBar from '@/components/v2.0/UIElements/ProgressBar.vue'
    import ClockTimeFour from 'vue-material-design-icons/ClockTimeFour.vue'
    import {nameInitials} from '@/hooks/myHooks'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"CompleteCard",
        components:{
            BookMultiple,
            CalendarCheck,
            Menu,
            ClockTimeFour,
            ProgressBar,
            CheckCircle,
        },
        props:['courseDetails', 'index'],
        data(){
            return{
                translateHeaders: null,
                loading: false,
            }
        },
        methods:{
            routeToTraining(){
                this.loading = true
                setTimeout(() => {
                    this.loading = false
                    this.$router.push({name: "TrainingIndex", params:{type:'complete', id: this.courseDetails.code}})
                }, 1000);
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['card.endAt.label', 'duration.label', 'lesson.summary.title', 'card.lessons.on.label', 'lessons.done.label', 'congratulation.message', 'main.trainer.label', 'view.training.result.btn']);
        },
        computed:{
            trainerInitials(){
                let temp = nameInitials(this.courseDetails.trainer)
                return temp
            }
        },
    }
</script>

<style scoped>

</style>