<template>
    <TrainingHeader showLesson/>
    <div class="w-full h-full px-[120px] py-4">
        <div class="w-full h-full flex flex-row gap-2">
            
            <div class="w-[400px] min-h-[700px] px-6 py-4 flex flex-col gap-4 rounded-2xl bg-lightBlue drop-shadow-md shadow">
                <div class="flex justify-between items-center">
                    <button @click="routeToTraining" class="relative w-[30px] aspect-square">
                        <ArrowLeftCircle :size="30" class="absolute top-0 left-0"/>
                        <ArrowLeftCircle :size="30" class="hover:animate-ping absolute top-0 left-0"/>
                    </button>
                    <span class="w-full uppercase text-[28px] flex justify-center text-center font-myriadBold font-bold">{{ translator('summary.label') }}</span>
                </div>
                <SommaireComponents lessonStart :isActive="isLessonIndex" :indexUrl="indexUrl"/>
                
                <div v-if="resourceListLength > 0" class="w-full flex flex-col gap-2">
                    <span class="pb-2 border-b !border-grayToneStroke font-bold font-myriadBold text-[26px]">{{ translator('resource.title') }}</span>
                    <div class="w-full flex flex-col gap-2">
                        <button @click="routeToRessources" :class="isRessourcePage?'!bg-pakDarkBlue !text-white':'bg-white text-black'" class="flex flex-row gap-1 p-4 rounded-lg justify-start items-center hover:!bg-pakDarkBlue hover:!text-white">
                            <LinkVariant :size="20" class=""/>
                            <span class="grow shrink basis-0 font-bold text-left">{{ translator('resource.join.label') }}</span>
                            <span class="text-right">{{ resourceListLength }}</span>
                        </button>
                    </div>
                </div>
                
                <div class="w-full flex flex-col gap-2">
                    <div class="w-full pb-2 border-b !border-grayToneStroke flex gap-2 items-center">
                        <span class="font-bold font-myriadBold text-[26px]">{{ translator('lesson.summary.title') }}</span>
                        <span class="bg-pakYellow w-[35px] aspect-square p-1 flex justify-center items-center text-center font-bold font-centuryGothic rounded-full text-white text-[16px]">
                            {{ coursesList.length }}
                        </span>
                    </div>
                    <div v-if="coursesList.length===0" class="w-full flex flex-col gap-2">
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
                        <SommaireComponents v-for="(item, index) in coursesList"
                            :viewOnly="!isCurrent(item.sequence)"
                            :isCurrentActivity="isCurrent(item.sequence)"
                            :key="index"
                            :code="item.code"
                            :index="item.elementIndex"
                            :elementType="item.type"
                            :title="item.title"
                            :duration="item.duration"
                            :isActive="isActiveCourseLink(item.type,item.code)"
                        />
                    </div>
                </div>
            </div>

            <div class="w-[calc(100%-400px)] py-3 px-6 min-h-[700px] flex flex-col gap-4 rounded-2xl bg-white drop-shadow-md shadow">
                <router-view></router-view>
                <div class="w-full flex flex-row gap-2 justify-between">
                    <button v-if="currentActivityIndex > 1" @click="previousStep()" class="w-[220px] flex float-left font-centuryGothic text-xl !text-pakBlue font-bold p-3 rounded-lg border !border-pakBlue !bg-white hover:!bg-pakBlue hover:!text-white">
                        {{ translator('previous.activitiy.btn') }}
                    </button>
                    <button v-if="hasNext" @click="nextStep()" class="float-right font-centuryGothic text-xl !text-pakDarkBlue font-bold p-3 rounded-lg border !border-pakDarkBlue !bg-white hover:!bg-pakDarkBlue hover:!text-white">
                        {{ translator('next.activity.btn') }}
                    </button>
                    <v-btn
                        class="!w-[calc(100%-220px)] !h-full !capitalize !flex justify-center items-center font-centuryGothic text-xl !text-[#34A853] font-bold p-3 rounded-lg border !border-[#34A853] !bg-white hover:!bg-[#34A853] hover:!text-white"
                        v-if="isLast && !isDone"
                        :loading="closeLoading"
                        size="large"
                        type="submit"
                        variant="flat"
                        @click="closeActivity()"
                    >
                        {{ translator('complete.lesson.btn') }}
                    </v-btn>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import TrainingHeader from '@/components/v2.0/Training/TrainingHeader.vue'
    import ArrowLeftCircle from 'vue-material-design-icons/ArrowLeftCircle.vue'
    import LinkVariant from 'vue-material-design-icons/LinkVariant.vue'
    import SommaireComponents from '@/components/v2.0/Training/SommaireComponents.vue'
    import store from '@/store';
    import {getDurationBtwDates} from '@/hooks/myHooks'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"LessonLayout",
        components:{
            TrainingHeader,
            ArrowLeftCircle,
            SommaireComponents,
            LinkVariant,
        },
        setup(){
            const v2baseURL = '/v2.0/pak-Elearning'
            return{
                v2baseURL
            }
        },
        data(){
            return{
                trainingId: this.$route.params.id,
                trainingType: this.$route.params.type,
                id: this.$route.params.lessonId,
                // courseList:[
                //     {
                //         title: "Intitulé cours",
                //         duration: "30 mins",
                //         type: 'PDF',
                //     },
                //     {
                //         title: "Intitulé cours",
                //         duration: "15 mins",
                //         type: 'Video'
                //     }
                // ],
                // testList:[
                //     {
                //         title: "Intitulé du Test",
                //         duration: "30 mins",
                //         type: 'Test',
                //     }
                // ],

                // Integrated Data
                
                lessonDetails: null,
                lessonIndex: 1,
                currentActivityIndex:1,
                courseFormatList:[],
                error: false,
                overlay:false,
                closeLoading: false,
                translateHeaders: null,
            }
        },
        methods:{
            async loadData() {
                try {
                    const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                    const lessonId = this.$route.params.lessonId;
                    this.lessonDetails = await axiosService.leconResult(lessonId);
                    this.lessonIndex = this.lessonDetails.activity.sequence;
                    this.currentActivityIndex = this.lessonDetails.currentIndex.sequence;
                    this.courseFormatList = this.lessonDetails.activity.contents.map((item:any)=>{
                        // console.log(item)
                        return{
                            title: item.name[lang]?.value,
                            format: item.format.code
                        }
                    })
                    // console.log(this.courseFormatList)
                } catch (error) {
                    this.error = true ;
                }
            },
            routeToTraining(){
                this.$router.push({name: "TrainingIndex", params:{type:this.trainingType, id: this.trainingId}})
            },
            routeToRessources(){
                this.$router.push({name: "LessonRessource", params: {count:2}})
            },
            isCurrent(sequence: number) {
                const entry = this.lessonDetails != null ? this.lessonDetails.currentIndex : null;
                return (entry != null && entry.sequence === sequence);
            },
            async nextStep() {
                try{
                    this.lessonDetails = await axiosService.nextStepCourse(this.lessonDetails.pk);
                    this.currentActivityIndex = this.lessonDetails.currentIndex.sequence;
                    
                    const summaryEntry = this.lessonDetails != null ? this.lessonDetails.currentIndex : null
                    if(this.isCourse){
                        const format = summaryEntry.index.format.code
                        const code = summaryEntry.index.id
                        this.routeToElement(format,code)
                    }
                    else if(this.isTest){
                        const code = summaryEntry.index.id
                        this.routeToElement("TEST",code)
                    }
                }catch(error){
                    console.log(error)
                }
            },
            async previousStep() {
                try{
                    this.lessonDetails = await axiosService.previousStepCourse(this.lessonDetails.pk);
                    this.currentActivityIndex = this.lessonDetails.currentIndex.sequence;
                    const summaryEntry = this.lessonDetails != null ? this.lessonDetails.currentIndex : null;
                    if(this.isCourse){
                        const format = summaryEntry.index.format.code
                        const code = summaryEntry.index.id
                        this.routeToElement(format,code)
                    }
                    else if(this.isTest){
                        const code = summaryEntry.index.id
                        this.routeToElement("TEST",code)
                    }
                }catch(error){
                    console.log(error)
                }
            },
            async closeActivity() {
                try{
                    this.closeLoading =  true,
                    this.lessonDetails = await axiosService.closeLeconActivity(this.lessonDetails.pk);
                    this.currentActivityIndex = this.lessonDetails.currentIndex.sequence;
                    this.closeLoading =  false,
                    this.routeToTraining()
                }catch(error){
                    console.log(error)
                }
            },
            routeToElement(format: string, code:string){
                if(format === 'PDF'){
                    this.$router.push({name: "LessonPDF", params:{courseId: code}})
                }else if(format === 'MP4'){
                    this.$router.push({name: "LessonVideo", params:{courseId: code}})
                }else if(format === 'TEST'){
                    this.$router.push({name: "LessonTest", params:{testId: code}})
                }
            },
            isActiveCourseLink(type:string, id:string){
                let fullLink = this.indexUrl
                if(type === "PDF"){
                    fullLink += `/cours-pdf/${id}`
                }else if(type === "Video"){
                    fullLink += `/cours-video/${id}`
                }else if(type === "Test"){
                    fullLink += `/evaluation/${id}`
                }
                return this.$route.path === fullLink
            },
            getDuration(value1:string, value2:string){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr"
                const days =  getDurationBtwDates(value1, value2, " ",lang)
                return days
            },
            getCourseTypeFromArray(value:string){
                const courseFormat = this.courseFormatList.find((course:any)=>{
                    return course.title === value
                })?.format
                switch(courseFormat){
                    case 'PDF':
                        return 'PDF'
                    case 'MP4':
                        return 'Video'
                    default: return 'Leçon'
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
            this.translateHeaders = await axiosService.translate(['summary.label','resource.title','resource.join.label','lesson.summary.title', 'previous.activitiy.btn', 'next.activity.btn', 'complete.lesson.btn']);
        },
        async mounted() {
            this.overlay = true ;
            await this.loadData();
            this.overlay = false;
        },
        computed:{
            isLessonIndex(){
                const tempUrlArray = this.$route.path.split('/')
                return tempUrlArray[tempUrlArray.length-2] === 'lecon'
            },
            isRessourcePage(){
                return this.$route.path.includes("ressources")
            },
            indexUrl(){
                return `${this.v2baseURL}/formations/${this.trainingType}/${this.trainingId}/presentation/lecon/${this.id}`
            },
            coursesList(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                let temp = []
                if(this.lessonDetails!=null){
                    temp = this.lessonDetails.activity.summary?.map((item:any)=>{
                        return {
                            elementIndex:item.index.sequence,
                            code:item.index.id,
                            type: item.index.nature === "COURSE"? this.getCourseTypeFromArray(item.index.name[lang].value) : 'Test',
                            sequence: item.sequence,
                            title: item.index.name[lang].value,
                            duration: "30mins"
                        }
                    })
                    // console.log(temp)
                }
                return temp
            },
            resourceListLength(){
                return this.lessonDetails!=null ? this.lessonDetails.activity.resources.length:0
            },
            summaries() {
                const activity = this.lessonDetails != null && this.lessonDetails.activity != null ? this.lessonDetails.activity : null;
                return activity != null ? activity.summary : [];
            },
            isDone(){
                const state = this.lessonDetails != null ? this.lessonDetails.state: null
                return state != null ? state.code === 'DONE' : false
            },
            isLast() {
                if (this.summaries != null) {
                    const isLastSummary = this.currentActivityIndex === this.summaries.length && this.lessonDetails.state.code != 'DONE'
                    if(isLastSummary){
                        if(this.lessonDetails.evaluations.length>0) {
                            this.lessonDetails.evaluations.forEach((test:any)=>{
                                 if(test != null && test.state != null && test.state.code != "DONE"){
                                    return false
                                 }
                            })
                            return true
                        }else{
                            return true
                        }
                    }
                }
                return false;
            },
            hasNext() {
                if (this.summaries != null) {
                    return this.summaries.length > this.currentActivityIndex;
                }
                return false ;
            },
            isCourse() {
                const summaryEntry = this.lessonDetails != null ? this.lessonDetails.currentIndex : null ;
                
                if (summaryEntry != null && summaryEntry.index != null) {
                    return summaryEntry.index.nature === 'COURSE';
                }
                return false ;
            },
            isTest() {
                const summaryEntry = this.lessonDetails != null ? this.lessonDetails.currentIndex : null ;
                
                if (summaryEntry != null && summaryEntry.index != null) {
                    return summaryEntry.index.nature === 'TEST';
                }
                return false ;
            },
        }
    }
</script>

<style scoped>

</style>