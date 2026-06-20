<script setup>
defineProps({
  hover: { type: Boolean, default: false },
  padding: { type: String, default: 'p-6' },
})
</script>

<template>
  <div
    :class="[
      'rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)]',
      padding,
      hover && 'transition-all duration-200 hover:scale-[1.02] cursor-pointer',
    ]"
    style="box-shadow: 0 8px 24px hsl(var(--shadow-color) / 0.12)"
  >
    <slot />
  </div>
</template>
