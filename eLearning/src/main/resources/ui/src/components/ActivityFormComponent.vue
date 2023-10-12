<template>
   <div>
    <overlay :overlay="overlay"></overlay>
    <alert-component v-if="error"></alert-component>
     <v-card style="margin: 20px 20px 5px 20px;text-align: justify;"
        class="mx-auto"
        width="900">
        <v-card
          class="mx-auto"
        >
        <v-card-item>
          <div>
            <div class="text-overline mb-1">
              {{ trainingName }}
            </div>
            <div class="text-caption">{{ trainingResume }}</div>
          </div>
        </v-card-item>
      </v-card>
      <v-expansion-panels variant="popout" class="my-4">
        <v-expansion-panel v-for="act in activities" :key="act.code"
          :title="act.name">
          <v-expansion-panel-text>
            <activity-component  
              :item="act"
              :training="item"
               startable="true"></activity-component>   
          </v-expansion-panel-text>          
        </v-expansion-panel>
    </v-expansion-panels>      
    </v-card>
  </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';
import { defineAsyncComponent } from 'vue';

const axiosService = new AxiosService();

@Options({
    props: {
      
    }, components: {
       ActivityComponent: defineAsyncComponent(() => import('@/components/ActivityComponent.vue')),
       Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
       AlertComponent: defineAsyncComponent(() => import('@/components/AlertComponent.vue')),
    }, data: function() {
        return {
            item: null,
            overlay : false,
            error: false,
        }
    }, methods: {
        async getTraining(id: any) {
            try {
              this.item = await axiosService.getTraining(id);
            } catch (error) {
                this.error = true ;
            }
        } 
    }, computed: {
        trainingName() {
          return this.item != null ? this.item.name : "";
        }, trainingResume() {
           return this.item != null ? this.item.resume: "";
        }, activities() {
          return this.item != null ? this.item.activities : [];
        }
    },async mounted() {
        const trainingId = this.$route.params.op;
        this.overlay = true ;
        if (trainingId != null) {
            await this.getTraining(trainingId);
        }
        this.overlay = false ;
    }
  })

export default class ActivityFormComponent extends Vue {

}
</script>
<style scoped>
</style>