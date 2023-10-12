<template>
  <video controls class="media-player"
       v-if="isVideo" 
       :src="mediaUrl" 
       :type="mediaType"></video>
</template>
<script lang="ts">
import AxiosService from '@/services/axiosService';
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';

const axiosService = new AxiosService();

@Options({
    props: {
        item: Object,
        format: String,
    }, components: {
        Overlay: defineAsyncComponent(() => import('@/components/Overlay.vue')),
    }, data: function() {
        return {
          mediaUrl: null,
          overlay: false,
        }
    }, methods: {
       async loadResource() {
            if (this.item != null) {
                const response = await axiosService.getResourceAsStream(this.item.pk, this.item.file);
                this.mediaUrl = window.URL.createObjectURL(new Blob([response]));
            }
        }
    }, computed: {
        isVideo() {
            return this.format == "MP4";
        }, mediaType() {
            if (this.format == "MP4") {
                return "video/mp4";
            }
        }
    }, watch: {
        async item() {
           await this.loadResource();
        }
    },async mounted() {
        await this.loadResource();
    } 
})

export default class MediaViewerComponent extends Vue {

}
</script>
<style scoped>
  .media-player {
      width: 700px;
      height: 100;
  }
</style>