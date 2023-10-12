<template>
    <HomeHeader/>
    <router-view></router-view>
    <ChatBot/>
</template>

<script lang="ts">
    import HomeHeader from '@/components/v2.0/HomeHeader.vue'
    import ChatBot from '@/components/v2.0/Training/ChatBot.vue'

    export default {
        name:"TraningLayout",
        components:{
            HomeHeader,
            ChatBot
        }
    }
</script>

<style>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;

    ::-webkit-scrollbar {
        width: 15px;
        @apply mt-2 mb-2
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