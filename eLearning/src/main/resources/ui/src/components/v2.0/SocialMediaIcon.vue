<template>
    <div v-if="type === 'Contact'" class="group rounded-full relative">
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden">
            <Phone :size="28" class="text-white"/>
        </button>
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block">
            <Phone :size="28" class="text-pakYellow"/>
        </button>
        <span class="w-[180px] absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-2 text-xl">{{ text }}</span>
    </div>

    <div v-if="type === 'Email'" class="group rounded-full relative">
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden">
            <EmailOutline :size="28" class="text-white"/>
        </button>
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block">
            <img src="../../assets/images/gmailLogo.png" alt="" class="w-[28px] aspect-square">
        </button>
        <span class="absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl">{{ text }}</span>
    </div>

    <div v-if="type === 'Facebook'" class="group rounded-full relative">
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden">
            <Facebook :size="28" class="text-white"/>
        </button>
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block">
            <Facebook :size="28" class="text-pakDarkBlue"/>
        </button>
        <span class="absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl">{{ text }}</span>
    </div>

    <div v-if="type === 'Linkedin'" class="group rounded-full relative">
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden">
            <Linkedin :size="28" class="text-white"/>
        </button>
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block">
            <Linkedin :size="28" class="text-pakDarkBlue"/>
        </button>
        <span class="absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl">{{ text }}</span>
    </div>

    <div v-if="type === 'Twitter'" class="group rounded-full relative">
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] group-hover:hidden">
            <Twitter :size="28" class="text-white"/>
        </button>
        <button class="p-2 rounded-full bg-[rgba(255,255,255,0.3)] hidden group-hover:block">
            <Twitter :size="28" class="text-pakDarkBlue"/>
        </button>
        <span class="absolute bottom-[-40px] left-[5px] hidden group-hover:block bg-[rgba(255,255,255,0.3)] rounded-md text-white font-myriadBold p-1 px-4 text-xl">{{ text }}</span>
    </div>
</template>

<script lang="ts">
    import Phone from 'vue-material-design-icons/Phone.vue'
    import EmailOutline from 'vue-material-design-icons/EmailOutline.vue'
    import Facebook from 'vue-material-design-icons/Facebook.vue'
    import Linkedin from 'vue-material-design-icons/Linkedin.vue'
    import Twitter from 'vue-material-design-icons/Twitter.vue'

export default{
    name: 'SocialMediaIcon',
    props: {
        type:String,
        text:String
    },
    components:{
            Phone,
            EmailOutline,
            Facebook,
            Linkedin,
            Twitter,
        },
    setup () {
        return {}
    },
    data(){
        return{

        }
    }
}
</script>

<style scoped>

</style>