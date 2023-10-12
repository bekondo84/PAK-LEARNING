<template>
   <v-icon
          color="success"
          end
          :icon="getIcon"
        ></v-icon>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({
    props: {
        state: Boolean
    },computed : {
        getIcon() {
            return this.state == true ? "mdi-checkbox-marked-circle" :"none";
        }
    }
})

export default class MarkQuestionComponent extends Vue {

}
</script>
<style scoped>
</style>