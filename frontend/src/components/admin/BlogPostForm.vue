<script setup>
import { reactive, watch, onMounted } from 'vue'
import BaseInput from '@/components/ui/BaseInput.vue'
import BaseTextarea from '@/components/ui/BaseTextarea.vue'
import BaseButton from '@/components/ui/BaseButton.vue'
import { useTagsStore } from '@/stores/tags'

const props = defineProps({
  initial: { type: Object, default: null },
  loading: { type: Boolean, default: false },
})
const emit = defineEmits(['submit', 'cancel'])

const tagsStore = useTagsStore()
onMounted(() => { if (!tagsStore.items.length) tagsStore.fetchAll() })

const form = reactive({
  title: '', slug: '', summary: '', content: '',
  coverImageUrl: '', published: false, tagIds: [],
})

watch(() => props.initial, (val) => {
  if (!val) return
  Object.assign(form, {
    title: val.title ?? '',
    slug: val.slug ?? '',
    summary: val.summary ?? '',
    content: val.content ?? '',
    coverImageUrl: val.coverImageUrl ?? '',
    published: val.published ?? false,
    tagIds: val.tags?.map((t) => t.id) ?? [],
  })
}, { immediate: true })

function autoSlug() {
  if (!props.initial) {
    form.slug = form.title.toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '')
  }
}

function toggleTag(id) {
  const idx = form.tagIds.indexOf(id)
  if (idx === -1) form.tagIds.push(id)
  else form.tagIds.splice(idx, 1)
}
</script>

<template>
  <form @submit.prevent="emit('submit', { ...form })" class="space-y-4">
    <div class="grid gap-4 sm:grid-cols-2">
      <BaseInput v-model="form.title" label="Title" placeholder="Post title" required @input="autoSlug" />
      <BaseInput v-model="form.slug" label="Slug" placeholder="post-slug" required />
    </div>
    <BaseInput v-model="form.summary" label="Summary" placeholder="Brief description shown in cards" required />
    <BaseInput v-model="form.coverImageUrl" label="Cover Image URL" placeholder="https://…" />
    <BaseTextarea v-model="form.content" label="Content (Markdown)" :rows="10" placeholder="Write your post in Markdown…" />

    <!-- Tags multi-select -->
    <div>
      <label class="mb-2 block text-sm font-medium text-[var(--text-secondary)]">Tags</label>
      <div class="flex flex-wrap gap-2 rounded-xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-3">
        <button
          v-for="tag in tagsStore.items"
          :key="tag.id"
          type="button"
          @click="toggleTag(tag.id)"
          :class="[
            'rounded-lg px-3 py-1 text-xs font-medium transition-all',
            form.tagIds.includes(tag.id)
              ? 'bg-[var(--color-brand-500)] text-white'
              : 'border border-[var(--border-default)] text-[var(--text-secondary)] hover:border-[var(--color-brand-400)]',
          ]"
        >
          {{ tag.name }}
        </button>
        <span v-if="!tagsStore.items.length" class="text-xs text-[var(--text-tertiary)]">No tags yet — add some in Tags.</span>
      </div>
    </div>

    <!-- Published toggle -->
    <div class="flex items-center gap-3">
      <button
        type="button"
        @click="form.published = !form.published"
        :class="[
          'relative flex h-6 w-11 items-center rounded-full border transition-colors duration-200',
          form.published ? 'border-[var(--color-brand-500)] bg-[var(--color-brand-500)]' : 'border-[var(--border-strong)] bg-[var(--bg-surface-alt)]',
        ]"
      >
        <span :class="['h-4 w-4 rounded-full bg-white shadow transition-transform duration-200', form.published ? 'translate-x-5' : 'translate-x-1']" />
      </button>
      <span class="text-sm font-medium text-[var(--text-secondary)]">
        {{ form.published ? 'Published' : 'Draft' }}
      </span>
    </div>

    <div class="flex justify-end gap-3 pt-2">
      <BaseButton variant="secondary" type="button" @click="emit('cancel')">Cancel</BaseButton>
      <BaseButton type="submit" :loading="loading">{{ initial ? 'Save Changes' : 'Create Post' }}</BaseButton>
    </div>
  </form>
</template>
