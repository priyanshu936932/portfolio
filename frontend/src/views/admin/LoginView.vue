<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useToast } from '@/composables/useToast'
import BaseInput from '@/components/ui/BaseInput.vue'
import BaseButton from '@/components/ui/BaseButton.vue'

const router    = useRouter()
const authStore = useAuthStore()
const toast     = useToast()

const form    = reactive({ email: '', password: '' })
const loading = ref(false)

async function submit() {
  if (!form.email || !form.password) return
  loading.value = true
  try {
    await authStore.login(form.email, form.password)
    router.push('/admin/dashboard')
  } catch {
    toast.error('Invalid email or password.')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="flex min-h-screen items-center justify-center bg-[var(--bg-base)] px-4">

    <!-- Background glow -->
    <div class="pointer-events-none fixed inset-0 overflow-hidden">
      <div class="absolute left-1/2 top-1/2 h-[500px] w-[500px] -translate-x-1/2 -translate-y-1/2 rounded-full opacity-10 blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-500), transparent 70%)" />
    </div>

    <div class="relative w-full max-w-sm">
      <!-- Logo -->
      <div class="mb-8 text-center">
        <RouterLink to="/" class="font-display text-2xl font-bold text-[var(--color-brand-500)]">
          priyanshu<span class="text-[var(--color-accent-500)]">.</span>
        </RouterLink>
        <p class="mt-2 text-sm text-[var(--text-tertiary)]">Admin Dashboard</p>
      </div>

      <!-- Card -->
      <div
        class="rounded-2xl border border-[var(--border-default)] bg-[var(--bg-surface)] p-8"
        style="box-shadow: 0 16px 48px hsl(var(--shadow-color) / 0.15)"
      >
        <h1 class="mb-6 font-display text-xl font-bold text-[var(--text-primary)]">Sign in</h1>

        <form @submit.prevent="submit" class="space-y-5">
          <BaseInput
            v-model="form.email"
            label="Email"
            type="email"
            placeholder="admin@example.com"
            required
            :disabled="loading"
          />
          <BaseInput
            v-model="form.password"
            label="Password"
            type="password"
            placeholder="••••••••"
            required
            :disabled="loading"
          />
          <BaseButton type="submit" :loading="loading" class="w-full" size="lg">
            Sign in
          </BaseButton>
        </form>
      </div>

      <p class="mt-6 text-center text-xs text-[var(--text-tertiary)]">
        <RouterLink to="/" class="hover:text-[var(--color-brand-400)]">← Back to site</RouterLink>
      </p>
    </div>
  </div>
</template>
