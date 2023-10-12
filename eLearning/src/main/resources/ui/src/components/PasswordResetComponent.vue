<template>
   <v-row justify="center">
    <v-dialog
      v-model="dialog"
      width="auto"
    >
    <template v-slot:activator="{ props }">
        <v-btn style="height: 45px;"
          class="text-none text-subtitle-1"
          v-bind="props"
          variant="text"
          @click="resetData()">
          {{ title }}
        </v-btn>
    </template>
     <v-card>
        <v-card-title>{{ t('PasswordReset.title.name') }}</v-card-title>
        <v-card-subtitle v-if="haserror" class="error">{{ errormessage }}</v-card-subtitle>
        <v-divider></v-divider>
        <v-card-text style="height: 230px;width: 400px;">
            <v-container>
                <v-row>
                <!--
                  <v-col cols="12">
                    <v-text-field 
                    :label="t('PasswordReset.oldpassword.name')"
                    type="password"
                    :rules="[required]"
                    v-model="data.currentpassword"
                    required
                    ></v-text-field>
                  </v-col>
                  -->
                  <v-col cols="12">
                    <v-text-field
                    :label="t('PasswordReset.newpassword.name')"
                    type="password"
                    :rules="[required]"
                    v-model="data.newpassword"
                    required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                    :label="t('PasswordReset.confirmpassword.name')"
                    type="password"
                    :rules="[required]"
                    v-model="data.confirmpassword"
                    required
                    ></v-text-field>
                  </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn
            color="blue-darken-1"
            class="text-none text-subtitle-1"
            variant="text"
            @click="saveAction()"
          >
          {{ t('PasswordReset.save.name') }}
          </v-btn>
          <v-btn
            color="blue-darken-1"
            class="text-none text-subtitle-1"
            variant="text"
            @click="dialog = false"
          >
            {{ t('PasswordReset.cancel.name') }}
          </v-btn>
          
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script lang="ts">
import AxiosService from '@/services/axiosService';
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';

const axiosService = new AxiosService();

@Options({
  props: {
     title: String,
  }, components: {
     Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
  }, data: function() {
      return {
        dialog : false ,
        translate: null,
        data: null,
        haserror: false,
        errormessage: 'Error has occurated',
      }
  }, methods: {
      t(key: string) {
         if (this.translate != null) {
            return this.translate[key];
         }
         return key ;
      },
      required (v: any) {
        return !!v || 'Field is required'
      },async saveAction() {
        if (this.data.newpassword != this.data.confirmpassword) {
            this.errormessage = this.t('PasswordReset.newandconfirm.nomatch');
            this.haserror = true ;
            return ;
        }
        /**
        if (! await axiosService.isPasswordMatch(this.data.currentpassword)) {
            this.errormessage = this.t('PasswordReset.oldpassword.nomatch');
            this.haserror = true ;
        } */
        if (this.data.newpassword != null && this.data.confirmpassword != null) {
            await axiosService.resetPassword(this.data.newpassword);
            this.dialog = false ;
            console.log('---------------------- redirect to login')
            this.$router.push('/e-learning/login')
        }
      }, resetData() {
        this.haserror = false;
        this.data.newpassword = null;
        this.data.confirmpassword = null ;
      }
  },async created() {
     this.translate = await axiosService.translate(['PasswordReset.title.name', 'PasswordReset.username.name', 'PasswordReset.oldpassword.name', 'PasswordReset.newpassword.name', 'PasswordReset.confirmpassword.name', 'PasswordReset.save.name', 'PasswordReset.cancel.name', 'PasswordReset.newandconfirm.nomatch', 'PasswordReset.oldpassword.nomatch']);
  }, mounted() {
      this.data = {currentpassword:null, newpassword: null, confirmpassword:null};
  }
})
 export default class PasswordResetComponent extends Vue {

 }
</script>
<style scoped>
.error {
    color: brown;
}
</style>