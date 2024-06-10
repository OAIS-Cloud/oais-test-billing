<script lang="ts" setup>
import { X } from 'lucide-vue-next';

import { ToggleThemeButton } from '../buttons';
import { LanguageSelect } from '../select-menus';
import { Button } from '../ui/button';
import { sidebarItems } from '.';

const route = useRoute();
const { mobileMenuOpen, setMobileMenuOpen } = useMobileMenu();

onMounted(() => {
  watch(
    () => route.path,
    () => {
      setMobileMenuOpen(false);
    },
    {
      flush: 'post',
    },
  );
});
</script>

<template>
  <aside
    :class="[
      'fixed inset-0 z-30 size-full flex-col bg-background p-8 pt-12 md:sticky md:top-14 md:flex md:h-[calc(100vh-3.5rem)] md:w-64 md:p-0 md:pr-4 md:pt-8',
      {
        flex: mobileMenuOpen,
        hidden: !mobileMenuOpen,
      },
    ]"
  >
    <Button
      v-if="mobileMenuOpen"
      variant="ghost"
      size="icon"
      class="absolute right-4 top-4 hover:text-red-500 md:hidden"
      @click="() => setMobileMenuOpen(false)"
    >
      <X />
    </Button>

    <div class="w-full space-y-4">
      <div v-for="(sidebarItem, index) in sidebarItems" :key="`sidebar-item-${index}`">
        <div class="flex items-center px-2 text-sm">
          <span class="font-bold text-foreground">
            {{ $t(sidebarItem.category) }}
          </span>
        </div>

        <div class="flex flex-col gap-y-1 pt-2">
          <RouterLink
            v-for="item in sidebarItem.items"
            :key="item.href"
            :to="item.href"
            class="block"
          >
            <Button
              variant="ghost"
              :class="['h-8 w-full justify-start px-2', route.path === item.href ? 'bg-muted' : '']"
            >
              {{ $t(item.name) }}
            </Button>
          </RouterLink>
        </div>
      </div>
    </div>

    <div v-if="mobileMenuOpen" class="mt-auto border-t pt-4 md:hidden">
      <ClientOnly>
        <div class="flex items-center gap-4">
          <LanguageSelect />
          <ToggleThemeButton outlined />
        </div>
      </ClientOnly>
    </div>
  </aside>
</template>
