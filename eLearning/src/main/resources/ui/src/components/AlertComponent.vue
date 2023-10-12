<template>
    <v-alert
      v-model="alert"
      :type="type"
      icon="mdi-emoticon-cry-outline"
       closable
      :text="message"
    ></v-alert>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props: {
       alertMsg: String, //Alert message to print,
       alertType: String,
    }, data: function() {
        return {
            alert : true,
            translate : null
        }
    }, methods: {
        t(key: string) {
            if (this.translate != null && this.translate[key] != null) {
                return this.translate[key];
            }
            return key;
        }
    }, computed: {
        message() {
           if (this.alertMsg == null && this.alertType == null) {
               return  this.t('my.learning.errormessage');
           }
           if( this.alertType == null ) {
              return this.t(this.alertMsg) ;
           }

           if (this.alertMsg != null) {
             return this.t(this.alertMsg);
           }
        },
        type () {
            return this.alertType == null ? 'error' : this.alertType;
        }
    }, watch: {
        
    },async created() {
        this.translate = await axiosService.translate(['my.learning.errormessage', 'my.learning.errortitle']);
    }
})

export default class AlertComponent extends Vue {

}
</script>
<style scoped>
</style>