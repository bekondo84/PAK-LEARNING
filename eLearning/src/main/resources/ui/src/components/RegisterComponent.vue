<template>
   <v-card style="border: solid;">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          sm="6"
        >
           <v-row>
            <v-col
          cols="12"
          sm="6"
        >
          <v-text-field
            model-value="John Doe"
            label="Regular"
            variant="underlined"
            disabled
          ></v-text-field>
        </v-col>

        <v-col
          cols="12"
          sm="6"
        >
          <v-text-field
            model-value="John Doe"
            label="Regular"
            readonly
          ></v-text-field>
        </v-col>
           </v-row>
        </v-col>
        <v-col
          cols="12"
          sm="6"
        >
           Section II
        </v-col>
      </v-row>
    </v-container>
       
        </v-card>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
    
})

export default class RegisterComponent extends Vue {

}
</script>
<style scoped>
</style>