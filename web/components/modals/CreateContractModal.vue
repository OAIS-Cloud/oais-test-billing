<script lang="ts" setup>
import { toast } from 'vue-sonner';
import { z } from 'zod';

import type { APIContract, APICurrency } from '~/types/api';

import { FormInput } from '../inputs';
import { Button } from '../ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '../ui/dialog';
import { Label } from '../ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import type { Spinner } from '../ui/spinner';

const { t } = useI18n();

const formSchema = z.object({
  currency_id: z.coerce
    .number()
    .min(1, t('contracts.forms.create_contract_modal.errors.required_field')),
  currency_value: z
    .number()
    .min(0.01, t('contracts.forms.create_contract_modal.errors.currency_min_value')),
  invoice_closing_day: z.coerce
    .number()
    .min(1, t('contracts.forms.create_contract_modal.errors.required_field'))
    .max(31),
});

const emit = defineEmits<{
  (e: 'success', data: APIContract & { currency: APICurrency }): void;
}>();

const open = ref(false);
const loading = ref(false);
const errors = ref<z.ZodFormattedError<z.infer<typeof formSchema>> | null>(null);
const form = ref({
  currency_id: null,
  currency_value: 0,
  invoice_closing_day: null,
});

const abortController = new AbortController();
const {
  data: currencies,
  pending,
  refresh,
} = useApi<APICurrency[]>('/currencies', {
  signal: abortController.signal,
  lazy: true,
  server: false,
  useFetch: true,
});

function handleToggleModalStatus(value: boolean) {
  if (value) {
    refresh();
    open.value = value;
    return;
  }

  if (!value && (loading.value || pending.value)) {
    return;
  }

  form.value = {
    currency_id: null,
    currency_value: 0,
    invoice_closing_day: null,
  };

  open.value = value;
}

function handleCurrencyValueInputBlurred() {
  form.value.currency_value = Math.max(form.value.currency_value, 0.01);
}

async function handleSubmit() {
  const parsed = formSchema.safeParse(form.value);
  if (parsed.success) {
    errors.value = null;
    loading.value = true;

    try {
      const { data, error } = await useApi<APIContract & { currency: APICurrency }>('/contracts', {
        method: 'POST',
        body: JSON.stringify(parsed.data),
        headers: {
          'content-type': 'application/json',
        },
      });

      if (data.value) {
        open.value = false;
        form.value = {
          currency_id: null,
          currency_value: 0,
          invoice_closing_day: null,
        };
        emit('success', data.value);
      } else if (error.value) {
        if (error.value?.statusCode === 400) {
          toast.error(t('contracts.forms.create_contract_modal.errors.bad_request'));
        } else {
          toast.error(t('contracts.forms.create_contract_modal.errors.unknown_creation_error'));
        }
      }
    } finally {
      loading.value = false;
    }
  } else {
    errors.value = parsed.error.format();
  }
}
</script>

<template>
  <Dialog :open @update:open="handleToggleModalStatus">
    <DialogTrigger as-child>
      <slot />
    </DialogTrigger>

    <DialogContent class="max-w-96">
      <DialogHeader>
        <DialogTitle>{{ $t('contracts.forms.create_contract_modal.title') }}</DialogTitle>
        <DialogDescription>
          {{ $t('contracts.forms.create_contract_modal.description') }}
        </DialogDescription>
      </DialogHeader>

      <form @submit.prevent="handleSubmit">
        <div class="flex flex-col gap-6 pt-6">
          <div class="flex gap-2">
            <div class="flex flex-1 flex-col">
              <Label for="select-currency-input" class="mb-2 block">
                {{ $t('contracts.forms.create_contract_modal.select_menu.select_currency.label') }}
              </Label>
              <Select v-model="form.currency_id">
                <SelectTrigger :disabled="pending || loading" class="w-full">
                  <div v-if="pending" class="flex items-center gap-2">
                    <Spinner />
                    {{
                      $t(
                        'contracts.forms.create_contract_modal.select_menu.select_currency.loading_label',
                      )
                    }}
                  </div>
                  <SelectValue
                    v-else
                    :placeholder="
                      $t(
                        'contracts.forms.create_contract_modal.select_menu.select_currency.placeholder',
                      )
                    "
                  />
                </SelectTrigger>

                <SelectContent>
                  <SelectItem
                    v-for="currency in currencies"
                    :key="`select-box-currency-${currency.id}`"
                    :value="currency.id.toString()"
                  >
                    {{ currency.code }}
                  </SelectItem>
                </SelectContent>
              </Select>
              <span v-if="errors?.currency_id?._errors[0]" class="mt-2 text-xs text-red-700">
                {{ errors.currency_id._errors[0] }}
              </span>
            </div>

            <div class="flex flex-1 flex-col">
              <Label for="select-currency-input" class="mb-2 block">
                {{
                  $t('contracts.forms.create_contract_modal.select_menu.select_invoice_day.label')
                }}
              </Label>
              <Select v-model="form.invoice_closing_day">
                <SelectTrigger class="w-full" :disabled="loading || pending">
                  <SelectValue
                    :placeholder="
                      $t(
                        'contracts.forms.create_contract_modal.select_menu.select_invoice_day.placeholder',
                      )
                    "
                  />
                </SelectTrigger>

                <SelectContent>
                  <SelectItem
                    v-for="i in 31"
                    :key="`select-box-invoice-closing-day-${i}`"
                    :value="i.toString()"
                  >
                    {{ i }}
                  </SelectItem>
                </SelectContent>
              </Select>
              <span
                v-if="errors?.invoice_closing_day?._errors[0]"
                class="mt-2 text-xs text-red-700"
              >
                {{ errors.invoice_closing_day?._errors[0] }}
              </span>
            </div>
          </div>

          <FormInput
            :label="$t('contracts.forms.create_contract_modal.inputs.currency_value.label')"
            type="number"
            step="0.01"
            v-model="form.currency_value"
            :error="errors?.currency_value?._errors[0]"
            :disabled="loading || pending"
            @blur="handleCurrencyValueInputBlurred"
          />
        </div>

        <div class="mt-6 flex w-full justify-end gap-2">
          <Button
            type="button"
            variant="ghost"
            :disabled="loading || pending"
            @click="() => handleToggleModalStatus(false)"
          >
            {{ $t('contracts.forms.create_contract_modal.buttons.cancel') }}
          </Button>
          <Button
            :variant="loading ? 'secondary' : 'default'"
            type="submit"
            class="w-24"
            :disabled="loading || pending || !currencies?.length"
          >
            <Spinner v-if="loading" />
            <span v-else>{{ $t('contracts.forms.create_contract_modal.buttons.save') }}</span>
          </Button>
        </div>
      </form>
    </DialogContent>
  </Dialog>
</template>
