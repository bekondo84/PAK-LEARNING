<template>
    <div v-if="type==='TRUEORFALSE'" role="status" class="animate-pulse w-full rounded-2xl border border-gray-200 dark:border-gray-700 p-4 flex flex-row justify-center items-start">
        <div class="w-full h-full flex flex-col gap-4 pr-2 justify-center items-start">
            <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            <div class="w-full flex flex-col gap-2.5 p-2.5 justify-start items-start">
                <div class="w-full flex flex-row gap-6 items-center">
                    <div class="w-5 aspect-square rounded-full bg-gray-200 dark:bg-gray-500"></div>
                    <div class="w-[100px] h-5 rounded-full bg-gray-200 dark:bg-gray-500"></div>
                </div>
                <div class="w-full flex flex-row gap-6 items-center">
                    <div class="w-5 aspect-square rounded-full bg-gray-200 dark:bg-gray-500"></div>
                    <div class="w-[100px] h-5 rounded-full bg-gray-200 dark:bg-gray-500"></div>
                </div>
            </div>
            <div class="w-[120px] h-8 p-4 rounded-lg bg-gray-200 dark:bg-gray-500"></div>
        </div>
        <div class="w-[150px] flex gap-2 justify-end">
            <div class="w-[30px] h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            <div class="w-[80px] h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
        </div>
    </div>

    <div v-if="type==='MULTI'" role="status" class="animate-pulse w-full rounded-2xl border border-gray-200 dark:border-gray-700 p-4 flex flex-row justify-center items-start">
        <div class="w-full h-full flex flex-col gap-4 pr-2 justify-center items-start">
            <div class="w-full h-7 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            <div class="w-full flex flex-col gap-2.5 p-2.5 justify-start items-start">
                <div class="w-full flex flex-row gap-6 items-center">
                    <div class="w-5 aspect-square rounded-full bg-gray-200 dark:bg-gray-500"></div>
                    <div class="w-[70%] h-5 rounded-full bg-gray-200 dark:bg-gray-500"></div>
                </div>
                <div class="w-full flex flex-row gap-6 items-center">
                    <div class="w-5 aspect-square rounded-full bg-gray-200 dark:bg-gray-500"></div>
                    <div class="w-[80%] h-5 rounded-full bg-gray-200 dark:bg-gray-500"></div>
                </div>
                <div class="w-full flex flex-row gap-6 items-center">
                    <div class="w-5 aspect-square rounded-full bg-gray-200 dark:bg-gray-500"></div>
                    <div class="w-[75%] h-5 rounded-full bg-gray-200 dark:bg-gray-500"></div>
                </div>
            </div>
            <div class="w-[120px] h-8 p-4 rounded-lg bg-gray-200 dark:bg-gray-500"></div>
        </div>
        <div class="w-[150px] flex gap-2 justify-end">
            <div class="w-[30px] h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
            <div class="w-[80px] h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
        </div>
    </div>
</template>

<script lang="ts">
    export default {
        name:"QuestionCardSkeleton",
        props:{
            type:{
                type:String,
                validator(value:string) {
                    return ['TRUEORFALSE', 'MULTI'].includes(value)
                }
            }
        }
    }
</script>

<style scoped>

</style>