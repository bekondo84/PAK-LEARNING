<template>
    <div class="w-full flex flex-col">
        <div class="w-full h-[200px] bg-pakDarkBlue relative">
            <div class="absolute left-0 top-0 right-0 bottom-0 flex flex-col gap-2 items-center justify-center">
                <span class="w-full top-[60px] uppercase text-center text-white text-bold font-myriadBold text-3xl">{{ translator('training.details.header') }}</span>
            </div>
            <button @click="routeToHome" class="absolute ml-[120px] bottom-2.5 flex flex-row items-center gap-2 py-1 px-2 rounded-lg text-white hover:bg-white hover:!text-pakDarkBlue">
                <Home :size="20" class=""/>
                <span class="text-base text-center text-bold font-prototype">{{ translator('return.home.btn') }}</span>
            </button>
        </div>
        <div class="w-full h-full py-2 px-[120px] flex flex-col">
            <div class="w-full h-[300px] relative rounded-2xl drop-shadow-md shadow">
                <div v-if="trainingImage===''" role="status" class="animate-pulse w-full h-full absolute rounded-2xl flex items-center justify-center bg-gray-300 dark:bg-gray-700">
                    <svg class="w-10 aspect-square rounded-[12px] text-gray-200 dark:text-gray-600" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18">
                        <path d="M18 0H2a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2Zm-5.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3Zm4.376 10.481A1 1 0 0 1 16 15H4a1 1 0 0 1-.895-1.447l3.5-7A1 1 0 0 1 7.468 6a.965.965 0 0 1 .9.5l2.775 4.757 1.546-1.887a1 1 0 0 1 1.618.1l2.541 4a1 1 0 0 1 .028 1.011Z"/>
                    </svg>
                </div>
                <img v-else :src="trainingImage" alt="" class="w-full h-full rounded-2xl absolute bg-cover bg-center">
                <div class="w-[250px] p-2 absolute right-0 bottom-0 rounded-tl-2xl rounded-br-2xl flex flex-col gap-1 items-center bg-white">
                    <span class="text-center text-sm flex font-bold">{{ translator('main.trainer.label') }}</span>
                    <span class="w-[40px] aspect-square bg-pakBlue rounded-full flex justify-center items-center text-white text-base font-bold">{{ mainTrainerInitials!==''?mainTrainerInitials:getInitials(translator('noTrainer.label')) }}</span>
                    <span class="text-center font-myriadBold flex text-zinc-800 text-lg font-bold leading-snug tracking-tight">{{ trainers[0]!==''? trainers[0] : translator('noTrainer.label') }}</span>
                </div>
            </div>

            <div class="w-full h-full flex flex-row gap-2 py-4">
                
                <div class="w-[400px] min-h-[700px] px-6 py-4 flex flex-col gap-4 rounded-2xl bg-lightBlue drop-shadow-md shadow">
                    <div class="flex justify-center items-center gap-4">
                        <Menu :size="30" class=""/>
                        <span class="uppercase text-[28px] font-myriadBold font-bold">{{ translator('summary.label') }}</span>
                    </div>

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
                                viewOnly
                                :elementType="getType(activity.type)"
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
                            <SommaireComponents :index="1" elementType="Devoir" title="Intitulé de la leçon" duration="15mins" viewOnly/>
                            <SommaireComponents :index="2" elementType="Devoir" title="Intitulé de la leçon" duration="15mins" viewOnly/>
                        </div>
                    </div>

                    <div class="w-full flex flex-col gap-2">
                        <div class="w-full pb-2 border-b !border-grayToneStroke flex gap-2 items-center">
                            <span class="font-bold font-myriadBold text-[26px]">Evaluations</span>
                            <span class="bg-pakDarkBlue w-[35px] aspect-square p-1 flex justify-center items-center text-center font-bold font-centuryGothic rounded-full text-white text-[16px]">1</span>
                        </div>
                        <div class="w-full flex flex-col gap-2">
                            <SommaireComponents :index="1" elementType="Test" title="Intitulé du Test" duration="30mins" viewOnly/>
                        </div>
                    </div> -->
                </div>

                <div class="w-[calc(100%-400px)] py-3 px-6 min-h-[700px] flex flex-col gap-4 rounded-2xl bg-white drop-shadow-md shadow">
                    <div class="w-full flex flex-col">
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
                        <div v-if="trainers.length === 0" class="flex gap-2 items-center pl-6">
                            <span class="w-[30px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white text-sm font-bold">{{ getInitials(translator('noTrainer.label')) }}</span>
                            <span class="font-centuryGothic text-left">{{ translator('noTrainer.label') }}</span>
                        </div>
                        <div v-else v-for="(trainer, index) in trainers" :key="index" class="flex gap-2 items-center pl-6">
                            <span class="w-[30px] aspect-square bg-pakDarkBlue rounded-full flex justify-center items-center text-white text-sm font-bold">{{ getInitials(trainer) }}</span>
                            <span class="font-centuryGothic text-left">{{ trainer }}</span>
                        </div>
                    </div>

                    <div class="flex w-full">
                        <div class="w-full flex flex-col">
                            <div class="flex gap-1 items-center text-grayTone1">
                                <CalendarText :size="20" class=""/>
                                <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('training.startAt.label')}}</span>
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

            <Teleport to="body">
                <div class="w-full h-full overflow-hidden z-10">
                    <v-btn
                        class="flex flex-row fixed bottom-4 right-4 p-8 rounded-[10px] bg-pakBlue justify-evenly text-white items-center gap-2 hover:text-white hover:bg-pakDarkBlue"
                        :loading="loading"
                        size="large"
                        type="submit"
                        variant="elevated"
                        @click="subscribeAction"
                        >
                        <span class="flex font-bold uppercase">{{ translator('subscribe.btn') }}</span>
                        <ChevronRight :size="24" class=""/>
                    </v-btn>
                </div>
            </Teleport>
        </div>
        
    </div>
</template>

<script lang="ts">
    import Home from 'vue-material-design-icons/Home.vue'
    import Menu from 'vue-material-design-icons/Menu.vue'
    import BookMultiple from 'vue-material-design-icons/BookMultiple.vue'
    import AccountTie from 'vue-material-design-icons/AccountTie.vue'
    import CalendarText from 'vue-material-design-icons/CalendarText.vue'
    import ChevronRight from 'vue-material-design-icons/ChevronRight.vue'
    import CalendarCheck from 'vue-material-design-icons/CalendarCheck.vue'
    import SommaireComponents from '@/components/v2.0/Training/SommaireComponents.vue'
    import {nameInitials, translateDateTime,getDurationBtwDates} from '@/hooks/myHooks'
    import store from '@/store'
    import AxiosService from '@/services/axiosService';
    import { baseURL } from '@/data/config';

    const axiosService = new AxiosService();
    export default {
        name:"CourseDetails",
        components:{
            Home,
            Menu,
            AccountTie,
            CalendarCheck,
            CalendarText,
            BookMultiple,
            ChevronRight,
            SommaireComponents
        },
        data() {
            return{
                trainingId: this.$route.params.id,
                groupCode:this.$route.params.groupCode,
                loading:false,

                // Integrated data
                trainingDetails: null,
                overlay : false,
                error: false,
                translateHeaders: null,
            }
        },
        methods:{
            routeToHome(){
                this.$router.push({name: "HomePage"})
            },
            subscribeAction(){
                this.loading = true
                setTimeout(async () => {
                    await axiosService.registerOnlineTraining(this.groupCode);
                    this.loading = false
                    this.$router.push({name: "SubscribedCourse"})
                }, 1000);
            },
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
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async mounted() {
            this.overlay = true ;
            if (this.trainingId != null) {
                await this.getTraining(this.trainingId);
            }
            this.overlay = false ;
        },
        async created() {
            this.emitter.on("authorization-error", ()=>{this.$router.push({name: 'LoginPage'})});
            this.translateHeaders = await axiosService.translate(['training.details.header', 'return.home.btn','main.trainer.label', 'trainers.label','training.startAt.label','training.endAt.label', 'lessons.label', 'description.label', 'subscribe.btn', 'summary.label', 'training.content.title','noTrainer.label']);
        },
        computed:{
            trainingName() {
                return this.trainingDetails != null ? this.trainingDetails.name : "";
            },
            themeName(){
                return this.trainingDetails!=null ? this.trainingDetails.theme.name: ""
            },
            trainingImage(){
                return this.trainingDetails!=null ? baseURL+this.trainingDetails.image:""
            },
            trainers(){
                return this.trainingDetails!=null ? this.trainingDetails.involves.map((member:any)=>{
                    if(member.involveType === "TEACHER"){
                        return member.concern.name
                    }
                }): []
            },
            mainTrainerInitials(){
                let temp = this.trainers.length !== 0 ?nameInitials(this.trainers[0]) : ""
                return temp!==null ? temp : ""
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
            trainingResume() {
                return this.trainingDetails != null ? this.trainingDetails.resume: "";
            },
            trainingDescription() {
                return this.trainingDetails != null ? this.trainingDetails.description: "";
            },
            activities() {
                return this.trainingDetails != null ? this.trainingDetails.activities : [];
            }
        }
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
</style>