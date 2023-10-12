<template>
    <div class="w-full aspect-video rounded-2xl border-[2px] !border-pakBlue !bg-[#E6E6E6]">
        <div class="w-full p-4 !bg-[#F7F7F7] drop-shadow-xl !font-prototype !text-black rounded-t-2xl">
            <template v-if="isLoading"> {{ translator('loading.pdf.label') }} </template>
            <div v-else class="w-full flex flex-row justify-evenly">
                <span v-if="showAllPages"> {{ pageCount }} page(s) </span>
                <span v-else class="flex justify-center items-center gap-2">
                    <button class="px-4" :disabled="page <= 1" @click="page--">❮</button>
                        {{ page }} / {{ pageCount }}
                    <button class="px-4" :disabled="page >= pageCount" @click="page++">❯</button>
                </span>

                <label class="">
                    <input v-model="showAllPages" type="checkbox" />
                    {{ translator('show.allPages.label') }}
                </label>
            </div>
        </div>
        <div class="w-full aspect-video p-2">
            <div class="w-full aspect-video px-4 py-2 overflow-auto drop-shadow-md">
                <VuePdfEmbed
                    class="!w-full !h-fit"
                    ref="pdfRef"
                    :source="pdfSource"
                    :page="page"
                    @rendered="handleDocumentRender"
                />
            </div>
        </div>
        
    </div>
</template>

<script lang="ts">
    import VuePdfEmbed from 'vue-pdf-embed'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:'PDFViewer',
        components:{
            VuePdfEmbed
        },
        props:{
            src:String,
        },
        data() {
            return {
            isLoading: true,
            page: 0,
            pageCount: 1,
            showAllPages: true,
            translateHeaders: null,
            }
        },
        watch: {
            showAllPages() {
                this.page = this.showAllPages ? null : 1
            }
        },
        methods: {
            handleDocumentRender() {
                this.isLoading = false
                this.pageCount = this.$refs.pdfRef.pageCount
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['loading.pdf.label', 'show.allPages.label']);
        },
        computed:{
            pdfSource(){
                return this.src
            }
        }
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;

    ::-webkit-scrollbar {
        width: 15px;
    }

    /* Track */
    ::-webkit-scrollbar-track {
        @apply bg-grayToneStroke rounded-xl;
    }
    
    /* Handle */
    ::-webkit-scrollbar-thumb {
        @apply bg-pakDarkBlue rounded-xl;
    }

    /* Handle on hover */
    ::-webkit-scrollbar-thumb:hover {
        @apply bg-pakBlue
    }
</style>