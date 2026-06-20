<script setup>
import { reactive, watch } from 'vue'
import BaseInput from '@/components/ui/BaseInput.vue'
import BaseButton from '@/components/ui/BaseButton.vue'

const props = defineProps({
  initial: { type: Object, default: null },
  loading: { type: Boolean, default: false },
})
const emit = defineEmits(['submit', 'cancel'])

const CATEGORIES = ['Frontend', 'Backend', 'Database', 'DevOps', 'Mobile', 'Other']

const form = reactive({ name: '', category: 'Backend', proficiency: 3 })

watch(() => props.initial, (val) => {
  if (!val) return
  Object.assign(form, { name: val.name ?? '', category: val.category ?? 'Backend', proficiency: val.proficiency ?? 3 })
}, { immediate: true })
</script>

<template>
  <form @submit.prevent="emit('submit', { ...form })" class="space-y-5">
    <BaseInput v-model="form.name" label="Skill Name" placeholder="e.g. Spring Boot" required />

    <div class="flex flex-col gap-1.5">
      <label class="text-sm font-medium text-[var(--text-secondary)]">Category</label>
      <select v-model="form.category"
        class="rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] px-4 py-2.5 text-sm text-[var(--text-primary)] outline-none focus:border-[var(--color-brand-400)]"
      >
        <option v-for="cat in CATEGORIES" :key="cat" :value="cat">{{ cat }}</option>
      </select>
    </div>

    <div class="flex flex-col gap-2">
      <label class="text-sm font-medium text-[var(--text-secondary)]">
        Proficiency — <span class="text-[var(--color-brand-400)]">{{ form.proficiency }}/5</span>
      </label>
      <input
        type="range" min="1" max="5" v-model.number="form.proficiency"
        class="h-2 w-full cursor-pointer appearance-none rounded-full bg-[var(--border-strong)] accent-[var(--color-brand-500)]"
      />
      <div class="flex justify-between text-xs text-[var(--text-tertiary)]">
        <span>Beginner</span><span>Expert</span>
      </div>
    </div>

    <div class="flex justify-end gap-3 pt-2">
      <BaseButton variant="secondary" type="button" @click="emit('cancel')">Cancel</BaseButton>
      <BaseButton type="submit" :loading="loading">{{ initial ? 'Save' : 'Add Skill' }}</BaseButton>
    </div>
  </form>
</template>
