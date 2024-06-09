<script lang="ts" setup>
import { AppHeader, AppSidebar } from '~/components/layout';
</script>

<template>
  <div>
    <AppHeader />
    <div class="container flex">
      <AppSidebar class="md:-ml-2" />
      <div class="flex-1 py-8 md:px-4">
        <slot />
      </div>
    </div>
  </div>
</template>
