<script lang="ts" setup>
import { Toaster } from '@/components/ui/sonner';

const colorMode = useColorMode();
const theme = computed(() => colorMode.value as 'light' | 'dark');

useHead({
  titleTemplate: title => (title ? `${title} · Oais Billing` : 'Oais Billing'),
  meta: [{ name: 'viewport', content: 'width=device-width, initial-scale=1' }],
  link: [{ rel: 'icon', type: 'image/svg+xml', href: '/logo.svg' }],
  htmlAttrs: {
    lang: 'pt',
  },
});
</script>

<template>
  <NuxtLoadingIndicator color="#0084ff" :height="2" />
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
  <Toaster rich-colors :theme="theme" />
</template>
