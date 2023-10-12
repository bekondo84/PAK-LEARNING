<template>
    <div class="w-screen h-screen flex flex-col relative">
        <HomeHeader/>
        <router-view></router-view>
        <HomeFooter/>
    </div>
</template>

<script lang="ts">
    import HomeHeader from '@/components/v2.0/HomeHeader.vue'
    import HomeFooter from '@/components/v2.0/HomeFooter.vue'

    export default {
        name:'HomePage',
        components:{
            HomeHeader,
            HomeFooter,
        },
    }
</script>

<style>
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