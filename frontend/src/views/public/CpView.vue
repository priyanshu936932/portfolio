<script setup>
const platforms = [
  {
    name: 'LeetCode',
    handle: 'Kalki_2898',
    url: 'https://leetcode.com/u/Kalki_2898/',
    abbr: 'LC',
    rank: 'Knight',
    current: 1964,
    max: 2069,
    stat: 'Top 3.11% · Rank 26,248',
    problems: 383,
    contests: 36,
    textColor: 'text-orange-400',
    bgColor: 'bg-orange-500/10',
    borderHover: 'hover:border-orange-500/50',
    glowColor: 'shadow-orange-500/10',
    badge: 'bg-orange-500/15 text-orange-400',
  },
  {
    name: 'Codeforces',
    handle: 'Ellipse',
    url: 'https://codeforces.com/profile/Ellipse',
    abbr: 'CF',
    rank: 'Expert',
    current: 1701,
    max: 1701,
    stat: 'AIR 900 · India 66',
    problems: 1095,
    contests: 69,
    textColor: 'text-blue-400',
    bgColor: 'bg-blue-500/10',
    borderHover: 'hover:border-blue-500/50',
    glowColor: 'shadow-blue-500/10',
    badge: 'bg-blue-500/15 text-blue-400',
  },
  {
    name: 'CodeChef',
    handle: 'priyansh_misra',
    url: 'https://www.codechef.com/users/priyansh_misra',
    abbr: 'CC',
    rank: '5★ Div 1',
    current: 1856,
    max: 1929,
    stat: 'Top 0.2% · Rank 24',
    problems: 697,
    contests: 74,
    textColor: 'text-amber-400',
    bgColor: 'bg-amber-500/10',
    borderHover: 'hover:border-amber-500/50',
    glowColor: 'shadow-amber-500/10',
    badge: 'bg-amber-500/15 text-amber-400',
  },
  {
    name: 'AtCoder',
    handle: 'Kalki_2898',
    url: 'https://atcoder.jp/users/Kalki_2898',
    abbr: 'AT',
    rank: 'Green',
    current: 822,
    max: 822,
    stat: 'Top 16%',
    problems: 0,
    contests: 45,
    textColor: 'text-emerald-400',
    bgColor: 'bg-emerald-500/10',
    borderHover: 'hover:border-emerald-500/50',
    glowColor: 'shadow-emerald-500/10',
    badge: 'bg-emerald-500/15 text-emerald-400',
  },
]

const dsaTopics = [
  { name: 'Arrays',           count: 243 },
  { name: 'Dynamic Prog.',    count: 103 },
  { name: 'HashMap & Set',    count: 83  },
  { name: 'Strings',          count: 71  },
  { name: 'DFS',              count: 71  },
  { name: 'BFS',              count: 70  },
  { name: 'Math',             count: 59  },
  { name: 'Sorting',          count: 58  },
  { name: 'Trees',            count: 54  },
  { name: 'Matrix',           count: 49  },
]
const maxTopic = Math.max(...dsaTopics.map(t => t.count))

const achievements = [
  {
    title: 'TCS CodeVita Season 11',
    desc: 'Global Rank 227 out of millions of participants worldwide',
    icon: '🏆',
    color: 'border-amber-500/30 bg-amber-500/5',
  },
  {
    title: 'Meta Hacker Cup Round 2',
    desc: 'Qualified for Round 2 · Global Rank 2063',
    icon: '🌐',
    color: 'border-blue-500/30 bg-blue-500/5',
  },
  {
    title: 'Image-AI-Thon\'24',
    desc: '2nd Position — Organized by GDG IIIT Surat',
    icon: '🥈',
    color: 'border-purple-500/30 bg-purple-500/5',
  },
  {
    title: 'Codeforces Div.2',
    desc: 'Best ranks 534 & 703 · India ranks 66 & 75',
    icon: '⚡',
    color: 'border-blue-500/30 bg-blue-500/5',
  },
  {
    title: 'CodeChef Starters',
    desc: 'Best ranks 24 & 89 · Top 0.2% globally',
    icon: '🔥',
    color: 'border-amber-500/30 bg-amber-500/5',
  },
  {
    title: 'LeetCode Contests',
    desc: 'Biweekly Contest 171 · Rank 5001 globally',
    icon: '💡',
    color: 'border-orange-500/30 bg-orange-500/5',
  },
]
</script>

<template>
  <div class="relative overflow-hidden min-h-screen">

    <!-- Background glows -->
    <div class="pointer-events-none absolute inset-0 overflow-hidden">
      <div class="absolute -top-48 right-0 h-[600px] w-[600px] rounded-full opacity-[0.06] blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-500), transparent 70%)" />
      <div class="absolute bottom-0 -left-32 h-[400px] w-[400px] rounded-full opacity-[0.04] blur-3xl"
        style="background: radial-gradient(circle, #f97316, transparent 70%)" />
    </div>

    <div class="relative mx-auto max-w-6xl px-6 py-16 md:py-24">

      <!-- Header -->
      <div class="mb-16 text-center">
        <div class="mb-5 inline-flex items-center gap-2 rounded-full border border-[var(--color-brand-400)]/30 bg-[var(--color-brand-500)]/10 px-4 py-1.5">
          <span class="text-xs font-semibold uppercase tracking-widest text-[var(--color-brand-400)]">Competitive Programming</span>
        </div>
        <h1 class="font-display text-4xl font-bold text-[var(--text-primary)] md:text-5xl lg:text-6xl">
          Grinding since day one
        </h1>
        <p class="mx-auto mt-4 max-w-lg text-lg text-[var(--text-secondary)]">
          2380+ problems solved across platforms. Consistently competing and improving every week.
        </p>
      </div>

      <!-- Global stats -->
      <div class="mb-12 grid grid-cols-2 gap-4 sm:grid-cols-4">
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-5 text-center"
          style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.08)">
          <p class="font-display text-3xl font-bold text-[var(--color-brand-400)]">2380<span class="text-xl">+</span></p>
          <p class="mt-1 text-xs text-[var(--text-tertiary)]">Problems Solved</p>
        </div>
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-5 text-center"
          style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.08)">
          <p class="font-display text-3xl font-bold text-[var(--color-brand-400)]">232</p>
          <p class="mt-1 text-xs text-[var(--text-tertiary)]">Contests</p>
        </div>
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-5 text-center"
          style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.08)">
          <p class="font-display text-3xl font-bold text-[var(--color-brand-400)]">473</p>
          <p class="mt-1 text-xs text-[var(--text-tertiary)]">Active Days</p>
        </div>
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-5 text-center"
          style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.08)">
          <p class="font-display text-3xl font-bold text-[var(--color-brand-400)]">2261</p>
          <p class="mt-1 text-xs text-[var(--text-tertiary)]">Global Rank</p>
        </div>
      </div>

      <!-- Platform cards -->
      <div class="mb-12 grid gap-5 sm:grid-cols-2 lg:grid-cols-4">
        <a
          v-for="p in platforms" :key="p.name"
          :href="p.url" target="_blank" rel="noopener noreferrer"
          class="group relative overflow-hidden rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-5 transition-all duration-300"
          :class="[p.borderHover, 'hover:-translate-y-1']"
          style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.08)"
        >
          <!-- Platform badge -->
          <div class="mb-4 flex items-center justify-between">
            <div :class="['flex h-10 w-10 items-center justify-center rounded-xl text-sm font-black', p.bgColor, p.textColor]">
              {{ p.abbr }}
            </div>
            <span :class="['rounded-full px-2.5 py-0.5 text-[10px] font-semibold', p.badge]">
              {{ p.rank }}
            </span>
          </div>

          <p class="font-display text-base font-bold text-[var(--text-primary)]">{{ p.name }}</p>
          <p class="mt-0.5 text-xs text-[var(--text-tertiary)]">@{{ p.handle }}</p>

          <!-- Rating -->
          <div class="mt-3 flex items-end gap-1">
            <span :class="['font-display text-3xl font-bold', p.textColor]">{{ p.current }}</span>
            <span class="mb-0.5 text-xs text-[var(--text-tertiary)]">/ max {{ p.max }}</span>
          </div>

          <p class="mt-1 text-[11px] text-[var(--text-secondary)]">{{ p.stat }}</p>

          <!-- Stats row -->
          <div class="mt-3 flex gap-3 border-t border-[var(--border-default)] pt-3">
            <div v-if="p.problems" class="text-center">
              <p class="text-xs font-semibold text-[var(--text-primary)]">{{ p.problems }}</p>
              <p class="text-[10px] text-[var(--text-tertiary)]">Problems</p>
            </div>
            <div class="text-center">
              <p class="text-xs font-semibold text-[var(--text-primary)]">{{ p.contests }}</p>
              <p class="text-[10px] text-[var(--text-tertiary)]">Contests</p>
            </div>
            <div class="ml-auto flex items-center">
              <svg class="h-3.5 w-3.5 text-[var(--text-tertiary)] transition-transform group-hover:translate-x-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14" />
              </svg>
            </div>
          </div>
        </a>
      </div>

      <!-- DSA Topic Analysis -->
      <div class="mb-12 grid gap-6 lg:grid-cols-2">

        <!-- Topics bar chart -->
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6"
          style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.08)">
          <h2 class="mb-5 font-display text-base font-semibold text-[var(--text-primary)]">DSA Topic Analysis</h2>
          <div class="space-y-3">
            <div v-for="topic in dsaTopics" :key="topic.name" class="flex items-center gap-3">
              <span class="w-28 shrink-0 text-right text-xs text-[var(--text-secondary)]">{{ topic.name }}</span>
              <div class="flex-1 overflow-hidden rounded-full bg-[var(--bg-surface-alt)] h-2">
                <div
                  class="h-full rounded-full bg-[var(--color-brand-400)] transition-all duration-700"
                  :style="{ width: (topic.count / maxTopic * 100) + '%' }"
                />
              </div>
              <span class="w-8 shrink-0 text-right text-xs font-medium text-[var(--text-primary)]">{{ topic.count }}</span>
            </div>
          </div>
        </div>

        <!-- LeetCode difficulty breakdown -->
        <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6"
          style="box-shadow: 0 4px 20px hsl(var(--shadow-color) / 0.08)">
          <h2 class="mb-5 font-display text-base font-semibold text-[var(--text-primary)]">LeetCode Breakdown</h2>

          <div class="mb-6 flex items-center justify-center">
            <div class="relative flex h-36 w-36 items-center justify-center">
              <svg class="absolute inset-0 h-full w-full -rotate-90" viewBox="0 0 100 100">
                <!-- Easy -->
                <circle cx="50" cy="50" r="40" fill="none" stroke="var(--bg-surface-alt)" stroke-width="12"/>
                <!-- Easy 83/383 = 21.7% → 54 -->
                <circle cx="50" cy="50" r="40" fill="none" stroke="#22c55e" stroke-width="12"
                  stroke-dasharray="54 251" stroke-dashoffset="0"/>
                <!-- Medium 225/383 = 58.7% → 147 -->
                <circle cx="50" cy="50" r="40" fill="none" stroke="#f97316" stroke-width="12"
                  stroke-dasharray="147 251" stroke-dashoffset="-54"/>
                <!-- Hard 75/383 = 19.6% → 49 -->
                <circle cx="50" cy="50" r="40" fill="none" stroke="#ef4444" stroke-width="12"
                  stroke-dasharray="49 251" stroke-dashoffset="-201"/>
              </svg>
              <div class="text-center">
                <p class="font-display text-2xl font-bold text-[var(--text-primary)]">383</p>
                <p class="text-[10px] text-[var(--text-tertiary)]">Solved</p>
              </div>
            </div>
          </div>

          <div class="grid grid-cols-3 gap-3 text-center">
            <div class="rounded-xl bg-green-500/10 p-3">
              <p class="font-display text-xl font-bold text-green-400">83</p>
              <p class="mt-0.5 text-[10px] text-[var(--text-tertiary)]">Easy</p>
            </div>
            <div class="rounded-xl bg-orange-500/10 p-3">
              <p class="font-display text-xl font-bold text-orange-400">225</p>
              <p class="mt-0.5 text-[10px] text-[var(--text-tertiary)]">Medium</p>
            </div>
            <div class="rounded-xl bg-red-500/10 p-3">
              <p class="font-display text-xl font-bold text-red-400">75</p>
              <p class="mt-0.5 text-[10px] text-[var(--text-tertiary)]">Hard</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Achievements -->
      <div>
        <h2 class="mb-6 font-display text-2xl font-bold text-[var(--text-primary)]">Notable Achievements</h2>
        <div class="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          <div
            v-for="a in achievements" :key="a.title"
            class="rounded-xl border p-4 transition-all hover:-translate-y-0.5"
            :class="a.color"
          >
            <div class="mb-2 flex items-center gap-2">
              <span class="text-xl">{{ a.icon }}</span>
              <p class="text-sm font-semibold text-[var(--text-primary)]">{{ a.title }}</p>
            </div>
            <p class="text-xs text-[var(--text-secondary)]">{{ a.desc }}</p>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
