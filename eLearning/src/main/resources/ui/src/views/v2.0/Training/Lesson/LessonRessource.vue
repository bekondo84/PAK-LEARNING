<template>
    <div class="w-full pb-1 border-b !border-grayToneStroke flex flex-col gap-2.5">
        <div class="w-full text-[40px] flex flex-row gap-1.5">
            <div v-if="lessonName===''" role="status" class="w-full flex gap-2 animate-pulse mb-2">
                <div class="w-[200px] h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            </div>
            <span v-else class="font-centuryGothic font-bold text-justify">{{ translator('lesson.btn.label') }} {{ lessonIndex }} : {{ lessonName }}</span>
        </div>
        <span class="font-centuryGothic font-bold text-left text-[30px] text-grayTone1">{{ translator('resource.join.label') }}</span>
        <span class="font-centuryGothic font-bold text-justify">
            {{ translator('lesson.resource.msg') }}
        </span>
    </div>
    
    <div v-if="resourceList.length===0" class="w-full flex flex-col gap-5">
        <div role="status" class="animate-pulse w-full flex flex-col gap-1.5">
            <div class="w-[300px] h-4 bg-gray-200 rounded-full dark:bg-gray-400"></div>
            <div class="w-full pl-6 flex flex-col gap-2">
                <div class="flex items-center w-full gap-2">
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                </div>
                <div class="flex items-center w-full gap-2">
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                </div>
            </div>
            <div class="w-full flex justify-between items-center pl-6">
                <div class="w-[500px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-[150px] h-12 px-3 py-2 flex justify-center items-center bg-gray-200 rounded-lg dark:bg-gray-700">
                </div>
            </div>
        </div>
        <div role="status" class="animate-pulse w-full flex flex-col gap-1.5">
            <div class="w-[300px] h-4 bg-gray-200 rounded-full dark:bg-gray-400"></div>
            <div class="w-full pl-6 flex flex-col gap-2">
                <div class="flex items-center w-full gap-2">
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                </div>
                <div class="flex items-center w-full gap-2">
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                </div>
            </div>
            <div class="w-full flex justify-between items-center pl-6">
                <div class="w-[500px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-[150px] h-12 px-3 py-2 flex justify-center items-center bg-gray-200 rounded-lg dark:bg-gray-700">
                </div>
            </div>
        </div>
        <div role="status" class="animate-pulse w-full flex flex-col gap-1.5">
            <div class="w-[300px] h-4 bg-gray-200 rounded-full dark:bg-gray-400"></div>
            <div class="w-full pl-6 flex flex-col gap-2">
                <div class="flex items-center w-full gap-2">
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                </div>
                <div class="flex items-center w-full gap-2">
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-full"></div>
                    <div class="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-full"></div>
                    <div class="h-2.5 bg-gray-300 rounded-full dark:bg-gray-600 w-24"></div>
                </div>
            </div>
            <div class="w-full flex justify-between items-center pl-6">
                <div class="w-[500px] h-4 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                <div class="w-[150px] h-12 px-3 py-2 flex justify-center items-center bg-gray-200 rounded-lg dark:bg-gray-700">
                </div>
            </div>
        </div>
    </div>
    <div v-else v-for="(ressource, index) in resourceList"
        :key="index"
        class="w-full flex flex-col"
    >
        <div class="flex gap-1 items-center text-grayTone1">
            <Paperclip :size="20" class=""/>
            <span class="font-centuryGothic font-bold text-left text-lg">
                {{ ressource.name }}
            </span>
        </div>
        <span class="pl-6 font-centuryGothic text-left">
            {{ ressource.description }}
        </span>
        <div class="w-full pl-6 flex items-center gap-2">
            <FileDocumentMultiple v-if="(ressource.type === 'docx' || ressource.type === 'pdf')" :size="16" class=""/>
            <PlayCircle v-if="(ressource.type === 'mp4')" :size="16" class=""/>
            <span class="w-full font-bold">{{ ressource.filename }}</span>
            <button @click="downloadRessource(ressource.pk, ressource.filename)" class="flex items-center gap-2 font-centuryGothic !text-[#34A853] font-bold px-3 py-2 rounded-lg border !border-[#34A853] !bg-white hover:!bg-[#34A853] hover:!text-white">
                <span class="">{{ translator('download.resource.btn') }}</span>
                <Download :size="20" class=""/>
            </button>
        </div>
    </div>

    <div class="w-full flex flex-col gap-2 font-bold">
        <span class="font-myriadBold text-xl text-grayTone1 text-left">{{ translator('lesson.resource.remarks') }}</span>
        <ul class="font-centuryGothic text-justify list-disc pl-5">
            <li>
                {{ translator('lesson.resource.hint1') }} {{ ressourceFormats }}.
            </li>
            <li>
                {{ translator('lesson.resource.hint2') }}
            </li>
            <li>
                {{ translator('lesson.resource.hint3') }}
            </li>
        </ul>
    </div>
</template>

<script lang="ts">
    import Paperclip from 'vue-material-design-icons/Paperclip.vue'
    import Download from 'vue-material-design-icons/Download.vue'
    import PlayCircle from 'vue-material-design-icons/PlayCircle.vue' 
    import FileDocumentMultiple from 'vue-material-design-icons/FileDocumentMultiple.vue'
    import store from '@/store';
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name: "LessonRessource",
        components:{
            Paperclip,
            Download,
            FileDocumentMultiple,
            PlayCircle,
        },
        data(){
            return{
                // Integrated Data
                lessonDetails: null,
                overlay: false,
                translateHeaders: null,
            }
        },
        methods:{
            async loadData() {
                try {
                    const lessonId = this.$route.params.lessonId;
                    this.lessonDetails = await axiosService.leconResult(lessonId);
                } catch (error) {
                    this.error = true ;
                }
            },
            async downloadRessource(pk: number, filename:string) {
                this.overlay = true ;
                try{
                    await axiosService.download(pk, filename);
                } catch( error) {
                    console.error(error);
                }
                this.overlay = false ;
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['lesson.btn.label', 'resource.join.label', 'lesson.resource.msg', 'download.resource.btn', 'lesson.resource.remarks', 'lesson.resource.hint1', 'lesson.resource.hint2', 'lesson.resource.hint3']);
        },
        async mounted() {
            this.overlay = true ;
            await this.loadData();
            this.overlay = false;
        },
        computed:{
            lessonName(){
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                return this.lessonDetails!=null?this.lessonDetails.activity.name[lang].value:""
            },
            lessonIndex(){
                return this.lessonDetails!=null? this.lessonDetails.activity.sequence:0
            },
            resourceList(){
                let tempList = []
                const lang = store.getters.getCurrentSession!=null?store.getters.getCurrentSession.language:"fr";
                if(this.lessonDetails!=null && this.lessonDetails.activity.resources.length>0){
                    tempList = this.lessonDetails.activity.resources.map((resource:any)=>{
                        return {
                            pk: resource.pk,
                            name: resource.name,
                            description: resource.description[lang].value,
                            filename: resource.file,
                            type: resource.file.substring(resource.file.lastIndexOf('.')+1)
                        }
                    })
                }
                return tempList
            },
            ressourceFormats(){
                let formats = ""
                this.resourceList.forEach((resource:any) => {
                    formats += `.${resource.type} `
                });
                return formats
            }
        }
    }
</script>

<style scoped>

</style>