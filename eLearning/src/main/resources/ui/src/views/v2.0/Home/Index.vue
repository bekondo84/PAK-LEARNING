<template>
    <HomeHero :heroType="'Available'"/>
    <div class="w-full px-[120px] py-4 flex flex-col gap-4">
        <HomeNavBar :title="translator('my.learning.available.title')"/>
        <SearchInput v-if="apiList.length>0" v-model="searchValue" @setNewSearchValue="sortCourse"  :placeholder="translator('my.learning.available.search')"/>
        
        <div v-if="apiList.length>0" class="w-full min-h-[600px] flex flex-wrap gap-4 animate__animated animate__fadeIn">
            <AvailableCard v-for="(training, index) in displayList"
                :key="training.nom"
                :courseDetails="training"
                :index="index"
            />
        </div>
        
        <div v-else class="w-full min-h-[600px] py-8 flex flex-col justify-center items-center gap-4 animate__animated animate__fadeIn font-myriadBold text-2xl">
            <img src='@/assets/images/NoAvailableTraining.png' alt="PAK E-learning no Available Training Image" class="w-[50%]"/>
            <span class="text-center px-20">{{ translator('my.learning.noAvailable.msg1') }}</span>
            <span class="text-center px-20">{{ translator('my.learning.noAvailable.msg2') }}</span>
        </div>
    </div>
</template>

<script lang="ts">
    import 'animate.css';
    import HomeHero from '@/components/v2.0/HomeHero.vue'
    import HomeNavBar from '@/components/v2.0/HomeNavBar.vue'
    import AvailableCard from '@/components/v2.0/Course/AvailableCard.vue'
    import SearchInput from '@/components/v2.0/FormElement/SearchInput.vue'
    import AxiosService from '@/services/axiosService';
    import {getDurationBtwDates} from '@/hooks/myHooks'
    import store from '@/store';
    import {baseURL} from '@/data/config'

    const axiosService = new AxiosService();
    export default {
        name: "NewCoursePage",
        components:{
            HomeHero,
            HomeNavBar,
            AvailableCard,
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
                    const response = await axiosService.getOnlineTraining();
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
            this.translateHeaders = await axiosService.translate(['my.learning.available.title', 'my.learning.available.search','noTrainer.label', 'my.learning.noAvailable.msg1', 'my.learning.noAvailable.msg2']);
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
                        let count = 0
                        item.training.activities.forEach((activity:any) => {
                                if(activity.type === 'LECON'){
                                    count++
                                }
                        })
                        return {    
                                    pk: item.pk!=null ? item.pk : "",
                                    groupCode: item.code!=null ?item.code:"",
                                    groupName: item.name!=null ? item.name:"",
                                    image: baseURL + item.training.image,
                                    code: item.training.code!=null ?item.training.code:"",
                                    name: item.training.name!=null ?item.training.name:"",
                                    themeName: item.training.theme.name!=null ?item.training.theme.name:"",
                                    trainer: mainTrainer,
                                    lessons: count,
                                    duration: (item.beginAt!=null && item.endAt!=null)? getDurationBtwDates(item.beginAt, item.endAt, " ",lang):"0d"
                                }
                    })
                    this.displayList = this.trainingList
                }
            },
        }
    }
</script>

<style>

</style>