<template>
    <div class="inputBox">
        <input required :value="modelValue" @input="(event) => $emit('update:modelValue', (event.target as HTMLInputElement).value.trimEnd())" :type="type" :disabled="disabled"/>
        <span class='capitalize'>{{disabled?"":label}}</span>
    </div>
</template>

<script lang="ts">
    export default {
        name: 'InputField',
        props:{
            label: String,
            type:{
              type: String,
              default: 'text'
            },
            disabled:{
                type: Boolean,
                default: false,
            },
            modelValue: {
                type: String,
                default: "",
            },
        },
    }
</script>

<style scoped>
  .inputBox {
    position: relative;
    width: 400px;
  }
  
  .inputBox input {
    padding: 15px 20px;
    outline: none;
    background: white;
    border-radius: 5px;
    color: black;
    border: 2px solid#B8BFCC;
    font-size: 1em;
    width: 100%;
  }
  
  .inputBox span {
    position: absolute;
    left: 0;
    padding: 15px 20px;
    pointer-events: none;
    font-size: 16px;
    transition: 0.4s cubic-bezier(0.05, 0.81, 0, 0.93);
    color: #B8BFCC;
    letter-spacing: 0.1em;
  }

  .inputBox input:focus, .inputBox input:valid{
    border: 2px solid var(--pakBlue);
  }
  
  .inputBox input:focus ~ span,
  .inputBox input:valid ~ span {
    font-size: 16px;
    transform: translateX(14px) translateY(-7.5px);
    padding: 0 5px;
    border-radius: 2px;
    background: white;
    letter-spacing: 0em;
    color: var(--pakBlue);
  }
</style>