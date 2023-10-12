<template>
   <div class="lecon-question">
      <test-result :test="item"></test-result>
      <v-card  class="max-auto question-card"
         variant="plain"
         v-for="answer in questionnaires" :key="answer.pk">
        <multi-question v-if="answer.type=='MULTI'"
            :item="answer"
            tries="3"
            :test="item"
            @result-event="onResultEvent"
            @error-event="errorHandler"></multi-question>
        <true-false-question v-if="answer.type=='TRUEORFALSE'"
            :item="answer"
            :test="item"
            tries="3"
            @result-event="onResultEvent"
            @error-event="errorHandler"></true-false-question>
    </v-card>
   </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { defineAsyncComponent } from 'vue';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props: {
       item: Object,
    }, components: {
        TestResult: defineAsyncComponent(() => import('@/components/TestResultComponent.vue')),
        TrueFalseQuestion: defineAsyncComponent(() => import('@/components/TrueFalseQuestionComponent.vue')),
        MultiQuestion: defineAsyncComponent(() => import('@/components/MultiQuestionComponent.vue')),
    }, data: function() {
        return {
           evaluation: null,
        }
    }, methods: {
      async  getEvaluation() {
           if (this.item != null && this.item.currentIndex != null) {
                const question = this.item.currentIndex.index;
                this.evaluation = await axiosService.getEvaluation(question.pk, this.item.pk);
           }
       }
    }, computed : {
        questionnaires() {
            return this.evaluation != null ? this.evaluation.response : [];
        }
    },async mounted() {
        await this.getEvaluation();
    }
})

export default class LeconQuestion extends Vue {

}
</script>
<style scoped>
 .lecon-question {
    display: flex;
    flex-flow: column;
 }
 
 .question-card {
    margin-top: 10px;
 }
</style>