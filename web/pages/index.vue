<script setup lang="ts">
import { AreaChart } from '~/components/ui/chart-area';
import { Skeleton } from '~/components/ui/skeleton';
import type { APIAnalytics } from '~/types/api';
import { getAllDaysFromAgoAsDateString } from '~/utils';

const analytics = ref<
  {
    name: string;
    totalContracts: number;
    totalCurrencies: number;
  }[]
>([]);

const abortController = new AbortController();
const { data, pending } = await useApi<APIAnalytics[]>('/analytics', {
  signal: abortController.signal,
  lazy: true,
  server: false,
  useFetch: true,
});

const dates = getAllDaysFromAgoAsDateString(30);
dates.reverse();

watchEffect(() => {
  analytics.value = dates.map(date => {
    const tmp = {
      name: date.split('-').reverse().join('/'),
      totalContracts: 0,
      totalCurrencies: 0,
    };

    data.value?.forEach(item => {
      const analytics = item.analytics.find(a => a.date === date);
      if (analytics) {
        if (item.id === 'contracts') {
          tmp.totalContracts += 'total_contracts' in analytics ? analytics.total_contracts : 0;
        } else if (item.id === 'currencies') {
          tmp.totalCurrencies += 'total_currencies' in analytics ? analytics.total_currencies : 0;
        }
      }
    });

    return tmp;
  });
});

onUnmounted(() => abortController.abort());
</script>

<template>
  <div>
    <h1 @click="() => $forceUpdate()" class="text-4xl font-bold">{{ $t('home.header.title') }}</h1>
    <p class="mt-2 leading-relaxed text-muted-foreground">
      {{ $t('home.header.subtitle') }}
    </p>

    <div class="my-2 rounded-md border-orange-900 bg-orange-900/40 p-4 text-sm text-orange-500">
      {{ $t('home.header.warn') }}
    </div>

    <div class="mt-8 flex flex-col gap-4 md:flex-row md:items-center">
      <div class="flex flex-col rounded-md border">
        <div class="p-4">
          <Skeleton v-if="pending" class="h-6 w-32 rounded-full" />
          <strong v-else class="mb-2">{{ $t('home.analytics.currencies.title') }}</strong>
        </div>
        <AreaChart
          class="h-24 w-full pb-4 md:w-72"
          v-if="analytics"
          :data="
            analytics.map(x => ({
              name: x.name,
              [$t('home.analytics.currencies.fields.created_currencies')]: x.totalCurrencies,
            }))
          "
          :show-grid-line="false"
          :show-legend="false"
          :show-x-axis="false"
          :show-y-axis="false"
          index="name"
          :categories="[$t('home.analytics.currencies.fields.created_currencies')]"
        />
      </div>

      <div class="flex flex-col rounded-md border">
        <div class="p-4">
          <Skeleton v-if="pending" class="h-6 w-32 rounded-full" />
          <strong v-else class="mb-2">{{ $t('home.analytics.contracts.title') }}</strong>
        </div>
        <AreaChart
          class="h-24 w-full pb-4 md:w-72"
          v-if="analytics"
          :data="
            analytics.map(x => ({
              name: x.name,
              [$t('home.analytics.contracts.fields.created_contracts')]: x.totalContracts,
            }))
          "
          :show-grid-line="false"
          :show-legend="false"
          :show-x-axis="false"
          :show-y-axis="false"
          index="name"
          :categories="[$t('home.analytics.contracts.fields.created_contracts')]"
        />
      </div>
    </div>

    <div class="mt-4 rounded-md border p-4">
      <h2 class="text-xl font-bold">Visão geral</h2>
      <AreaChart
        v-if="analytics"
        :data="
          analytics.map(x => ({
            name: x.name,
            [$t('home.analytics.general.fields.created_contracts')]: x.totalContracts,
            [$t('home.analytics.general.fields.created_currencies')]: x.totalCurrencies,
          }))
        "
        index="name"
        :categories="[
          $t('home.analytics.general.fields.created_contracts'),
          $t('home.analytics.general.fields.created_currencies'),
        ]"
      />
    </div>
  </div>
</template>
