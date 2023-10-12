<template>
 <div class="pdf-viewer">
  <div class="app-header">
    <template v-if="isLoading"> Loading... </template>

    <template v-else>
      <span v-if="showAllPages"> {{ pageCount }} page(s) </span>

      <span v-else>
        <button :disabled="page <= 1" @click="page--">❮</button>

        {{ page }} / {{ pageCount }}

        <button :disabled="page >= pageCount" @click="page++">❯</button>
      </span>

      <label class="right">
        <input v-model="showAllPages" type="checkbox" />

        Show all pages
      </label>
    </template>
  </div>

  <div class="app-content">
    <vue-pdf-embed
      ref="pdfRef"
      :source="pdfSource"
      :page="page"
      @rendered="handleDocumentRender"
    />
  </div>
</div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import VuePdfEmbed from 'vue-pdf-embed'
import AxiosService from '@/services/axiosService';

const axiosService = new AxiosService();

@Options({
    props: {
        item: Object,
    }, components: {
       VuePdfEmbed,
    }, data: function() {
        return {
            isLoading: true,
            page: null,
            pageCount: 1,
            pdfSource: null,
            showAllPages: true
        }
    },
  watch: {
    showAllPages() {
      this.page = this.showAllPages ? null : 1
    }
  },
  methods: {
    handleDocumentRender(args: any) {
      console.log(args)
      this.isLoading = false
      this.pageCount = this.$refs.pdfRef.pageCount
    } 
   
  },async mounted() {
      if (this.item != null) {
          const response = await axiosService.getResourceAsStream(this.item.pk, this.item.file);
          this.pdfSource = window.URL.createObjectURL(new Blob([response]));
      }
    }    
})
export default class PdfViewerComponent extends Vue {

}
</script>
<style scoped>
.vue-pdf-embed > div {
  margin-bottom: 8px;
  box-shadow: 0 2px 8px 4px rgba(0, 0, 0, 0.1);
}

.app-header {
  padding: 16px;
  box-shadow: 0 2px 8px 4px rgba(0, 0, 0, 0.1);
  background-color: #555;
  color: #ddd;
}
.pdf-viewer {
    display: flex;
    flex-flow: column nowrap;
    height: 400px;
    overflow-y: auto;
    overflow-x: hidden;
}
.rigth {
   margin-right: 40px;
}
</style>