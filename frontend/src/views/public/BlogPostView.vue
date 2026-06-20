<script setup>
import { onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBlogStore } from '@/stores/blog'
import BaseBadge from '@/components/ui/BaseBadge.vue'
import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import { marked } from 'marked'
import DOMPurify from 'dompurify'

const route  = useRoute()
const router = useRouter()
const store  = useBlogStore()

onMounted(async () => {
  await store.fetchBySlug(route.params.slug)
  if (store.current) document.title = store.current.title + ' — Priyanshu'
})

const renderedContent = computed(() => {
  if (!store.current?.content) return ''
  return DOMPurify.sanitize(marked.parse(store.current.content))
})

function formatDate(iso) {
  if (!iso) return ''
  return new Date(iso).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })
}
</script>

<template>
  <div class="mx-auto max-w-3xl px-6 py-16">
    <button
      @click="router.back()"
      class="mb-8 flex items-center gap-2 text-sm text-[var(--text-tertiary)] transition-colors hover:text-[var(--color-brand-400)]"
    >
      <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
      </svg>
      Back to blog
    </button>

    <LoadingSpinner v-if="store.loading" />

    <div v-else-if="store.error" class="rounded-2xl border border-red-500/20 bg-red-500/5 p-8 text-center">
      <p class="text-red-400">{{ store.error }}</p>
      <RouterLink to="/blog"
        class="mt-4 inline-flex items-center gap-1.5 text-sm text-[var(--color-brand-400)] hover:underline"
      >← Back to blog</RouterLink>
    </div>

    <article v-else-if="store.current">
      <!-- Cover image -->
      <div v-if="store.current.coverImageUrl" class="mb-8 overflow-hidden rounded-2xl border border-[var(--border-default)]">
        <img :src="store.current.coverImageUrl" :alt="store.current.title" class="h-72 w-full object-cover" />
      </div>

      <!-- Tags -->
      <div v-if="store.current.tags?.length" class="mb-4 flex flex-wrap gap-2">
        <BaseBadge v-for="tag in store.current.tags" :key="tag.id" variant="accent">
          {{ tag.name }}
        </BaseBadge>
      </div>

      <!-- Title -->
      <h1 class="font-display text-3xl font-bold leading-tight text-[var(--text-primary)] sm:text-4xl md:text-5xl">
        {{ store.current.title }}
      </h1>

      <!-- Meta -->
      <div class="mt-4 flex items-center gap-4 text-sm text-[var(--text-tertiary)]">
        <span>{{ formatDate(store.current.createdAt) }}</span>
      </div>

      <!-- Summary -->
      <p v-if="store.current.summary" class="mt-5 text-lg leading-relaxed text-[var(--text-secondary)]">
        {{ store.current.summary }}
      </p>

      <!-- Divider -->
      <div class="my-8 h-px bg-gradient-to-r from-transparent via-[var(--border-strong)] to-transparent" />

      <!-- Content -->
      <div
        class="prose-content leading-relaxed text-[var(--text-secondary)]"
        v-html="renderedContent"
      />
    </article>
  </div>
</template>

<style>
.prose-content h1, .prose-content h2, .prose-content h3 {
  font-family: var(--font-display);
  color: var(--text-primary);
  margin-top: 2rem;
  margin-bottom: 0.75rem;
  font-weight: 700;
}
.prose-content h1 { font-size: 2rem; }
.prose-content h2 { font-size: 1.5rem; }
.prose-content h3 { font-size: 1.25rem; }
.prose-content p  { margin-bottom: 1.25rem; }
.prose-content a  { color: var(--color-brand-400); text-decoration: underline; }
.prose-content a:hover { color: var(--color-brand-300); }
.prose-content code {
  background: var(--bg-surface-alt);
  border: 1px solid var(--border-default);
  border-radius: 6px;
  padding: 0.15em 0.4em;
  font-size: 0.875em;
  color: var(--color-accent-400);
}
.prose-content pre {
  background: var(--bg-surface);
  border: 1px solid var(--border-default);
  border-radius: 12px;
  padding: 1.25rem;
  overflow-x: auto;
  margin-bottom: 1.5rem;
}
.prose-content pre code {
  background: none;
  border: none;
  padding: 0;
  color: var(--text-secondary);
}
.prose-content ul, .prose-content ol {
  padding-left: 1.5rem;
  margin-bottom: 1.25rem;
}
.prose-content li { margin-bottom: 0.5rem; }
.prose-content blockquote {
  border-left: 3px solid var(--color-brand-500);
  padding-left: 1rem;
  margin: 1.5rem 0;
  color: var(--text-tertiary);
  font-style: italic;
}
.prose-content img {
  border-radius: 12px;
  max-width: 100%;
  margin: 1.5rem 0;
}
</style>
