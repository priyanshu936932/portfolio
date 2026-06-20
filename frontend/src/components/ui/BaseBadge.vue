<script setup>
defineProps({
  variant: {
    type: String,
    default: 'brand',
    validator: (v) => ['brand', 'accent', 'neutral', 'success', 'error'].includes(v),
  },
})
</script>

<template>
  <span
    :class="[
      'inline-flex items-center rounded-full px-3 py-0.5 text-xs font-medium',
      variant === 'brand'   && 'bg-[var(--color-brand-500)]/10 text-[var(--color-brand-400)]',
      variant === 'accent'  && 'bg-[var(--color-accent-500)]/10 text-[var(--color-accent-400)]',
      variant === 'neutral' && 'bg-[var(--bg-surface-alt)] text-[var(--text-secondary)]',
      variant === 'success' && 'bg-emerald-500/10 text-emerald-400',
      variant === 'error'   && 'bg-red-500/10 text-red-400',
    ]"
  >
    <slot />
  </span>
</template>