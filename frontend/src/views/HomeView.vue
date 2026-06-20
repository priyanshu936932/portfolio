<script setup>
import { ref, onMounted, computed } from 'vue'
import { useProjectsStore } from '@/stores/projects'
import { useSkillsStore } from '@/stores/skills'
import { profileApi } from '@/api/profile'
import ProjectCard from '@/components/public/ProjectCard.vue'
import SkeletonCard from '@/components/ui/SkeletonCard.vue'

const projectsStore = useProjectsStore()
const skillsStore   = useSkillsStore()
const profile       = ref(null)

onMounted(async () => {
  await Promise.all([
    projectsStore.fetchAll(),
    skillsStore.fetchAll(),
    profileApi.get().then(r => { profile.value = r.data }).catch(() => {}),
  ])
})

const featured   = computed(() => projectsStore.items.slice(0, 3))
const techStrip  = computed(() => skillsStore.items.slice(0, 12))
</script>

<template>
  <div class="relative overflow-hidden">

    <!-- Background glow blobs -->
    <div class="pointer-events-none absolute inset-0 overflow-hidden">
      <div class="absolute -top-40 left-1/2 h-[600px] w-[600px] -translate-x-1/2 rounded-full opacity-20 blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-500), transparent 70%)" />
      <div class="absolute top-1/3 -left-24 h-[400px] w-[400px] rounded-full opacity-10 blur-3xl"
        style="background: radial-gradient(circle, var(--color-accent-500), transparent 70%)" />
      <div class="absolute bottom-0 right-0 h-[350px] w-[350px] rounded-full opacity-10 blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-700), transparent 70%)" />
    </div>

    <!-- ── Hero ── -->
    <section class="relative mx-auto flex min-h-[88vh] max-w-6xl flex-col items-center justify-center px-6 py-24 text-center">
      <div class="mb-8 inline-flex items-center gap-2 rounded-full border border-[var(--border-strong)] bg-[var(--bg-surface)] px-4 py-1.5 text-xs font-medium text-[var(--text-secondary)] shadow-sm">
        <span class="relative flex h-2 w-2">
          <span class="absolute inline-flex h-full w-full animate-ping rounded-full bg-[var(--color-accent-400)] opacity-75" />
          <span class="relative inline-flex h-2 w-2 rounded-full bg-[var(--color-accent-500)]" />
        </span>
        Open to new opportunities
      </div>

      <h1
        class="font-display font-bold leading-[1.05] tracking-tight text-[var(--text-primary)]"
        style="font-size: clamp(2.5rem, 7vw, 5rem)"
      >
        Building things for
        <br />
        <span style="background: linear-gradient(135deg, var(--color-brand-400) 0%, var(--color-brand-500) 40%, var(--color-accent-400) 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text">
          the web
        </span>
      </h1>

      <p class="mt-3 font-display text-xl font-semibold text-[var(--text-secondary)] md:text-2xl">
        Hi, I'm <span class="text-[var(--text-primary)]">{{ profile?.fullName || 'Priyanshu' }}</span> — Full‑Stack Developer
      </p>

      <p class="mt-5 max-w-lg text-base leading-relaxed text-[var(--text-tertiary)] md:text-lg">
        {{ profile?.bio || 'I design and build fast, accessible, and thoughtful digital products using Spring Boot, Vue 3, and modern cloud infrastructure.' }}
      </p>

      <div class="mt-10 flex flex-wrap items-center justify-center gap-4">
        <RouterLink to="/projects"
          class="group inline-flex items-center gap-2 rounded-xl bg-[var(--color-brand-500)] px-7 py-3 text-sm font-semibold text-white shadow-lg transition-all duration-200 hover:bg-[var(--color-brand-600)] active:scale-95"
          style="box-shadow: 0 8px 24px color-mix(in srgb, var(--color-brand-500) 30%, transparent)"
        >
          View My Work
          <svg class="h-4 w-4 transition-transform duration-200 group-hover:translate-x-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8l4 4m0 0l-4 4m4-4H3" />
          </svg>
        </RouterLink>
        <RouterLink to="/contact"
          class="inline-flex items-center gap-2 rounded-xl border border-[var(--border-strong)] bg-[var(--bg-surface)] px-7 py-3 text-sm font-semibold text-[var(--text-secondary)] transition-all duration-200 hover:border-[var(--color-brand-400)] hover:text-[var(--color-brand-400)] active:scale-95"
        >
          Get in Touch
        </RouterLink>
      </div>

      <!-- Tech strip (live from skills API) -->
      <div v-if="techStrip.length" class="mt-20 flex flex-col items-center gap-4">
        <p class="text-xs font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">Tech I work with</p>
        <div class="flex flex-wrap items-center justify-center gap-2">
          <span v-for="skill in techStrip" :key="skill.id"
            class="rounded-lg border border-[var(--border-default)] bg-[var(--bg-surface)] px-3 py-1.5 text-xs font-medium text-[var(--text-secondary)] transition-colors hover:border-[var(--color-brand-500)] hover:text-[var(--color-brand-400)]"
          >{{ skill.name }}</span>
        </div>
      </div>
    </section>

    <div class="h-px bg-gradient-to-r from-transparent via-[var(--border-strong)] to-transparent" />

    <!-- ── Featured Projects ── -->
    <section class="mx-auto max-w-6xl px-6 py-20">
      <div class="mb-10 flex items-end justify-between">
        <div>
          <p class="text-xs font-semibold uppercase tracking-widest text-[var(--color-brand-400)]">Portfolio</p>
          <h2 class="mt-1 font-display text-3xl font-bold text-[var(--text-primary)]">Featured Projects</h2>
        </div>
        <RouterLink to="/projects"
          class="hidden text-sm font-medium text-[var(--text-secondary)] transition-colors hover:text-[var(--color-brand-400)] sm:block"
        >
          View all →
        </RouterLink>
      </div>

      <div v-if="projectsStore.loading" class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <SkeletonCard v-for="i in 3" :key="i" />
      </div>

      <div v-else-if="featured.length" class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <ProjectCard v-for="project in featured" :key="project.id" :project="project" />
      </div>

      <div v-else class="rounded-2xl border border-[var(--border-default)] py-16 text-center">
        <p class="text-[var(--text-tertiary)]">No projects published yet — check back soon.</p>
      </div>
    </section>

    <div class="h-px bg-gradient-to-r from-transparent via-[var(--border-strong)] to-transparent" />

    <!-- ── Skills strip ── -->
    <section class="bg-[var(--bg-surface-alt)] px-6 py-16">
      <div class="mx-auto max-w-6xl">
        <p class="mb-6 text-center text-xs font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">Skills & Technologies</p>
        <div class="flex flex-wrap items-center justify-center gap-3">
          <span
            v-for="skill in skillsStore.items"
            :key="skill.id"
            class="rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] px-4 py-2 text-sm font-medium text-[var(--text-secondary)] transition-all hover:border-[var(--color-brand-500)]/60 hover:text-[var(--color-brand-400)]"
          >
            {{ skill.name }}
          </span>
          <span v-if="!skillsStore.loading && !skillsStore.items.length"
            class="text-sm text-[var(--text-tertiary)]">Skills loading…</span>
        </div>
      </div>
    </section>

    <!-- ── CTA banner ── -->
    <section class="mx-auto max-w-6xl px-6 py-20 text-center">
      <h2 class="font-display text-3xl font-bold text-[var(--text-primary)] md:text-4xl">
        Have a project in mind?
      </h2>
      <p class="mx-auto mt-4 max-w-md text-[var(--text-secondary)]">
        I'm always open to discussing new ideas and opportunities.
      </p>
      <RouterLink to="/contact"
        class="mt-8 inline-flex items-center gap-2 rounded-xl bg-[var(--color-brand-500)] px-8 py-3.5 text-sm font-semibold text-white transition-all hover:bg-[var(--color-brand-600)] active:scale-95"
        style="box-shadow: 0 8px 24px color-mix(in srgb, var(--color-brand-500) 30%, transparent)"
      >
        Let's talk
        <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8l4 4m0 0l-4 4m4-4H3" />
        </svg>
      </RouterLink>
    </section>
  </div>
</template>
