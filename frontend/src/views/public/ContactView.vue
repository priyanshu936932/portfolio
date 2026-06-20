<script setup>
import { ref, onMounted } from 'vue'
import ContactForm from '@/components/public/ContactForm.vue'
import { profileApi } from '@/api/profile'

const profile        = ref(null)
const profileLoading = ref(true)

onMounted(async () => {
  try {
    const { data } = await profileApi.get()
    profile.value = data
  } catch { /* render without profile */ }
  finally { profileLoading.value = false }
})
</script>

<template>
  <div class="relative overflow-hidden min-h-screen">

    <!-- Background glows -->
    <div class="pointer-events-none absolute inset-0 overflow-hidden">
      <div class="absolute -top-48 -right-48 h-[700px] w-[700px] rounded-full opacity-[0.07] blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-500), transparent 70%)" />
      <div class="absolute bottom-0 -left-32 h-[400px] w-[400px] rounded-full opacity-[0.05] blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-400), transparent 70%)" />
    </div>

    <div class="relative mx-auto max-w-6xl px-6 py-16 md:py-24">

      <!-- Header -->
      <div class="mb-16 text-center">
        <div class="mb-5 inline-flex items-center gap-2 rounded-full border border-[var(--color-brand-400)]/30 bg-[var(--color-brand-500)]/10 px-4 py-1.5">
          <span class="h-1.5 w-1.5 animate-pulse rounded-full bg-emerald-400"></span>
          <span class="text-xs font-semibold uppercase tracking-widest text-[var(--color-brand-400)]">Available for work</span>
        </div>
        <h1 class="font-display text-4xl font-bold text-[var(--text-primary)] md:text-5xl lg:text-6xl">
          Let's work together
        </h1>
        <p class="mx-auto mt-4 max-w-md text-lg text-[var(--text-secondary)]">
          Have a project, an idea, or just want to say hi? I'd love to hear from you.
        </p>
      </div>

      <div class="grid gap-6 lg:grid-cols-5 lg:gap-8">

        <!-- Left: contact info -->
        <div class="lg:col-span-2 flex flex-col gap-5">

          <!-- Get in touch card -->
          <div class="flex-1 rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6"
            style="box-shadow: 0 4px 24px hsl(var(--shadow-color) / 0.10)">
            <h2 class="mb-5 font-display text-lg font-semibold text-[var(--text-primary)]">Get in touch</h2>

            <!-- Skeleton -->
            <div v-if="profileLoading" class="space-y-3">
              <div v-for="i in 4" :key="i" class="h-[60px] animate-pulse rounded-xl bg-[var(--bg-surface-alt)]" />
            </div>

            <div v-else class="space-y-2">

              <!-- Email -->
              <a v-if="profile?.email" :href="'mailto:' + profile.email"
                class="group flex items-center gap-4 rounded-xl p-3 transition-all hover:bg-[var(--bg-surface-alt)]">
                <span class="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-blue-500/10">
                  <svg class="h-5 w-5 text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                  </svg>
                </span>
                <div class="min-w-0 flex-1">
                  <p class="text-[10px] font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">Email</p>
                  <p class="mt-0.5 truncate text-sm font-medium text-[var(--text-primary)] transition-colors group-hover:text-[var(--color-brand-400)]">{{ profile.email }}</p>
                </div>
                <svg class="h-4 w-4 shrink-0 text-[var(--text-tertiary)] opacity-0 transition-opacity group-hover:opacity-100" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>

              <!-- Phone -->
              <a v-if="profile?.phone" :href="'tel:' + profile.phone"
                class="group flex items-center gap-4 rounded-xl p-3 transition-all hover:bg-[var(--bg-surface-alt)]">
                <span class="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-emerald-500/10">
                  <svg class="h-5 w-5 text-emerald-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                  </svg>
                </span>
                <div class="min-w-0 flex-1">
                  <p class="text-[10px] font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">Phone</p>
                  <p class="mt-0.5 text-sm font-medium text-[var(--text-primary)] transition-colors group-hover:text-[var(--color-brand-400)]">{{ profile.phone }}</p>
                </div>
                <svg class="h-4 w-4 shrink-0 text-[var(--text-tertiary)] opacity-0 transition-opacity group-hover:opacity-100" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>

              <!-- GitHub -->
              <a v-if="profile?.githubUrl" :href="profile.githubUrl" target="_blank" rel="noopener noreferrer"
                class="group flex items-center gap-4 rounded-xl p-3 transition-all hover:bg-[var(--bg-surface-alt)]">
                <span class="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-purple-500/10">
                  <svg class="h-5 w-5 text-purple-400" fill="currentColor" viewBox="0 0 24 24">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" />
                  </svg>
                </span>
                <div class="min-w-0 flex-1">
                  <p class="text-[10px] font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">GitHub</p>
                  <p class="mt-0.5 text-sm font-medium text-[var(--text-primary)] transition-colors group-hover:text-[var(--color-brand-400)]">View my projects</p>
                </div>
                <svg class="h-4 w-4 shrink-0 text-[var(--text-tertiary)] opacity-0 transition-opacity group-hover:opacity-100" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>

              <!-- LinkedIn -->
              <a v-if="profile?.linkedinUrl" :href="profile.linkedinUrl" target="_blank" rel="noopener noreferrer"
                class="group flex items-center gap-4 rounded-xl p-3 transition-all hover:bg-[var(--bg-surface-alt)]">
                <span class="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-sky-500/10">
                  <svg class="h-5 w-5 text-sky-400" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433a2.062 2.062 0 01-2.063-2.065 2.064 2.064 0 112.063 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z" />
                  </svg>
                </span>
                <div class="min-w-0 flex-1">
                  <p class="text-[10px] font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">LinkedIn</p>
                  <p class="mt-0.5 text-sm font-medium text-[var(--text-primary)] transition-colors group-hover:text-[var(--color-brand-400)]">Connect with me</p>
                </div>
                <svg class="h-4 w-4 shrink-0 text-[var(--text-tertiary)] opacity-0 transition-opacity group-hover:opacity-100" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </a>

            </div>
          </div>

          <!-- Response time card -->
          <div class="flex items-center gap-3 rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] px-4 py-3.5"
            style="box-shadow: 0 2px 12px hsl(var(--shadow-color) / 0.06)">
            <span class="flex h-9 w-9 shrink-0 items-center justify-center rounded-lg bg-amber-500/10">
              <svg class="h-4 w-4 text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </span>
            <div>
              <p class="text-xs font-semibold text-[var(--text-secondary)]">Typical response time</p>
              <p class="mt-0.5 text-xs text-[var(--text-tertiary)]">Within 24–48 hours</p>
            </div>
          </div>

          <!-- Competitive Programming profiles -->
          <div class="rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-5"
            style="box-shadow: 0 2px 12px hsl(var(--shadow-color) / 0.06)">

            <!-- Header + total problems badge -->
            <div class="mb-3 flex items-center justify-between">
              <h3 class="text-xs font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">Competitive Programming</h3>
              <span class="rounded-full bg-[var(--color-brand-500)]/10 px-2.5 py-0.5 text-[10px] font-bold text-[var(--color-brand-400)]">
                2500+ Problems
              </span>
            </div>

            <div class="grid grid-cols-2 gap-2">

              <!-- Codeforces -->
              <a href="https://codeforces.com/profile/Ellipse" target="_blank" rel="noopener noreferrer"
                class="group flex flex-col gap-1 rounded-lg border border-[var(--border-default)] p-3 transition-all hover:border-blue-500/40 hover:bg-blue-500/5">
                <div class="flex items-center gap-1.5">
                  <span class="rounded px-1.5 py-0.5 text-[10px] font-bold bg-blue-500/10 text-blue-400">CF</span>
                  <span class="text-[10px] text-[var(--text-tertiary)]">Codeforces</span>
                </div>
                <p class="text-xs font-semibold text-[var(--text-primary)] transition-colors group-hover:text-blue-400">Expert</p>
                <p class="text-[10px] text-[var(--text-tertiary)]">Max 1701</p>
              </a>

              <!-- CodeChef -->
              <a href="https://www.codechef.com/users/priyansh_misra" target="_blank" rel="noopener noreferrer"
                class="group flex flex-col gap-1 rounded-lg border border-[var(--border-default)] p-3 transition-all hover:border-amber-500/40 hover:bg-amber-500/5">
                <div class="flex items-center gap-1.5">
                  <span class="rounded px-1.5 py-0.5 text-[10px] font-bold bg-amber-500/10 text-amber-400">CC</span>
                  <span class="text-[10px] text-[var(--text-tertiary)]">CodeChef</span>
                </div>
                <p class="text-xs font-semibold text-[var(--text-primary)] transition-colors group-hover:text-amber-400">4★ Div 2</p>
                <p class="text-[10px] text-[var(--text-tertiary)]">Max 1929 · 697 solved</p>
              </a>

              <!-- LeetCode -->
              <a href="https://leetcode.com/u/Kalki_2898/" target="_blank" rel="noopener noreferrer"
                class="group flex flex-col gap-1 rounded-lg border border-[var(--border-default)] p-3 transition-all hover:border-orange-500/40 hover:bg-orange-500/5">
                <div class="flex items-center gap-1.5">
                  <span class="rounded px-1.5 py-0.5 text-[10px] font-bold bg-orange-500/10 text-orange-400">LC</span>
                  <span class="text-[10px] text-[var(--text-tertiary)]">LeetCode</span>
                </div>
                <p class="text-xs font-semibold text-[var(--text-primary)] transition-colors group-hover:text-orange-400">Top 3.11%</p>
                <p class="text-[10px] text-[var(--text-tertiary)]">Max 2069 contest</p>
              </a>

              <!-- AtCoder -->
              <a href="https://atcoder.jp/users/Kalki_2898" target="_blank" rel="noopener noreferrer"
                class="group flex flex-col gap-1 rounded-lg border border-[var(--border-default)] p-3 transition-all hover:border-emerald-500/40 hover:bg-emerald-500/5">
                <div class="flex items-center gap-1.5">
                  <span class="rounded px-1.5 py-0.5 text-[10px] font-bold bg-emerald-500/10 text-emerald-400">AT</span>
                  <span class="text-[10px] text-[var(--text-tertiary)]">AtCoder</span>
                </div>
                <p class="text-xs font-semibold text-[var(--text-primary)] transition-colors group-hover:text-emerald-400">Green</p>
                <p class="text-[10px] text-[var(--text-tertiary)]">Max 826 · 46 contests</p>
              </a>

            </div>
          </div>

        </div>

        <!-- Right: form -->
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6 lg:col-span-3 lg:p-8"
          style="box-shadow: 0 4px 24px hsl(var(--shadow-color) / 0.10)">
          <div class="mb-6">
            <h2 class="font-display text-lg font-semibold text-[var(--text-primary)]">Send a message</h2>
            <p class="mt-1 text-sm text-[var(--text-secondary)]">Fill out the form and I'll get back to you shortly.</p>
          </div>
          <ContactForm />
        </div>

      </div>
    </div>
  </div>
</template>
