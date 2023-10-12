<template>
    <div class="w-full pb-1 border-b !border-grayToneStroke flex flex-col gap-2.5">
        <div class="w-full text-[40px] flex flex-row gap-1.5">
            <div v-if="lessonName===''" role="status" class="w-full flex gap-2 animate-pulse mb-2">
                <div class="w-[200px] h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="font-centuryGothic font-bold text-justify">{{ translator('lesson.btn.label') }} {{ lessonIndex }} : {{ lessonName }}</span>
        </div>
        <span class="font-centuryGothic font-bold text-justify">
            {{ translator('lesson.welcome.msg') }}
        </span>
    </div>
    <div class="w-full flex flex-col">
        <div class="flex gap-1 items-center text-grayTone1">
            <ClockTimeFour :size="20" class=""/>
            <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('duration.label') }}</span>
        </div>
        <div v-if="duration===''" role="status" class="animate-pulse pl-6">
            <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
        </div>
        <span v-else class="pl-6 font-centuryGothic text-left">{{ duration }}</span>
    </div>
    <div class="w-full flex flex-col">
        <div class="flex gap-1 items-center text-grayTone1">
            <CalendarCheck :size="20" class=""/>
            <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('activity.endAt.label') }}</span>
        </div>
        <div v-if="closeDate===''" role="status" class="animate-pulse pl-6">
            <div class="w-[250px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
        </div>
        <span v-else class="pl-6 font-centuryGothic text-left">{{ closeDate }}</span>
    </div>
    <div class="w-full flex items-center">
        <div class="w-full flex flex-col">
            <div class="flex gap-1 items-center text-grayTone1">
                <BrightnessPercent :size="20" class=""/>
                <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('percentage.label') }}</span>
            </div>
            <span class="pl-6 font-centuryGothic text-left">{{ percentage }}%</span>
        </div>
        <span v-if="isDone" class="w-full font-centuryGothic font-bold text-left text-xl text-[#34A853]">{{ translator('lesson.complete.msg') }}</span>
        <span v-if="!isDone" class="w-full font-centuryGothic font-bold text-left text-xl text-pakYellow">{{ translator('lesson.inprogress.msg') }}</span>
    </div>
    <div class="w-full flex flex-col gap-2 font-bold">
        <span class="font-myriadBold text-xl text-grayTone1 text-left">{{ translator('description.label') }}</span>
        <div v-if="description===''" role="status" class="animate-pulse w-full flex flex-col gap-2">
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-80"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
                        <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-32"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-80"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
            </div>
        </div>
        <span v-else class="font-centuryGothic text-justify">
            {{ description }}
        </span>
    </div>
    <div class="w-full flex flex-col gap-2 font-bold">
        <span class="font-myriadBold text-xl text-grayTone1 text-left">{{ translator('lesson.hint.label') }}</span>
        <span class="font-centuryGothic text-justify">
            {{ translator('lesson.hint.intro') }}:
        </span>
        <ul class="font-centuryGothic text-justify list-disc pl-5">
            <li>
                {{ translator('lesson.hint.msg1') }}
            </li>
            <li>
                {{ translator('lesson.hint.msg2') }}
            </li>
            <li>
                {{ translator('lesson.hint.msg3') }}
            </li>
            <li>
                {{ translator('lesson.hint.msg4') }}
            </li>
        </ul>
    </div>
    
</template>

<script lang="ts">
    import ClockTimeFour from 'vue-material-design-icons/ClockTimeFour.vue'
    import CalendarCheck from 'vue-material-design-icons/CalendarCheck.vue'
    import BrightnessPercent from 'vue-material-design-icons/BrightnessPercent.vue'
    import {translateDateTime,getDurationBtwDates} from '@/hooks/myHooks'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"LessonIndex",
        components:{
            ClockTimeFour,
            CalendarCheck,
            BrightnessPercent,
        },
        data(){
            return{
                id: this.$route.params.lessonId,

                // Integrated Data
                lessonDetails: null,
                lessonIndex: 1,
                error: false,
                overlay:false,
                translateHeaders: null,
            }
        },
        methods:{
            async loadData() {
                try {
                    const lessonId = this.$route.params.lessonId;
                    this.lessonDetails = await axiosService.leconResult(lessonId);
                    this.lessonIndex = this.lessonDetails.activity.sequence;
                } catch (error) {
                    this.error = true ;
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
            this.translateHeaders = await axiosService.translate(['lesson.btn.label', 'lesson.welcome.msg', 'activity.endAt.label','duration.label', 'percentage.label', 'lesson.complete.msg', 'lesson.inprogress.msg', 'description.label', 'lesson.hint.intro', 'lesson.hint.label', 'lesson.hint.msg1', 'lesson.hint.msg2', 'lesson.hint.msg3', 'lesson.hint.msg4']);
        },
        async mounted() {
            this.overlay = true ;
            await this.loadData();
            this.overlay = false;
        },
        computed:{
            lessonName(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr" ;
                return this.lessonDetails!=null?this.lessonDetails.activity.name[lang].value:""
            },
            duration(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return this.lessonDetails!=null? getDurationBtwDates(this.lessonDetails.activity.beginAt, this.lessonDetails.activity.endAt, "T",lang):"0j"
            },
            closeDate(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return this.lessonDetails!=null?translateDateTime(this.lessonDetails.activity.endAt,"T",lang):""
            },
            percentage(){
                return this.lessonDetails!=null?this.lessonDetails.activity.percent:0
            },
            description(){
                return this.lessonDetails!=null?this.lessonDetails.activity.description.fr.value:""
            },
            isDone(){
                const state = this.lessonDetails != null ? this.lessonDetails.state: null
                return state != null ? state.code === 'DONE' : false
            },
        }
    }
</script>

<style scoped>

</style>