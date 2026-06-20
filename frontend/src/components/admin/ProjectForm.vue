<script setup>
import { reactive, watch, onMounted } from 'vue'
import BaseInput from '@/components/ui/BaseInput.vue'
import BaseTextarea from '@/components/ui/BaseTextarea.vue'
import BaseButton from '@/components/ui/BaseButton.vue'
import { useSkillsStore } from '@/stores/skills'

const props = defineProps({
  initial: { type: Object, default: null },
  loading: { type: Boolean, default: false },
})
const emit = defineEmits(['submit', 'cancel'])

const skillsStore = useSkillsStore()
onMounted(() => { if (!skillsStore.items.length) skillsStore.fetchAll() })

const form = reactive({
  title: '', slug: '', summary: '', description: '',
  imageUrl: '', liveUrl: '', githubUrl: '',
  status: 'IN_PROGRESS', displayOrder: 0, skillIds: [],
})

watch(() => props.initial, (val) => {
  if (!val) return
  Object.assign(form, {
    title: val.title ?? '',
    slug: val.slug ?? '',
    summary: val.summary ?? '',
    description: val.description ?? '',
    imageUrl: val.imageUrl ?? '',
    liveUrl: val.liveUrl ?? '',
    githubUrl: val.githubUrl ?? '',
    status: val.status ?? 'IN_PROGRESS',
    displayOrder: val.displayOrder ?? 0,
    skillIds: val.skills?.map((s) => s.id) ?? [],
  })
}, { immediate: true })

function autoSlug() {
  if (!props.initial) {
    form.slug = form.title.toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '')
  }
}

function toggleSkill(id) {
  const idx = form.skillIds.indexOf(id)
  if (idx === -1) form.skillIds.push(id)
  else form.skillIds.splice(idx, 1)
}
</script>

<template>
  <form @submit.prevent="emit('submit', { ...form })" class="space-y-4">
    <div class="grid gap-4 sm:grid-cols-2">
      <BaseInput v-model="form.title" label="Title" placeholder="My Project" required @input="autoSlug" />
      <BaseInput v-model="form.slug" label="Slug" placeholder="my-project" required />
    </div>
    <BaseInput v-model="form.summary" label="Summary" placeholder="One-line description" required />
    <BaseTextarea v-model="form.description" label="Description" :rows="4" placeholder="Full project description…" />
    <div class="grid gap-4 sm:grid-cols-2">
      <BaseInput v-model="form.imageUrl" label="Image URL" placeholder="https://…" />
      <BaseInput v-model="form.liveUrl" label="Live URL" placeholder="https://…" />
    </div>
    <div class="grid gap-4 sm:grid-cols-2">
      <BaseInput v-model="form.githubUrl" label="GitHub URL" placeholder="https://github.com/…" />
      <div class="flex flex-col gap-1.5">
        <label class="text-sm font-medium text-[var(--text-secondary)]">Status</label>
        <select v-model="form.status"
          class="rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] px-4 py-2.5 text-sm text-[var(--text-primary)] outline-none focus:border-[var(--color-brand-400)]"
        >
          <option value="IN_PROGRESS">In Progress</option>
          <option value="LIVE">Live</option>
          <option value="ARCHIVED">Archived</option>
        </select>
      </div>
    </div>

    <!-- Skills multi-select -->
    <div>
      <label class="mb-2 block text-sm font-medium text-[var(--text-secondary)]">Skills</label>
      <div class="flex flex-wrap gap-2 rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-3">
        <button
          v-for="skill in skillsStore.items"
          :key="skill.id"
          type="button"
          @click="toggleSkill(skill.id)"
          :class="[
            'rounded-lg px-3 py-1 text-xs font-medium transition-all',
            form.skillIds.includes(skill.id)
              ? 'bg-[var(--color-brand-500)] text-white'
              : 'border border-[var(--border-default)] text-[var(--text-secondary)] hover:border-[var(--color-brand-400)]',
          ]"
        >
          {{ skill.name }}
        </button>
        <span v-if="!skillsStore.items.length" class="text-xs text-[var(--text-tertiary)]">No skills yet</span>
      </div>
    </div>

    <div class="flex justify-end gap-3 pt-2">
      <BaseButton variant="secondary" type="button" @click="emit('cancel')">Cancel</BaseButton>
      <BaseButton type="submit" :loading="loading">{{ initial ? 'Save Changes' : 'Create Project' }}</BaseButton>
    </div>
  </form>
</template>
