<template>
 <div>
    <overlay :overlay="overlay"></overlay>
    <alert-component v-if="error"
        :alertType="errorType"
        :alertMsg="errorMsg"></alert-component>
    <test-result :test="item"></test-result>
  <v-card variant="plain"
           class="mx-auto">
      <v-card-item>
          <div>
            <div class="text-overline mb-1">
              {{ activityName }}
            </div>
          </div>
        </v-card-item>
        <v-card-item>
            <v-window
                v-model="window"
                show-arrows
            >
                <v-window-item
                v-for="n in length"
                :key="n"
                >
                <v-card  class="max-auto">
                    <multi-question v-if="questionnaires[n-1].type=='MULTI'"
                       :item="questionnaires[n-1]"
                       :tries="nbreOfattemps"
                       :test="item"
                       @result-event="onResultEvent"
                       @error-event="errorHandler"></multi-question>
                    <true-false-question v-if="questionnaires[n-1].type=='TRUEORFALSE'"
                       :item="questionnaires[n-1]"
                       :test="item"
                       :tries="nbreOfattemps"
                       @result-event="onResultEvent"
                       @error-event="errorHandler"></true-false-question>
                    <open-question v-if="questionnaires[n-1].type=='OPEN'"
                       :item="questionnaires[n-1]"
                       :tries="nbreOfattemps"
                       @error-event="errorHandler">
                    </open-question>
                </v-card>
                </v-window-item>
            </v-window>
        </v-card-item>
        <v-card-actions v-if="window == (length-1)"
           style="display: flex;flex-flow:row nowrap;justify-content: flex-end;">
            <v-btn v-if="!isTestDone"
                variant="text"
                color="teal-accent-4"
                @click="maskTestAsDone()"
            >
                {{ translater('activity.close.btn') }}
            </v-btn>
        </v-card-actions>
  </v-card>
</div>
</template>
<script lang="ts">
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';
import store from '@/store';

const axiosService = new AxiosService();

@Options({
    components: {
        TrueFalseQuestion: defineAsyncComponent(() => import('@/components/TrueFalseQuestionComponent.vue')),
        MultiQuestion: defineAsyncComponent(() => import('@/components/MultiQuestionComponent.vue')),
        OpenQuestion: defineAsyncComponent(() => import('@/components/OpenQuestionComponent.vue')),
        Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
        AlertComponent: defineAsyncComponent(() => import('@/components/AlertComponent.vue')),
        TestResult: defineAsyncComponent(() => import('@/components/TestResultComponent.vue')),
    }, data: function() {
        return {
            window: 0,
            item: null,
            translate: null,
            overlay: false,
            error: false,
            errorMsg: null,
            errorType: null,
        }
    }, methods: {
       async loadTest() {
          try {
            const activity = this.$route.params.activity;
            this.item = await axiosService.testResult(activity);
          } catch (error) {
            this.error = true ;
          }
       }, translater(key: string) {
          if (this.translate != null) {
            return this.translate[key];
          }
          return key;
        }, onResultEvent(result : any) {
            this.overlay = true;
            let questionnaires = this.item.response;
            let i = questionnaires.findIndex((x:any) => x.pk==result.pk);
            questionnaires.splice(i, 1, result);
            this.overlay = false;
        }, async maskTestAsDone() {
            this.overlay = true;
            try {
                this.item = await axiosService.markTestAsDone(this.item.pk);
            } catch (error) {
                this.error = true ;
                this.errorType = "error";
                this.errorMsg = this.translater("TestComponent.markTestAsDone.undoTestExists.error");
                console.log(' :::::::::::::::::: '+this.errorMsg)
            }
            this.overlay = false;
        }, errorHandler() {
            this.error = true ;
        }
    }, computed: {
        length() {
            return this.item != null && this.item.response != null ? this.item.response.length : [];
        }, activityName() {
            const lang = store.getters.getCurrentSession.language ;
            const activity = this.item != null && this.item.activity != null ? this.item.activity : null;
            let name = activity != null ? activity.name[lang] : null;
            return  name != null ? name.value : null;
        }, lang() {
            return store.getters.getCurrentSession.language ;
        }, questionnaires() {
            return this.item != null ? this.item.response : [];
        }, nbreOfattemps() {
            const activity = this.item != null ? this.item.activity : null ;
            return activity != null && activity.nbreOfattemps != null ? activity.nbreOfattemps : 1 ;
        }, isTestDone() {
            const state = this.item != null ? this.item.state : null ;
            return (state != null && state.code =='DONE')
        }
    }, async mounted() {
        this.overlay = true;
        this.translate = await axiosService.translate(['activity.close.btn', 'TestComponent.markTestAsDone.undoTestExists.error']);
        await this.loadTest();
        this.overlay = false;
    }
})
export default class TestComponent extends Vue {

}
</script>
<style scoped>
</style>