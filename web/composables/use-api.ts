import type { UseFetchOptions } from '#app';
import { useAsyncData, useFetch } from '#app';

export function useApi<T>(
  path: string,
  options?: UseFetchOptions<T> & { useFetch: true },
): ReturnType<typeof useFetch<T>>;
export function useApi<T>(
  path: string,
  options?: Parameters<typeof $fetch>[1] & { useFetch?: false },
): ReturnType<typeof useAsyncData<T>>;
export function useApi<T>(
  path: string,
  options: (UseFetchOptions<T> | Parameters<typeof $fetch>[1]) & { useFetch?: boolean } = {
    useFetch: false,
  },
) {
  const runtimeConfig = useRuntimeConfig();
  const url = new URL(path, runtimeConfig.public.API_BASE_URL);

  if (options.useFetch || import.meta.server) {
    return useFetch(url.toString(), options);
  }

  return useAsyncData<T>(async () =>
    $fetch<T>(url.toString(), options as Parameters<typeof $fetch>[1]),
  );
}
