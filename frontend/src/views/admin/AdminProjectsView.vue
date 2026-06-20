<script setup>
import { ref, onMounted } from 'vue'
import { useProjectsStore } from '@/stores/projects'
import { useToast } from '@/composables/useToast'
import DataTable from '@/components/admin/DataTable.vue'
import ProjectForm from '@/components/admin/ProjectForm.vue'
import BaseModal from '@/components/ui/BaseModal.vue'
import BaseButton from '@/components/ui/BaseButton.vue'
import BaseBadge from '@/components/ui/BaseBadge.vue'

const store   = useProjectsStore()
const toast   = useToast()
const modal   = ref(false)
const editing = ref(null)
const saving  = ref(false)

onMounted(() => store.fetchAll())

const columns = [
  { key: 'title',  label: 'Title' },
  { key: 'status', label: 'Status' },
  { key: 'skills', label: 'Skills' },
]

function openCreate() { editing.value = null; modal.value = true }
function openEdit(row) { editing.value = row; modal.value = true }
function closeModal() { modal.value = false; editing.value = null }

async function submit(payload) {
  saving.value = true
  try {
    if (editing.value) {
      await store.update(editing.value.id, payload)
      toast.success('Project updated.')
    } else {
      await store.create(payload)
      toast.success('Project created.')
    }
    closeModal()
  } catch {
    toast.error('Failed to save project.')
  } finally {
    saving.value = false
  }
}

async function remove(row) {
  if (!confirm('Delete "' + row.title + '"?')) return
  try {
    await store.remove(row.id)
    toast.success('Project deleted.')
  } catch {
    toast.error('Failed to delete.')
  }
}
</script>

<template>
  <div>
    <div class="mb-6 flex items-center justify-between">
      <div>
        <h1 class="font-display text-2xl font-bold text-[var(--text-primary)]">Projects</h1>
        <p class="mt-0.5 text-sm text-[var(--text-secondary)]">{{ store.items.length }} total</p>
      </div>
      <BaseButton @click="openCreate">+ New Project</BaseButton>
    </div>

    <DataTable
      :columns="columns"
      :rows="store.items"
      :loading="store.loading"
      @edit="openEdit"
      @delete="remove"
    >
      <template #cell-status="{ value }">
        <BaseBadge :variant="value === 'LIVE' ? 'success' : value === 'ARCHIVED' ? 'neutral' : 'brand'">
          {{ value === 'IN_PROGRESS' ? 'In Progress' : value }}
        </BaseBadge>
      </template>
      <template #cell-skills="{ value }">
        <span class="text-xs text-[var(--text-tertiary)]">{{ value?.map(s => s.name).join(', ') || '—' }}</span>
      </template>
    </DataTable>

    <BaseModal :open="modal" :title="editing ? 'Edit Project' : 'New Project'" size="lg" @close="closeModal">
      <ProjectForm :initial="editing" :loading="saving" @submit="submit" @cancel="closeModal" />
    </BaseModal>
  </div>
</template>
