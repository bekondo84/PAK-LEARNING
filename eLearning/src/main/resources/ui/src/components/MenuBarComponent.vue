<template>
<v-app-bar>
        <template v-slot:image>
          <v-img
            gradient="to top right, rgba(19,84,122,.8), rgba(128,208,199,.8)"
          ></v-img>
        </template>
        <template v-slot:prepend>
          <v-app-bar-nav-icon></v-app-bar-nav-icon>
          <v-app-bar-title>{{ title }}</v-app-bar-title>
        </template> 
        <v-spacer></v-spacer>
       
        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
          <v-menu activator="parent">
            <v-list>
              <v-list-item class="v-list-item">
                <password-reset
                  :title="t('MenuBar.passwordreset.name')"></password-reset>
              </v-list-item>
             <v-list-item class="v-list-item" 
             @click="logout()">
                {{ t('MenuBar.connexion.name') }}
              </v-list-item>
            </v-list>
          </v-menu>
        </v-btn>
      </v-app-bar>
</template>
<script lang="ts">
import AxiosService from '@/services/axiosService';
import store from '@/store';
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';

const axiosService = new AxiosService();

@Options({
    components: {
      PasswordReset: defineAsyncComponent(() => import('@/components/PasswordResetComponent.vue'))
    }, data: function() {
        return {
           session : null ,
           generalInfo: null,
           translate: null,
        }
    }, methods: {
        t(key: string) {
           if (this.translate != null) {
              return this.translate[key];
           }
           return key ;
        }, logout() {
         this.$router.push('/e-learning/login')
        }
    }, computed : {
        username() {
            return this.session != null ? this.session.name : "";
        }, title() {
            return this.generalInfo != null ? this.generalInfo.title: "E-LEARNING"
        }
    }, mounted() {
        this.session = store.getters.getCurrentSession;
        this.generalInfo = store.getters.getGeneral;
    },async created() {
       this.translate = await axiosService.translate(['MenuBar.dashbord.name', 'MenuBar.connexion.name', 'MenuBar.passwordreset.name','MenuBar.settings.name']);
    }
})

export default class MenuBarComponent extends Vue {

}
</script>
<style scoped>
.v-list-item {
   cursor: pointer;
   width: max-content;
   height: 45px;
}
</style>