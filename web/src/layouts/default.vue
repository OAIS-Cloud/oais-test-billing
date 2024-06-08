<script lang="ts" setup>
const { toggleTheme } = useTheme();
</script>

<template>
  <div>
    <UButton @click="toggleTheme" size="sm" square icon="i-heroicons-sun" variant="solid" />
    <slot />
  </div>
</template>
