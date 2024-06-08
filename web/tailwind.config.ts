import type { Config } from 'tailwindcss';

export default <Partial<Config>>{
  darkMode: 'class',
  theme: {
    extend: {
      fontFamily: {
        sans: 'Inter, sans-serif',
      },
      colors: {
        'oais-blue': {
          50: '#eff6ff',
          100: '#dae9ff',
          200: '#bddaff',
          300: '#90c3ff',
          400: '#58a0fe',
          500: '#367efb',
          600: '#205ef0',
          700: '#1849dd',
          800: '#1a3cb3',
          900: '#1b378d',
          950: '#152356',
        },
      },
    },
  },
};
