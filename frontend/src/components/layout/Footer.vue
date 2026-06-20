<script setup>
import { ref, onMounted } from 'vue'
import apiClient from '@/api/client'

const profile = ref(null)
onMounted(async () => {
  try {
    const { data } = await apiClient.get('/profile')
    profile.value = data
  } catch { /* footer works without profile data */ }
})

const year = new Date().getFullYear()
</script>

<template>
  <footer class="border-t border-[var(--border-default)] bg-[var(--bg-surface)]">
    <div class="mx-auto max-w-6xl px-6 py-12">
      <div class="flex flex-col gap-8 sm:flex-row sm:items-start sm:justify-between">

        <!-- Brand block -->
        <div class="space-y-2">
          <p class="font-display text-xl font-bold text-[var(--color-brand-500)]">
            {{ profile?.fullName?.split(' ')[0]?.toLowerCase() ?? 'priyanshu' }}<span class="text-[var(--color-accent-500)]">.</span>
          </p>
          <p v-if="profile?.bio" class="max-w-xs text-sm text-[var(--text-tertiary)] line-clamp-2">{{ profile.bio }}</p>
          <p v-else class="text-sm text-[var(--text-tertiary)]">Full‑stack developer &amp; builder</p>
          <p class="text-xs text-[var(--text-tertiary)]">Built with Vue 3 &amp; Spring Boot</p>
        </div>

        <!-- Nav links -->
        <div class="flex flex-wrap gap-x-8 gap-y-2 text-sm text-[var(--text-secondary)]">
          <RouterLink to="/projects" class="hover:text-[var(--color-brand-500)] transition-colors">Projects</RouterLink>
          <RouterLink to="/blog"     class="hover:text-[var(--color-brand-500)] transition-colors">Blog</RouterLink>
          <RouterLink to="/contact"  class="hover:text-[var(--color-brand-500)] transition-colors">Contact</RouterLink>
        </div>

        <!-- Social icons -->
        <div class="flex items-center gap-4">
          <a
            v-if="profile?.githubUrl"
            :href="profile.githubUrl"
            target="_blank" rel="noopener noreferrer"
            class="flex h-9 w-9 items-center justify-center rounded-lg border border-[var(--border-default)] text-[var(--text-tertiary)] transition-all hover:border-[var(--color-brand-500)] hover:text-[var(--color-brand-500)]"
            aria-label="GitHub"
          >
            <svg class="h-4 w-4" fill="currentColor" viewBox="0 0 24 24">
              <path fill-rule="evenodd" clip-rule="evenodd"
                d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" />
            </svg>
          </a>
          <a
            v-if="profile?.linkedinUrl"
            :href="profile.linkedinUrl"
            target="_blank" rel="noopener noreferrer"
            class="flex h-9 w-9 items-center justify-center rounded-lg border border-[var(--border-default)] text-[var(--text-tertiary)] transition-all hover:border-[var(--color-brand-500)] hover:text-[var(--color-brand-500)]"
            aria-label="LinkedIn"
          >
            <svg class="h-4 w-4" fill="currentColor" viewBox="0 0 24 24">
              <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433a2.062 2.062 0 01-2.063-2.065 2.064 2.064 0 112.063 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z" />
            </svg>
          </a>
          <a
            v-if="profile?.email"
            :href="`mailto:${profile.email}`"
            class="flex h-9 w-9 items-center justify-center rounded-lg border border-[var(--border-default)] text-[var(--text-tertiary)] transition-all hover:border-[var(--color-brand-500)] hover:text-[var(--color-brand-500)]"
            aria-label="Email"
          >
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
            </svg>
          </a>
        </div>
      </div>

      <!-- Competitive programming profiles -->
      <div class="mt-8 flex flex-wrap items-center justify-center gap-2">
        <span class="mr-1 text-[10px] font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">2500+ Problems:</span>

        <a href="https://codeforces.com/profile/Ellipse" target="_blank" rel="noopener noreferrer"
          class="flex items-center gap-1.5 rounded-lg border border-[var(--border-default)] px-3 py-1.5 text-[var(--text-tertiary)] transition-all hover:border-blue-500/40 hover:text-blue-400"
          aria-label="Codeforces">
          <span class="text-[10px] font-bold">CF</span>
          <span class="text-[10px]">Expert · max 1701</span>
        </a>

        <a href="https://www.codechef.com/users/priyansh_misra" target="_blank" rel="noopener noreferrer"
          class="flex items-center gap-1.5 rounded-lg border border-[var(--border-default)] px-3 py-1.5 text-[var(--text-tertiary)] transition-all hover:border-amber-500/40 hover:text-amber-400"
          aria-label="CodeChef">
          <span class="text-[10px] font-bold">CC</span>
          <span class="text-[10px]">4★ · max 1929</span>
        </a>

        <a href="https://leetcode.com/u/Kalki_2898/" target="_blank" rel="noopener noreferrer"
          class="flex items-center gap-1.5 rounded-lg border border-[var(--border-default)] px-3 py-1.5 text-[var(--text-tertiary)] transition-all hover:border-orange-500/40 hover:text-orange-400"
          aria-label="LeetCode">
          <span class="text-[10px] font-bold">LC</span>
          <span class="text-[10px]">Top 3.11% · max 2069</span>
        </a>

        <a href="https://atcoder.jp/users/Kalki_2898" target="_blank" rel="noopener noreferrer"
          class="flex items-center gap-1.5 rounded-lg border border-[var(--border-default)] px-3 py-1.5 text-[var(--text-tertiary)] transition-all hover:border-emerald-500/40 hover:text-emerald-400"
          aria-label="AtCoder">
          <span class="text-[10px] font-bold">AT</span>
          <span class="text-[10px]">Green · max 826</span>
        </a>
      </div>

      <div class="mt-6 border-t border-[var(--border-default)] pt-6 text-center text-xs text-[var(--text-tertiary)]">
        &copy; {{ year }} Priyanshu Mishra — All rights reserved
      </div>
    </div>
  </footer>
</template>