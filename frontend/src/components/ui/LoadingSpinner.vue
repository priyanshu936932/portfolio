<script setup>
defineProps({
  size: { type: String, default: 'md' },
  label: { type: String, default: 'Loading…' },
})
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-3 py-12 text-[var(--text-tertiary)]">
    <div
      :class="[
        'animate-spin rounded-full border-2 border-[var(--border-default)] border-t-[var(--color-brand-500)]',
        size === 'sm' && 'h-6 w-6',
        size === 'md' && 'h-10 w-10',
        size === 'lg' && 'h-14 w-14',
      ]"
    />
    <p class="text-sm">{{ label }}</p>
  </div>
</template>
