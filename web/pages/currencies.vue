<script setup lang="ts">
import { EllipsisVertical, SquarePen, Trash2 } from 'lucide-vue-next';
import { toast } from 'vue-sonner';

import { CreateCurrencyModal, EditCurrencyModal } from '~/components/modals';
import { Badge } from '~/components/ui/badge';
import { Button } from '~/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '~/components/ui/dropdown-menu';
import { Skeleton } from '~/components/ui/skeleton';
import { Spinner } from '~/components/ui/spinner';
import type { APICurrency } from '~/types/api';

const deletingCurrencyId = ref(-1);
const editingCurrency = ref<APICurrency | null>(null);
const editCurrencyModalOpen = ref(false);

const { t } = useI18n();

const abortController = new AbortController();
const {
  data: currencies,
  pending,
  status,
} = await useApi<APICurrency[]>('/currencies', {
  signal: abortController.signal,
  lazy: true,
  useFetch: true,
});

async function handleCurrencyCreated(data: APICurrency) {
  currencies.value.push(data);
}

async function handleCurrencyUpdated(data: APICurrency) {
  const idx = currencies.value.findIndex(x => x.id === data.id);
  if (idx > -1) {
    currencies.value.splice(idx, 1, data);
  }

  editingCurrency.value = null;
}

async function handleDeleteCurrency(id: number) {
  deletingCurrencyId.value = id;

  try {
    const { status, error } = await useApi(`/currencies/${id}`, {
      method: 'DELETE',
    });

    if (status.value === 'success') {
      const idx = currencies.value?.findIndex(x => x.id === id) ?? -1;
      if (idx > -1) {
        currencies.value?.splice(idx, 1);
      }

      toast.success(t('currencies.toasts.deleted_successfully'));
    } else if (error) {
      if (error.value?.statusCode === 409) {
        toast.error(t('currencies.toasts.cannot_delete'));
      }
    }
  } finally {
    deletingCurrencyId.value = -1;
  }
}

onUnmounted(() => abortController.abort());
</script>

<template>
  <div>
    <h1 class="text-4xl font-bold">{{ $t('currencies.header.title') }}</h1>
    <div class="mt-2 leading-relaxed text-muted-foreground">
      {{ $t('currencies.header.subtitle') }}
    </div>

    <div class="mt-4 flex w-full">
      <CreateCurrencyModal @success="handleCurrencyCreated">
        <Button class="ml-auto">{{ $t('currencies.buttons.new_currency') }}</Button>
      </CreateCurrencyModal>
    </div>

    <div class="mt-4">
      <div v-if="pending" class="grid w-full grid-cols-3 gap-2">
        <div
          v-for="i in 4"
          :key="`loading-currencies-skeleton-${i}`"
          class="col-span-1 space-y-2 rounded-md border p-4"
        >
          <Skeleton class="h-6 w-10/12 rounded-md" />
          <Skeleton class="h-6 w-full rounded-md" />
        </div>
      </div>

      <div
        v-if="status === 'success'"
        class="grid w-full grid-cols-1 gap-2 md:grid-cols-2 lg:grid-cols-3"
      >
        <div
          v-for="currency in currencies"
          :key="currency.id"
          class="relative grid-cols-1 rounded-md border bg-background p-4 ring-border transition-shadow hover:ring-2"
        >
          <DropdownMenu>
            <DropdownMenuTrigger as-child :disabled="deletingCurrencyId !== -1">
              <Button variant="ghost" size="icon" class="absolute right-2 top-2 size-8">
                <Spinner class="size-4" v-if="deletingCurrencyId === currency.id" />
                <EllipsisVertical v-else :size="18" />
              </Button>
            </DropdownMenuTrigger>

            <DropdownMenuContent class="w-44">
              <DropdownMenuItem
                class="cursor-pointer"
                @click="
                  () => {
                    editingCurrency = currency;
                    editCurrencyModalOpen = true;
                  }
                "
              >
                <div class="flex items-center gap-2">
                  <SquarePen :size="16" />
                  {{ $t('currencies.buttons.edit_currency') }}
                </div>
              </DropdownMenuItem>

              <DropdownMenuItem
                @click="() => handleDeleteCurrency(currency.id)"
                class="cursor-pointer"
              >
                <div class="flex items-center gap-2 text-red-500">
                  <Trash2 :size="16" />
                  {{ $t('currencies.buttons.delete_currency') }}
                </div>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>

          <Badge>{{ currency.code }}</Badge>
          <div class="mt-2 text-sm">{{ currency.name }}</div>
        </div>
      </div>
    </div>

    <EditCurrencyModal
      v-if="editingCurrency"
      :data="editingCurrency"
      v-model="editCurrencyModalOpen"
      @success="handleCurrencyUpdated"
      @close="() => (editingCurrency = null)"
    />
  </div>
</template>
