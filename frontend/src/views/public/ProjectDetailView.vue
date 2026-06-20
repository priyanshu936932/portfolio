<script setup>
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useProjectsStore } from '@/stores/projects'
import SkillBadgeGroup from '@/components/public/SkillBadgeGroup.vue'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import BaseButton from '@/components/ui/BaseButton.vue'

const route  = useRoute()
const router = useRouter()
const store  = useProjectsStore()

onMounted(async () => {
  await store.fetchBySlug(route.params.slug)
  if (store.current) document.title = store.current.title + ' — Priyanshu'
})
</script>

<template>
  <div class="mx-auto max-w-4xl px-6 py-16">

    <button @click="router.back()"
      class="mb-8 flex items-center gap-2 text-sm text-[var(--text-tertiary)] transition-colors hover:text-[var(--color-brand-400)]"
    >
      <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
      </svg>
      Back to projects
    </button>

    <LoadingSpinner v-if="store.loading" />

    <div v-else-if="store.error" class="rounded-2xl border border-red-500/20 bg-red-500/5 p-8 text-center">
      <p class="text-red-400">{{ store.error }}</p>
      <BaseButton variant="ghost" class="mt-4" @click="router.push('/projects')">Back to projects</BaseButton>
    </div>

    <template v-else-if="store.current">
      <!-- Cover image -->
      <div v-if="store.current.imageUrl" class="mb-8 overflow-hidden rounded-2xl border border-[var(--border-default)]">
        <img :src="store.current.imageUrl" :alt="store.current.title" class="h-72 w-full object-cover" />
      </div>

      <!-- Title + meta -->
      <div class="mb-8">
        <div class="mb-3 flex flex-wrap items-center gap-3">
          <span
            v-if="store.current.status"
            :class="[
              'rounded-full px-3 py-1 text-xs font-semibold',
              store.current.status === 'LIVE'         && 'bg-emerald-500/10 text-emerald-400',
              store.current.status === 'IN_PROGRESS'  && 'bg-[var(--color-brand-500)]/10 text-[var(--color-brand-400)]',
              store.current.status === 'ARCHIVED'     && 'bg-[var(--bg-surface-alt)] text-[var(--text-tertiary)]',
            ]"
          >
            {{ store.current.status === 'IN_PROGRESS' ? 'In Progress' : store.current.status }}
          </span>
        </div>
        <h1 class="font-display text-4xl font-bold text-[var(--text-primary)]">{{ store.current.title }}</h1>
        <p class="mt-3 text-lg text-[var(--text-secondary)]">{{ store.current.summary }}</p>
      </div>

      <!-- Action links -->
      <div class="mb-10 flex flex-wrap gap-3">
        <a v-if="store.current.liveUrl" :href="store.current.liveUrl" target="_blank" rel="noopener noreferrer"
          class="inline-flex items-center gap-2 rounded-xl bg-[var(--color-brand-500)] px-5 py-2.5 text-sm font-semibold text-white transition-colors hover:bg-[var(--color-brand-600)]"
        >
          <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
          </svg>
          Live Demo
        </a>
        <a v-if="store.current.githubUrl" :href="store.current.githubUrl" target="_blank" rel="noopener noreferrer"
          class="inline-flex items-center gap-2 rounded-xl border border-[var(--border-strong)] bg-[var(--bg-surface)] px-5 py-2.5 text-sm font-semibold text-[var(--text-secondary)] transition-all hover:border-[var(--color-brand-400)] hover:text-[var(--color-brand-400)]"
        >
          <svg class="h-4 w-4" fill="currentColor" viewBox="0 0 24 24">
            <path fill-rule="evenodd" clip-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" />
          </svg>
          GitHub
        </a>
      </div>

      <!-- Description -->
      <div class="prose prose-invert mb-10 max-w-none">
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-8">
          <h2 class="mb-4 font-display text-xl font-semibold text-[var(--text-primary)]">About this project</h2>
          <p class="whitespace-pre-wrap leading-relaxed text-[var(--text-secondary)]">{{ store.current.description }}</p>
        </div>
      </div>

      <!-- Skills -->
      <div v-if="store.current.skills?.length" class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-8">
        <h2 class="mb-5 font-display text-xl font-semibold text-[var(--text-primary)]">Technologies Used</h2>
        <SkillBadgeGroup :skills="store.current.skills" />
      </div>
    </template>
  </div>
</template>
