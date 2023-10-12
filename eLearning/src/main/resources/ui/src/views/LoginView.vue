<template>
    <v-card>
        <v-layout>
        <v-main style="min-height: 570px; display: flex;flex-flow:row nowrap">
            <div class="form-credentials-panel">
            <div class="form-credentials-panel-header">
                <div class="title-bar">{{ config.title }}</div>
                <div>
                    <h2>{{ config.welcomeMsge }}</h2>
                    <p v-if="!haserror">{{ config.loginMsge }}</p>
                    <p v-else class="error">{{ config.errorMsge }}</p>
                </div>
            </div>
            <div class="form-credentials-panel-body">
                <v-card class="mx-auto px-6 py-8" max-width="344">
        <v-form
            v-model="form"
            @submit.prevent="onSubmit"
        >
            <v-text-field
            v-model="email"
            :readonly="loading"
            :rules="[required]"
            class="mb-2"
            clearable
            :label="config.loginLabel"
            ></v-text-field>

            <v-text-field
            v-model="password"
            :readonly="loading"
            :rules="[required]"
            clearable
            :label="config.passwordLabel"
            placeholder="Enter your password"
            type="password"
            ></v-text-field>

            <br>

            <v-btn
            :disabled="!form"
            :loading="loading"
            block
            color="success"
            size="large"
            type="submit"
            variant="elevated"
            >
            {{ config.connexionBtnLabel }}
            </v-btn>
        </v-form>
        </v-card>
           </div>
           <!--<div><footer-component></footer-component></div>-->  
        </div>
        <div class="form-comm-panel">
            <v-img  style="height: 100%;"
                aspect-ratio="16/9"
                cover
                src="/backoffice/api/v1/public/media/port-image.jpg"
            >
            <span class="img-text">Hello the world</span>
        </v-img>
        </div>
      </v-main>
    </v-layout>
  </v-card>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { defineAsyncComponent } from 'vue';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    components: {
        FooterComponent: defineAsyncComponent(() => import('@/components/FooterComponent.vue'))
    }, data: function() {
        return {
            form: false,
            email: null,
            password: null,
            loading: false,
            haserror: false,
            config: {} ,
        }
    }, methods: {
      async onSubmit () {
        if (!this.form) return
        this.loading = true
        try {
            await axiosService.authenticate(this.email, this.password);
            this.$router.push("/e-learning");
        } catch (error) {
            this.haserror = true;
        }
        this.loading = false;
      },
      required (v: any) {
        return !!v || 'Field is required'
      },
    }, async mounted() {
        this.config = Object.assign({}, await axiosService.getLoginComponent());
    },
})

export default class LoginView extends Vue {

}
</script>
<style scoped>
    .img-text {
        margin-top: 50px;
        font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
        font-size: larger;
        color: #fff;
    }
    .title-bar {
       margin-top: 25px;   
       font-family: Verdana, Geneva, Tahoma, sans-serif;
       font-size: larger;
       font-weight: 900;
       opacity: 0.6; 
       color: #2596be;
    }
  .form-credentials-panel {
     height: 100%;
     width: 50%;
     display: flex;
     flex-flow: column nowrap;
  }
  .form-comm-panel {
    flex: 1;
  }
  .form-credentials-panel-header {
     height: 30%;
     display: flex;
     flex-flow: column nowrap;
     justify-content: space-between;
  }
  .form-credentials-panel-body {
    flex: 1;
  }
  .error {
    color: brown;
  }
</style>