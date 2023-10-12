<template>
  <div style="display: flex; flex-flow: column wrap;align-items: flex-start;">
    <overlay :overlay="overlay"></overlay> 
    <alert-component v-if="error"></alert-component>
    <training-done-list 
       :title="translator('my.learning.done')" 
       :items="items"></training-done-list>
     
  </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { defineAsyncComponent } from 'vue'
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();  
@Options({
   components: {
      TrainingDoneList: defineAsyncComponent(() => import('@/components/TrainingListComponent.vue')),
      Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
      AlertComponent: defineAsyncComponent(() => import('@/components/AlertComponent.vue')),
   }, data: function() {
       return {
          items: [],
          translate: null,
          overlay: false,
          error : false,
       }
   }, methods: {
      async getItems() {
         try {
            this.items.splice(0, this.items.length);
            const response = await axiosService.getDoneTraining();
            this.items.push(...response);
         } catch (error) {
            this.error = true;
         }
      }, translator(key: string) {
         if (this.translate != null) {
            return this.translate[key];
         }
         return key ;
      }
   },async mounted() {
       this.overlay = true ;
       await this.getItems();
       this.translate = await axiosService.translate(['my.learning.done']);
       this.overlay = false ;
   }
})

export default class MyLearningDoneComponent extends Vue {
    
}
</script>
<style scoped>
</style>