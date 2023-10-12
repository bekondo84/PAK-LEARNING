<template>
   <div>
      <overlay :overlay="overlay"></overlay>
      <alert-component v-if="error"></alert-component>
     <training-list-component
         :title="translator('my.learning.inprogress')"
         :items="items"
         @error-event="errorHandler"></training-list-component>
   </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { defineAsyncComponent } from 'vue'
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
   components: {
       TrainingListComponent: defineAsyncComponent(() => import('@/components/TrainingListComponent.vue')),
       Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
       AlertComponent : defineAsyncComponent(() => import('@/components/AlertComponent.vue')),
  }, data: function() {
       return {
          items: [],
          translate: null,
          overlay: false,
          error: false,
       }
   }, methods: {
      async getItems() {
         try {
            this.items.splice(0, this.items.length);
            const response = await axiosService.registerTrainings();
            this.items.push(...response);
         } catch(error) {
            this.error = true ;
         }
      }, translator(key: string) {
         if (this.translate != null) {
            return this.translate[key];
         }
         return key ;
      }, errorHandler() {
         this.error = true ;
      }
   }, async mounted() {
       this.overlay = true ;
       await this.getItems();
       this.translate = await axiosService.translate(['my.learning.inprogress']);
       this.overlay = false ;
   }
})

export default class MyLearningInprogressComponent extends Vue {
    
}
</script>
<style scoped>
</style>