<script setup lang="ts">
import { AlignRight } from 'lucide-vue-next';

import { ToggleThemeButton } from '../buttons';
import { LanguageSelect } from '../select-menus';
import { Button } from '../ui/button';

defineProps<{
  hideMobileMenuButton?: boolean;
}>();

const { setMobileMenuOpen } = useMobileMenu();
</script>

<template>
  <header class="sticky top-0 z-10 h-[var(--header-height)] w-full bg-background">
    <div class="container flex h-full items-center">
      <a href="/" class="flex items-center gap-2 text-xl">
        <img src="/logo.svg" alt="Logo Oais Cloud" class="h-7" />
        <strong class="hidden md:block">OAIS <span class="text-blue-500">BILLING</span></strong>
      </a>

      <Button
        v-if="!hideMobileMenuButton"
        variant="ghost"
        size="icon"
        class="ml-auto md:hidden"
        @click="() => setMobileMenuOpen(true)"
      >
        <AlignRight />
      </Button>

      <div class="ml-auto hidden gap-2 md:flex">
        <ClientOnly>
          <LanguageSelect />
          <ToggleThemeButton />
        </ClientOnly>
      </div>
    </div>
  </header>
</template>
