<template>
    <div class="w-full flex flex-row gap-4 items-center">
        <div class="w-full h-fit rounded-[16px] bg-[#F2F2F2] p-1.5 flex justify-start items-center">
            <div class="h-[10px] bg-[#34A853] flex rounded-[16px]" :class="progressWidth">
            </div>
        </div>
        <span class="w-[180px] font-bold flex">{{ value }}% {{ label }}</span>
    </div>
</template>

<script lang="ts">
    export default {
        name:"ProgressBar",
        props:{
            value:{
                type: Number,
                default:0
            },
            label:{
                type: String,
                default:"de Progrès"
            }
        },
        computed:{
            progressWidth(){
                const value  = this.value
                if(value === 100){
                    return  'w-full'
                }else if(value >= 70){
                    return 'w-['+value+'%]'
                }else if(value>=65 && value < 70){
                    return 'w-2/3'
                }else if(value >= 60 && value < 65){
                    return 'w-3/5'
                }else if(value >= 55 && value < 60){
                    return 'w-7/12'
                }else if(value >= 50 && value < 55){
                    return 'w-1/2'
                }else if(value >= 40 && value < 50){
                    return 'w-2/5'
                }else if(value >= 30 && value < 40){
                    return 'w-1/3'
                }else if(value >= 25 && value < 30){
                    return 'w-1/4'
                }else if(value >= 20 && value < 25){
                    return 'w-1/5'
                }else if(value >=15 && value < 20){
                    return 'w-1/6'
                }else if(value >= 8 && value < 15){
                    return 'w-1/12'
                }else {
                    return 'w-[10px]'
                }
            }
        }
    }
</script>

<style>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
</style>