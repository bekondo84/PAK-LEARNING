<template>
    <div class="w-full pb-1 border-b !border-grayToneStroke flex flex-col gap-2.5">
        <div class="w-full text-[40px] flex flex-row gap-1.5">
            <div v-if="testName===''" role="status" class="w-full flex gap-2 animate-pulse mb-2">
                <div class="w-[200px] h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="w-full font-centuryGothic font-bold text-justify">{{ translator('test.btn.label') }} {{ testIndex }} : {{ testName }}</span>
        </div>
    </div>
    <div class="flex w-full">
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
        <div class="w-full flex flex-col">
            <span class="font-centuryGothic font-bold text-left text-2xl">{{ translator('test.mark.label') }}</span>
            <div v-if="maxPoint===0" role="status" class="animate-pulse pl-15 flex gap-2">
                <div class="w-[30px] h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-[30px] h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="w-full pl-15 font-centuryGothic text-left text-pakBlue font-bold text-xl">{{ evaluationMark }} / {{ maxPoint }}</span>
        </div>
    </div>
    <div class="w-full flex flex-col">
        <div class="flex gap-1 items-center text-grayTone1">
            <Cached :size="20" class=""/>
            <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('test.trial.label') }}</span>
        </div>
        <div v-if="maxTrial===0" role="status" class="animate-pulse pl-6 flex gap-2">
            <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
        </div>
        <span v-else class="pl-6 font-centuryGothic text-left">{{ actualTrail }} / {{ maxTrial }}</span>
    </div>
    <div class="flex w-full">
        <div class="w-full flex flex-col">
            <div class="flex gap-1 items-center text-grayTone1">
                <ClipboardCheckOutline :size="20" class=""/>
                <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('test.successAt.label') }}</span>
            </div>
            <div v-if="successAt===0" role="status" class="animate-pulse pl-6">
                <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="pl-6 font-centuryGothic text-left">{{ successAt }}</span>
        </div>
        <div class="w-full flex flex-col">
            <div class="flex gap-1 items-center text-grayTone1">
                <Information :size="20" class=""/>
                <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('test.markOn.label') }}</span>
            </div>
            <div v-if="maxPoint===0" role="status" class="animate-pulse pl-6">
                <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="pl-6 font-centuryGothic text-left">{{ maxPoint }}</span>
        </div>
    </div>
    <div class="w-full flex items-center">
        <div class="w-full flex flex-col">
            <div class="flex gap-1 items-center text-grayTone1">
                <BrightnessPercent :size="20" class=""/>
                <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('percentage.label') }}</span>
            </div>
            <span class="pl-6 font-centuryGothic text-left">{{ percentage }}%</span>
        </div>
        <span v-if="isDone" class="w-full font-centuryGothic font-bold text-left text-xl text-[#34A853]">{{ translator('test.complete.msg') }}</span>
        <span v-if="!isDone" class="w-full font-centuryGothic font-bold text-left text-xl text-pakYellow">{{ translator('test.inprogress.msg') }}</span>
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
    <div class="w-full flex justify-end">
        <button v-if="!isDone" @click="routeToQuiz" class="w-fit flex flex-row gap-2 justify-center items-center font-centuryGothic text-xl !text-pakYellow font-bold p-3 rounded-lg border !border-pakYellow !bg-white hover:!bg-pakYellow hover:!text-white">
            {{ translator('start.btn')}}
            <Flag :size="20" class=""/>
        </button>
        <button v-else @click="routeToQuiz" class="w-fit flex flex-row gap-2 justify-center items-center font-centuryGothic text-xl !text-[#34A853] font-bold p-3 rounded-lg border !border-[#34A853] !bg-white hover:!bg-[#34A853] hover:!text-white">
            {{ translator('test.viewResult.btn') }}
        </button>
    </div>
</template>

<script lang="ts">
    import CalendarCheck from 'vue-material-design-icons/CalendarCheck.vue'
    import Flag from 'vue-material-design-icons/Flag.vue'
    import BrightnessPercent from 'vue-material-design-icons/BrightnessPercent.vue'
    import Information from 'vue-material-design-icons/Information.vue'
    import Cached from 'vue-material-design-icons/Cached.vue'
    import ClipboardCheckOutline from 'vue-material-design-icons/ClipboardCheckOutline.vue'
    import store from '@/store';
    import {translateDateTime} from '@/hooks/myHooks'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"LessonTest",
        components:{
            BrightnessPercent,
            Information,
            ClipboardCheckOutline,
            Flag,
            Cached,
            CalendarCheck
        },
        data(){
            return{
                testId: this.$route.params.testId,
                id: this.$route.params.lessonId,

                // Integration data
                lessonDetails:null,
                testDetails: null,
                translateHeaders: null,
            }
        },
        methods:{
            routeToQuiz(){
                this.$router.push({name: 'LessonQuizPage', params:{lessonId:this.id, testId: this.testId}})
            },
            async getEvaluation(){
                try {
                    this.lessonDetails = await axiosService.leconResult(this.id);
                    // console.log(this.lessonDetails)
                    if (this.lessonDetails != null && this.lessonDetails.evaluations != null) {
                        this.testDetails = this.lessonDetails.evaluations?.find((element:any)=>{
                            return element.question.id === this.testId
                        })
                        // console.log(this.testDetails)
                        // console.log(this.testDetails.index.pk+ "/" + this.lessonDetails.pk)
                        // this.testMark = await axiosService.getEvaluationMark(this.testDetails.index.pk, this.lessonDetails.pk);
                    }
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
            this.translateHeaders = await axiosService.translate(['test.btn.label', 'activity.endAt.label', 'test.mark.label', 'test.trial.label', 'test.successAt.label', 'test.markOn.label', 'percentage.label', 'test.complete.msg', 'test.inprogress.msg', 'description.label', 'test.viewResult.btn', 'start.btn']);
        },
        async mounted() {
            this.testId = this.$route.params.testId
            await this.getEvaluation();
        },
        updated(){
            this.testId = this.$route.params.testId
        },
        watch:{
            async testId(){
                await this.getEvaluation();
            }
        },
        computed:{
            testName(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                const question  = this.testDetails != null ? this.testDetails.question : null;
                const temp = question !=null ? question.name[lang] : null;
                return temp != null? temp.value: ""
            },
            testIndex(){
                const question  = this.testDetails != null ? this.testDetails.question : null;
                return question != null? question.sequence:0
            },
            description(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                const question  = this.testDetails != null ? this.testDetails.question : null;
                const temp = question !=null ? question.description[lang] : null;
                const value = temp != null ? temp.value: null
                return value != null ? value : ""
            },
            closeDate(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return (this.lessonDetails != null && this.lessonDetails.activity != null)? translateDateTime(this.lessonDetails.activity.endAt, "T", lang):""
            },
            actualTrail(){
                const question  = this.testDetails != null ? this.testDetails.question : null;
                return question != null? question.nbreOfattemps:0
            },
            maxTrial(){
                const question  = this.testDetails != null ? this.testDetails.question : null;
                return question != null? question.nbreOfattemps:0
            },
            successAt(){
                return (this.lessonDetails != null && this.lessonDetails.activity != null)? this.lessonDetails.activity.successAt:0
            },
            maxPoint(){
                return (this.lessonDetails != null && this.lessonDetails.activity != null)? this.lessonDetails.activity.markOn:0
            },
            percentage(){
                return (this.lessonDetails != null && this.lessonDetails.evaluations.length>0)?100/this.lessonDetails.evaluations.length:0
            },
            evaluationMark(){
                return (this.testDetails != null)?this.testDetails.mark:0
            },
            isDone(){
                const state = this.testDetails != null ? this.testDetails.state: null
                return state != null ? state.code === 'DONE' : false
            },
        }
    }
</script>

<style scoped>

</style>