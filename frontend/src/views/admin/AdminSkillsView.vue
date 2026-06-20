<script setup>
import { ref, onMounted } from 'vue'
import { useSkillsStore } from '@/stores/skills'
import { useToast } from '@/composables/useToast'
import DataTable from '@/components/admin/DataTable.vue'
import SkillForm from '@/components/admin/SkillForm.vue'
import BaseModal from '@/components/ui/BaseModal.vue'
import BaseButton from '@/components/ui/BaseButton.vue'

const store   = useSkillsStore()
const toast   = useToast()
const modal   = ref(false)
const editing = ref(null)
const saving  = ref(false)

onMounted(() => store.fetchAll())

const columns = [
  { key: 'name',        label: 'Name' },
  { key: 'category',    label: 'Category' },
  { key: 'proficiency', label: 'Proficiency' },
]

function openCreate() { editing.value = null; modal.value = true }
function openEdit(row) { editing.value = row; modal.value = true }
function closeModal() { modal.value = false; editing.value = null }

async function submit(payload) {
  saving.value = true
  try {
    if (editing.value) {
      await store.update(editing.value.id, payload)
      toast.success('Skill updated.')
    } else {
      await store.create(payload)
      toast.success('Skill added.')
    }
    closeModal()
  } catch {
    toast.error('Failed to save skill.')
  } finally {
    saving.value = false
  }
}

async function remove(row) {
  if (!confirm('Delete skill "' + row.name + '"?')) return
  try {
    await store.remove(row.id)
    toast.success('Skill deleted.')
  } catch {
    toast.error('Failed to delete.')
  }
}
</script>

<template>
  <div>
    <div class="mb-6 flex items-center justify-between">
      <div>
        <h1 class="font-display text-2xl font-bold text-[var(--text-primary)]">Skills</h1>
        <p class="mt-0.5 text-sm text-[var(--text-secondary)]">{{ store.items.length }} skills</p>
      </div>
      <BaseButton @click="openCreate">+ Add Skill</BaseButton>
    </div>

    <DataTable
      :columns="columns"
      :rows="store.items"
      :loading="store.loading"
      @edit="openEdit"
      @delete="remove"
    >
      <template #cell-proficiency="{ value }">
        <span class="flex gap-0.5">
          <span v-for="n in 5" :key="n"
            :class="['h-2 w-2 rounded-full', n <= value ? 'bg-[var(--color-brand-500)]' : 'bg-[var(--border-strong)]']"
          />
        </span>
      </template>
    </DataTable>

    <BaseModal :open="modal" :title="editing ? 'Edit Skill' : 'Add Skill'" size="sm" @close="closeModal">
      <SkillForm :initial="editing" :loading="saving" @submit="submit" @cancel="closeModal" />
    </BaseModal>
  </div>
</template>
