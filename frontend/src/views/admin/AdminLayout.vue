<script setup>
import AdminSidebar from '@/components/admin/AdminSidebar.vue'
import { ref } from 'vue'

const sidebarOpen = ref(false)
</script>

<template>
  <div class="flex min-h-screen bg-[var(--bg-base)]">
    <!-- Mobile overlay -->
    <div
      v-if="sidebarOpen"
      class="fixed inset-0 z-20 bg-black/50 backdrop-blur-sm lg:hidden"
      @click="sidebarOpen = false"
    />

    <!-- Sidebar -->
    <aside
      :class="[
        'fixed inset-y-0 left-0 z-30 w-64 transform transition-transform duration-200 lg:static lg:translate-x-0',
        sidebarOpen ? 'translate-x-0' : '-translate-x-full',
      ]"
    >
      <AdminSidebar @close="sidebarOpen = false" />
    </aside>

    <!-- Main -->
    <div class="flex flex-1 flex-col min-w-0">
      <!-- Mobile topbar -->
      <div class="flex items-center gap-4 border-b border-[var(--border-default)] bg-[var(--bg-surface)] px-4 py-3 lg:hidden">
        <button
          @click="sidebarOpen = true"
          class="rounded-lg p-2 text-[var(--text-secondary)] hover:bg-[var(--bg-surface-alt)]"
          aria-label="Open menu"
        >
          <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
          </svg>
        </button>
        <span class="font-display font-semibold text-[var(--text-primary)]">Admin</span>
      </div>

      <main class="flex-1 overflow-auto p-6 lg:p-8">
        <RouterView />
      </main>
    </div>
  </div>
</template>
