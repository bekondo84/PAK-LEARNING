<template>
    <div class="w-full flex flex-row p-4 bg-white rounded-[12px] border !border-slate-300 justify-start items-center gap-2.5 focus:!border-pakBlue valid:!border-pakBlue">
        <input type="search" name="" id="" :value="modelValue" :placeholder="placeholder" @input="(event) => $emit('setNewSearchValue', (event.target as HTMLInputElement).value.trim())"  class="w-full h-[30px] px-2 placeholder:text-[#B8BFCC] placeholder:text-[20px] border-none outline-none">
        <Magnify :size="24" class=""/>
    </div>
</template>

<script lang="ts">
    import Magnify from 'vue-material-design-icons/Magnify.vue'
    
    export default {
        name:"SearchInput",
        components:{
            Magnify
        },
        props:{
            modelValue: {
                type: String,
                default: "",
            },
            placeholder:{
                type: String,
                default:"Rechercher..."
            }
        },
        emits: ['setNewSearchValue']
    }
</script>

<style scoped>
@tailwind base;
@tailwind components;
@tailwind utilities;
</style>