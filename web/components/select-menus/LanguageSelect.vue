<script lang="ts" setup>
import { BRFlag, USFlag } from '../flags';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';

const { locale, setLocale } = useI18n();
</script>

<template>
  <Select :model-value="locale" @update:model-value="setLocale">
    <SelectTrigger class="w-[160px]">
      <SelectValue class="truncate" />
    </SelectTrigger>
    <SelectContent>
      <SelectItem value="pt-BR">
        <div class="flex">
          <BRFlag class="mr-2 w-4 min-w-4" />
          <span class="truncate">Português</span>
        </div>
      </SelectItem>
      <SelectItem value="en-US">
        <div class="flex">
          <USFlag class="mr-2 w-4 min-w-4" />
          <span class="truncate">English (US)</span>
        </div>
      </SelectItem>
    </SelectContent>
  </Select>
</template>
