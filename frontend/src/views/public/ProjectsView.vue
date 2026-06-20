<script setup>
import { onMounted, ref, computed } from 'vue'
import { useProjectsStore } from '@/stores/projects'
import ProjectCard from '@/components/public/ProjectCard.vue'
import SkeletonCard from '@/components/ui/SkeletonCard.vue'
import EmptyState from '@/components/ui/EmptyState.vue'

const store = useProjectsStore()
const search = ref('')

onMounted(() => store.fetchAll())

const filtered = computed(() => {
  const q = search.value.toLowerCase()
  if (!q) return store.items
  return store.items.filter((p) =>
    p.title.toLowerCase().includes(q) ||
    p.summary?.toLowerCase().includes(q) ||
    (p.skills ?? []).some((s) => s.name.toLowerCase().includes(q))
  )
})

const emptyTitle = computed(() => search.value ? 'No results' : 'No projects yet')
const emptyDesc  = computed(() => search.value ? 'No projects match "' + search.value + '"' : 'Check back soon.')
</script>

<template>
  <div class="mx-auto max-w-6xl px-6 py-16">
    <!-- Header -->
    <div class="mb-10">
      <p class="text-xs font-semibold uppercase tracking-widest text-[var(--color-brand-400)]">Work</p>
      <h1 class="mt-1 font-display text-4xl font-bold text-[var(--text-primary)]">Projects</h1>
      <p class="mt-3 max-w-lg text-[var(--text-secondary)]">
        Things I've built — from side projects to production systems.
      </p>
    </div>

    <!-- Search -->
    <div class="mb-8">
      <div class="relative max-w-sm">
        <svg class="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-[var(--text-tertiary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
        </svg>
        <input
          v-model="search"
          placeholder="Search projects or skills…"
          class="w-full rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] py-2.5 pl-9 pr-4 text-sm text-[var(--text-primary)] placeholder-[var(--text-tertiary)] outline-none transition-colors focus:border-[var(--color-brand-400)] focus:ring-2 focus:ring-[var(--color-brand-400)]/20"
        />
      </div>
    </div>

    <div v-if="store.loading" class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
      <SkeletonCard v-for="i in 6" :key="i" />
    </div>

    <EmptyState
      v-else-if="!filtered.length"
      :title="emptyTitle"
      :description="emptyDesc"
    />

    <div v-else class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
      <ProjectCard v-for="project in filtered" :key="project.id" :project="project" />
    </div>
  </div>
</template>
