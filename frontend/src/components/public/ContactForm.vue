<script setup>
import { ref, reactive } from 'vue'
import BaseInput from '@/components/ui/BaseInput.vue'
import BaseTextarea from '@/components/ui/BaseTextarea.vue'
import BaseButton from '@/components/ui/BaseButton.vue'
import { contactApi } from '@/api/contact'
import { useToast } from '@/composables/useToast'

const toast = useToast()

const form = reactive({ name: '', email: '', subject: '', message: '' })
const errors = reactive({ name: '', email: '', subject: '', message: '' })
const loading = ref(false)
const sent = ref(false)

function validate() {
  let ok = true
  errors.name    = form.name.trim()    ? '' : 'Name is required'
  errors.email   = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email) ? '' : 'Valid email required'
  errors.subject = form.subject.trim() ? '' : 'Subject is required'
  errors.message = form.message.trim().length >= 10 ? '' : 'Message must be at least 10 characters'
  if (errors.name || errors.email || errors.subject || errors.message) ok = false
  return ok
}

async function submit() {
  if (!validate()) return
  loading.value = true
  try {
    await contactApi.submit({ ...form })
    sent.value = true
    toast.success('Message sent! I\'ll get back to you soon.')
  } catch {
    toast.error('Something went wrong. Please try again.')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <!-- Success state -->
  <div v-if="sent" class="flex flex-col items-center gap-4 py-12 text-center">
    <div class="flex h-16 w-16 items-center justify-center rounded-full bg-emerald-500/10">
      <svg class="h-8 w-8 text-emerald-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
      </svg>
    </div>
    <h3 class="font-display text-xl font-semibold text-[var(--text-primary)]">Message sent!</h3>
    <p class="text-sm text-[var(--text-secondary)]">Thanks for reaching out. I'll reply as soon as I can.</p>
    <BaseButton variant="ghost" @click="sent = false">Send another</BaseButton>
  </div>

  <!-- Form -->
  <form v-else @submit.prevent="submit" class="space-y-5" novalidate>
    <div class="grid gap-5 sm:grid-cols-2">
      <BaseInput
        v-model="form.name"
        label="Name"
        placeholder="Your name"
        :error="errors.name"
        required
      />
      <BaseInput
        v-model="form.email"
        label="Email"
        type="email"
        placeholder="you@example.com"
        :error="errors.email"
        required
      />
    </div>
    <BaseInput
      v-model="form.subject"
      label="Subject"
      placeholder="What's this about?"
      :error="errors.subject"
      required
    />
    <BaseTextarea
      v-model="form.message"
      label="Message"
      placeholder="Tell me more..."
      :rows="5"
      :error="errors.message"
      required
    />
    <BaseButton type="submit" :loading="loading" class="w-full" size="lg">
      Send Message
    </BaseButton>
  </form>
</template>
