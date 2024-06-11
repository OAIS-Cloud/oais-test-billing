<script lang="ts" setup>
import { FileText, Printer, Sheet } from 'lucide-vue-next';
import { toast } from 'vue-sonner';

import { dateToCleanFileNameDateFormat } from '~/utils';

import { Button } from '../ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '../ui/dropdown-menu';
import { Spinner } from '../ui/spinner';

const { t } = useI18n();

const loading = ref(false);

async function handleStartDownloadReportLogs(xlsx?: boolean) {
  if (loading.value) return;

  loading.value = true;

  try {
    const { data, error } = await useApi<Blob>(`/reports/contracts?xlsx=${xlsx}`);

    if (error.value) {
      toast.error(error.value?.message ?? t('home.errors.unknown_report_download_error'));
    } else {
      const date = dateToCleanFileNameDateFormat(new Date());

      const anchor = document.createElement('a');
      anchor.href = window.URL.createObjectURL(data.value);
      anchor.download = `contracts_report_${date}.${xlsx ? 'xlsx' : 'pdf'}`;
      document.body.append(anchor);
      anchor.click();
      anchor.remove();

      toast.success(t('home.toasts.report_download_started'));
    }
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <DropdownMenu>
    <DropdownMenuTrigger as-child>
      <Button :disabled="loading" :variant="loading ? 'secondary' : 'default'">
        <span>{{ $t('home.buttons.generate_report.name') }}</span>
        <Spinner v-if="loading" class="ml-2" />
        <Printer v-else :size="18" class="ml-2" />
      </Button>
    </DropdownMenuTrigger>

    <DropdownMenuContent class="w-44">
      <DropdownMenuItem class="cursor-pointer" @click="() => handleStartDownloadReportLogs()">
        <FileText :size="18" class="mr-2" /> <span>PDF</span>
      </DropdownMenuItem>
      <DropdownMenuItem class="cursor-pointer" @click="() => handleStartDownloadReportLogs(true)">
        <Sheet :size="18" class="mr-2" /> <span>XLS</span>
      </DropdownMenuItem>
    </DropdownMenuContent>
  </DropdownMenu>
</template>
