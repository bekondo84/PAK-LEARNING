<template>
    <HomeHero :heroType="'Completed'"/>
    <div class="w-full h-full min-h-[800px] px-[120px] py-4 flex flex-col gap-4">
        <HomeNavBar :title="translator('my.learning.done.title')"/>
        <SearchInput v-if="apiList.length>0" v-model="searchValue" @setNewSearchValue="sortCourse"  :placeholder="translator('my.learning.done.search')"/>
        
        <div v-if="apiList.length>0" class="w-full min-h-[600px] flex flex-col gap-4 animate__animated animate__fadeIn">
            <CompleteCard v-for="(training, index) in displayList"
                :key="training.nom"
                :courseDetails="training"
                :index="index"
            />
        </div>

        <div v-else class="w-full min-h-[600px] py-8 flex flex-col justify-center items-center gap-4 animate__animated animate__fadeIn font-myriadBold text-2xl">
            <img src='@/assets/images/NoCompleteTraining.png' alt="PAK E-learning no Completed Training Image" class="w-[50%]"/>
            <span class="text-center px-20">{{ translator('my.learning.noDone.msg1') }}</span>
        </div>
    </div>
</template>

<script lang="ts">
    import 'animate.css';
    import HomeHero from '@/components/v2.0/HomeHero.vue'
    import HomeNavBar from '@/components/v2.0/HomeNavBar.vue'
    import CompleteCard from '@/components/v2.0/Course/CompleteCard.vue'
    import SearchInput from '@/components/v2.0/FormElement/SearchInput.vue'
    import {translateDateTime, getDurationBtwDates} from '@/hooks/myHooks'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';
    import { baseURL } from '@/data/config';

    const axiosService = new AxiosService();

    export default {
        name: "CompleteCourse",
        components:{
            HomeHero,
            HomeNavBar,
            CompleteCard,
            SearchInput
        },
        data(){
            return{
                searchValue: "",
                courseList: [],

                // Integrated data
                apiTraining: [],
                error: false,
                trainingList: [],
                displayTrainingList:[],
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
            async getItems() {
                try {
                    this.apiTraining.splice(0, this.apiTraining.length);
                    const response = await axiosService.getDoneTraining();
                    this.apiTraining.push(...response);
                    // console.log(this.apiTraining)
                    this.apiList = this.apiTraining
                } catch(error) {
                    this.error = true ;
                }
            },
            errorHandler() {
                this.error = true ;
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
            this.translateHeaders = await axiosService.translate(['my.learning.done.title', 'my.learning.done.search', 'noTrainer.label','my.learning.noDone.msg1']);
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
            apiList:{
                // getter
                get() {
                    return this.trainingList
                },
                // setter
                set(value: any) {
                    const lang = store.getters.getCurrentSession.language;
                    this.trainingList = value.map((item:any)=>{
                        let mainTrainer = this.translator('noTrainer.label')
                        if(item.training.involves.length !== 0){
                            // console.log(item.training.involves[0].concern.name)
                            mainTrainer = item.training.involves[0].concern.name
                        }
                        return {
                                image: baseURL + item.training.image,
                                name: item.training.name,
                                code:item.training.code,
                                themeName: item.training.theme.name,
                                endAt: translateDateTime(item.finishedDate, " ",lang),
                                duration: getDurationBtwDates(item.training.startAt, item.training.endAt, " ",lang),
                                finishedActivities: item.nbreOfCompleteActivities,
                                totalActivities: item.training.activities.length,
                                progress: item.training.skill,
                                trainer: mainTrainer,
                            }
                    })
                    this.displayList = this.trainingList
                }
            }
        }
    }
</script>

<style scoped>

</style>