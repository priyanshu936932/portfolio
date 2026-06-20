<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import ThemeToggle from './ThemeToggle.vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const router = useRouter()
const mobileOpen = ref(false)

const navLinks = [
  { to: '/', label: 'Home', exact: true },
  { to: '/projects', label: 'Projects' },
  { to: '/blog', label: 'Blog' },
  { to: '/contact', label: 'Contact' },
]

function logout() {
  authStore.logout()
  router.push('/')
  mobileOpen.value = false
}
</script>

<template>
  <header
    class="sticky top-0 z-40 border-b border-[var(--border-default)] backdrop-blur-md"
    style="background: color-mix(in srgb, var(--bg-surface) 85%, transparent)"
  >
    <nav class="mx-auto flex max-w-6xl items-center justify-between px-6 py-3">

      <!-- Logo -->
      <RouterLink
        to="/"
        class="font-display text-lg font-bold tracking-tight text-[var(--color-brand-500)] transition-opacity hover:opacity-80 sm:text-xl"
      >
        priyanshu<span class="text-[var(--color-accent-500)]">.</span>
      </RouterLink>

      <!-- Desktop nav links -->
      <ul class="hidden items-center gap-1 md:flex">
        <li v-for="link in navLinks" :key="link.to">
          <RouterLink
            :to="link.to"
            class="rounded-lg px-4 py-2 text-sm font-medium text-[var(--text-secondary)] transition-all duration-150 hover:bg-[var(--bg-surface-alt)] hover:text-[var(--text-primary)]"
            :class="{ 'active-link': $route.path === link.to || (!link.exact && $route.path.startsWith(link.to) && link.to !== '/') }"
          >
            {{ link.label }}
          </RouterLink>
        </li>
      </ul>

      <!-- Right actions -->
      <div class="flex items-center gap-3">
        <ThemeToggle />

        <template v-if="!authStore.isAuthenticated">
          <RouterLink
            to="/admin/login"
            class="hidden rounded-lg border border-[var(--border-strong)] px-4 py-2 text-sm font-medium text-[var(--text-secondary)] transition-all duration-150 hover:border-[var(--color-brand-500)] hover:text-[var(--color-brand-500)] md:inline-flex"
          >
            Admin
          </RouterLink>
        </template>
        <template v-else>
          <div class="hidden items-center gap-2 md:flex">
            <RouterLink
              to="/admin/dashboard"
              class="rounded-lg px-4 py-2 text-sm font-medium text-[var(--text-secondary)] hover:bg-[var(--bg-surface-alt)] transition-colors"
            >
              Dashboard
            </RouterLink>
            <button
              @click="logout"
              class="rounded-lg border border-[var(--border-default)] px-4 py-2 text-sm font-medium text-[var(--text-tertiary)] hover:border-red-400 hover:text-red-400 transition-all"
            >
              Logout
            </button>
          </div>
        </template>

        <!-- Hamburger -->
        <button
          @click="mobileOpen = !mobileOpen"
          class="rounded-lg p-2 text-[var(--text-secondary)] hover:bg-[var(--bg-surface-alt)] transition-colors md:hidden"
          aria-label="Toggle menu"
        >
          <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path v-if="!mobileOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M4 6h16M4 12h16M4 18h16" />
            <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>
    </nav>

    <!-- Mobile menu -->
    <Transition name="slide">
      <div
        v-if="mobileOpen"
        class="border-t border-[var(--border-default)] bg-[var(--bg-surface)] px-6 py-4 md:hidden"
      >
        <ul class="flex flex-col gap-1">
          <li v-for="link in navLinks" :key="link.to">
            <RouterLink
              :to="link.to"
              @click="mobileOpen = false"
              class="block rounded-lg px-4 py-2.5 text-sm font-medium text-[var(--text-secondary)] hover:bg-[var(--bg-surface-alt)] hover:text-[var(--text-primary)] transition-colors"
            >
              {{ link.label }}
            </RouterLink>
          </li>
          <li class="mt-2 border-t border-[var(--border-default)] pt-2">
            <RouterLink
              v-if="!authStore.isAuthenticated"
              to="/admin/login"
              @click="mobileOpen = false"
              class="block rounded-lg border border-[var(--border-strong)] px-4 py-2.5 text-center text-sm font-medium text-[var(--text-secondary)]"
            >
              Admin Login
            </RouterLink>
            <button
              v-else
              @click="logout"
              class="w-full rounded-lg px-4 py-2.5 text-left text-sm font-medium text-red-400 hover:bg-red-400/10 transition-colors"
            >
              Logout
            </button>
          </li>
        </ul>
      </div>
    </Transition>
  </header>
</template>

<style scoped>
.active-link {
  background-color: var(--bg-surface-alt);
  color: var(--color-brand-500);
  font-weight: 600;
}

.slide-enter-active,
.slide-leave-active {
  transition: opacity 150ms ease, transform 150ms ease;
}
.slide-enter-from,
.slide-leave-to {
  opacity: 0;
  transform: translateY(-6px);
}
</style>