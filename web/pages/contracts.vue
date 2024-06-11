<script lang="ts" setup>
import { EllipsisVertical, SquarePen, Trash2 } from 'lucide-vue-next';
import { toast } from 'vue-sonner';

import { CreateContractModal, EditContractModal } from '~/components/modals';
import { Button } from '~/components/ui/button';
import type { APIContract, APICurrency } from '~/types/api';
import { formatCurrency } from '~/utils';

type APIContractWithCurrency = APIContract & { currency: APICurrency };

const { t } = useI18n();

const deletingContractId = ref(-1);
const editingContract = ref<APIContractWithCurrency | null>(null);
const editContractModalOpen = ref(false);

const abortController = new AbortController();
const { data: contracts, pending } = useApi<APIContractWithCurrency[]>('/contracts', {
  signal: abortController.signal,
  lazy: true,
  server: false,
  useFetch: true,
});

function handleContractCreated(contract: APIContractWithCurrency) {
  contracts.value?.push(contract);
}

async function handleContractUpdated(data: APIContractWithCurrency) {
  const idx = contracts.value?.findIndex(x => x.id === data.id) ?? -1;
  if (idx > -1) {
    contracts.value.splice(idx, 1, data);
  }

  editingContract.value = null;
}

async function handleDeleteCurrency(id: number) {
  deletingContractId.value = id;

  try {
    const { status, error } = await useApi(`/contracts/${id}`, {
      method: 'DELETE',
    });

    if (status.value === 'success') {
      const idx = contracts.value?.findIndex(x => x.id === id) ?? -1;
      if (idx > -1) {
        contracts.value.splice(idx, 1);
      }

      toast.success(t('contracts.toasts.deleted_successfully'));
    } else if (error) {
      if (error.value?.statusCode === 404) {
        toast.error(t('contracts.errors.unknown_delete_error'));
      }
    }
  } finally {
    deletingContractId.value = -1;
  }
}

onUnmounted(() => abortController.abort());
</script>

<template>
  <div>
    <h1 class="text-4xl font-bold">{{ $t('contracts.header.title') }}</h1>
    <div class="mt-2 leading-relaxed text-muted-foreground">
      {{ $t('contracts.header.description') }}
    </div>

    <div class="mt-4 flex justify-end">
      <CreateContractModal @success="handleContractCreated">
        <Button>{{ $t('contracts.buttons.new_contract') }}</Button>
      </CreateContractModal>
    </div>

    <div class="mt-4">
      <div v-if="!pending && !contracts?.length">
        <div
          class="flex h-32 items-center justify-center rounded-md border border-dashed p-4 text-muted-foreground"
        >
          {{ $t('contracts.misc.no_contracts') }}
        </div>
      </div>
      <div v-if="pending" class="w-full space-y-2">
        <div v-for="i in 4" :key="`loading-contracts-skeleton-${i}`">
          <Skeleton class="h-14 w-full rounded-md" />
        </div>
      </div>
      <div v-else-if="contracts?.length" class="space-y-2">
        <div
          v-for="contract in contracts"
          :key="`contract-box-${contract.id}`"
          class="relative flex w-full flex-col space-y-4 rounded-md border p-4 sm:flex-row sm:space-y-0"
        >
          <div class="flex flex-1 flex-col">
            <span class="text-[10px] text-muted-foreground">{{
              $t('contracts.misc.contract_row.contract_id')
            }}</span>
            <strong>{{ contract.id }}</strong>
          </div>
          <div class="flex flex-1 flex-col">
            <span class="text-[10px] text-muted-foreground">
              {{ $t('contracts.misc.contract_row.currency') }}
            </span>
            <strong>{{ contract.currency.code }}</strong>
          </div>
          <div class="flex flex-1 flex-col">
            <span class="text-[10px] text-muted-foreground">
              {{ $t('contracts.misc.contract_row.currency_value') }}
            </span>
            <strong>{{ formatCurrency(contract.currency_value) }}</strong>
          </div>
          <div class="flex flex-1 flex-col">
            <span class="text-[10px] text-muted-foreground">
              {{ $t('contracts.misc.contract_row.invoice_day') }}
            </span>
            <strong>{{ contract.invoice_closing_day.toString().padStart(2, '0') }}</strong>
          </div>

          <DropdownMenu>
            <DropdownMenuTrigger as-child :disabled="deletingContractId !== -1">
              <Button variant="ghost" size="icon" class="absolute right-4 top-4 sm:static">
                <Spinner class="size-4" v-if="deletingContractId === contract.id" />
                <EllipsisVertical v-else :size="22" />
              </Button>
            </DropdownMenuTrigger>

            <DropdownMenuContent class="w-44">
              <DropdownMenuItem
                class="cursor-pointer"
                @click="
                  () => {
                    editingContract = contract;
                    editContractModalOpen = true;
                  }
                "
              >
                <div class="flex items-center gap-2">
                  <SquarePen :size="16" />
                  {{ $t('contracts.buttons.edit_contract') }}
                </div>
              </DropdownMenuItem>

              <DropdownMenuItem
                @click="() => handleDeleteCurrency(contract.id)"
                class="cursor-pointer"
              >
                <div class="flex items-center gap-2 text-red-500">
                  <Trash2 :size="16" />
                  {{ $t('contracts.buttons.delete_contract') }}
                </div>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>
    </div>

    <EditContractModal
      v-if="editingContract"
      :data="editingContract"
      v-model="editContractModalOpen"
      @success="handleContractUpdated"
      @close="() => (editingContract = null)"
    />
  </div>
</template>
