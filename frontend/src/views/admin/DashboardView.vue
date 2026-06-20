<script setup>
import { ref, onMounted } from 'vue'
import { useProjectsStore } from '@/stores/projects'
import { useBlogStore } from '@/stores/blog'
import { contactApi } from '@/api/contact'

const projectsStore = useProjectsStore()
const blogStore     = useBlogStore()
const messages      = ref([])

onMounted(async () => {
  await Promise.all([
    projectsStore.fetchAll(),
    blogStore.fetchAllAdmin(),
    contactApi.getAll().then(r => { messages.value = r.data }).catch(() => {}),
  ])
})

const unread = () => messages.value.filter((m) => !m.read).length
</script>

<template>
  <div>
    <div class="mb-8">
      <h1 class="font-display text-3xl font-bold text-[var(--text-primary)]">Dashboard</h1>
      <p class="mt-1 text-sm text-[var(--text-secondary)]">Welcome back — here's your portfolio overview.</p>
    </div>

    <!-- Stat cards -->
    <div class="grid gap-4 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-4">
      <div v-for="stat in [
        { label: 'Projects',         value: projectsStore.items.length,          icon: 'M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10', to: '/admin/projects' },
        { label: 'Blog Posts',       value: blogStore.items.length,              icon: 'M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z', to: '/admin/blog' },
        { label: 'Published Posts',  value: blogStore.items.filter(p=>p.published).length, icon: 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z', to: '/admin/blog' },
        { label: 'Unread Messages',  value: unread(),                            icon: 'M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z', to: '/admin/messages', highlight: unread() > 0 },
      ]" :key="stat.label">
        <RouterLink :to="stat.to"
          :class="[
            'block rounded-2xl border p-5 transition-all hover:-translate-y-0.5',
            stat.highlight
              ? 'border-[var(--color-brand-500)]/40 bg-[var(--color-brand-500)]/5'
              : 'border-[var(--border-default)] bg-[var(--bg-surface)]',
          ]"
          style="box-shadow: 0 4px 16px hsl(var(--shadow-color) / 0.08)"
        >
          <div class="flex items-start justify-between">
            <div>
              <p class="text-sm text-[var(--text-tertiary)]">{{ stat.label }}</p>
              <p class="mt-1 font-display text-3xl font-bold text-[var(--text-primary)]">{{ stat.value }}</p>
            </div>
            <div :class="['flex h-10 w-10 items-center justify-center rounded-xl', stat.highlight ? 'bg-[var(--color-brand-500)]/15' : 'bg-[var(--bg-surface-alt)]']">
              <svg :class="['h-5 w-5', stat.highlight ? 'text-[var(--color-brand-400)]' : 'text-[var(--text-tertiary)]']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" :d="stat.icon" />
              </svg>
            </div>
          </div>
        </RouterLink>
      </div>
    </div>

    <!-- Quick links -->
    <div class="mt-10">
      <h2 class="mb-4 font-display text-lg font-semibold text-[var(--text-primary)]">Quick actions</h2>
      <div class="grid gap-3 sm:grid-cols-2 lg:grid-cols-3">
        <RouterLink v-for="link in [
          { to: '/admin/projects', label: 'Manage Projects', desc: 'Add, edit or delete projects' },
          { to: '/admin/blog',     label: 'Write a Post',    desc: 'Create or update blog posts' },
          { to: '/admin/messages', label: 'View Messages',   desc: 'Read contact form submissions' },
        ]" :key="link.to" :to="link.to"
          class="group rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-4 transition-all hover:border-[var(--color-brand-500)]/40 hover:bg-[var(--bg-surface-alt)]"
        >
          <p class="font-medium text-[var(--text-primary)] group-hover:text-[var(--color-brand-400)] transition-colors">{{ link.label }}</p>
          <p class="mt-0.5 text-xs text-[var(--text-tertiary)]">{{ link.desc }}</p>
        </RouterLink>
      </div>
    </div>
  </div>
</template>
