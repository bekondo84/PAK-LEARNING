<template>
  <router-view/>
</template>


<style lang="css">
@tailwind base;
@tailwind components;
@tailwind utilities;

:root{
    --pakBlue : #009DDC;
  }

  @font-face {
    font-family: 'PROTOTYPE';
    src: url('@/assets/fonts/Prototype.ttf');
  }

  @font-face {
    font-family: 'MYRIADBOLD';
    src: url('@/assets/fonts/MyriadProBold.ttf');
  }

  @font-face {
    font-family: 'CENTURYGOTHIC';
    src: url('@/assets/fonts/CenturyGothic.ttf');
  }

  @font-face {
    font-family: 'AGRESSIVE';
    src: url('@/assets/fonts/AGRESSIVE.otf');
  }
</style>
