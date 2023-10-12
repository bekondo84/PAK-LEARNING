import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { emitter } from './services/emitter'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css'
import '@/style/global.module.css'

const vuetify = createVuetify({
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
        mdi,
    }
  },
  components,
  directives,
})

const app = createApp(App);
app.config.globalProperties.emitter = emitter;
      app.use(vuetify)
              .use(store)
              .use(router)
              .mount('#app');