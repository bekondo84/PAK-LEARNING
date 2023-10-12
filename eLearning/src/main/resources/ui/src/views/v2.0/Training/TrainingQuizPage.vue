<template>
    <div class="w-full h-[95px] sticky z-10 top-0 left-0 right-0 drop-shadow-md py-3 bg-white flex flex-row justify-center items-center gap-8">
        <img src="@/assets/images/pakLogo2.svg" alt="" class="w-[300px]">
        <h1 class="text-center text-black text-[32px] font-normal leading-10 font-prototype">PAK E-Learning</h1>
    </div>
    <div class="w-full h-[100px] flex flex-row gap-1 justify-center items-center bg-pakBlue">
        <span class="font-centuryGothic font-bold text-3xl text-white">{{ translator('training.label') }} : {{ trainingName }}</span>
    </div>
    <div class="w-full h-[100px] flex flex-row gap-1 justify-center items-center bg-pakDarkBlue">
        <span class="font-centuryGothic font-bold text-3xl text-white">{{ translator('summary.evaluation.title') }} {{ testIndex }} : {{ testName }}</span>
    </div>
    <div class="w-full px-[120px] py-4">
        <div class="w-full min-h-[950px] flex flex-col gap-2 rounded-2xl bg-white drop-shadow-md shadow px-4 py-4">
            <div class="w-full flex flex-row justify-between pb-4 border-b !border-grayToneStroke">
                <div v-if="isDone" class="flex gap-1 items-center text-[#34A853] text-2xl">
                    <Information :size="25" class=""/>
                    <span class="font-centuryGothic font-bold text-left">{{ translator('test.done.label') }}</span>
                </div>
                <div v-else class="flex gap-1 items-center text-pakYellow text-2xl">
                    <Information :size="25" class=""/>
                    <span class="font-centuryGothic font-bold text-left">{{ translator('test.inprogress.label') }}</span>
                </div>
                <div class="flex flex-row gap-2 font-centuryGothic text-2xl items-center justify-center">
                    <div class="flex gap-1 items-center text-grayTone1">
                        <ClipboardCheckOutline :size="25" class=""/>
                        <span class="font-bold text-left">{{ translator('test.successAt.label') }}</span>
                    </div>
                    <div v-if="successAt===0" role="status" class="animate-pulse">
                        <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                    </div>
                    <span v-else class="text-pakYellow text-left">{{ successAt }}</span>
                </div>
            </div>

            <div v-if="questionnaires.length===0" class="w-full flex flex-col gap-4 p-2">
                <QuestionCardSkeleton type="TRUEORFALSE"/>
                <QuestionCardSkeleton type="TRUEORFALSE"/>
                <QuestionCardSkeleton type="MULTI"/>
                <QuestionCardSkeleton type="MULTI"/>
                <QuestionCardSkeleton type="MULTI"/>
            </div>
            <div class="w-full flex flex-col gap-4 p-2">
                <QuestionCard
                    v-for="(question, index) in questionnaires"
                    :key="index"
                    :questionIndex="index"
                    :type="question.type"
                    :data="question"
                    :evaluationState="evaluationState"
                    @registerQuestion="()=>{registerQuestion(index)}"
                />
            </div>

            <div class="w-full flex flex-col gap-8 px-2 justify-center items-start">
                <div v-if="!isDone" class="w-full flex gap-4">
                    <input type="checkbox" name="" id="" class="w-6 h-6 rounded" v-model="checkedVerify">
                    <span class="font-centuryGothic font-bold text-xl text-black text-center">{{ translator('test.questionCheck.label') }}</span>
                    <span v-if="errorFlag" class="font-centuryGothic font-bold text-xl text-[#F54646] text-center">{{ translator('test.checkError.label') }}</span>
                </div>
                <div class="w-full flex gap-4">
                    <v-btn
                        class="flex items-center justify-center !rounded-lg border !border-[#F54646] !bg-white text-[#F54646] p-4 hover:!bg-[#F54646] hover:!text-white"
                        :loading="cancelLoading"
                        size="large"
                        type="submit"
                        variant="flat"
                        @click="cancelTrial"
                    >
                        <span class="flex font-bold font-myriadBold text-xl">{{ translator('exit.btn') }}</span>
                    </v-btn>
                    <v-btn
                        class="flex flex-row !p-4 !rounded-lg !bg-pakDarkBlue text-white items-center justify-center"
                        v-if="!isDone"
                        :disabled="!checkedVerify"
                        :loading="submitLoading"
                        size="large"
                        type="submit"
                        variant="flat"
                        @click="submitAnswers"
                    >
                        <span class="flex font-bold font-myriadBold text-xl">{{ translator('submit.evaluation.btn') }}</span>
                        <Upload :size="24" class="ml-2"/>
                    </v-btn>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import Information from 'vue-material-design-icons/Information.vue'
    import Upload from 'vue-material-design-icons/Upload.vue'
    import ClipboardCheckOutline from 'vue-material-design-icons/ClipboardCheckOutline.vue'
    import QuestionCard from '@/components/v2.0/Training/QuestionCard.vue'
    import QuestionCardSkeleton from '@/components/v2.0/UIElements/QuestionCardSkeleton.vue'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"TrainingQuizPage",
        components:{
            Information,
            ClipboardCheckOutline,
            QuestionCard,
            Upload,
            QuestionCardSkeleton,
        },
        data(){
            return{
                testId: this.$route.params.testId,

                checkedVerify:false,
                errorFlag:false,

                cancelLoading:false,
                submitLoading: false,

                // Integrated Data
                evaluation: null,
                questionCheckArray: [],
                translateHeaders: null,
            }
        },
        methods:{
            registerQuestion(index:number){ // Register Questions by storing their indices in the Array for Check 
                if(!this.questionCheckArray.includes(index)){
                    this.questionCheckArray.push(index)
                }
            },
            cancelTrial(){
                this.cancelLoading = true
                setTimeout(() => {
                    this.cancelLoading = false
                    this.$router.go(-1);
                }, 1000);
            },
            submitAnswers(){
                this.submitLoading = true
                setTimeout(async () => {
                    if(this.questionCheckArray.length === this.questionnaires.length){ // Check if all the Questions were registered
                        try{
                            await axiosService.markTestAsDone(this.evaluation.pk)
                            this.$router.go(-1);
                        }catch(error){
                            console.log(error)
                        }
                    }else{
                        this.errorFlag = true
                    }
                    this.submitLoading = false
                }, 1000);
            },
            async getEvaluation() {
                try{
                    this.evaluation = await axiosService.testResult(this.testId)
                }catch(error){
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
            this.translateHeaders = await axiosService.translate(['training.label', 'summary.evaluation.title', 'test.inprogress.label', 'test.done.label', 'test.successAt.label', 'test.questionCheck.label', 'test.checkError.label', 'exit.btn', 'submit.evaluation.btn']);
            this.emitter.on("authorization-error", ()=>{this.$router.push({name: 'LoginPage'})});
        },
        async mounted(){
            await this.getEvaluation()
        },
        computed:{
            trainingName(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                const training = this.evaluation != null ? this.evaluation.training : null;
                const temp = training !=null ? training.name[lang] : null;
                return temp != null? temp.value: ""
            },
            testName(){ 
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                const activity  = this.evaluation != null ? this.evaluation.activity : null;
                const temp = activity !=null ? activity.name[lang] : null;
                return temp != null? temp.value: ""
            },
            testIndex(){  
                const activity  = this.evaluation != null ? this.evaluation.activity : null;
                return activity != null? activity.sequence:1
            },
            evaluationState(){
                return (this.evaluation != null && this.evaluation.state != null) ? this.evaluation.state.code: null
            },
            isDone(){ 
                const state = this.evaluation != null ? this.evaluation.state: null
                return state != null ? state.code === 'DONE' : false
            },
            successAt(){  
                return (this.evaluation != null && this.evaluation.activity != null)? this.evaluation.activity.successAt:0
            },
            questionnaires() { 
                return this.evaluation != null ? this.evaluation.response : [];
            }
        }
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;

    ::-webkit-scrollbar {
        width: 15px;
    }

    /* Track */
    ::-webkit-scrollbar-track {
        @apply bg-grayToneStroke rounded-xl;
    }
    
    /* Handle */
    ::-webkit-scrollbar-thumb {
        @apply bg-pakDarkBlue rounded-xl;
    }

    /* Handle on hover */
    ::-webkit-scrollbar-thumb:hover {
        @apply bg-pakBlue
    }
</style>