<script setup>
import { reactive, onMounted, watch } from 'vue'
import { useProfileStore } from '@/stores/profile'
import { useToast } from '@/composables/useToast'
import BaseInput from '@/components/ui/BaseInput.vue'
import BaseTextarea from '@/components/ui/BaseTextarea.vue'
import BaseButton from '@/components/ui/BaseButton.vue'

const store  = useProfileStore()
const toast  = useToast()

const form = reactive({
  fullName: '', bio: '', email: '', phone: '',
  linkedinUrl: '', githubUrl: '', resumeUrl: '',
})

const saving = reactive({ state: false })

onMounted(() => store.fetch())

watch(() => store.data, (val) => {
  if (!val) return
  Object.assign(form, {
    fullName:    val.fullName    ?? '',
    bio:         val.bio         ?? '',
    email:       val.email       ?? '',
    phone:       val.phone       ?? '',
    linkedinUrl: val.linkedinUrl ?? '',
    githubUrl:   val.githubUrl   ?? '',
    resumeUrl:   val.resumeUrl   ?? '',
  })
}, { immediate: true })

async function submit() {
  saving.state = true
  try {
    await store.update({ ...form })
    toast.success('Profile updated.')
  } catch {
    toast.error('Failed to save profile.')
  } finally {
    saving.state = false
  }
}
</script>

<template>
  <div class="mx-auto max-w-2xl">
    <div class="mb-6">
      <h1 class="font-display text-2xl font-bold text-[var(--text-primary)]">Profile</h1>
      <p class="mt-0.5 text-sm text-[var(--text-secondary)]">Your public-facing portfolio information.</p>
    </div>

    <div v-if="store.loading" class="space-y-4">
      <div v-for="i in 5" :key="i" class="h-12 animate-pulse rounded-xl bg-[var(--bg-surface-alt)]" />
    </div>

    <form v-else @submit.prevent="submit" class="space-y-5">
      <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6 space-y-5">
        <h2 class="font-display font-semibold text-[var(--text-primary)]">Personal Info</h2>
        <BaseInput v-model="form.fullName" label="Full Name" placeholder="Priyanshu Mishra" required />
        <BaseTextarea v-model="form.bio" label="Bio" :rows="3" placeholder="A short bio shown on the hero section…" />
      </div>

      <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6 space-y-5">
        <h2 class="font-display font-semibold text-[var(--text-primary)]">Contact Details</h2>
        <div class="grid gap-4 sm:grid-cols-2">
          <BaseInput v-model="form.email" label="Email" type="email" placeholder="you@example.com" />
          <BaseInput v-model="form.phone" label="Phone" placeholder="+91 …" />
        </div>
      </div>

      <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6 space-y-5">
        <h2 class="font-display font-semibold text-[var(--text-primary)]">Social Links</h2>
        <BaseInput v-model="form.githubUrl" label="GitHub URL" placeholder="https://github.com/username" />
        <BaseInput v-model="form.linkedinUrl" label="LinkedIn URL" placeholder="https://linkedin.com/in/username" />
      </div>

      <div class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-6 space-y-5">
        <div>
          <h2 class="font-display font-semibold text-[var(--text-primary)]">Resume</h2>
          <p class="mt-0.5 text-xs text-[var(--text-tertiary)]">Paste your public Google Drive or any direct download link. Visitors will see a Download button on the Resume page.</p>
        </div>
        <BaseInput v-model="form.resumeUrl" label="Resume URL" placeholder="https://drive.google.com/file/d/…/view?usp=sharing" />
      </div>

      <div class="flex justify-end">
        <BaseButton type="submit" :loading="saving.state" size="lg">Save Profile</BaseButton>
      </div>
    </form>
  </div>
</template>
