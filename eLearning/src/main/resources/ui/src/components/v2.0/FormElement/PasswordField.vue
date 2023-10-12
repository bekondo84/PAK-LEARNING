<template>
    <div class="inputBox">
        <button class="h-full px-4 text-[#B8BFCC] absolute text-center z-10 right-0 hover:text-pakBlue" @click="showPassword = !showPassword">
            <EyeOutline v-if="!showPassword" :size="20" class=""/>
            <EyeOffOutline v-if="showPassword" :size="20" class=""/>
        </button>
        <input v-if="!showPassword" required :value="modelValue" minlength="7" @input="(event) => $emit('update:modelValue', (event.target as HTMLInputElement).value.trimEnd())" type="password" :disabled="disabled" class="z-0 outline-none text-black"/>
        <input v-if="showPassword" required :value="modelValue" minlength="7" @input="(event) => $emit('update:modelValue', (event.target as HTMLInputElement).value.trimEnd())" type="text" :disabled="disabled" class="z-0 outline-none text-black"/>
        <span class='capitalize password-label'>{{ disabled?"":label }}</span>
    </div>
</template>

<script lang="ts">
    import EyeOutline from 'vue-material-design-icons/EyeOutline.vue'
    import EyeOffOutline from 'vue-material-design-icons/EyeOffOutline.vue'

    export default {
        name:"PasswordField",
        components:{
            EyeOutline,
            EyeOffOutline
        },
        props:{
            label: String,
            disabled:{
                type: Boolean,
                default: false,
            },
            modelValue: {
                type: String,
                default: "",
            },
        },
        data(){
            return{
                showPassword: false,
            }
        }
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
  
  .inputBox .password-label {
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
  
  .inputBox input:focus ~ .password-label,
  .inputBox input:valid ~ .password-label {
    font-size: 16px;
    transform: translateX(14px) translateY(-7.5px);
    padding: 0 5px;
    border-radius: 2px;
    background: white;
    letter-spacing: 0em;
    color: var(--pakBlue);
  }
</style>