<template>
    <div className="inputBox">
        <select className="custom-dropdown" :value="modelValue" @change="(event) => $emit('update:modelValue', (event.target as HTMLInputElement).value.trimEnd())">
            <option v-if="placeholder !== ''" value="" disabled selected hidden ><span className='text-[#0B5DA7]'>{{ placeholder }}</span></option>
            <option  v-if="defaultValue !== ''" value="" disabled selected hidden>{{ defaultValue }}</option>
            <option
                v-for="(option, index) in optionList"
                :key="index"
                :value="option"
                className='py-4 px-5 text-base h-4'
            >
                {{ option }}
            </option>
        </select>
        <span className='capitalize'>{{ label }}</span>
    </div>
</template>

<script lang="ts">
    export default {
        name:"DropDownField",
        components:{

        },
        props:{
            defaultValue: String,
            placeholder: String,
            optionList: Array,
            label: String,
            modelValue: String,
        },
        data(){
            return{

            }
        },
        methods:{

        },
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
    /* custom-dropdown */
    .inputBox {
        position: relative;
        width: 400px;
    }

    .inputBox span {
    position: absolute;
    left: 0;
    padding: 15px 20px;
    pointer-events: none;
    font-size: 16px;
    transition: 0.4s cubic-bezier(0.05, 0.81, 0, 0.93);
    @apply text-pakBlue;
    letter-spacing: 0.1em;
  }
  .inputBox .custom-dropdown {
    padding: 15px 20px;
    outline: none;
    background: transparent;
    border-radius: 5px;
    color: black;
    @apply border-2 border-pakBlue;
    font-size: 1em;
    width: 100%;
  }

  .inputBox .custom-dropdown:focus{
    @apply border-2 border-pakBlue;
  }

  .inputBox .custom-dropdown:focus ~ span,
  .inputBox .custom-dropdown:valid ~ span {
    font-size: 16px;
    transform: translateX(14px) translateY(-7.5px);
    padding: 0 5px;
    border-radius: 2px;
    background: white;
    letter-spacing: 0em;
    @apply text-pakBlue
  }
</style>