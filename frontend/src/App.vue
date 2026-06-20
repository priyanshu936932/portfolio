<script setup>
import { useTheme } from '@/composables/useTheme'
import { useToast } from '@/composables/useToast'
import NavBar from '@/components/layout/NavBar.vue'
import Footer from '@/components/layout/Footer.vue'

useTheme()
const { toasts, dismiss } = useToast()
</script>

<template>
  <div class="flex min-h-screen flex-col bg-[var(--bg-base)]">
    <NavBar />
    <main class="flex-1">
      <RouterView v-slot="{ Component }">
        <Transition name="fade" mode="out-in">
          <component :is="Component" />
        </Transition>
      </RouterView>
    </main>
    <Footer />
  </div>

  <!-- Toast notifications -->
  <Teleport to="body">
    <div class="fixed bottom-6 right-6 z-50 flex flex-col gap-2">
      <TransitionGroup name="toast">
        <div
          v-for="toast in toasts"
          :key="toast.id"
          @click="dismiss(toast.id)"
          :class="[
            'flex cursor-pointer items-center gap-3 rounded-xl border px-4 py-3 text-sm font-medium shadow-lg backdrop-blur-sm',
            toast.type === 'success' && 'border-green-200 bg-green-50 text-green-800 dark:border-green-800 dark:bg-green-900/80 dark:text-green-200',
            toast.type === 'error' && 'border-red-200 bg-red-50 text-red-800 dark:border-red-800 dark:bg-red-900/80 dark:text-red-200',
            toast.type === 'info' && 'border-[var(--border-default)] bg-[var(--bg-surface)] text-[var(--text-primary)]',
          ]"
        >
          <span>{{ toast.message }}</span>
        </div>
      </TransitionGroup>
    </div>
  </Teleport>
</template>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 150ms ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.toast-enter-active,
.toast-leave-active {
  transition: all 200ms ease;
}
.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateX(16px);
}
</style>