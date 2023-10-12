<template>
   <div>
      <overlay :overlay="overlay"></overlay>
      <alert-component v-if="error"></alert-component>
      <v-card style="margin: 2px 20px 5px 20px;font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;opacity: 0.6;"
      variant="plain"
      class="mx-auto"
      :title="translater('myLearning.training')">
      <v-card-item style="display: flex; flex-flow: row wrap;align-items: flex-start;">
         <training-card-component v-for="(item, index) in items" 
               :key="index"
               :item="item"></training-card-component>
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
      TrainingCardComponent: defineAsyncComponent(() => import('@/components/TrainingCardComponent.vue')),
      AlertComponent: defineAsyncComponent(() => import('@/components/AlertComponent.vue')),
      Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
   }, data: function() {
       return {
          items: [],
          translate : null,
          overlay: false,
          error: false,
       }
   }, methods: {
      translater(key: string) {
          if (this.translate != null) {
             return this.translate[key];
          }
          return key ;
      },async loadItems() {
          try {
            this.items.slice(0, this.items.length);
            const response = await axiosService.getMyLearning();
            this.items.push(...response);
          } catch (error) {
            this.error = true;
          }
      }
   }, async mounted() {
        this.overlay = true;
        await this.loadItems();
        this.overlay = false;
   },async created() {
      this.translate = await axiosService.translate(['myLearning.training'])  
   }
})

export default class MyLearningComponent extends Vue {
    
}
</script>
<style scoped>
</style>