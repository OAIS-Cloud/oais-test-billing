<template>
  <div>
    <h1 class="text-4xl font-bold">
      {{ $t('home.hello_world') }}
    </h1>
    <div class="mt-4 text-sm leading-relaxed text-muted-foreground">
      <div>
        Lorem ipsum, dolor sit amet consectetur adipisicing elit. Deserunt, delectus et. Rerum
        molestias, cum quam commodi tempora ut illo natus odit nulla vel sint obcaecati, at id
        tenetur cupiditate quo.
      </div>

      <div class="mb-2 mt-8 w-max rounded-md border bg-muted/50 px-2 text-foreground">
        {{ $t('home.translation') }}
      </div>

      <div>
        {{ $t('home.lorem_translated') }}
      </div>
    </div>
  </div>
</template>