<template>
    <HomeHero :heroType="'Ongoing'"/>
    <div class="w-full px-[120px] py-4 flex flex-col gap-4">
        <HomeNavBar :title="translator('my.learning.inprogress.title')"/>
        <SearchInput v-if="apiList.length>0" v-model="searchValue" @setNewSearchValue="sortCourse"  :placeholder="translator('my.learning.inprogress.search')"/>
        
        <div v-if="apiList.length>0" class="w-full min-h-[600px] flex flex-col gap-4 animate__animated animate__fadeIn">
            <OngoingCard v-for="(training, index) in displayList"
                :key="index"
                :courseDetails="training"
                :index="index"
            />
        </div>

        <div v-else class="w-full min-h-[600px] py-8 flex flex-col justify-center items-center gap-4 animate__animated animate__fadeIn font-myriadBold text-2xl">
            <img src='@/assets/images/NoOngoingTraining.png' alt="PAK E-learning no Ongoing Training Image" class="w-[50%]"/>
            <span class="text-center px-20">{{ translator('my.learning.noInprogress.msg1') }}</span>
        </div>
    </div>
</template>

<script lang="ts">
    import 'animate.css';
    import HomeHero from '@/components/v2.0/HomeHero.vue'
    import HomeNavBar from '@/components/v2.0/HomeNavBar.vue'
    import OngoingCard from '@/components/v2.0/Course/OngoingCard.vue'
    import SearchInput from '@/components/v2.0/FormElement/SearchInput.vue'
    import {translateDateTime} from '@/hooks/myHooks'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';
    import { baseURL } from '@/data/config';

    const axiosService = new AxiosService();

    export default {
        name: "OngoingCourse",
        components:{
            HomeHero,
            HomeNavBar,
            OngoingCard,
            SearchInput
        },
        data(){
            return{
                searchValue: "",
                courseList: [],

                // Integrated data
                apiTraining: [],
                displayTrainingList:[],
                error: false,
                translateHeaders: null,
            }
        },
        methods:{
            sortCourse(value: string){
                // console.log(value)
                if(value !== ""){
                    let tempList = [...this.apiList]
                    tempList = this.apiList.filter((training:any)=>{
                        return (training.name.toLowerCase().trim().includes(value.toLowerCase().trim()) ||
                                training.themeName.toLowerCase().trim().includes(value.toLowerCase().trim()) ||
                                training.trainer.toLowerCase().trim().includes(value.toLowerCase().trim())
                        )
                    })
                    // console.log(tempList.length)
                    if(tempList.length > 0) {
                        this.displayList = tempList
                    }
                }else{
                    this.displayList = this.apiList
                }
            },
            async getNextLeconActivity(activities:any){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                let nextActivity = null
                for (let i = 0; i < activities.length; i++){
                    const act = activities[i];
                    if(act.type === 'LECON'){
                        const resultActivity = await axiosService.leconResult(act.code)
                        nextActivity = (resultActivity!='' && resultActivity.state.code != 'DONE')? resultActivity.currentIndex.index.name[lang].value: null
                    }
                }
                return nextActivity
            },
            async getNextTestActivity(activities:any){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                let nextActivity = null
                for (let i = 0; i < activities.length; i++) {
                    const act = activities[i];
                    if(act.type === 'TEST'){
                        const resultActivity = await axiosService.testResult(act.code)
                        nextActivity = (resultActivity!='' && resultActivity.state != 'DONE')? resultActivity.activity.name[lang].value: null
                    }
                }
                return nextActivity
            },
            async getNextActivity(activities:any, nbreOfCompleteActivities:number){
                let nextActivity = null
                if(activities.length > 0 && nbreOfCompleteActivities!=activities.length){
                    nextActivity = await this.getNextLeconActivity(activities)
                    if(nextActivity==null){
                        nextActivity = await this.getNextTestActivity(activities)
                    }
                }
                return nextActivity!=null? nextActivity: activities[0].name
            },
            errorHandler() {
                this.error = true ;
            },
            async constructList(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                let trainingList:any = []
                for (let i = 0; i < this.apiTraining.length; i++) {
                    const item = this.apiTraining[i];
                    let mainTrainer = this.translator('noTrainer.label')
                    if(item.training.involves.length !== 0){
                        mainTrainer = item.training.involves[0].concern.name
                    }
                    const nextAct = await this.getNextActivity(item.training.activities, item.nbreOfCompleteActivities)
                    const tempInvolve = {
                                image: baseURL + item.training.image,
                                name: item.training.name,
                                code:item.training.code,
                                themeName: item.training.theme.name,
                                trainer: mainTrainer,
                                finishedActivities: item.nbreOfCompleteActivities,
                                totalActivities: item.training.activities.length,
                                progress: item.training.skill,
                                startAt: translateDateTime(item.startingDate, " ",lang),
                                nextActivity: nextAct!=null? nextAct :"",
                            }
                    trainingList.push(tempInvolve)
                }
                this.courseList = trainingList
                this.displayList = trainingList
            },
            async getItems() {
                try {
                    this.apiTraining.splice(0, this.apiTraining.length);
                    const response = await axiosService.registerTrainings();
                    this.apiTraining.push(...response);
                    if(this.apiTraining != null){
                        this.constructList();
                    }
                } catch(error) {
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
        async mounted() {
            await this.getItems();
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['my.learning.inprogress.title', 'my.learning.inprogress.search', 'noTrainer.label', 'my.learning.noInprogress.msg1']);
        },
        computed:{
            displayList:{
                get(){
                    return this.displayTrainingList
                },
                set(value:any){
                    this.displayTrainingList = value
                }
            },
            apiList(){
                return this.courseList
            },
        }
    }
</script>

<style scoped>

</style>