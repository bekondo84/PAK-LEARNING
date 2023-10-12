/** @type {import('tailwindcss').Config} */

module.exports = {
  content: [
    // "./app/**/*.{js,ts,jsx,tsx,mdx}",
    // "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    // "./components/**/*.{js,ts,jsx,tsx,mdx}",
    
    './public/index.html',
    // Or if using `src` directory:
    './src/**/*.{vue,js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        transparent: 'transparent',
        current: 'currentColor',
        'pakYellow': '#F2AA06',
        'pakBlue': '#009DDC',
        'pakDarkBlue': '#0958A9',
        'lightBlue': '#E8F2F8',
        'whiteTone': '#F9F9F9',
        'grayToneStroke': '#B8BFCC',
        'grayTone1': '#3C404B',
        'grayTone2': '#6C768A',
        'grayTone3': '#A0ABC0',
        'textDarkTone': '#23252F',
      },
      fontFamily:{
        'prototype' : 'PROTOTYPE',
        'myriadBold': 'MYRIADBOLD',
        'centuryGothic': 'CENTURYGOTHIC',
        'agressive': 'AGRESSIVE',
      },
      dropShadow: {
        '3xl': '0 35px 35px rgba(0, 0, 0, 0.25)',
        '4xl': [
            '0 35px 35px rgba(0, 0, 0, 0.25)',
            '0 45px 65px rgba(0, 0, 0, 0.15)'
        ]
      },
      keyframes: {
        typing: {
          "0%": {
            width: "0%",
            visibility: "hidden"
          },
          "100%": {
            width: "100%"
          }  
        },
        blink: {
          "50%": {
            borderColor: "transparent"
          },
          "100%": {
            borderColor: "white"
          }  
        }
      },
      animation: {
        typing: "typing 2s steps(20) , blink .7s infinite"
      },
    },
    screens: {
      '2xl': {'max': '1535px'},
      // => @media (max-width: 1535px) { ... }

      'xl': {'max': '1279px'},
      // => @media (max-width: 1279px) { ... }

      'lg': {'max': '1023px'},
      // => @media (max-width: 1023px) { ... }

      'md': {'max': '767px'},
      // => @media (max-width: 767px) { ... }

      'sm': {'max': '639px'},
      // => @media (max-width: 639px) { ... }
    },
  },
  plugins: [require('@tailwindcss/forms')({
    // strategy: 'base', // only generate global styles
    strategy: 'class', // only generate classes
  }),],
}

