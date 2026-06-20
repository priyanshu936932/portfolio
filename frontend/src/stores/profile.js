import { defineStore } from 'pinia'
import { ref } from 'vue'
import { profileApi } from '@/api/profile'

export const useProfileStore = defineStore('profile', () => {
  const data    = ref(null)
  const loading = ref(false)
  const error   = ref(null)

  async function fetch() {
    loading.value = true
    error.value   = null
    try {
      const { data: d } = await profileApi.get()
      data.value = d
    } catch (e) {
      error.value = e.response?.data?.message || 'Failed to load profile'
    } finally {
      loading.value = false
    }
  }

  async function update(payload) {
    const { data: d } = await profileApi.update(payload)
    data.value = d
    return d
  }

  return { data, loading, error, fetch, update }
})
