<script setup>
defineProps({
  variant: {
    type: String,
    default: 'primary',
    validator: (v) => ['primary', 'secondary', 'ghost', 'danger'].includes(v),
  },
  size: {
    type: String,
    default: 'md',
    validator: (v) => ['sm', 'md', 'lg'].includes(v),
  },
  disabled: Boolean,
  loading: Boolean,
  type: { type: String, default: 'button' },
})
</script>

<template>
  <button
    :type="type"
    :disabled="disabled || loading"
    :class="[
      'inline-flex items-center justify-center gap-2 font-medium transition-all duration-150 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed',
      variant === 'primary'   && 'rounded-xl bg-[var(--color-brand-500)] text-white hover:bg-[var(--color-brand-600)] active:scale-[0.97] shadow-sm focus-visible:ring-[var(--color-brand-400)]',
      variant === 'secondary' && 'rounded-xl border border-[var(--border-strong)] bg-[var(--bg-surface)] text-[var(--text-primary)] hover:bg-[var(--bg-surface-alt)] active:scale-[0.97] focus-visible:ring-[var(--color-brand-400)]',
      variant === 'ghost'     && 'rounded-xl text-[var(--color-brand-500)] hover:bg-[var(--bg-surface-alt)] active:scale-[0.97] focus-visible:ring-[var(--color-brand-400)]',
      variant === 'danger'    && 'rounded-xl bg-red-600 text-white hover:bg-red-700 active:scale-[0.97] focus-visible:ring-red-400',
      size === 'sm' && 'px-4 py-1.5 text-xs',
      size === 'md' && 'px-5 py-2.5 text-sm',
      size === 'lg' && 'px-7 py-3 text-base',
    ]"
  >
    <span v-if="loading" class="h-4 w-4 animate-spin rounded-full border-2 border-current border-t-transparent" />
    <slot />
  </button>
</template>