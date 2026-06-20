<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

defineEmits(['close'])

const router    = useRouter()
const authStore = useAuthStore()

const links = [
  {
    to: '/admin/dashboard',
    label: 'Dashboard',
    icon: 'M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6',
  },
  {
    to: '/admin/projects',
    label: 'Projects',
    icon: 'M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10',
  },
  {
    to: '/admin/blog',
    label: 'Blog',
    icon: 'M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z',
  },
  {
    to: '/admin/skills',
    label: 'Skills',
    icon: 'M13 10V3L4 14h7v7l9-11h-7z',
  },
  {
    to: '/admin/tags',
    label: 'Tags',
    icon: 'M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z',
  },
  {
    to: '/admin/messages',
    label: 'Messages',
    icon: 'M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z',
  },
  {
    to: '/admin/profile',
    label: 'Profile',
    icon: 'M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z',
  },
]

function logout() {
  authStore.logout()
  router.push('/')
}
</script>

<template>
  <div class="flex h-full flex-col border-r border-[var(--border-default)] bg-[var(--bg-surface)]">
    <!-- Logo -->
    <div class="flex items-center justify-between border-b border-[var(--border-default)] px-5 py-4">
      <RouterLink to="/" class="font-display text-lg font-bold text-[var(--color-brand-500)]">
        priyanshu<span class="text-[var(--color-accent-500)]">.</span>
      </RouterLink>
      <button
        @click="$emit('close')"
        class="rounded-lg p-1.5 text-[var(--text-tertiary)] hover:bg-[var(--bg-surface-alt)] lg:hidden"
      >
        <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
    </div>

    <!-- User badge -->
    <div class="border-b border-[var(--border-default)] px-4 py-3">
      <div class="flex items-center gap-3 rounded-xl bg-[var(--bg-surface-alt)] px-3 py-2.5">
        <div class="flex h-8 w-8 items-center justify-center rounded-full bg-[var(--color-brand-500)] text-xs font-bold text-white">
          {{ authStore.userEmail?.[0]?.toUpperCase() }}
        </div>
        <div class="min-w-0 flex-1">
          <p class="truncate text-xs font-medium text-[var(--text-primary)]">{{ authStore.userEmail }}</p>
          <p class="text-xs text-[var(--text-tertiary)]">Administrator</p>
        </div>
      </div>
    </div>

    <!-- Nav -->
    <nav class="flex-1 overflow-y-auto px-3 py-4">
      <p class="mb-2 px-3 text-xs font-semibold uppercase tracking-wider text-[var(--text-tertiary)]">Menu</p>
      <ul class="space-y-0.5">
        <li v-for="link in links" :key="link.to">
          <RouterLink
            :to="link.to"
            class="flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-medium text-[var(--text-secondary)] transition-all hover:bg-[var(--bg-surface-alt)] hover:text-[var(--text-primary)]"
            active-class="bg-[var(--color-brand-500)]/10 text-[var(--color-brand-400)] font-semibold"
          >
            <svg class="h-4.5 w-4.5 shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="1.8">
              <path stroke-linecap="round" stroke-linejoin="round" :d="link.icon" />
            </svg>
            {{ link.label }}
          </RouterLink>
        </li>
      </ul>
    </nav>

    <!-- Footer actions -->
    <div class="border-t border-[var(--border-default)] px-3 py-4 space-y-1">
      <RouterLink to="/" target="_blank"
        class="flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm text-[var(--text-tertiary)] transition-colors hover:bg-[var(--bg-surface-alt)] hover:text-[var(--text-secondary)]"
      >
        <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
        </svg>
        View Site
      </RouterLink>
      <button @click="logout"
        class="flex w-full items-center gap-3 rounded-xl px-3 py-2.5 text-sm text-red-400 transition-colors hover:bg-red-500/10"
      >
        <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
        </svg>
        Logout
      </button>
    </div>
  </div>
</template>
