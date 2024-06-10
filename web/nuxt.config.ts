// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      API_BASE_URL: process.env.API_BASE_URL,
    },
  },
  modules: [
    '@pinia/nuxt',
    '@nuxtjs/color-mode',
    '@nuxt/fonts',
    '@nuxtjs/i18n',
    'shadcn-nuxt',
    '@nuxtjs/tailwindcss',
  ],
  tailwindcss: {
    cssPath: '~/assets/css/tailwind.css',
  },
  colorMode: {
    classSuffix: '',
    preference: 'light',
  },
  fonts: {
    families: [
      {
        name: 'Inter',
        provider: 'google',
      },
    ],
    provider: 'google',
  },
  i18n: {
    strategy: 'no_prefix',
    langDir: '../locales',
    defaultLocale: 'pt-BR',
    locales: [
      {
        code: 'pt-BR',
        iso: 'pt-BR',
        name: 'Português',
        file: 'pt-BR.json',
      },
      {
        code: 'en-US',
        iso: 'en-US',
        name: 'English (US)',
        file: 'en-US.json',
      },
    ],
  },
});
