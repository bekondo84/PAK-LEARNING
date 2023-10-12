<template>
<v-card
    class="mx-auto"
  >
    <v-card-text>
      <div class="text-h5 text--primary">{{ name }}</div>
      <div class="text--primary">
        {{ theme }}
      </div>
      <div>
        {{ resume }}
      </div>
    </v-card-text>
    <v-card-text>
      <div v-html="description"></div>
    </v-card-text>
    <v-expansion-panels variant="popout" class="my-4">
        <div class="text-h6 text--primary">{{ t('myLearning.training.organization') }}</div>
        <v-expansion-panel v-for="act in activities" :key="act.code"
          :title="act.name">
          <v-expansion-panel-text>
            <activity-component  
              :item="act"
              :training="item"></activity-component>   
          </v-expansion-panel-text>          
        </v-expansion-panel>
    </v-expansion-panels>   
  </v-card>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';
import { defineAsyncComponent } from 'vue';

const axiosService = new AxiosService();

@Options({
    components: {
        ActivityComponent: defineAsyncComponent(() => import('@/components/ActivityComponent.vue')),
    }, data: function() {
        return {
           item: null,
           translate: null,
        }
    }, methods: {
        async getTraining() {
            const trainingCode = this.$route.params.op ;
            this.item = await axiosService.getTraining(trainingCode);
        }, t(key: string) {
            if (this.translate != null) {
                return this.translate[key];
            }
            return key ;
        }
    }, computed: {
        name() {
            return this.item != null ? this.item.name : null ;
        }, theme() {
            const elt = this.item != null ? this.item.theme : null ;
            return elt != null ? elt.name : null ;
        }, resume() {
            return this.item != null ? this.item.resume : null;
        }, description() {
            return this.item != null ? this.item.description : null;
        }, activities() {
            return this.item != null ? this.item.activities : [] ;
        }
    },async mounted() {
          await this.getTraining();
          this.translate = await axiosService.translate(['myLearning.training.organization']);
    }
})

export default class TrainingFormComponent extends Vue {

}
</script>
<style scoped>
</style>