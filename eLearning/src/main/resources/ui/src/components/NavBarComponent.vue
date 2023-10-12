<template>
   <v-card
        class="mx-auto"
        max-width="300">
    <v-list>
        <v-list-item
        prepend-avatar="/backoffice/api/v1/public/media/imagenotfound.png"
        :title="username"
        :subtitle="usermail"
        ></v-list-item>
    </v-list>
    <v-divider></v-divider>
        <v-list density="compact">
        <v-list-subheader>{{ label('menuBar.title') }}</v-list-subheader>

        <v-list-item
            v-for="(item, i) in items"
            :key="i"
            :value="item"
            active-color="primary"
            rounded="xl"
            @click="itemSelected(item)"
        >
        <template v-slot:append>
            <v-badge
                :color="item.badge"
                :content="item.total"
                inline
                >
            </v-badge>
        </template>
        <v-list-item-title v-text="item.text"></v-list-item-title>
        </v-list-item>
        </v-list>
    </v-card>  
</template>
<script lang="ts">
import store from '@/store';
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
  props: {
    items: Array,
  },  data: function() {
    return {
        session: null,
        translate: [],
    }
  }, methods: {
    itemSelected(item: any) {
        this.$router.push('/e-learning/'+item.code);
    }, label(key: string) {
        if (this.translate != null){
          return this.translate[key];
        }
        return key;
    }
  }, computed: {
     username() {
        return this.session != null ? this.session.name : "unkown"
     },
     usermail() {
       return this.session != null ? this.session.email : "";
     }
  },async mounted() {
      this.session = store.getters.getCurrentSession;
      this.translate = await axiosService.translate(['menuBar.title']) 
  }
})
export default class NavBarComponent extends Vue {

}
</script>
<style scoped>
</style>