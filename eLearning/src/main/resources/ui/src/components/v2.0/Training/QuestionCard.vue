<template>
    <div class="w-full rounded-2xl border !border-grayToneStroke p-4 flex flex-row justify-center items-start">
        <div class="w-full h-full flex flex-col gap-4 pr-2 justify-center items-start">
            <span class="w-full font-centuryGothic font-bold text-2xl text-left">Q{{ questionIndex+1 }}. {{ questionText }}</span>
            <div class="w-full flex flex-col gap-2.5 p-2.5 justify-start items-start">
                
                <template v-if="type === 'TRUEORFALSE'">
                    <div class="w-full flex flex-row gap-6 items-center">
                        <input type="radio" name="" id="" class="w-5 h-5 rounded-full form-radio" value="TRUE" v-model="userAnswer">
                        <span class="w-full font-centuryGothic text-xl text-left" :class="showCorrectAnswer && evaluateAnwser('TRUE')?'text-[#34A853]':''">{{ translator('true.label') }}</span>
                    </div>
                    <div class="w-full flex flex-row gap-6 items-center">
                        <input type="radio" name="" id="" class="w-5 h-5 rounded-full form-radio" value="FALSE" v-model="userAnswer">
                        <span class="w-full font-centuryGothic text-xl text-left" :class="showCorrectAnswer && evaluateAnwser('FALSE')?'text-[#34A853]':''">{{ translator('false.label') }}</span>
                    </div>
                </template>

                <template v-if="type === 'MULTI'">
                    <div v-for="(option,index) in questionOptions" :key="index" class="w-full flex flex-row gap-6 items-center">
                        <input type="radio" name="" id="" class="w-5 h-5 rounded-full form-radio" :value="option.pk" v-model="userAnswer">
                        <span class="w-full font-centuryGothic text-xl text-left" :class="showCorrectAnswer && evaluateAnwser(option)?'text-[#34A853]':''">{{ option.label }}</span>
                    </div>
                </template>

                <div v-if="!showCorrectAnswer" class="w-full flex flex-row gap-4 items-center">
                    <v-btn
                        class="flex flex-row !p-4 !rounded-lg text-white items-center justify-center"
                        :class="answerFlag?'!bg-[#34A853]':'!bg-pakBlue'"
                        :loading="submitLoading"
                        :disabled="!isAnswered || !isEvaluable"
                        size="large"
                        type="submit"
                        variant="flat"
                        @click="validateAnswer"
                    >
                        <span class="flex font-bold font-myriadBold text-xl">{{ translator('validate.btn') }}</span>
                        <Upload :size="24" class="ml-2"/>
                    </v-btn>
                    <span v-if="!isEvaluable" class="w-full font-centuryGothic font-bold text-left text-xl text-pakYellow">{{ translator('trial.exceeded.msg') }}</span>
                </div>

            </div>
        </div>
        <div class="w-fit flex">
            <span class="w-[150px] font-centuryGothic font-bold text-xl text-right" :class="!isAnswered?'text-black':(actualMark===0?'text-[#EA4335]':'text-[#34A853]')">{{ actualMark!=null?actualMark:0 }} / {{ questionMark }} point{{ questionMark>1?'s':'' }}</span>
        </div>
    </div>
</template>

<script lang="ts">
    import store from '@/store';
    import AxiosService from '@/services/axiosService';
    
    const axiosService = new AxiosService();
    export default {
        name:"QuestionCard",
        components:{

        },
        props:{
            data: Object,
            questionIndex: {
                type: Number,
                default: 0
            },
            type: {
                type: String,
                validator(value:string){
                    return ['TRUEORFALSE', 'MULTI'].includes(value)
                }
            },
            evaluationState:String,
            trialNumber:{
                type: Number,
                default: 3
            }
        },
        emits:['registerQuestion'],
        data(){
            return{
                userAnswer: null,
                submitLoading:false,
                trial:3,
                translateHeaders: null,
                answerFlag:false,
            }
        },
        methods:{
            registerQuestion(){
                this.$emit('registerQuestion')
            },
            async validateAnswer(){ // After Pressing the 'Confirm' Button
                if(this.userAnswer !== ""){
                    this.submitLoading = true
                    if(this.type === 'MULTI'){
                        try {
                            const response = await axiosService.evaluateMulti(""+this.userAnswer, this.data.pk);
                            // console.log(response)
                            this.registerQuestion()
                            this.answerFlag = true
                        } catch (error) {
                            console.log(error)
                        }
                    }else if(this.type === 'TRUEORFALSE'){
                        try {
                            const response = await axiosService.evaluateTrueOrFalse(this.userAnswer, this.data.pk);
                            // console.log(response)
                            this.registerQuestion()
                            this.answerFlag = true
                        } catch (error) {
                            console.log(error)
                        }
                    }
                    this.submitLoading = false
                }
            },
            evaluateAnwser(answer: any) {
                if(this.type === 'MULTI'){
                    return answer.mark === 100 ;
                }
                else if(this.type === 'TRUEORFALSE'){
                    const state = this.evaluationState != null? this.evaluationState : null;
                    const correctAnwser = (this.data != null && this.data.question != null && this.data.question.answer != null) ? this.data.question.answer.code: null;
                    const result = (state === 'DONE' && answer == correctAnwser);
                    return result ;
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
            const answer = this.data != null ? this.data.answer : null;
            if (answer != null) { // We set the user Answer to the previous Answer
                if(this.type === 'MULTI'){
                    this.userAnswer = answer.pk ;
                } else if(this.type === 'TRUEORFALSE'){
                    this.userAnswer = answer.code;
                }
            }
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['true.label', 'false.label', 'validate.btn', 'trial.exceeded.msg']);
        },
        computed:{
            isAnswered(){
                if(this.userAnswer != null){
                    this.registerQuestion()
                    return true
                }else{
                    return false
                }
            },
            question() {
                return this.data != null ? this.data.question : null ;
            },
            questionText() {
                const lang = store.getters.getCurrentSession.language; 
                const text = this.question != null ? this.question.text : null ;
                return text != null && text[lang] != null ?  text[lang].value : "" ;
            },
            actualMark(){
                return (this.data != null && this.data.mark != null) ? this.data.mark: null
            },
            questionMark(){
                return this.question != null ? this.question.mark: 0
            },
            questionOptions(){
                const lang = store.getters.getCurrentSession.language; 
                const optionArray = this.data != null && this.question != null ? this.question.question : []
                
                return optionArray.length>0? optionArray.map((option:any)=>{
                    const text = option != null ? option.text : null ;
                    return {
                        label: text != null && text[lang] != null ?  text[lang].value : "",
                        pk: option.pk,
                        mark: option.mark
                    } ;
                }) : []
            },
            showCorrectAnswer() {
                const state = this.evaluationState != null ? this.evaluationState: null;
                return state === 'DONE';
            },
            isEvaluable() {
                const bool = (this.trialNumber > this.data.tries) && this.data.isOpen
                if(!bool && !this.showCorrectAnswer){ // We register the question if it is not Open and if the quiz is not over, to mark the question as 'checked'
                    this.registerQuestion()
                }
                return bool;
            },
        }
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
</style>