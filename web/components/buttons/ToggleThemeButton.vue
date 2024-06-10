<script setup lang="ts">
import { Moon, Sun } from 'lucide-vue-next';

import { Button } from '../ui/button';

const colorMode = useColorMode();

const isDark = computed({
  get() {
    return colorMode.value === 'dark';
  },
  set() {
    colorMode.preference = colorMode.preference === 'dark' ? 'light' : 'dark';
  },
});

defineProps<{
  outlined?: boolean;
}>();
</script>

<template>
  <Button :variant="outlined ? 'outline' : 'ghost'" size="icon" @click="isDark = !isDark">
    <Moon :size="20" :stroke-width="1.25" v-if="isDark" />
    <Sun :size="20" :stroke-width="1.25" v-else />
  </Button>
</template>
