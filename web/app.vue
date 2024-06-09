<script lang="ts" setup>
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
</template>
