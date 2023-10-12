<template>
    <div v-if="videoSource===''" role="status" class="w-full aspect-video rounded-2xl flex items-center justify-center bg-gray-300 animate-pulse dark:bg-gray-700">
        <Video :size="80" class="text-gray-200 dark:text-gray-600"/>
    </div>
    <div v-else class="w-full aspect-video relative">
        <video
            @pause="pause()"
            @play="play()"
            class="w-full aspect-video rounded-2xl border-[4px] !border-pakBlue absolute top-0 left-0"
            :src="videoSource"
            controls
            ref="player"
            :type="format"
        />
        <button v-if="!playing" @click="play" class="group w-[70px] aspect-square relative top-1/2 left-1/2 translate-x-[-50%] translate-y-[-50%] flex justify-center items-center rounded-full drop-shadow-xl shadow-md ">
            <div class="w-[70px] aspect-square absolute group-hover:animate-ping rounded-full !bg-pakBlue !text-white flex justify-center items-center">
            </div>
            <div class="w-[70px] aspect-square absolute rounded-full !bg-pakBlue !text-white flex justify-center items-center">
                <Play :size="40" class=""/>
            </div>
        </button>
    </div>
</template>

<script lang="ts">
    import Play from 'vue-material-design-icons/Play.vue'
    import Video from 'vue-material-design-icons/Video.vue'

    export default {
        name:'VideoPlayer',
        components:{
            Play,
            Video
        },
        props:{
            src:String,
            format:{
                type: String,
                default: "video/mp4"
            }
        },
        data(){
            return{
                playing: false,
            }
        },
        methods:{
            play() {
                this.$refs.player.play();
                this.playing = true;
            },

            pause() {
                this.$refs.player.pause();
                this.playing = false;
            },
            togglePlay() {
                if (this.playing) {
                    this.pause();
                } else {
                    this.play();
                }
            },
        },
        computed:{
            videoSource(){
                return this.src!=""?this.src:""
            }
        }
    }
</script>

<style scoped>

</style>