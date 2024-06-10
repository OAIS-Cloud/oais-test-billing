<script lang="ts" setup>
import { toast } from 'vue-sonner';
import { z } from 'zod';

import type { APICurrency } from '~/types/api';

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
import type { Spinner } from '../ui/spinner';

const { t } = useI18n();

const formSchema = z.object({
  code: z
    .string({ required_error: t('currencies.forms.create_currency_modal.errors.required') })
    .min(1, t('currencies.forms.create_currency_modal.errors.code_min_length'))
    .max(4, t('currencies.forms.create_currency_modal.errors.code_max_length')),
  name: z
    .string({ required_error: t('currencies.forms.create_currency_modal.errors.required') })
    .min(3, t('currencies.forms.create_currency_modal.errors.name_min_length'))
    .max(256, t('currencies.forms.create_currency_modal.errors.name_max_length')),
});

const emit = defineEmits<{
  (e: 'success', data: APICurrency): void;
}>();

const open = ref(false);
const loading = ref(false);
const errors = ref<z.ZodFormattedError<z.infer<typeof formSchema>> | null>(null);
const form = ref({
  code: '',
  name: '',
});

function handleToggleModalStatus(value: boolean) {
  if (!value && loading.value) {
    return;
  }

  form.value = {
    code: '',
    name: '',
  };

  open.value = value;
}

function handleCodeInputChange(val: string | number) {
  return val
    .toString()
    .replace(/[^a-z]/gi, '')
    .slice(0, 4)
    .toUpperCase();
}

async function handleSubmit() {
  const parsed = formSchema.safeParse(form.value);
  if (parsed.success) {
    errors.value = null;
    loading.value = true;

    try {
      const { data, error } = await useApi<APICurrency>('/currencies', {
        method: 'POST',
        body: JSON.stringify(parsed.data),
        headers: {
          'content-type': 'application/json',
        },
      });

      if (data.value) {
        open.value = false;
        emit('success', data.value);
      } else if (error) {
        if (error.value?.statusCode === 409) {
          toast.error(t('currencies.forms.create_currency_modal.errors.code_conflict'));
        } else if (error.value?.statusCode === 400) {
          toast.error(t('currencies.forms.create_currency_modal.errors.bad_request'));
        } else {
          toast.error(t('currencies.forms.create_currency_modal.errors.unknown_creation_error'));
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
        <DialogTitle>{{ $t('currencies.forms.create_currency_modal.title') }}</DialogTitle>
        <DialogDescription>
          {{ $t('currencies.forms.create_currency_modal.description') }}
        </DialogDescription>
      </DialogHeader>

      <form @submit.prevent="handleSubmit">
        <div class="flex flex-col gap-6 pt-6">
          <FormInput
            :label="$t('currencies.forms.create_currency_modal.inputs.code.label')"
            v-model="form.code"
            :error="errors?.code?._errors[0]"
            :transform="handleCodeInputChange"
            :disabled="loading"
          />
          <FormInput
            :label="$t('currencies.forms.create_currency_modal.inputs.name.label')"
            v-model="form.name"
            :error="errors?.name?._errors[0]"
            :disabled="loading"
          />
        </div>

        <div class="mt-6 flex w-full justify-end gap-2">
          <Button
            type="button"
            variant="ghost"
            :disabled="loading"
            @click="() => handleToggleModalStatus(false)"
          >
            {{ $t('currencies.forms.create_currency_modal.buttons.cancel') }}
          </Button>
          <Button
            :variant="loading ? 'secondary' : 'default'"
            type="submit"
            class="w-24"
            :disabled="loading"
          >
            <Spinner v-if="loading" />
            <span v-else>{{ $t('currencies.forms.create_currency_modal.buttons.save') }}</span>
          </Button>
        </div>
      </form>
    </DialogContent>
  </Dialog>
</template>
