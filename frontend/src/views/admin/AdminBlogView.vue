<script setup>
import { ref, onMounted } from 'vue'
import { useBlogStore } from '@/stores/blog'
import { useToast } from '@/composables/useToast'
import DataTable from '@/components/admin/DataTable.vue'
import BlogPostForm from '@/components/admin/BlogPostForm.vue'
import BaseModal from '@/components/ui/BaseModal.vue'
import BaseButton from '@/components/ui/BaseButton.vue'
import BaseBadge from '@/components/ui/BaseBadge.vue'

const store   = useBlogStore()
const toast   = useToast()
const modal   = ref(false)
const editing = ref(null)
const saving  = ref(false)

onMounted(() => store.fetchAllAdmin())

const columns = [
  { key: 'title',     label: 'Title' },
  { key: 'published', label: 'Status' },
  { key: 'tags',      label: 'Tags' },
]

function openCreate() { editing.value = null; modal.value = true }
function openEdit(row) { editing.value = row; modal.value = true }
function closeModal() { modal.value = false; editing.value = null }

async function submit(payload) {
  saving.value = true
  try {
    if (editing.value) {
      await store.update(editing.value.id, payload)
      toast.success('Post updated.')
    } else {
      await store.create(payload)
      toast.success('Post created.')
    }
    closeModal()
  } catch {
    toast.error('Failed to save post.')
  } finally {
    saving.value = false
  }
}

async function remove(row) {
  if (!confirm('Delete "' + row.title + '"?')) return
  try {
    await store.remove(row.id)
    toast.success('Post deleted.')
  } catch {
    toast.error('Failed to delete.')
  }
}
</script>

<template>
  <div>
    <div class="mb-6 flex items-center justify-between">
      <div>
        <h1 class="font-display text-2xl font-bold text-[var(--text-primary)]">Blog Posts</h1>
        <p class="mt-0.5 text-sm text-[var(--text-secondary)]">{{ store.items.length }} total</p>
      </div>
      <BaseButton @click="openCreate">+ New Post</BaseButton>
    </div>

    <DataTable
      :columns="columns"
      :rows="store.items"
      :loading="store.loading"
      @edit="openEdit"
      @delete="remove"
    >
      <template #cell-published="{ value }">
        <BaseBadge :variant="value ? 'success' : 'neutral'">
          {{ value ? 'Published' : 'Draft' }}
        </BaseBadge>
      </template>
      <template #cell-tags="{ value }">
        <span class="text-xs text-[var(--text-tertiary)]">{{ value?.map(t => t.name).join(', ') || '—' }}</span>
      </template>
    </DataTable>

    <BaseModal :open="modal" :title="editing ? 'Edit Post' : 'New Post'" size="xl" @close="closeModal">
      <BlogPostForm :initial="editing" :loading="saving" @submit="submit" @cancel="closeModal" />
    </BaseModal>
  </div>
</template>
