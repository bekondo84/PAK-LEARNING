<template>
     <div>
        <pdf-viewer v-if="format == 'PDF'"
           :item="resource"></pdf-viewer>
        <media-viewer v-else
          :item="resource"
          :format="format"></media-viewer>
     </div>
</template>
<script lang="ts">
import store from '@/store';
import format from 'pretty-format';
import { defineAsyncComponent } from 'vue';
import { Options, Vue } from 'vue-class-component';
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props: {
      item: Object,
    }, components: {
        PdfViewer: defineAsyncComponent(() => import('@/components/PdfViewerComponent.vue')),
        MediaViewer: defineAsyncComponent(() => import('@/components/MediaViewerComponent.vue')),
    }, data: function() {
        return {
           data : null ,
        }
    }, methods: {
         async getCourse() {
            const summaryEntry = this.item != null ? this.item.currentIndex : null ;
            const course = summaryEntry != null ? summaryEntry.index : null ;

            if (course != null) {
                this.data = await axiosService.getCourse(course.id, this.item.activity.code);
            }
         }
    }, computed: {
        format() {
           if (this.data != null && this.data.format != null) {
              return this.data.format.code ;
           }
           return null ;
        }, resource() {
           return this.data != null ? this.data.resource : null;
        }, course() {
            const lang = store.getters.getCurrentSession.language ;
            const name = this.data != null ? this.data.name : null ;
            return name != null ? name[lang] : null;
        }
    },async mounted() {
       await this.getCourse();
    }
})

export default class CourseComponent extends Vue {

}
</script>
<style  scoped>
</style>