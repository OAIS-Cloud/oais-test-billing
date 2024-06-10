<script lang="ts" setup>
import { AppHeader } from '~/components/layout';
</script>

<template>
  <div>
    <AppHeader hide-mobile-menu-button />
    <div class="container flex w-full items-center justify-center pt-44">
      <slot />
    </div>
  </div>
</template>

<style scoped></style>
