<template>
  <div>
    <overlay :overlay="overlay"></overlay> 
    <alert-component v-if="error"></alert-component>
    <v-card style="margin: 20px 20px 5px 20px;text-align: justify;"
    class="mx-auto"
    width="900"
    :title="title"
    
  >
  <v-table>
    <thead>
      <tr>
        <th class="text-left">
          {{ header('workgroup.code') }}
        </th>
        <th class="text-left">
            {{ header('workgroup.name') }}
        </th>
        <th class="text-left">
            {{ header('workgroup.beginAt') }}
        </th>
        <th class="text-left">
            {{ header('workgroup.endAt') }}
        </th>    
        <th></th>
        <th></th>    
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="item in items"
        :key="item.code"
      >
        <td>{{ item.code }}</td>
        <td>{{ item.name }}</td>
        <td>{{ item.beginAt }}</td>
        <td>{{ item.endAt }}</td>
        <td>
          <v-progress-linear
              v-model="skill"
              color="blue-grey"
              height="25"
            >
            <template v-slot:default="{ value }">
              <strong>{{ Math.ceil(value) }}%</strong>
            </template>
          </v-progress-linear>
        </td>
        <td>
          <view-more-btn-component
             :items="item.timesheets"></view-more-btn-component>
        </td>
        <td>
          <v-btn variant="text"
            color="error"
            icon="mdi-pencil"
            size="x-small"
            @click="registerAction(item)"
        ></v-btn>
        </td>
      </tr>
    </tbody>
  </v-table>
  </v-card>  
</div>
</template>
<script lang="ts">
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';
import store from '@/store';
import UrlUtils from '@/services/urlUtils';

const axiosService = new AxiosService();

@Options({
    components: {
        RegisterComponent: defineAsyncComponent(() => import('@/components/RegisterComponent.vue')),
        ViewMoreBtnComponent: defineAsyncComponent(() => import('@/components/ViewMoreBtnComponent.vue')),
        Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
        AlertComponent: defineAsyncComponent(() => import('@/components/AlertComponent.vue')),
    }, data: function() {
        return {
         headers: null,
         trainingId: null,
         items: [],
         involve : null,
         overlay: false,
         error: false,
        }
    }, methods: {
        header(key: string) {
           if (this.headers != null) {
              return this.headers[key];
           }
           return null;
        }, registerAction(item: any) {
            this.overlay = true;
            try {
                  axiosService.registerForTraining(this.involve.pk, item.code);
                  this.emitter.emit(UrlUtils.HOMECMS_REFRESH_EVENT);
                  this.$router.push({name: "mylearining"})
            } catch (error) {
                this.error = true;
            }     
            this.overlay = false;       
        },async loadGroups() {
          try {
            this.items.slice(0, this.items.length);
            const response = await axiosService.getGroupsForTraining(this.trainingId);
            this.items.push(...response);
          } catch (error) {
            this.error = true;
          }
          
        }
    }, async mounted() {
      this.overlay = true;
        this.involve = store.getters.getInvolve ;   
        this.trainingId = this.$route.params.op;
        await this.loadGroups();
        this.overlay = false;
    }, async created() {
        this.headers = await axiosService.translate(['workgroup.code','workgroup.name','workgroup.beginAt', 'workgroup.endAt'])
    }
})

export default class RegisterpanelComponent extends Vue {

}
</script>
<style scoped>
</style>