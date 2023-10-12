<template>
    <div class="card-margin">
        <overlay :overlay="overlay"></overlay>
        <v-card
            class="mx-auto"
            max-width="258"
        >
            <v-img
            src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
            height="120px"
            cover
            ></v-img>
            <v-card-subtitle style="text-align: justify;color: #2596be;font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;">
               {{ theme }}
            </v-card-subtitle>

            <v-card-title style="font-size: 14px;font-weight: bolder;text-align: justify;white-space: normal;" >
               {{ name }}
            </v-card-title>           
            <v-card-actions style="display: flex;flex-flow: row nowrap;">
            <v-btn
                color="orange-lighten-2"
                variant="text"
                @click="start()">
                {{  translater('Training.start.name')  }}
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn
                color="orange-lighten-2"
                variant="text"
                @click="viewDetails(item)">
                {{  translater('Training.viewmore.name')  }}
            </v-btn>
            </v-card-actions>
            <div class="card-footer">
                <div>
                    <v-icon icon="mdi-clock" size="13px"></v-icon><span class="card-footer-text">2 heures</span>
                </div>
                <div>
                    <v-icon icon="mdi-flag" size="13px" color="danger"></v-icon><span class="card-footer-text">Required</span>
                </div>
            </div>            
        </v-card>
    </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { defineAsyncComponent } from 'vue';
import store from '@/store';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props : {
        item: Object,
    }, components: {
        Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
    }, data: function() {
        return {
           translate: null,
           overlay: false ,
        }
    }, methods: {
       async start() {
          try{
            this.overlay = true ;
            const data = axiosService.registerOnlineTraining(this.item.code);  
            this.overlay = false ;
            this.$router.push({name: 'activity', params: {op: this.training.code}})
          } catch(err) {
             this.$emit("error-event");
          }  
        }, registerAction(item: any) {
            store.commit('setInvolve', item);
            this.$router.push({name:'register', params: {op: item.training.code}});
        }, translater(key: string) {
            if (this.translate != null && this.translate[key] != null) {
                return this.translate[key];
            }
            return key ;
        }, viewDetails(item : any) {
            this.$router.push({name: 'onlinetraining', params: {op: item.training.code}});
        }
    }, computed: {
        training() {
            return this.item != null ? this.item.training : null;
        }, theme() {
            let training = this.item.training ;
            return training != null && training.theme != null ? training.theme.code : null ;
        }, name() {
            let training = this.item.training ;
            return training != null ? training.name : "";
        }
    }, async created() {
         this.translate = await axiosService.translate(['Training.register.name', 'Training.viewmore.name','Training.start.name']) 
    }
})

export default class TrainingCardComponent extends Vue {

}
</script>
<style scoped>
   .card-margin {
     margin: 5px 10px 5px 5px;
   }
   .card-footer {
     display: flex;
     flex-flow: row nowrap;
     justify-content: space-between;
   }
   .card-footer-text {
     font-size: 12px;
     font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
   }
</style>