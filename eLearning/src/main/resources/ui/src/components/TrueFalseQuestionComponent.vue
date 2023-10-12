<template>
  <div>
    <overlay :overlay="overlay"></overlay>
  <v-card style="margin-top: 10px;"
    class="mx-auto"
    max-width="700"
    variant="elevated"
    color="#E3F2FD"
  >
    <v-card-item>
      <div class="truefalse">
        <div class="mb-1">
          {{ questionText }}
        </div>
        <div>
            <v-radio-group v-model="radios">
            <template v-slot:label>
                <div><span>{{ translater('answer.selection.message') }}</span><span>{{ markMessage }}</span></div>
            </template>
            <v-radio value="TRUE">
                <template v-slot:label>
                <div><strong class="text-primary">{{ translater('true.label.name') }}</strong></div>
                 <mark-question v-if="showCorrectAnswer" :state="evaluateAnswer('TRUE')"></mark-question>
                </template>
            </v-radio>
            <v-radio value="FALSE">
                <template v-slot:label>
                <div><strong class="text-primary">{{ translater('false.label.name') }}</strong></div>
                <mark-question v-if="showCorrectAnswer" :state="evaluateAnswer('FALSE')"></mark-question>
                </template>
            </v-radio>
            </v-radio-group>
        </div>
      </div>
    </v-card-item>

    <v-card-actions>
      <v-btn variant="flat" 
             color="#E0E0E0"
             :disabled="!isEvaluable"
             @click="evaluate()">
        {{ translater('validate.btn.name') }}
      </v-btn>
    </v-card-actions>
  </v-card>
</div>
</template>
<script lang="ts">
import store from '@/store';
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';
import { defineAsyncComponent } from 'vue';

const axiosService = new AxiosService();

@Options({
    props: {
      item : Object ,
      tries: Number,
      test : Object,
    }, components: {
      Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
      MarkQuestion: defineAsyncComponent(() => import('@/components/MarkQuestionComponent.vue')),
    }, data: function() {
        return {
            radios: null,
            translate: null,
            overlay: false,
        }
    }, methods: {
        translater(key: string) {
          if (this.translate != null) {
            return this.translate[key];
          }
          return key;
        }, answerItem(item: any) {
          let lang = store.getters.getCurrentSession.language; 
           const text = item.text ;
           return text != null && text[lang] != null ? text[lang].value : null ;
        },async evaluate() {
          
          if (this.radios == null) {
                alert("Veuillez selectionner un resultat");
          } else {
              this.overlay = true ;
              try {
                const response = await axiosService.evaluateTrueOrFalse(this.radios, this.item.pk);
                this.$emit('result-event', response);
              } catch (error) {
                 this.$emit("error-event");
              }
              this.overlay = false ;
              
          }
        }, evaluateAnswer(answer: string) {
           const state = this.test != null && this.test.state != null ? this.test.state.code: null;
           const correctAnwser = this.item != null && this.item.question != null && this.item.question.answer != null ? this.item.question.answer.code: null;
           const result = state == 'DONE' && answer == correctAnwser;
           return result ;
       }
    }, computed: {
       question() {
          return this.item != null ? this.item.question : null ;
       }, questionText() {
          let lang = store.getters.getCurrentSession.language; 
          const text = this.question != null ? this.question.text : null ;
          return text != null && text[lang] != null ?  text[lang].value : null ;
       }, isEvaluable() {
           return (this.tries > this.item.tries) && (this.item.isOpen == true);
       }, showCorrectAnswer() {
           const state = this.test != null && this.test.state != null ? this.test.state.code: null;
           return state == 'DONE';
       }, markMessage() {
          if (this.item != null && this.item.question != null && this.item.question.mark != null) {
              return " ("+this.item.question.mark+" pts)" ;
          }
       }
    },async mounted() {
      this.overlay = true ;
       const answer = this.item != null ? this.item.answer : null ;
       if (answer != null) {
           this.radios = answer.code ;
       }
       this.translate = await axiosService.translate(['answer.selection.message', 'validate.btn.name', 'true.label.name', 'false.label.name', 'Resource.description', 'exit.btn.label', 'Activity.state']);
       this.overlay = false ;
    }
})

export default class TrueFalseQuestionComponent extends Vue {

}
</script>
<style scoped>
.truefalse {
     display: flex;
     flex-flow: column nowrap;
     align-items: flex-start    ;
}
</style>