<script setup>
defineProps({
  lines: { type: Number, default: 3 },
  image: { type: Boolean, default: true },
})
</script>

<template>
  <div class="overflow-hidden rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)]">
    <!-- Image placeholder -->
    <div v-if="image" class="h-48 animate-pulse bg-[var(--bg-surface-alt)]" />
    <div class="space-y-3 p-5">
      <!-- Title -->
      <div class="h-5 w-3/4 animate-pulse rounded-lg bg-[var(--bg-surface-alt)]" />
      <!-- Lines -->
      <div
        v-for="i in lines"
        :key="i"
        :class="['h-3 animate-pulse rounded-lg bg-[var(--bg-surface-alt)]', i === lines ? 'w-2/3' : 'w-full']"
        :style="{ animationDelay: i * 80 + 'ms' }"
      />
      <!-- Badge strip -->
      <div class="flex gap-2 pt-1">
        <div class="h-5 w-14 animate-pulse rounded-full bg-[var(--bg-surface-alt)]" style="animation-delay:160ms" />
        <div class="h-5 w-20 animate-pulse rounded-full bg-[var(--bg-surface-alt)]" style="animation-delay:240ms" />
        <div class="h-5 w-12 animate-pulse rounded-full bg-[var(--bg-surface-alt)]" style="animation-delay:320ms" />
      </div>
    </div>
  </div>
</template>
