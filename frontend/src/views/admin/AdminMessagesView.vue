<script setup>
import { ref, onMounted, computed } from 'vue'
import { contactApi } from '@/api/contact'
import { useToast } from '@/composables/useToast'

const toast    = useToast()
const messages = ref([])
const loading  = ref(false)

onMounted(async () => {
  loading.value = true
  try {
    const { data } = await contactApi.getAll()
    messages.value = data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
  } catch {
    toast.error('Failed to load messages.')
  } finally {
    loading.value = false
  }
})

const unreadCount = computed(() => messages.value.filter((m) => !m.read).length)

async function markRead(msg) {
  if (msg.read) return
  try {
    await contactApi.markRead(msg.id)
    msg.read = true
  } catch {
    toast.error('Failed to mark as read.')
  }
}

async function remove(msg) {
  if (!confirm('Delete message from ' + msg.name + '?')) return
  try {
    await contactApi.delete(msg.id)
    messages.value = messages.value.filter((m) => m.id !== msg.id)
    toast.success('Message deleted.')
  } catch {
    toast.error('Failed to delete.')
  }
}

function formatDate(iso) {
  return new Date(iso).toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric', hour: '2-digit', minute: '2-digit' })
}
</script>

<template>
  <div>
    <div class="mb-6 flex items-center justify-between">
      <div>
        <h1 class="font-display text-2xl font-bold text-[var(--text-primary)]">Messages</h1>
        <p class="mt-0.5 text-sm text-[var(--text-secondary)]">
          {{ messages.length }} total
          <span v-if="unreadCount" class="ml-2 rounded-full bg-[var(--color-brand-500)]/20 px-2 py-0.5 text-xs font-semibold text-[var(--color-brand-400)]">
            {{ unreadCount }} unread
          </span>
        </p>
      </div>
    </div>

    <div v-if="loading" class="flex items-center gap-3 py-16 text-sm text-[var(--text-tertiary)]">
      <span class="h-4 w-4 animate-spin rounded-full border-2 border-[var(--border-strong)] border-t-[var(--color-brand-500)]" />
      Loading messages…
    </div>

    <div v-else-if="!messages.length" class="rounded-2xl border border-[var(--border-default)] py-16 text-center text-sm text-[var(--text-tertiary)]">
      No messages yet.
    </div>

    <div v-else class="space-y-3">
      <div
        v-for="msg in messages"
        :key="msg.id"
        :class="[
          'group relative rounded-2xl border bg-[var(--bg-surface)] transition-all',
          !msg.read
            ? 'border-[var(--color-brand-500)]/30 shadow-[inset_3px_0_0_var(--color-brand-500)]'
            : 'border-[var(--border-default)]',
        ]"
        style="box-shadow-inner: none"
      >
        <div class="p-5">
          <div class="flex items-start justify-between gap-4">
            <div class="min-w-0 flex-1">
              <div class="flex flex-wrap items-center gap-2">
                <span class="font-semibold text-[var(--text-primary)]">{{ msg.name }}</span>
                <span v-if="!msg.read"
                  class="rounded-full bg-[var(--color-brand-500)]/15 px-2 py-0.5 text-xs font-semibold text-[var(--color-brand-400)]"
                >
                  New
                </span>
              </div>
              <div class="mt-0.5 flex flex-wrap items-center gap-3 text-xs text-[var(--text-tertiary)]">
                <a :href="'mailto:' + msg.email" class="hover:text-[var(--color-brand-400)]">{{ msg.email }}</a>
                <span>·</span>
                <span>{{ formatDate(msg.createdAt) }}</span>
              </div>
              <p class="mt-1 text-sm font-medium text-[var(--text-secondary)]">{{ msg.subject }}</p>
            </div>
            <div class="flex shrink-0 items-center gap-2 opacity-0 transition-opacity group-hover:opacity-100">
              <button v-if="!msg.read" @click="markRead(msg)"
                class="rounded-lg border border-[var(--border-default)] px-3 py-1.5 text-xs font-medium text-[var(--text-secondary)] hover:border-[var(--color-brand-400)] hover:text-[var(--color-brand-400)] transition-all"
              >
                Mark read
              </button>
              <button @click="remove(msg)"
                class="rounded-lg px-3 py-1.5 text-xs font-medium text-red-400 hover:bg-red-400/10 transition-colors"
              >
                Delete
              </button>
            </div>
          </div>
          <p class="mt-3 whitespace-pre-wrap text-sm leading-relaxed text-[var(--text-secondary)]">{{ msg.message }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
