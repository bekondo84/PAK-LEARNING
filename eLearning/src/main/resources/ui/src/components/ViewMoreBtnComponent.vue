<template>
   <v-dialog
      v-model="dialog"
      width="650"
    >
      <template v-slot:activator="{ props }">
        <v-btn variant="text"
            color="secondary"
            icon="mdi-calendar"
            size="x-small"
            v-bind="props">
        </v-btn>
      </template>

      <v-card>        
        <v-toolbar
            :title="header('timesheet.title')"
        >
        <template v-slot:image>
          <v-img
            gradient="to top right, rgba(19,84,122,.8), rgba(128,208,199,.8)"
          ></v-img>
        </template>
      </v-toolbar>
        <v-table>
    <thead>
      <tr>
        <th class="text-left">
          {{ header('timesheet.day') }}
        </th>
        <th class="text-left">
          {{ header('timesheet.beginAt') }}
        </th>
        <th class="text-left">
          {{ header('timesheet.endAt') }}
        </th>
        <th class="text-left">
          {{ header('timesheet.subject') }}
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="(item, index) in items"
        :key="index"
      >
        <td>{{ item.day }}</td>
        <td>{{ item.startAt }}</td>
        <td>{{ item.endAt }}</td>
        <td>{{ item.subject }}</td>        
      </tr>
    </tbody>
  </v-table>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green-darken-1"
            variant="text"
            @click="dialog = false"
          >
            {{ header('exit.btn.label') }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
<script lang="ts">
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props: {
      items: Array,
    }, components: {
         RegisterComponent: defineAsyncComponent(() => import('@/components/RegisterComponent.vue'))
    }, data: function() {
        return {
            dialog : false ,
            headers: null,
            
        }
    }, methods: {
      header(key: string) {
         if (this.headers != null) {
            return this.headers[key];
         }
         return null;
      }
    }, async created() {
       this.headers = await axiosService.translate(['timesheet.day', 'timesheet.beginAt', 'timesheet.endAt', 'timesheet.subject', 'timesheet.title', 'exit.btn.label']);
    }
})

export default class RegisterBtnComponent extends Vue {

}
</script>
<style scoped>
</style>