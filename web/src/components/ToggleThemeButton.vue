<script setup lang="ts">
const colorMode = useColorMode();

function toggleTheme() {
  colorMode.preference = colorMode.value === 'light' ? 'dark' : 'light';
}
</script>

<template>
  <UTooltip :text="$t(`tooltip.toggle_theme.${colorMode.value}`)">
    <UButton
      size="sm"
      square
      variant="ghost"
      @click="toggleTheme"
      :icon="colorMode.value === 'dark' ? 'i-heroicons-moon' : 'i-heroicons-sun'"
    />
  </UTooltip>
</template>
