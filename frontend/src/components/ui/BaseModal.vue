<script setup>
import { onMounted, onUnmounted } from 'vue'

const props = defineProps({
  open: Boolean,
  title: String,
  size: {
    type: String,
    default: 'md',
    validator: (v) => ['sm', 'md', 'lg', 'xl'].includes(v),
  },
})
const emit = defineEmits(['close'])

function onKey(e) {
  if (e.key === 'Escape' && props.open) emit('close')
}
onMounted(() => document.addEventListener('keydown', onKey))
onUnmounted(() => document.removeEventListener('keydown', onKey))
</script>

<template>
  <Teleport to="body">
    <Transition name="modal">
      <div
        v-if="open"
        class="fixed inset-0 z-50 flex items-center justify-center p-4"
        @click.self="$emit('close')"
      >
        <div class="absolute inset-0 bg-black/40 backdrop-blur-sm" @click="$emit('close')" />
        <div
          :class="[
            'relative z-10 rounded-2xl bg-[var(--bg-surface)] border border-[var(--border-default)] w-full',
            size === 'sm' && 'max-w-sm',
            size === 'md' && 'max-w-lg',
            size === 'lg' && 'max-w-2xl',
            size === 'xl' && 'max-w-4xl',
          ]"
          style="box-shadow: 0 24px 64px hsl(var(--shadow-color) / 0.2)"
        >
          <div v-if="title" class="flex items-center justify-between border-b border-[var(--border-default)] px-6 py-4">
            <h3 class="font-display text-lg font-semibold text-[var(--text-primary)]">{{ title }}</h3>
            <button
              @click="$emit('close')"
              class="rounded-lg p-1 text-[var(--text-tertiary)] hover:bg-[var(--bg-surface-alt)] hover:text-[var(--text-primary)] transition-colors"
            >
              <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
          <div class="p-6">
            <slot />
          </div>
          <div v-if="$slots.footer" class="border-t border-[var(--border-default)] px-6 py-4">
            <slot name="footer" />
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: opacity 150ms ease;
}
.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}
</style>
