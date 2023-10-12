<template>
    <v-card
    class="mx-auto"
  >
     <overlay :overlay="overlay"></overlay>
     <template v-slot:title>
      <span class="text-h6 font-weight-regular text-stype">{{ activityName }}</span>
      </template>

      <template v-slot:subtitle>
        {{ courseTitle }}
      </template>
      <v-window v-model="step">
      <v-window-item v-for="summary in summaries" :key="summary.sequence" :value="summary.sequence">
        <v-card-item v-if="isCurrent(item, summary.sequence)">
           <course v-if="isCourse"
              :item="item"></course>
           <lecon-question v-else
              :item="item"></lecon-question>
        </v-card-item>
        <v-card-item v-if="course != null && course != ''">
          <v-textarea
             variant="plain"
             auto-grow
             readOnly="true"
             :model-value="course"
          ></v-textarea>
        </v-card-item>
      </v-window-item>
    </v-window>

    <v-divider></v-divider>

    <v-card-actions>
      <v-btn
        v-if="step > 1"
        variant="text"
        color="secondary"
        @click="previousStep()"
      >
        {{ t('Step.previous.name') }}
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
        v-if="hasNext"
        color="teal-accent-4"
        variant="text"
        @click="nextStep()"
      >
        {{ t('Step.validation.name') }}
      </v-btn>
      <v-btn 
        v-if="isLast"
        color="teal-accent-4"
        variant="text"
        @click="closeActivity()"
      >
       {{ t('Step.closure.name') }}
      </v-btn>
    </v-card-actions>
  </v-card>
</template>
<script lang="ts">
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';
import store from '@/store';
import AxiosService from '@/services/axiosService';


const axiosService = new AxiosService();

@Options({
   components: {
      Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
      Course : defineAsyncComponent(() => import('@/components/CourseComponent.vue')),
      LeconQuestion: defineAsyncComponent(() => import('@/components/LeconQuestion.vue')),
   }, data: function() {
      return {
        step: 1,
        item : null,
        translate: null,
        overlay: false,
      }
   }, methods: {
       async loadData() {
         try {
            const activity = this.$route.params.activity;
            this.item = await axiosService.leconResult(activity);
            this.step = this.item.currentIndex.sequence;
          } catch (error) {
            this.error = true ;
          }
        }, t(key: string) {
            if (this.translate != null) {
               return this.translate[key];
            }
            return key;
        }, isCurrent(item: any, sequence: number) {
            const entry = item != null ? item.currentIndex : null;
            return entry != null && entry.sequence == sequence ;
        },async nextStep() {
           this.overlay = true ;
           this.item = await axiosService.nextStepCourse(this.item.pk);
           this.step = this.item.currentIndex.sequence;
           this.overlay = false;
        } ,async previousStep() {
           this.overlay = true ;
           this.item = await axiosService.previousStepCourse(this.item.pk);
           this.step = this.item.currentIndex.sequence;
           this.overlay = false;
        } , async closeActivity() {
            this.overlay = true ;
            this.item = await axiosService.closeLeconActivity(this.item.pk);
            this.step = this.item.currentIndex.sequence;
            this.overlay = false;
        } 
   }, computed: {
      isLast() {
        if (this.summaries != null) {
          return this.step == this.summaries.length && this.item.state.code != 'DONE';
        }
        return false ;
      }, hasNext() {
        if (this.summaries != null) {
          return this.step < this.summaries.length ;
        }
        return false ;
      }, activityName() {
            const lang = store.getters.getCurrentSession.language ;
            const activity = this.item != null && this.item.activity != null ? this.item.activity : null;
            let name = activity != null ? activity.name[lang] : null;
            return  name != null ? name.value : null;
      }, currentIndex() {
         return this.item != null ? this.item.currentIndex : null ;
      }, isCourse() {
           const summaryEntry = this.item != null ? this.item.currentIndex : null ;
           
           if (summaryEntry != null && summaryEntry.index != null) {
              return summaryEntry.index.nature == 'COURSE';
           }
           return false ;
        }, course() {
            const summaryEntry = this.item != null ? this.item.currentIndex : null ;
            const lang = store.getters.getCurrentSession.language ;
            if (summaryEntry != null && summaryEntry.index != null) {
                const content = summaryEntry.index.content ;

                if (content != null) {
                    return content[lang] != null ? content[lang].value : null ;
                }
            }
        }, isTest() {
            const summaryEntry = this.item != null ? this.item.currentIndex : null ;
            
            if (summaryEntry != null && summaryEntry.index != null) {
                return summaryEntry.index.nature == 'TEST';
            }
            return false ;
        }, courseTitle() {
            const lang = store.getters.getCurrentSession.language ;
            let summaryEntry = this.item != null ? this.item.currentIndex : null ;
            if (summaryEntry != null && summaryEntry.index != null) {
              return summaryEntry.index.name != null && summaryEntry.index.name[lang] != null ? summaryEntry.index.name[lang].value : null ;
            }
        }, summaries() {
          const activity = this.item != null && this.item.activity != null ? this.item.activity : null;
          return activity != null ? activity.summary : [];
        }
   }, async mounted() {
      this.overlay = true ;
      await this.loadData();
      this.overlay = false;
   },async created() {
       this.translate = await axiosService.translate(['Step.validation.name', 'Step.previous.name', 'Step.closure.name']);
   }
})
export default class LeconComponent extends Vue {

}
</script>
<style scoped>
.text-stype {
   opacity: 0.8;
}
</style>