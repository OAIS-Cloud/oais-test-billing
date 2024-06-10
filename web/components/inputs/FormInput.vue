<script lang="ts" setup>
const model = defineModel();

defineOptions({
  inheritAttrs: false,
});

const props = defineProps<{
  label?: string;
  transform?: (value: string | number) => string | number;
  error?: string;
}>();

function handleInputChange(value: string | number) {
  if (props.transform) {
    value = props.transform(value);
  }
  model.value = value;
}
</script>

<template>
  <div class="flex flex-col">
    <Label v-if="!!label" :for="$attrs.id?.toString()" class="mb-2 block">
      {{ label }}
    </Label>
    <Input :value="model" @update:model-value="handleInputChange" v-bind="$attrs" />
    <span v-if="!!error" class="mt-2 text-xs text-red-700">
      {{ error }}
    </span>
  </div>
</template>
