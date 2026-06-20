<script setup>
import BaseBadge from '@/components/ui/BaseBadge.vue'

defineProps({
  post: { type: Object, required: true },
})

function formatDate(iso) {
  if (!iso) return ''
  return new Date(iso).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' })
}
</script>

<template>
  <RouterLink :to="`/blog/${post.slug}`" class="group block">
    <article
      class="flex h-full flex-col overflow-hidden rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] transition-all duration-300 hover:-translate-y-1 hover:border-[var(--color-brand-500)]/40"
      style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.1)"
    >
      <!-- Cover -->
      <div class="h-44 overflow-hidden bg-[var(--bg-surface-alt)]">
        <img
          v-if="post.coverImageUrl"
          :src="post.coverImageUrl"
          :alt="post.title"
          class="h-full w-full object-cover transition-transform duration-500 group-hover:scale-105"
        />
        <div v-else class="flex h-full items-center justify-center">
          <svg class="h-10 w-10 text-[var(--border-strong)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1"
              d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
          </svg>
        </div>
      </div>

      <div class="flex flex-1 flex-col gap-3 p-5">
        <!-- Tags -->
        <div v-if="post.tags?.length" class="flex flex-wrap gap-1.5">
          <BaseBadge v-for="tag in post.tags.slice(0, 3)" :key="tag.id" variant="accent">
            {{ tag.name }}
          </BaseBadge>
        </div>

        <h3 class="font-display text-lg font-semibold leading-snug text-[var(--text-primary)] group-hover:text-[var(--color-brand-400)] transition-colors">
          {{ post.title }}
        </h3>

        <p class="line-clamp-2 text-sm leading-relaxed text-[var(--text-secondary)]">
          {{ post.summary }}
        </p>

        <div class="mt-auto flex items-center justify-between pt-2 text-xs text-[var(--text-tertiary)]">
          <span>{{ formatDate(post.createdAt) }}</span>
          <span class="text-[var(--color-brand-400)] font-medium group-hover:underline">Read more →</span>
        </div>
      </div>
    </article>
  </RouterLink>
</template>
