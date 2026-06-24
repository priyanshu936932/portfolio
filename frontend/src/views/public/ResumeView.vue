<script setup>
import { ref, onMounted } from 'vue'
import { profileApi } from '@/api/profile'

const DEFAULT_RESUME = 'https://drive.google.com/file/d/1baHQtbwvVp4u6241uKMvfqzudCCW5Qqo/view?usp=sharing'

const resumeUrl = ref(DEFAULT_RESUME)
const loading   = ref(true)

onMounted(async () => {
  try {
    const { data } = await profileApi.get()
    resumeUrl.value = data.resumeUrl || DEFAULT_RESUME
  } catch { /* use default */ }
  finally { loading.value = false }
})

const experience = [
  {
    role: 'Software Development Engineer Intern',
    company: 'Zeta (Directi)',
    location: 'Bangalore, India',
    period: 'January 2026 – Present',
    points: [
      'Productionized high-throughput Spring Boot backend services for fintech transaction workflows.',
      'Implemented secure API authentication and RBAC via Spring Security with JWT-based access control.',
      'Optimized Spring Data JPA queries and indexing, reducing average API response time.',
      'Engineered REST APIs handling 1k+ test transactions during internal validation cycles.',
      'Reduced build failure rate by improving CI/CD pipelines and resolving cross-module dependency issues.',
    ],
  },
  {
    role: 'Technical Solution Engineer',
    company: 'AlgoUniversity',
    location: 'Remote',
    period: 'June 2025 – July 2025',
    points: [
      'Mentored 100+ students in Data Structures and Algorithms for structured technical interview preparation.',
      'Created 50+ original problem sets and conducted 30+ mock interviews simulating real placement scenarios.',
      'Improved student coding interview performance speed by 35% using structured debugging frameworks.',
    ],
  },
]

const projects = [
  {
    name: 'Digital Fixed Deposit System',
    period: 'Oct 2025 – Jan 2026',
    github: 'https://github.com/priyanshu936932',
    stack: 'Java 17, Spring Boot, Spring Security, PostgreSQL, Vue 3, TypeScript, JWT, OAuth2',
    points: [
      'Architected a secure Fixed Deposit booking platform with automated interest computation and maturity lifecycle processing.',
      'Integrated JWT (Access + Refresh tokens) with refresh token rotation, Google OAuth2, OTP verification, and RBAC.',
      'Delivered a normalized PostgreSQL schema ensuring ACID compliance and optimized indexing.',
      'Operationalized production-grade RESTful APIs with global exception handling and structured logging.',
    ],
  },
  {
    name: 'Payments Management System',
    period: 'May 2025 – Jul 2025',
    github: 'https://github.com/priyanshu936932',
    stack: 'JavaScript, Vue 3, TypeScript, Vuex, Vite, Jest',
    points: [
      'Designed modular frontend architecture integrating Vue 3 Composition API with strong type safety in TypeScript.',
      'Established centralized state management with Vuex and modular client-side routing with Vue Router.',
      'Developed unit and component tests using Jest and Vue Test Utils to ensure code reliability.',
    ],
  },
]

const achievements = [
  { text: 'Codeforces Expert (1700+) — AIR 900; Div.2 Ranks 534 & 703 (India 66 & 75)' },
  { text: 'CodeChef 5-Star (2000+) — Top 0.2%; Starters Ranks 24 & 89' },
  { text: 'LeetCode Knight (2069 Rating) — Top 1.5% Globally' },
  { text: '2.3k+ DSA Problems Solved across Codeforces, CodeChef, LeetCode and other platforms' },
  { text: 'Image-AI-Thon\'24: 2nd Position — Organized by GDG IIIT Surat' },
  { text: 'TCS CodeVita Season 11 – Round 1: Global Rank 227' },
  { text: 'Meta Hacker Cup – Round 2: Global Rank 2063' },
]

const skills = [
  { label: 'Languages',           value: 'C++, Java, Python, JavaScript, SQL' },
  { label: 'Backend & Databases', value: 'Spring Boot, Spring Security, REST APIs, JWT, OAuth2, PostgreSQL, MySQL' },
  { label: 'Frontend',            value: 'React.js, Vue 3, TypeScript, Vuex' },
  { label: 'Tools',               value: 'Git, GitHub, IntelliJ, VS Code, Postman, Docker' },
]
</script>

<template>
  <div class="relative overflow-hidden min-h-screen">

    <!-- Background glow -->
    <div class="pointer-events-none absolute inset-0 overflow-hidden">
      <div class="absolute -top-48 right-0 h-[500px] w-[500px] rounded-full opacity-[0.06] blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-500), transparent 70%)" />
    </div>

    <div class="relative mx-auto max-w-4xl px-6 py-16 md:py-24">

      <!-- Header -->
      <div class="mb-10 text-center">
        <div class="mb-5 inline-flex items-center gap-2 rounded-full border border-[var(--color-brand-400)]/30 bg-[var(--color-brand-500)]/10 px-4 py-1.5">
          <span class="text-xs font-semibold uppercase tracking-widest text-[var(--color-brand-400)]">Resume</span>
        </div>
        <h1 class="font-display text-4xl font-bold text-[var(--text-primary)] md:text-5xl">Priyanshu Mishra</h1>
        <p class="mt-2 text-[var(--text-secondary)]">B.Tech CSE · IET Lucknow · CGPA 8.35</p>

        <!-- Download button -->
        <div class="mt-6 flex flex-wrap items-center justify-center gap-3">
          <a
            v-if="resumeUrl"
            :href="resumeUrl"
            target="_blank"
            rel="noopener noreferrer"
            class="inline-flex items-center gap-2 rounded-xl bg-[var(--color-brand-500)] px-6 py-2.5 text-sm font-semibold text-white shadow-lg transition-all hover:opacity-90 hover:-translate-y-0.5"
          >
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
            Download Resume
          </a>
          <div v-else-if="loading" class="h-10 w-40 animate-pulse rounded-xl bg-[var(--bg-surface-alt)]" />
          <p v-else class="text-sm text-[var(--text-tertiary)]">Resume link not configured yet.</p>

          <RouterLink to="/contact"
            class="inline-flex items-center gap-2 rounded-xl border border-[var(--border-default)] px-6 py-2.5 text-sm font-semibold text-[var(--text-secondary)] transition-all hover:border-[var(--color-brand-400)] hover:text-[var(--color-brand-400)]">
            Hire me →
          </RouterLink>
        </div>
      </div>

      <!-- Resume content card -->
      <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] divide-y divide-[var(--border-default)]"
        style="box-shadow: 0 4px 32px hsl(var(--shadow-color) / 0.10)">

        <!-- Education -->
        <div class="p-6 md:p-8">
          <h2 class="mb-4 flex items-center gap-2 font-display text-sm font-bold uppercase tracking-widest text-[var(--color-brand-400)]">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z" />
            </svg>
            Education
          </h2>
          <div class="flex flex-col gap-1 sm:flex-row sm:items-start sm:justify-between">
            <div>
              <p class="font-semibold text-[var(--text-primary)]">Institute of Engineering and Technology, Lucknow</p>
              <p class="text-sm text-[var(--text-secondary)]">B.Tech in Computer Science and Engineering</p>
              <p class="mt-1 text-xs text-[var(--text-tertiary)]">Relevant coursework: DBMS, OOP, Operating Systems, Computer Networks, DSA</p>
            </div>
            <div class="shrink-0 text-right">
              <p class="text-sm font-semibold text-[var(--color-brand-400)]">CGPA: 8.21</p>
              <p class="text-xs text-[var(--text-tertiary)]">Nov 2022 – May 2026</p>
            </div>
          </div>
        </div>

        <!-- Experience -->
        <div class="p-6 md:p-8">
          <h2 class="mb-5 flex items-center gap-2 font-display text-sm font-bold uppercase tracking-widest text-[var(--color-brand-400)]">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
            </svg>
            Experience
          </h2>
          <div class="space-y-6">
            <div v-for="exp in experience" :key="exp.company">
              <div class="flex flex-col gap-1 sm:flex-row sm:items-start sm:justify-between">
                <div>
                  <p class="font-semibold text-[var(--text-primary)]">{{ exp.role }}</p>
                  <p class="text-sm text-[var(--color-brand-400)] font-medium">{{ exp.company }} · {{ exp.location }}</p>
                </div>
                <p class="shrink-0 text-xs text-[var(--text-tertiary)]">{{ exp.period }}</p>
              </div>
              <ul class="mt-2 space-y-1">
                <li v-for="pt in exp.points" :key="pt" class="flex gap-2 text-sm text-[var(--text-secondary)]">
                  <span class="mt-1.5 h-1.5 w-1.5 shrink-0 rounded-full bg-[var(--color-brand-400)]" />
                  {{ pt }}
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Projects -->
        <div class="p-6 md:p-8">
          <h2 class="mb-5 flex items-center gap-2 font-display text-sm font-bold uppercase tracking-widest text-[var(--color-brand-400)]">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
            </svg>
            Projects
          </h2>
          <div class="space-y-6">
            <div v-for="proj in projects" :key="proj.name">
              <div class="flex flex-col gap-1 sm:flex-row sm:items-start sm:justify-between">
                <div>
                  <p class="font-semibold text-[var(--text-primary)]">{{ proj.name }}</p>
                  <p class="text-[11px] text-[var(--text-tertiary)]">{{ proj.stack }}</p>
                </div>
                <p class="shrink-0 text-xs text-[var(--text-tertiary)]">{{ proj.period }}</p>
              </div>
              <ul class="mt-2 space-y-1">
                <li v-for="pt in proj.points" :key="pt" class="flex gap-2 text-sm text-[var(--text-secondary)]">
                  <span class="mt-1.5 h-1.5 w-1.5 shrink-0 rounded-full bg-[var(--color-brand-400)]" />
                  {{ pt }}
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Achievements -->
        <div class="p-6 md:p-8">
          <h2 class="mb-4 flex items-center gap-2 font-display text-sm font-bold uppercase tracking-widest text-[var(--color-brand-400)]">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z" />
            </svg>
            Achievements
          </h2>
          <ul class="space-y-2">
            <li v-for="a in achievements" :key="a.text" class="flex gap-2 text-sm text-[var(--text-secondary)]">
              <span class="mt-1.5 h-1.5 w-1.5 shrink-0 rounded-full bg-amber-400" />
              {{ a.text }}
            </li>
          </ul>
        </div>

        <!-- Skills -->
        <div class="p-6 md:p-8">
          <h2 class="mb-4 flex items-center gap-2 font-display text-sm font-bold uppercase tracking-widest text-[var(--color-brand-400)]">
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
            </svg>
            Technical Skills
          </h2>
          <div class="space-y-2">
            <div v-for="s in skills" :key="s.label" class="flex flex-col gap-0.5 sm:flex-row sm:gap-3">
              <span class="w-36 shrink-0 text-xs font-semibold text-[var(--text-primary)]">{{ s.label }}:</span>
              <span class="text-xs text-[var(--text-secondary)]">{{ s.value }}</span>
            </div>
          </div>
        </div>

      </div>

      <!-- Bottom download CTA -->
      <div class="mt-8 text-center">
        <a
          v-if="resumeUrl"
          :href="resumeUrl"
          target="_blank"
          rel="noopener noreferrer"
          class="inline-flex items-center gap-2 rounded-xl bg-[var(--color-brand-500)] px-8 py-3 text-sm font-semibold text-white shadow-lg transition-all hover:opacity-90 hover:-translate-y-0.5"
        >
          <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
          </svg>
          Download PDF Resume
        </a>
      </div>

    </div>
  </div>
</template>
