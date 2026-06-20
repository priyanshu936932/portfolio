<script setup>
import { computed } from 'vue'
import BaseBadge from '@/components/ui/BaseBadge.vue'

const props = defineProps({
  skills: { type: Array, default: () => [] },
  groupByCategory: { type: Boolean, default: false },
})

const grouped = computed(() => {
  if (!props.groupByCategory) return null
  return props.skills.reduce((acc, skill) => {
    const cat = skill.category || 'Other'
    if (!acc[cat]) acc[cat] = []
    acc[cat].push(skill)
    return acc
  }, {})
})
</script>

<template>
  <!-- Grouped view -->
  <div v-if="groupByCategory && grouped" class="space-y-6">
    <div v-for="(catSkills, category) in grouped" :key="category">
      <h4 class="mb-3 text-xs font-semibold uppercase tracking-widest text-[var(--text-tertiary)]">
        {{ category }}
      </h4>
      <div class="flex flex-wrap gap-2">
        <div
          v-for="skill in catSkills"
          :key="skill.id"
          class="flex items-center gap-2 rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] px-3 py-2 text-sm text-[var(--text-secondary)] transition-colors hover:border-[var(--color-brand-500)]/50 hover:text-[var(--color-brand-400)]"
        >
          <span>{{ skill.name }}</span>
          <!-- Proficiency dots -->
          <span class="flex gap-0.5">
            <span
              v-for="n in 5"
              :key="n"
              :class="['h-1.5 w-1.5 rounded-full', n <= skill.proficiency ? 'bg-[var(--color-brand-500)]' : 'bg-[var(--border-strong)]']"
            />
          </span>
        </div>
      </div>
    </div>
  </div>

  <!-- Flat view -->
  <div v-else class="flex flex-wrap gap-2">
    <BaseBadge v-for="skill in skills" :key="skill.id" variant="brand">
      {{ skill.name }}
    </BaseBadge>
  </div>
</template>
