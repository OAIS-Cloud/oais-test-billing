// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  modules: ['@pinia/nuxt', '@nuxtjs/color-mode', '@nuxt/fonts', '@nuxt/ui', '@nuxtjs/i18n'],
  srcDir: './src',
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
    lazy: true,
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
