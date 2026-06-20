<script setup>
import BaseBadge from '@/components/ui/BaseBadge.vue'

defineProps({
  project: { type: Object, required: true },
})
</script>

<template>
  <article
    class="group flex flex-col overflow-hidden rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] transition-all duration-300 hover:-translate-y-1 hover:border-[var(--color-brand-500)]/40"
    style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.1)"
  >
    <!-- Cover image -->
    <div class="relative h-40 overflow-hidden bg-[var(--bg-surface-alt)] sm:h-48">
      <img
        v-if="project.imageUrl"
        :src="project.imageUrl"
        :alt="project.title"
        class="h-full w-full object-cover transition-transform duration-500 group-hover:scale-105"
      />
      <div v-else class="flex h-full items-center justify-center">
        <svg class="h-12 w-12 text-[var(--border-strong)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1"
            d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
        </svg>
      </div>
      <!-- Status pill -->
      <span
        v-if="project.status"
        :class="[
          'absolute right-3 top-3 rounded-full px-2.5 py-0.5 text-xs font-semibold',
          project.status === 'LIVE'     && 'bg-emerald-500/20 text-emerald-400',
          project.status === 'IN_PROGRESS' && 'bg-[var(--color-brand-500)]/20 text-[var(--color-brand-400)]',
          project.status === 'ARCHIVED' && 'bg-[var(--bg-surface-alt)] text-[var(--text-tertiary)]',
        ]"
      >
        {{ project.status === 'IN_PROGRESS' ? 'In Progress' : project.status.charAt(0) + project.status.slice(1).toLowerCase() }}
      </span>
    </div>

    <!-- Body -->
    <div class="flex flex-1 flex-col gap-3 p-5">
      <h3 class="font-display text-lg font-semibold leading-tight text-[var(--text-primary)] group-hover:text-[var(--color-brand-400)] transition-colors">
        {{ project.title }}
      </h3>

      <p class="line-clamp-2 text-sm leading-relaxed text-[var(--text-secondary)]">
        {{ project.summary }}
      </p>

      <!-- Skills -->
      <div v-if="project.skills?.length" class="flex flex-wrap gap-1.5 pt-1">
        <BaseBadge v-for="skill in project.skills.slice(0, 4)" :key="skill.id" variant="brand">
          {{ skill.name }}
        </BaseBadge>
        <BaseBadge v-if="project.skills.length > 4" variant="neutral">
          +{{ project.skills.length - 4 }}
        </BaseBadge>
      </div>

      <!-- Links -->
      <div class="mt-auto flex items-center gap-3 pt-2">
        <RouterLink
          :to="`/projects/${project.slug}`"
          class="text-sm font-medium text-[var(--color-brand-400)] transition-colors hover:text-[var(--color-brand-300)]"
        >
          Details →
        </RouterLink>
        <a
          v-if="project.liveUrl"
          :href="project.liveUrl"
          target="_blank" rel="noopener noreferrer"
          class="flex items-center gap-1 text-sm text-[var(--text-tertiary)] transition-colors hover:text-[var(--text-primary)]"
        >
          <svg class="h-3.5 w-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
          </svg>
          Live
        </a>
        <a
          v-if="project.githubUrl"
          :href="project.githubUrl"
          target="_blank" rel="noopener noreferrer"
          class="flex items-center gap-1 text-sm text-[var(--text-tertiary)] transition-colors hover:text-[var(--text-primary)]"
        >
          <svg class="h-3.5 w-3.5" fill="currentColor" viewBox="0 0 24 24">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" />
          </svg>
          GitHub
        </a>
      </div>
    </div>
  </article>
</template>
