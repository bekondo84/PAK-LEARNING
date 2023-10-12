<template>
    <button @click="startTraining" class="w-[400px] h-[450px] aspect-square px-2.5 pt-2.5 pb-5 bg-white rounded-[12px] drop-shadow-md flex flex-col hover:scale-105 ease-in-out duration-300">
        <div v-if="courseDetails.image===''" role="status" class="animate-pulse w-full aspect-video rounded-[12px] flex items-center justify-center bg-gray-300 dark:bg-gray-700">
            <svg class="w-10 aspect-square rounded-[12px] text-gray-200 dark:text-gray-600" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18">
                <path d="M18 0H2a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2Zm-5.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3Zm4.376 10.481A1 1 0 0 1 16 15H4a1 1 0 0 1-.895-1.447l3.5-7A1 1 0 0 1 7.468 6a.965.965 0 0 1 .9.5l2.775 4.757 1.546-1.887a1 1 0 0 1 1.618.1l2.541 4a1 1 0 0 1 .028 1.011Z"/>
            </svg>
        </div>
        <img v-else :src="courseDetails.image" :alt="courseDetails.name" class="w-full aspect-video rounded-[12px]">
        <div class="w-full h-full px-2 flex flex-col items-start justify-between gap-2">
            <div class="py-1 w-full flex flex-col items-start gap-1">
                <span class="text-zinc-950 text-[16px] capitalize font-centuryGothic font-bold text-left">{{ courseDetails.name }}</span>
                <span class="text-pakYellow text-[15px] font-medium capitalize text-left">{{ courseDetails.themeName }}</span>
            </div>
            <div class="flex flex-row gap-1 items-end justify-center2">
                <CalendarText :size="20" class="text-grayTone2"/>
                <span class="text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize">{{ translator('card.subscribedAt.label') }} :</span>
                <span class="text-textDarkTone text-[14px] flex justify-center font-centuryGothic items-center text-center font-bold">{{ courseDetails.subscribedAt }}</span>
            </div>
            <div class="w-full flex flex-col gap-4">
                <div class="w-full py-1 flex flex-row items-center gap-5">
                    <div class="flex flex-row gap-1 items-end justify-center">
                        <BookMultiple :size="20" class="text-grayTone2"/>
                        <span class="text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize">{{ translator('lessons.label') }} :</span>
                        <span class="text-textDarkTone text-[14px] flex justify-center font-centuryGothic items-center text-center font-bold">{{ courseDetails.lessons }}</span>
                    </div>
                    <div class="flex flex-row gap-1 items-end justify-center">
                        <ClockTimeFour :size="20" class="text-grayTone2"/>
                        <span class="text-grayTone2 text-[14px] font-centuryGothic font-bold capitalize">{{ translator('duration.label') }} :</span>
                        <span class="text-textDarkTone text-[14px] flex justify-center font-centuryGothic items-center text-center font-bold">{{ courseDetails.duration }}</span>
                    </div>
                </div>
                <div class="w-full flex flex-row justify-between">
                    <div class="py-1 w-full flex flex-row items-start gap-2.5">
                        <span class="w-[40px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white text-base font-bold">{{ trainerInitials }}</span>
                        <div class="flex flex-col items-start justify-between">
                            <span class="text-zinc-800 text-sm font-bold leading-snug tracking-tight text-left">{{ courseDetails.trainer }}</span>
                            <span class="text-slate-500 text-sm font-normal leading-[18px] tracking-tight">{{ translator('main.trainer.label') }}</span>
                        </div>
                    </div>
                    <v-btn
                        class="flex flex-row px-2 py-2.5 !rounded-[10px] !bg-pakBlue justify-evenly text-white items-center gap-2 hover:!bg-pakDarkBlue"
                        :loading="loading"
                        size="large"
                        type="submit"
                        variant="flat"
                        @click="startTraining"
                        >
                        <span class="flex font-bold font-myriadBold">{{ translator('start.btn') }}</span>
                        <ChevronRight :size="24" class=""/>
                    </v-btn>
                </div>
            </div>
        </div>
    </button>
</template>

<script lang="ts">
    import BookMultiple from 'vue-material-design-icons/BookMultiple.vue'
    import ClockTimeFour from 'vue-material-design-icons/ClockTimeFour.vue'
    import ChevronRight from 'vue-material-design-icons/ChevronRight.vue'
    import {nameInitials} from '@/hooks/myHooks'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"SubscribedCard",
        components:{
            BookMultiple,
            ClockTimeFour,
            ChevronRight,
        },
        props:['courseDetails', 'index'],
        data(){
            return{
                loading:false,
                translateHeaders: null,
            }
        },
        methods:{
            async startTraining(){
                this.loading = true
                try {
                    await axiosService.startTraining(this.courseDetails.pk)
                    this.loading = false
                    this.$router.push({name: "TrainingIndex", params:{type:'complete',id: this.courseDetails.code}})
                } catch (error) {
                    console.log(error)
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
            this.translateHeaders = await axiosService.translate(['start.btn', 'lessons.label', 'duration.label', 'card.subscribedAt.label', 'main.trainer.label']);
        },
        computed:{
            trainerInitials(){
                let temp = nameInitials(this.courseDetails.trainer)
                return temp
            }
        }
    }
</script>

<style scoped>
    .gt{
        height: fit-content;
    }
</style>