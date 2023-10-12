<template>
  <div>
    <overlay :overlay="overlay"></overlay>
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
          <v-container fluid>
            <v-textarea v-if="isReady" style="margin: 15px 5px 15px 5px;"
            autocomplete="email"
            :label="translater('type.HomeWork.text.name')"
            bg-color="grey-lighten-5"
            color="black"
            variant="filled"
            auto-grow
            v-model="item.text"
          ></v-textarea>
          </v-container>          
        </v-card-item>
        <v-card-actions v-if="!isHomeWorkDone"
           style="display: flex;flex-flow:row nowrap;justify-content: flex-end;">
            <v-btn 
                variant="elevated"
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
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';
import store from '@/store';
import { defineAsyncComponent } from 'vue';

const axiosService = new AxiosService();

@Options({
   components: {
    Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
   },
   data: function() {
      return {
        item: null,
        translate: null,
        overlay: false,
        error: true,
      }
   }, methods: {
       async loadHomework() {
          try {
            const activity = this.$route.params.activity;
            this.item = await axiosService.homeworkResult(activity);
          } catch (error) {
            this.error = true ;
          }
       }, translater(key: string) {
          if (this.translate != null) {
            return this.translate[key];
          }
          return key ;
       }, async maskTestAsDone() {
            this.overlay = true;
            try {
              this.item = await axiosService.markHomeworkAsDone(this.item);
            } catch (error) {
              this.error = true ;
            }
            this.overlay = false;
        }
   }, computed: {
       activityName() {
            const lang = store.getters.getCurrentSession.language ;
            const activity = this.item != null && this.item.activity != null ? this.item.activity : null;
            let name = activity != null ? activity.name[lang] : null;
            return  name != null ? name.value : null;
        },isReady() {
            return this.item != null ;
        }, isHomeWorkDone() {
            const state = this.item != null ? this.item.state : null ;
            return (state != null && state.code =='DONE')
        }
   },async mounted() {
       this.overlay = true; 
       this.translate = await axiosService.translate(['type.HomeWork.text.name', 'activity.close.btn'])
       await this.loadHomework();
       this.overlay = false;
   } 
})
export default class HomeWorkComponent extends Vue {

}
</script>
<style scoped>
</style>