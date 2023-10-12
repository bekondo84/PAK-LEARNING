<template>
     <v-card style="margin: 20px 20px 5px 20px;text-align: justify;"
    class="mx-auto"
    width="900"
    :title="title"
    
  >
  <v-table>
    <thead>
      <tr>
        <th class="text-left">
          {{ header('Training.code') }}
        </th>
        <th class="text-left">
          {{ header('Theme.code') }}
        </th>
        <th class="text-left">
          {{ header('Involve.registerDate') }}
        </th>
        <th class="text-left">
          {{ header('Training.progress') }}
        </th>
        <th></th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr class="table-row"
        v-for="item in items"
        :key="item.code"
        @dblclick="viewDetails(item)"
      >
        <td>{{ getTrainingName(item) }}</td>
        <td>{{ getTrainingTheme(item) }}</td>
        <td>{{ getRegisterData(item) }}</td>
        <td>
          <v-progress-linear
              v-model="item.training.skill"
              color="blue-grey"
              height="25"
            >
            <template v-slot:default="{ value }">
              <strong>{{ Math.ceil(value) }}%</strong>
            </template>
          </v-progress-linear>
        </td>
        <td>
          <v-btn
            variant="text"
            color="success"
            icon="mdi-calendar"
            size="x-small"
            @click="gotoActivitiesForm(item)">
          </v-btn>
        </td>
        <td>
          <v-btn v-if="certificate(item)"
            variant="text"
            icon="mdi-certificate"
            size="small" 
            :disabled="item.decision=='FAILED'">

          </v-btn>
        </td>
        <td >
          <v-btn v-if="registrable(item)"
            variant="text"
            color="error"
            icon="mdi-pencil"
            size="x-small"
            :disabled="item.registrable"
            @click="unRegiterAction(item)">
          </v-btn>
        </td>
      </tr>
    </tbody>
  </v-table>
  </v-card>  
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';
import { defineAsyncComponent } from 'vue';
import UrlUtils from '@/services/urlUtils';

const axiosService = new AxiosService();

@Options({
    props: {
       title: String,
       items: Array,
    }, components: {
      ActivityComponent: defineAsyncComponent(() => import('@/components/ActivityComponent.vue')),
    }, data: function() {
        return {
        skill: 20,
        headers: null,       
       }
     }, methods: {
        header(key: string) {
           if (this.headers != null) {
              return this.headers[key];
           }
           return key ;
        }, viewDetails(item: any) {
            this.$router.push({name: 'training', params: {op: item.training.code}});
        }, getTrainingCode(item : any) {
          return item.training != null ? item.training.code : null ;
        }, getTrainingName(item : any) {
          return item.training != null ? item.training.name : null ;
        }, getTrainingTheme(item: any) {
          return item.training != null && item.training.theme != null ? item.training.theme.code: null;
        }, getRegisterData(item: any) {
            return item.registerDate;
        }, getProgression(item: any){
               return item.progress ;
        },async unRegiterAction(item: any) {
          try{
            await axiosService.unregisterForTraining(item.pk, '');
            this.emitter.emit(UrlUtils.HOMECMS_REFRESH_EVENT);
            this.$router.push({name:"mylearining"})
          } catch(error) {
             this.$emit('error-event');
          }
        }, getActivities(item: any) {
          const training = item.training;
          return training != null ? training.activities : [];
        }, gotoActivitiesForm(item: any) {
           this.$router.push({name: 'activity', params: { op:item.training.code}});
        }, skill(item: any) {
            return item.skill;
        }, registrable(item: any) {
            if (item.training.state == 'CLOSE') {
              return false ;
            } 

            return true;
        }, certificate(item: any) {
          return item.decision == 'FAILED' || item.decision == 'SUCCESSED';
        } 
     }, computed: {
        
     },async mounted() {
        this.headers = await axiosService.translate(["Training.code","Training.name", "Theme.code", "Involve.registerDate", "Training.progress", "Training.certificate"])
     }
    
})
export default class TrainingListComponent extends Vue {

}
</script>
<style scoped>
.table-row {
   cursor: pointer;
}
</style>