<template>
    <button @click="()=>{!viewOnly?routeToElement():null}" :class="isActive?activeClass:isCurrentActivity?currentClass:defaultClass" class="flex flex-row gap-1 p-4 rounded-lg justify-start items-center hover:!bg-pakDarkBlue hover:!text-white">
        <BookOpenVariant v-if="elementType === 'Leçon'" :size="20" class=""/>
        <TextBoxEdit v-if="elementType === 'Test'" :size="20" class=""/>
        <FileDocumentEdit v-if="elementType === 'Devoir'" :size="20" class=""/>
        <PlayCircle v-if="elementType === 'Video'" :size="20" class=""/>
        <FileDocumentMultiple v-if="elementType === 'PDF'" :size="20" class=""/>
        <Information v-if="lessonStart || trainingStart" :size="20" class=""/>

        <span v-if="!trainingStart && !lessonStart" class="">{{ displayLabel }} {{ index }} :</span> 
        <span v-if="trainingStart" class="grow shrink basis-0 font-bold text-left">{{ translator('training.inCharge.label') }}</span>
        <span v-if="!trainingStart" class="grow shrink basis-0 font-bold text-left">{{ lessonStart?translator('lesson.inCharge.label'):title }}</span>

        <span class="">{{ duration }}</span>
    </button>
</template>

<script lang="ts">
    import BookOpenVariant from 'vue-material-design-icons/BookOpenVariant.vue'  // Course
    import Information from 'vue-material-design-icons/Information.vue'
    import TextBoxEdit from 'vue-material-design-icons/TextBoxEdit.vue'  // Exam
    import PlayCircle from 'vue-material-design-icons/PlayCircle.vue'  // Video
    import FileDocumentEdit from 'vue-material-design-icons/FileDocumentEdit.vue' // Homework
    import FileDocumentMultiple from 'vue-material-design-icons/FileDocumentMultiple.vue'
    import AxiosService from '@/services/axiosService';

    const axiosService = new AxiosService();
    export default {
        name:"SommaireComponents",
        components:{
            BookOpenVariant,
            TextBoxEdit,
            FileDocumentEdit,
            Information,
            PlayCircle,
            FileDocumentMultiple
        },
        props:{
            index:Number,
            code: String,
            isTrainingActivity: {
                type: Boolean,
                default: false
            },
            elementType:{
                type: String,
                validator(value:string) {
                    return ['Leçon', 'Devoir', 'Test', 'PDF', 'Video'].includes(value)
                }
            },
            title:{
                type: String,
                default:""
            },
            duration: String,
            viewOnly: {
                type: Boolean,
                default: false,
            },
            isCurrentActivity:{
                type: Boolean,
                default: false
            },
            lessonStart:{
                type: Boolean,
                default: false,
            },
            trainingStart:{
                type: Boolean,
                default: false,
            },
            indexUrl: String,
            isActive: Boolean,
        },
        data(){
            return{
                activeClass: '!bg-pakDarkBlue !text-white',
                defaultClass: 'bg-white text-black',
                currentClass: '!bg-[#34A853] !text-white',
                translateHeaders: null,
            }
        },
        methods:{
            async routeToElement(){
                // We Start to check of it is Activity in a Training
                if(this.elementType === 'Leçon' && this.isTrainingActivity){
                    await axiosService.startActivity(this.code);
                    this.$router.push({name: "LessonIndex", params:{lessonId: this.code}})
                }else if(this.elementType === 'Test' && this.isTrainingActivity){
                    await axiosService.startActivity(this.code);
                    this.$router.push({name: "TrainingTest", params:{testId: this.code}})
                }else // We check if it is an Activity in a Lesson
                if(this.elementType === 'PDF'){
                    this.$router.push({name: "LessonPDF", params:{courseId: this.code}})
                }else if(this.elementType === 'Video'){
                    this.$router.push({name: "LessonVideo", params:{courseId: this.code}})
                }else if(this.elementType === 'Test'){
                    this.$router.push({name: "LessonTest", params:{testId: this.code}})
                }else if(this.lessonStart || this.trainingStart){
                    this.$router.push(this.indexUrl)
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
            this.translateHeaders = await axiosService.translate(['course.btn.label','lesson.btn.label','training.inCharge.label', 'lesson.inCharge.label']);
        },
        computed:{
            displayLabel(){
                if(this.elementType === "Video" || this.elementType === "PDF"){
                    return this.translator('course.btn.label')
                }else if(this.elementType === "Leçon"){
                    return this.translator('lesson.btn.label')
                }else{
                    return this.elementType
                }
            },
        }
    }
</script>

<style>
</style>