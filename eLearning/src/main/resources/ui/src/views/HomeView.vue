<template>
  <v-card>
    <v-layout>
      <menu-bar-component></menu-bar-component>
      <v-main style="min-height: 570px; display: flex; flex-flow: row nowrap;">
          <div>
           <nav-bar-component
             :items ="profil"></nav-bar-component>           
          </div>
          <div class="home-content">
              <RouterView></RouterView>
          </div>        
      </v-main>
    </v-layout>
  </v-card>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { defineAsyncComponent } from 'vue';
import store from '@/store';
import AxiosService from '@/services/axiosService';
import UrlUtils from '@/services/urlUtils'

const axiosService = new AxiosService();

@Options({
  components: {
    NavBarComponent: defineAsyncComponent(()=> import('@/components/NavBarComponent.vue')),
    MyLearningComponent: defineAsyncComponent(() => import('@/components/MyLearningComponent.vue')),
    MenuBarComponent: defineAsyncComponent(() => import('@/components/MenuBarComponent.vue'))
  }, data: function() {
    return {
      profil: [],
      session : null ,
      timer: null,
    }
  }, methods: {
     async getHomeCms() {
        this.session = Object.assign({}, store.getters.getCurrentSession);
        let homecms = await axiosService.getHomeComponent();
        this.profil.splice(0, this.profil.length);
        this.profil.push(...homecms.menuitems)
     }, gotoLogin() {
        console.log('unauthorized error detected ............')
        this.$router.push({name: 'login'});
     }
  }, computed: {
      username() {
         return this.session != null ? this.session.name : "";
      }
  }, async mounted() {
     await this.getHomeCms();
     this.timer = setInterval(() => {
         this.getHomeCms();
     }, 60000)
  },async created() {
      this.emitter.on(UrlUtils.HOMECMS_REFRESH_EVENT, await this.getHomeCms);
      this.emitter.on("authorization-error", this.gotoLogin);
  }
})
export default class HomeView extends Vue {}
</script>
<style scoped>
 .home-content {
   flex: 1;
 }
</style>
