<template>
    <v-alert
    :type="typealert"
    variant="tonal"
  >
     <v-alert-title style="display: flex;flex-flow: row nowrap; justify-content: space-between;">
        <div>{{ titleText }}</div>  
        <div v-if="showTestResultAlert"><span>{{ t('test.score.message') }}</span><span>{{ resultMassage }}</span></div>
        <div v-if="!showTestResultAlert"><span>{{ t('test.minimum.score.message') }}</span><span>{{ minimumResultMessage }}</span></div>
    </v-alert-title>
  </v-alert>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props: {
      test: Object,
    }, data: function() {
        return {
           translate: null,
        }
    }, methods: {
        t(key: string) {
             if (this.translate != null) {
                return this.translate[key];
             }
             return key ;
        }
    }, computed: {
        showTestResultAlert() {
           const state = this.test != null && this.test.state != null ? this.test.state.code: null;
           return state == 'DONE';
       }, activite() {
          return this.test != null ? this.test.activity : null ;
       }, resultMassage() {
          const markOn = this.test != null && this.test.activity != null ? this.test.activity.markOn : null ;
          const result = this.test != null ? this.test.mark : null ;
          if (markOn != null && result != null) {
              return result+" / "+markOn ;
          }
       }, minimumResultMessage() {
         const markOn = this.test != null && this.test.activity != null ? this.test.activity.markOn : null ;
         const successAt = this.test != null && this.test.activity != null ? this.test.activity.successAt : null ;
         if (markOn != null && successAt != null) {
            return successAt+" / "+markOn ;
         }
       }, typealert() {
         const state = this.test != null && this.test.state != null ? this.test.state.code: null;
         if (state != 'DONE') {
            return "info";
         }
         const successAt = this.test != null && this.test.activity != null ? this.test.activity.successAt : null ;
          const result = this.test != null ? this.test.mark : null ;
          
          if (successAt != null && result != null) {
             return result >= successAt ? "success" : "error";
          }
       }, titleText() {
         const state = this.test != null && this.test.state != null ? this.test.state.code: null;
         if (state != 'DONE') {
            return this.t('test.inprogress.message');
         } else {
            return this.t('test.finish.message');
         }
       }
    },async created() {
       this.translate = await axiosService.translate(['test.finish.message', 'test.inprogress.message', 'test.minimum.score.message', 'test.score.message'])
    }
})

export default class TestResultComponent extends Vue {

}
</script>
<style scoped>
</style>