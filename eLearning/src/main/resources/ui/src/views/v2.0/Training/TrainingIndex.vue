<template>
    <TrainingHeader/>
    <div class="w-full h-full px-[120px] py-4 flex flex-col">
        <div class="w-full h-full flex flex-row gap-2">
            
            <div class="w-[400px] min-h-[700px] px-6 py-4 flex flex-col gap-4 rounded-2xl bg-lightBlue drop-shadow-md shadow">
                <div class="flex justify-center items-center gap-4">
                    <Menu :size="30" class=""/>
                    <span class="uppercase text-[28px] font-myriadBold font-bold">{{ translator('summary.label') }}</span>
                </div>
                
                <SommaireComponents trainingStart isActive :indexUrl="indexUrl"/>

                <div class="w-full flex flex-col gap-2">
                    <div class="w-full pb-2 border-b !border-grayToneStroke flex gap-2 items-center">
                        <span class="font-bold font-myriadBold text-[26px]">{{ translator('training.content.title') }}</span>
                        <span class="bg-pakYellow w-[35px] aspect-square p-1 flex justify-center items-center text-center font-bold font-centuryGothic rounded-full text-white text-[16px]">
                            {{ activities.length ? activities.length :0 }}
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
                            isTrainingActivity
                            :elementType="getType(activity.type)"
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
                        />
                    </div>
                </div>
    
                <!-- <div class="w-full flex flex-col gap-2">
                    <div class="w-full pb-2 border-b !border-grayToneStroke flex gap-2 items-center">
                        <span class="font-bold font-myriadBold text-[26px]">Devoirs</span>
                        <span class="bg-pakBlue w-[35px] aspect-square p-1 flex justify-center items-center text-center font-bold font-centuryGothic rounded-full text-white text-[16px]">2</span>
                    </div>
                    <div class="w-full flex flex-col gap-2">
                        <SommaireComponents :index="1" elementType="Devoir" title="Intitulé de la leçon" duration="15mins" />
                        <SommaireComponents :index="2" elementType="Devoir" title="Intitulé de la leçon" duration="15mins" />
                    </div>
                </div> -->
            </div>
    
            <div class="w-[calc(100%-400px)] py-3 px-6 min-h-[700px] flex flex-col gap-4 rounded-2xl bg-white drop-shadow-md shadow">
                <div class="w-full flex flex-col pb-1 border-b !border-grayToneStroke">
                    <div v-if="trainingName===''" role="status" class="w-full animate-pulse mb-2">
                        <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                    </div>
                    <span v-else class="font-centuryGothic text-[35px] font-bold">{{ trainingName }}</span>

                    <div v-if="themeName===''" role="status" class="w-full animate-pulse mb-2">
                        <div class="w-[60%] h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                    </div>
                    <span v-else class="font-centuryGothic text-[25px] font-bold text-pakYellow">{{ themeName }}</span>
                </div>
                <div class="flex flex-col">
                    <div class="flex gap-1 items-center text-grayTone1">
                        <AccountTie :size="20" class=""/>
                        <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('trainers.label') }}</span>
                    </div>
                    <div v-for="(trainer, index) in trainers" :key="index" class="flex gap-2 items-center pl-6">
                        <span class="w-[30px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white text-sm font-bold">{{ getInitials(trainer) }}</span>
                        <span class="font-centuryGothic text-left">{{ trainer }}</span>
                    </div>
                    <div v-if="trainers.length === 0" class="flex gap-2 items-center pl-6">
                        <span class="w-[30px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white text-sm font-bold">{{ getInitials(translator('noTrainer.label')) }}</span>
                        <span class="font-centuryGothic text-left">{{ translator('noTrainer.label') }}</span>
                    </div>
                </div>
                <div class="flex w-full">
                    <div class="w-full flex flex-col">
                        <div class="flex gap-1 items-center text-grayTone1">
                            <CalendarText :size="20" class=""/>
                            <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('training.startAt.label') }}</span>
                        </div>
                        <div v-if="startAt===''" role="status" class="animate-pulse pl-6">
                            <div class="w-[250px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                        </div>
                        <span v-else class="pl-6 font-centuryGothic text-left">{{ startAt }}</span>
                    </div>
                    <div class="w-full flex flex-col">
                        <div class="flex gap-1 items-center text-grayTone1">
                            <CalendarCheck :size="20" class=""/>
                            <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('training.endAt.label') }}</span>
                        </div>
                        <div v-if="endAt===''" role="status" class="animate-pulse pl-6">
                            <div class="w-[250px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                        </div>
                        <span v-else class="pl-6 font-centuryGothic text-left">{{ endAt }}</span>
                    </div>
                </div>
                <div class="flex w-full">
                    <div class="w-full flex flex-col">
                        <div class="flex gap-1 items-center text-grayTone1">
                            <BookMultiple :size="20" class=""/>
                            <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('lessons.label') }}</span>
                        </div>
                        <div v-if="lessonTotal===0" role="status" class="animate-pulse pl-6">
                            <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                        </div>
                        <span v-else class="pl-6 font-centuryGothic text-left">{{ lessonTotal }}</span>
                    </div>
                    <span v-if="isDone()" class="w-full font-centuryGothic font-bold text-left text-xl text-[#34A853]">{{ translator('training.complete.msg') }}</span>
                    <span v-else class="w-full font-centuryGothic font-bold text-left text-xl text-pakYellow">{{ translator('training.inprogress.msg') }}</span>
                </div>
                <div class="w-full flex flex-col">
                    <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('yourProgression.label') }}</span>
                    <ProgressBar :value="progress" label="Réalisés"/>
                </div>
                <div class="w-full flex flex-col gap-2 font-bold">
                    <span class="font-myriadBold text-xl text-grayTone1 text-left">{{ translator('description.label') }}</span>
                    <div v-if="trainingResume===''" role="status" class="animate-pulse">
                        <div class="w-full h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                    </div>
                    <span v-else class="font-centuryGothic text-justify">
                        {{ trainingResume }}
                    </span>
                    <div v-if="trainingDescription===''" role="status" class="animate-pulse w-full flex flex-col gap-2">
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
                        {{ trainingDescription }}
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import TrainingHeader from '@/components/v2.0/Training/TrainingHeader.vue'
    import BookMultiple from 'vue-material-design-icons/BookMultiple.vue'
    import AccountTie from 'vue-material-design-icons/AccountTie.vue'
    import CalendarText from 'vue-material-design-icons/CalendarText.vue'
    import CalendarCheck from 'vue-material-design-icons/CalendarCheck.vue'
    import Menu from 'vue-material-design-icons/Menu.vue'
    import SommaireComponents from '@/components/v2.0/Training/SommaireComponents.vue'
    import ProgressBar from '@/components/v2.0/UIElements/ProgressBar.vue'
    import {nameInitials, translateDateTime,getDurationBtwDates} from '@/hooks/myHooks'
    import AxiosService from '@/services/axiosService';
    import store from '@/store'

    const axiosService = new AxiosService();
    
    export default {
        name:"TrainingIndex",
        components:{
            TrainingHeader,
            Menu,
            AccountTie,
            CalendarCheck,
            CalendarText,
            BookMultiple,
            SommaireComponents,
            ProgressBar,
        },
        setup(){
            const v2baseURL = '/v2.0/pak-Elearning'
            return{
                v2baseURL
            }
        },
        data() {
            return{
                id: this.$route.params.id,
                trainingType: this.$route.params.type,

                // Integrated data
                trainingDetails: null,
                doneActivitiesCount:null,
                overlay : false,
                error: false,
                closeLoading:false,
                translateHeaders: null,
            }
        },
        methods: {
            async getTraining(id: string) {
                try {
                    this.trainingDetails = await axiosService.getTraining(id);
                } catch (error) {
                    this.error = true ;
                }
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
            async getNbrCompleteActivities(){
                const type = this.trainingType!=null? this.trainingType: null
                let response
                try {
                    if(type!=null && type ==="ongoing"){
                        response = await axiosService.registerTrainings()
                    } else if(type!=null && type ==="complete"){
                        response = await axiosService.getDoneTraining();
                    } else{
                        return 0
                    }
                    const count = response.length>0? response.find((item:any)=>{
                        return item.training.code === this.id
                    }).nbreOfCompleteActivities:0
                    return count
                } catch (error) {
                    console.log(error)
                }
            },
            isDone(){
                // console.log(this.doneActivitiesCount)
                return this.doneActivitiesCount!=null? (this.activities.length + this.testActivities.length) === this.doneActivitiesCount : false
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['summary.label', 'training.content.title', 'summary.evaluation.title', 'trainers.label', 'noTrainer.label', 'training.startAt.label', 'training.endAt.label', 'lessons.label', 'yourProgression.label', 'description.label','training.complete.msg', 'training.inprogress.msg', 'complete.training.btn']);
        },
        async mounted() {
            const trainingId = this.$route.params.id;
            this.overlay = true ;
            if (trainingId !== null) {
                await this.getTraining(trainingId);
                this.doneActivitiesCount = await this.getNbrCompleteActivities();
            }
            this.overlay = false;
        },
        computed: {
            indexUrl(){
                return `${this.v2baseURL}/formations/${this.trainingType}/${this.trainingId}/presentation/`
            },
            trainingName() {
                return this.trainingDetails != null ? this.trainingDetails.name : "";
            },
            themeName(){
                return this.trainingDetails!=null ? this.trainingDetails.theme.name: ""
            },
            trainers(){
                return this.trainingDetails!=null ? this.trainingDetails.involves.map((member:any)=>{
                    if(member.involveType === "TEACHER"){
                        return member.concern.name
                    }
                }): []
            },
            startAt(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return this.trainingDetails!=null ? translateDateTime(this.trainingDetails.startAt, " ",lang): ""
            },
            endAt(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return this.trainingDetails!=null ?  translateDateTime(this.trainingDetails.endAt, " ",lang): ""
            },
            lessonTotal(){
                if(this.trainingDetails != null){
                    let count = 0
                    this.trainingDetails.activities.forEach((activity:any) => {
                            if(activity.type === 'LECON'){
                                count++
                            }
                    })
                    return count
                }
                return 0
            },
            progress(){
                return this.trainingDetails != null ? this.trainingDetails.skill : 0
            },
            trainingResume() {
                return this.trainingDetails != null ? this.trainingDetails.resume: "";
            },
            trainingDescription() {
                return this.trainingDetails != null ? this.trainingDetails.description: "";
            },
            activities() { // All Activities different from an Evaluation
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
            testActivities() { // All Activities which are Evaluations
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
        },
        
    }
</script>

<style>

</style>