<script setup>
defineProps({
  columns: { type: Array, required: true },
  rows:    { type: Array, required: true },
  loading: { type: Boolean, default: false },
})
defineEmits(['edit', 'delete'])
</script>

<template>
  <div class="overflow-hidden rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)]">
    <div v-if="loading" class="flex items-center justify-center py-16 text-[var(--text-tertiary)] text-sm">
      <span class="h-5 w-5 animate-spin rounded-full border-2 border-[var(--border-strong)] border-t-[var(--color-brand-500)]" />
      <span class="ml-3">Loading…</span>
    </div>

    <div v-else-if="!rows.length" class="py-16 text-center text-sm text-[var(--text-tertiary)]">
      No entries yet.
    </div>

    <div v-else class="overflow-x-auto">
      <table class="w-full text-sm">
        <thead>
          <tr class="border-b border-[var(--border-default)] bg-[var(--bg-surface-alt)]">
            <th
              v-for="col in columns"
              :key="col.key"
              class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-[var(--text-tertiary)]"
            >
              {{ col.label }}
            </th>
            <th class="px-4 py-3 text-right text-xs font-semibold uppercase tracking-wider text-[var(--text-tertiary)]">
              Actions
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-[var(--border-default)]">
          <tr
            v-for="row in rows"
            :key="row.id"
            class="group transition-colors hover:bg-[var(--bg-surface-alt)]"
          >
            <td
              v-for="col in columns"
              :key="col.key"
              class="px-4 py-3 text-[var(--text-secondary)]"
            >
              <slot :name="'cell-' + col.key" :row="row" :value="row[col.key]">
                {{ row[col.key] ?? '—' }}
              </slot>
            </td>
            <td class="px-4 py-3">
              <div class="flex items-center justify-end gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
                <button
                  @click="$emit('edit', row)"
                  class="rounded-lg border border-[var(--border-default)] px-3 py-1.5 text-xs font-medium text-[var(--text-secondary)] transition-all hover:border-[var(--color-brand-500)] hover:text-[var(--color-brand-400)]"
                >
                  Edit
                </button>
                <button
                  @click="$emit('delete', row)"
                  class="rounded-lg border border-transparent px-3 py-1.5 text-xs font-medium text-red-400 transition-all hover:border-red-400/40 hover:bg-red-400/5"
                >
                  Delete
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
