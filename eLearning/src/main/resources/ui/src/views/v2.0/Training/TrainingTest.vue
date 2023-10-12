<template>
    <TrainingHeader isTrainingActivity :testIndex="testIndex" :testTitle="testName"/>
    
    <div class="w-full h-fit px-[120px] py-4">
        <div class="w-full h-full flex flex-row gap-2">
            
            <div class="w-[400px] min-h-[700px] px-6 py-4 flex flex-col gap-4 rounded-2xl bg-lightBlue drop-shadow-md shadow">
                <div class="flex justify-center items-center gap-4">
                    <Menu :size="30" class=""/>
                    <span class="uppercase text-[28px] font-myriadBold font-bold">{{ translator('summary.label') }}</span>
                </div>

                <SommaireComponents trainingStart :indexUrl="indexUrl"/>

                <div class="w-full flex flex-col gap-2">
                    <div class="w-full pb-2 border-b !border-grayToneStroke flex gap-2 items-center">
                        <span class="font-bold font-myriadBold text-[26px]">{{ translator('training.content.title') }}</span>
                        <span class="bg-pakYellow w-[35px] aspect-square p-1 flex justify-center items-center text-center font-bold font-centuryGothic rounded-full text-white text-[16px]">
                            {{ activities.length>0 ? activities.length :0 }}
                        </span>
                    </div>
                    <div v-if="activities.length===0" class="w-full flex flex-col gap-2">
                        <div role="status" class="animate-pulse flex flex-row gap-1 p-4 rounded-lg justify-start items-center bg-white text-black">
                            <div class="w-[130px] h-5 bg-gray-200 rounded-full dark:bg-gray-400"></div>
                            <div class="w-full h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                            <div class="w-[35px] h-5 bg-gray-200 rounded-full dark:bg-gray-400"></div>
                        </div>
                        <div role="status" class="animate-pulse flex flex-row gap-1 p-4 rounded-lg justify-start items-center bg-white text-black">
                            <div class="w-[130px] h-5 bg-gray-200 rounded-full dark:bg-gray-400"></div>
                            <div class="w-full h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                            <div class="w-[35px] h-5 bg-gray-200 rounded-full dark:bg-gray-400"></div>
                        </div>
                        <div role="status" class="animate-pulse flex flex-row gap-1 p-4 rounded-lg justify-start items-center bg-white text-black">
                            <div class="w-[130px] h-5 bg-gray-200 rounded-full dark:bg-gray-400"></div>
                            <div class="w-full h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                            <div class="w-[35px] h-5 bg-gray-200 rounded-full dark:bg-gray-400"></div>
                        </div>
                    </div>
                    <div v-else class="w-full flex flex-col gap-2">
                        <SommaireComponents v-for="(activity, index) in activities"
                            :key="index"
                            :index="index+1"
                            :code="activity.code"
                            :elementType="getType(activity.type)"
                            isTrainingActivity
                            :title="activity.name"
                            :duration="getDuration(activity.beginAt,activity.endAt)"
                        />
                    </div>
                </div>

                <div v-if="testActivities.length>0" class="w-full flex flex-col gap-2">
                    <div class="w-full pb-2 border-b !border-grayToneStroke flex gap-2 items-center">
                        <span class="font-bold font-myriadBold text-[26px]">{{ translator('summary.evaluation.title') }}</span>
                        <span class="bg-pakBlue w-[35px] aspect-square p-1 flex justify-center items-center text-center font-bold font-centuryGothic rounded-full text-white text-[16px]">
                            {{ testActivities.length>0 ? testActivities.length :0 }}
                        </span>
                    </div>
                    <div class="w-full flex flex-col gap-2">
                        <SommaireComponents v-for="(activity, index) in testActivities"
                            :key="index"
                            :index="index+1"
                            :code="activity.code"
                            :elementType="getType(activity.type)"
                            isTrainingActivity
                            :title="activity.name"
                            :duration="getDuration(activity.beginAt,activity.endAt)"
                            :isActive="activity.code === testId"
                        />
                    </div>
                </div>
            </div>
    
            <div class="w-[calc(100%-400px)] py-3 px-6 min-h-[700px] flex flex-col gap-4 rounded-2xl bg-white drop-shadow-md shadow">
                <div class="w-full pb-1 border-b !border-grayToneStroke flex flex-col gap-2.5">
                    <div class="w-full text-[40px] flex flex-row gap-1.5">
                        <div v-if="testName===''" role="status" class="w-full flex gap-2 animate-pulse mb-2">
                            <div class="w-[200px] h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                            <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                        </div>
                        <span v-else class="min-w-[140px] font-centuryGothic font-bold">{{ translator('test.btn.label') }} {{ testIndex }} : {{ testName }}</span>
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
                    <span v-else class="pl-6 font-centuryGothic text-left">{{ maxTrial }}</span>
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
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import TrainingHeader from '@/components/v2.0/Training/TrainingHeader.vue'
    import CalendarCheck from 'vue-material-design-icons/CalendarCheck.vue'
    import Flag from 'vue-material-design-icons/Flag.vue'
    import BrightnessPercent from 'vue-material-design-icons/BrightnessPercent.vue'
    import Information from 'vue-material-design-icons/Information.vue'
    import Cached from 'vue-material-design-icons/Cached.vue'
    import ClipboardCheckOutline from 'vue-material-design-icons/ClipboardCheckOutline.vue'
    import Menu from 'vue-material-design-icons/Menu.vue'
    import SommaireComponents from '@/components/v2.0/Training/SommaireComponents.vue'
    import store from '@/store';
    import {nameInitials, translateDateTime, getDurationBtwDates} from '@/hooks/myHooks'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name: 'TrainingTest',
        components:{
            TrainingHeader,
            CalendarCheck,
            Flag,
            BrightnessPercent,
            Information,
            Cached,
            ClipboardCheckOutline,
            Menu,
            SommaireComponents,
        },
        setup(){
            const v2baseURL = '/v2.0/pak-Elearning'
            return{
                v2baseURL
            }
        }, 
        data(){
            return {
                trainingId: this.$route.params.id,
                trainingType: this.$route.params.type,
                testId: this.$route.params.testId,
                // Integrated data
                trainingDetails: null,
                testDetails: null,
                overlay : false,
                error: false,
                translateHeaders: null,
            }
        },
        methods:{
            async getTraining() {
                try {
                    this.trainingDetails = await axiosService.getTraining(this.trainingId);
                    this.testDetails = await axiosService.testResult(this.testId)
                } catch (error) {
                    this.error = true ;
                }
            },
            routeToQuiz(){
                this.$router.push({name: 'TrainingQuizPage', params:{trainingId:this.trainingId, testId: this.testId}})
            },
            getType(value:string){
                switch(value){
                    case 'LECON':
                        return 'Leçon'
                    case 'TEST':
                        return 'Test'
                    default: return 'Leçon'
                }
            },
            getDuration(value1:string, value2:string){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                const days =  getDurationBtwDates(value1, value2, " ",lang)
                return days
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
        async created() {
            this.translateHeaders = await axiosService.translate(['summary.label', 'training.content.title', 'summary.evaluation.title','test.btn.label','activity.endAt.label','test.mark.label','test.trial.label','test.successAt.label', 'test.markOn.label','percentage.label','test.complete.msg', 'test.inprogress.msg', 'description.label', 'test.viewResult.btn', 'start.btn']);
        },
        async mounted() {
            await this.getTraining();
            this.overlay = false ;
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
            indexUrl(){
                return `${this.v2baseURL}/formations/${this.trainingType}/${this.trainingId}/presentation/`
            },
            activities() {
                let tempArray:any = []
                if(this.trainingDetails != null){
                    this.trainingDetails.activities.forEach((activity:any)=>{
                        if(activity.type !== 'TEST'){
                            tempArray.push(activity)
                        }
                    })
                }
                return tempArray
            },
            testActivities() {
                let tempArray:any = []
                if(this.trainingDetails != null){
                    this.trainingDetails.activities.forEach((activity:any)=>{
                        if(activity.type === 'TEST'){
                            tempArray.push(activity)
                        }
                    })
                }
                return tempArray
            },
            testName(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                const activity  = this.testDetails != null ? this.testDetails.activity : null;
                const temp = activity !=null ? activity.name[lang] : null;
                return temp != null? temp.value: ""
            },
            testIndex(){
                const activity  = this.testDetails != null ? this.testDetails.activity : null;
                return activity != null? activity.sequence:0
            },
            description(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                const activity  = this.testDetails != null ? this.testDetails.activity : null;
                const temp = activity !=null ? activity.description[lang] : null;
                const value = temp != null ? temp.value: null
                return value != null ? value : ""
            },
            closeDate(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return (this.testDetails != null && this.testDetails.activity != null)? translateDateTime(this.testDetails.activity.endAt, "T", lang):""
            },
            maxTrial(){
                const question  = this.testDetails != null ? this.testDetails.activity : null;
                return question != null? question.nbreOfattemps:0
            },
            successAt(){
                return (this.testDetails != null && this.testDetails.activity != null)? this.testDetails.activity.successAt:0
            },
            maxPoint(){
                return (this.testDetails != null && this.testDetails.activity != null)? this.testDetails.activity.markOn:0
            },
            percentage(){
                return (this.testDetails != null && this.testDetails.activity != null)? this.testDetails.activity.percent:0
            },
            evaluationMark(){
                return (this.testDetails != null && this.testDetails.mark != null )?this.testDetails.mark:0
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