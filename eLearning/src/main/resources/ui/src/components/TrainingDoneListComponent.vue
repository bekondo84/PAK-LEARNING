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
          {{ header('Training.name') }}
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
        <th class="text-left">
          {{ header('Training.certificate') }}
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="item in items"
        :key="item.code"
      >
        <td>{{ getTrainingCode(item) }}</td>
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
          <v-icon icon="mdi-certificate"></v-icon>
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

const axiosService = new AxiosService();

@Options({
    props: {
       title: String,
       items: Array,
    }, components: {
     
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
        }, getTrainingCode(item : any) {
          return item.training != null ? item.training.code : null ;
        }, getTrainingName(item : any) {
          return item.training != null ? item.training.name : null ;
        }, getTrainingTheme(item: any) {
          return item.training != null && item.training.theme != null ? item.training.theme.code: null;
        }, getRegisterData(item: any) {
            return item.registerDate;
        }, unRegiterAction(item: any) {
            console.log('Uregister to training')
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
</style>