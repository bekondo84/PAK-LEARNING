<template>
    <div class="w-full pb-1 border-b !border-grayToneStroke flex flex-col gap-2.5">
        <div class="w-full text-[40px] flex flex-row gap-1.5">
            <div v-if="courseName===''" role="status" class="w-full flex gap-2 animate-pulse mb-2">
                <div class="w-[200px] h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="w-full font-centuryGothic font-bold text-justify">{{ translator('course.btn.label') }} {{ courseIndex }} : {{ courseName }}</span>
        </div>
        <div class="w-full flex flex-col">
            <div class="flex gap-1 items-center text-grayTone1">
                <ClockTimeFour :size="20" class=""/>
                <span class="font-centuryGothic font-bold text-left text-lg">{{ translator('duration.label') }}</span>
            </div>
            <div v-if="duration===''" role="status" class="animate-pulse pl-6">
                <div class="w-[30px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="pl-6 font-centuryGothic text-left">{{ duration }}</span>
        </div>
    </div>
    <PDFViewer :src="pdfRessource"/>
    
    <div class="w-full flex flex-col gap-2 font-bold">
        <span class="font-myriadBold text-xl text-grayTone1 text-left">{{ translator('description.label') }}</span>
        <div v-if="description===''" role="status" class="animate-pulse w-full flex flex-col gap-2">
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
                        <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-80"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
                        <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-32"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
            </div>
            <div class="flex items-center w-full gap-2">
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-80"></div>
                <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
            </div>
        </div>
        <span v-else class="font-centuryGothic text-justify">
            {{ description }}
        </span>
    </div>
</template>

<script lang="ts">
    import ClockTimeFour from 'vue-material-design-icons/ClockTimeFour.vue'
    import store from '@/store';
    import PDFViewer from '@/components/v2.0/UIElements/PDFViewer.vue'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"LessonPDF",
        components:{
            ClockTimeFour,
            PDFViewer
        },
        data(){
            return{
                lessonId: this.$route.params.lessonId,
                courseId: this.$route.params.courseId,
                courseDetails: null,
                pdfSource: null,
                translateHeaders: null,
                // "https://raw.githubusercontent.com/mozilla/pdf.js/ba2edeae/web/compressed.tracemonkey-pldi-09.pdf"
            }
        },
        methods: {
            async getCourse() {
                if (this.courseId != "") {
                    this.courseDetails = await axiosService.getCourse(this.courseId, this.lessonId)
                }
                if(this.courseDetails != null){
                    const response = await axiosService.getResourceAsStream(this.courseDetails.resource.pk, this.courseDetails.resource.file);
                    this.pdfSource = window.URL.createObjectURL(new Blob([response]));
                    // console.log(this.pdfSource)
                }
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['course.btn.label', 'duration.label', 'description.label']);
        },
        async mounted() {
            await this.getCourse();
        },
        updated(){
            this.courseId = this.$route.params.courseId
        },
        watch:{
            async courseId(){
                this.pdfSource = null
                await this.getCourse();
            }
        },
        computed:{
            courseName(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr" ;
                const name = this.courseDetails != null ? this.courseDetails.name : null ;
                return name!=null? name[lang].value : ""
            },
            courseIndex(){
                return this.courseDetails != null ? this.courseDetails.sequence:0
            },
            description(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr" ;
                const content = this.courseDetails != null ? this.courseDetails.content : null ;
                return content!=null? content[lang].value : ""
            },
            pdfRessource(){
                return this.pdfSource
            },
            duration(){
                // const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return this.courseDetails!=null? "30 mins":""
            },
        }
    }
</script>

<style scoped>

</style>