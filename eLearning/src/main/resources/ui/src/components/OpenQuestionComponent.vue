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
        <div class="mb-1" style="width: 100%;">
          <v-textarea style="width: 100%;"
            name="input-7-1"
            variant="plain"
            auto-grow
            readonly="true"
            :model-value="questionText"
          ></v-textarea>
        </div>
        <div  style="width: 100%;">
          <v-textarea style="width: 100%;"
            name="input-7-2"
            variant="filled"
            :label="translater('answer.enter.message')"
            auto-grow
            v-model="item.answer"
          ></v-textarea>
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
    }, components: {
      Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
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
               this.overlay = true ;
               try {
                  const response = await axiosService.evaluateOpenQuestion(this.item);
                  this.$emit('result-event', response);
               } catch (error) {
                 this.$emit("error-event");
               }
              this.overlay = false ; 
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

export default class OpenQuestionComponent extends Vue {

}
</script>
<style scoped>
.truefalse {
     display: flex;
     flex-flow: column nowrap;
     align-items: flex-start    ;
}
</style>