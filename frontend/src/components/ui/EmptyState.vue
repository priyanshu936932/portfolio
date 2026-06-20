<script setup>
defineProps({
  title: { type: String, default: 'Nothing here yet' },
  description: String,
})
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-4 py-16 text-center">
    <div class="flex h-16 w-16 items-center justify-center rounded-2xl bg-[var(--bg-surface-alt)]">
      <svg class="h-8 w-8 text-[var(--text-tertiary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
          d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
      </svg>
    </div>
    <div>
      <p class="font-display font-semibold text-[var(--text-primary)]">{{ title }}</p>
      <p v-if="description" class="mt-1 text-sm text-[var(--text-secondary)]">{{ description }}</p>
    </div>
    <slot />
  </div>
</template>