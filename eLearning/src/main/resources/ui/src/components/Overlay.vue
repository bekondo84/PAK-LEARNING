<template>
   <v-overlay
      :model-value="overlay"
      class="align-center justify-center"
    >
      <v-progress-circular
        color="primary"
        indeterminate
        size="64"
      ></v-progress-circular>
    </v-overlay>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
    props: {
        overlay: Boolean,
    },
    data: function() {
        return {
            
        }
    }
})
export default class Overlay extends Vue {

}
</script>
<style scoped>
</style>