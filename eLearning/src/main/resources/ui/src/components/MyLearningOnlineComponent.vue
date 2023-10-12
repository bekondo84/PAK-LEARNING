<template>
   <div>
      <overlay :overlay="overlay"></overlay>
      <alert-component v-if="error"></alert-component>
      <v-card style="margin: 2px 20px 5px 20px;font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;opacity: 0.6;"
         variant="plain"
         class="mx-auto"
         :title="translater('myLearning.online.training')">
         <v-card-item style="display: flex; flex-flow: row wrap;align-items: flex-start;">
            <training-card-component v-for="(item, index) in items" 
                  :key="index"
                  :item="item"
                  @error-event="errorHandler"></training-card-component>
         </v-card-item>
      </v-card>
 </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { defineAsyncComponent } from 'vue'
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
   components: {
      TrainingCardComponent: defineAsyncComponent(() => import('@/components/OnlineTrainingCardComponent.vue')),
      Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
      AlertComponent: defineAsyncComponent(() => import('@/components/AlertComponent.vue')),
   }, data: function() {
       return {
          items: [],
          translate : null,
          overlay: false,
          error: false,
       }
   }, methods: {
      async getItems() {
         try {
            this.items.splice(0, this.items.length);
            const response = await axiosService.getOnlineTraining();
            this.items.push(...response);
         } catch (error) {
            this.error = true ;
         }
      }, translater(key: string) {
          if (this.translate != null) {
             return this.translate[key];
          }
          return key ;
      }, errorHandler() {
         this.error = true ;
      }
   },async mounted() {
       this.overlay = true ; 
       await this.getItems();
       this.overlay = false ; 
   },async created() {
      this.translate = await axiosService.translate(['myLearning.online.training'])  
   }
})

export default class MyLearningOnlineComponent extends Vue {
    
}
</script>
<style scoped>
</style>