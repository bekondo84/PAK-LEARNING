<template>
  <div>
    <overlay :overlay="overlay"></overlay>
  <v-hover v-slots="{ isHovering, props }">
  <v-card
      variant="plain"
      v-bind="props"
      class="mx-auto"
      :elevation="isHovering ? 24 : 6">
      <v-card-text class="pb-0 rounded-shaped">
        <!--<div class="text-overline mb-1">{{ item.name }}</div>-->
        <div class="text-caption text--primary"><span class="margin-05 text--error">{{ header('open.activity.period.name') }}</span><span class="margin-05">{{ item.beginAt }}</span><span class="margin-05">{{ header('connector.to.name') }}</span><span class="margin-05">{{ item.endAt }}</span></div>
        <div class="text-caption text--primary">          
          <v-textarea v-model="item.description"
                variant="plain"
                readonly="true"
                auto-grow></v-textarea>
        </div>        
      </v-card-text>
      <v-expand-transition>
        <v-card>
          <v-card-item v-if="resources.length > 0"
             class="pb-0">
            <v-table density="compact" style="font-size: small;">
              <thead>
                <tr>
                  <th class="text-left">{{ header('Resource.name') }}</th>
                  <th class="text-left">{{ header('Resource.description') }}</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="item in resources"
                  :key="item.name"
                >
                  <td>{{ item.name }}</td>
                  <td>{{ item.description }}</td>
                  <td>
                    <v-btn
                      variant="text"
                      color="success"
                      icon="mdi-download-circle"
                      size="small"
                      @click="download(item)">
                    </v-btn>
                  </td>
                </tr>
              </tbody>
            </v-table>
          </v-card-item>
          <v-card-actions class="pt-0">
            <v-btn v-if="online"
              variant="text"
              color="teal-accent-4"
              @click="startActivity()"
            >
            {{ header('start.btn.name') }}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-expand-transition>
     </v-card>
 </v-hover>
</div>
  </template>
<script lang="ts">
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props: {
      item: Object,
      training: Object,
      startable: Boolean,
      overlay: false,
    }, components: {
         RegisterComponent: defineAsyncComponent(() => import('@/components/RegisterComponent.vue')),
         Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
    }, data: function() {
        return {
            headers: null,
            overlay: false,
        }
    }, methods: {
      header(key: string) {
         if (this.headers != null) {
            return this.headers[key];
         }
         return key;
      },async download(item: any) {
        this.overlay = true ;
        try{
         await axiosService.download(item.pk, item.filename);
        } catch( error) {
          console.error(error);
        }
        this.overlay = false ;
      },async startActivity() {
         this.overlay = true ;
          try {
             await axiosService.startActivity(this.item.code);
             const type = this.item.type;
             if (type == 'TEST') {
               this.$router.push({name: 'test', params: {op: this.training.code, activity:this.item.code}});
             } else if (type == 'HOMEWORK') {
              this.$router.push({name: 'homework', params: {op: this.training.code, activity:this.item.code}});
             } else if (type == 'LECON') {
              this.$router.push({name: 'lecon', params: {op: this.training.code, activity:this.item.code}});
             }
          } catch (error) {
             console.error(error);
          }     
          this.overlay = false ;    
      }
    }, computed: {
       resources() {
          return this.item != null ? this.item.resources : [];
       }, online() {
          return (this.item != null ? this.item.online : false) && this.startable;
       }
    }, async created() {
       this.headers = await axiosService.translate(['start.btn.name', 'open.activity.period.name', 'connector.to.name', 'Resource.name', 'Resource.description', 'exit.btn.label', 'Activity.state']);
    }
})

export default class RegisterBtnComponent extends Vue {

}
</script>
<style scoped>
 .margin-05 {
    margin-left: 5px;
    margin-top: 5px;
    color: #FF4081;
 }
 .v-card--reveal {
  bottom: 0;
  opacity: 1 !important;
  position: absolute;
  width: 100%;
}
</style>