<script setup>
import { ref, onMounted } from 'vue'
import { useTagsStore } from '@/stores/tags'
import { useToast } from '@/composables/useToast'
import BaseInput from '@/components/ui/BaseInput.vue'
import BaseButton from '@/components/ui/BaseButton.vue'

const store   = useTagsStore()
const toast   = useToast()
const newName = ref('')
const saving  = ref(false)

onMounted(() => store.fetchAll())

async function create() {
  if (!newName.value.trim()) return
  saving.value = true
  try {
    await store.create({ name: newName.value.trim() })
    newName.value = ''
    toast.success('Tag created.')
  } catch {
    toast.error('Failed to create tag.')
  } finally {
    saving.value = false
  }
}

async function remove(tag) {
  if (!confirm('Delete tag "' + tag.name + '"?')) return
  try {
    await store.remove(tag.id)
    toast.success('Tag deleted.')
  } catch {
    toast.error('Failed to delete tag.')
  }
}
</script>

<template>
  <div>
    <div class="mb-6">
      <h1 class="font-display text-2xl font-bold text-[var(--text-primary)]">Tags</h1>
      <p class="mt-0.5 text-sm text-[var(--text-secondary)]">{{ store.items.length }} tags</p>
    </div>

    <!-- Create form -->
    <div class="mb-8 flex items-end gap-3">
      <div class="flex-1 max-w-sm">
        <BaseInput v-model="newName" label="New Tag" placeholder="e.g. Spring Boot" @keyup.enter="create" />
      </div>
      <BaseButton @click="create" :loading="saving">Add Tag</BaseButton>
    </div>

    <!-- Tags list -->
    <div v-if="store.loading" class="text-sm text-[var(--text-tertiary)]">Loading…</div>
    <div v-else-if="!store.items.length" class="rounded-2xl border border-[var(--border-default)] py-16 text-center text-sm text-[var(--text-tertiary)]">
      No tags yet. Create your first one above.
    </div>
    <div v-else class="flex flex-wrap gap-3">
      <div
        v-for="tag in store.items"
        :key="tag.id"
        class="group flex items-center gap-2 rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] px-4 py-2 text-sm text-[var(--text-secondary)]"
      >
        <span>{{ tag.name }}</span>
        <button
          @click="remove(tag)"
          class="text-[var(--text-tertiary)] opacity-0 transition-all group-hover:opacity-100 hover:text-red-400"
          aria-label="Delete"
        >
          <svg class="h-3.5 w-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>
