<script lang="ts" setup>
import type { NuxtError } from '#app';

import { Button } from './components/ui/button';

defineProps({
  error: Object as () => NuxtError,
});

const handleError = () => clearError({ redirect: '/' });
</script>

<template>
  <NuxtLayout name="404">
    <div class="flex flex-col items-center">
      <div class="text-6xl font-bold sm:text-8xl">{{ error?.statusCode }}</div>
      <div class="mt-4 text-sm text-muted-foreground">Oops! Parece que você está perdido 🤔</div>
      <Button variant="secondary" class="mt-8 w-full font-bold" @click="handleError">Back</Button>
    </div>
  </NuxtLayout>
</template>
