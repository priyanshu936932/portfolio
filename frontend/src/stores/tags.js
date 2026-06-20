import { defineStore } from 'pinia'
import { ref } from 'vue'
import { tagsApi } from '@/api/tags'

export const useTagsStore = defineStore('tags', () => {
  const items   = ref([])
  const loading = ref(false)
  const error   = ref(null)

  async function fetchAll() {
    loading.value = true
    error.value   = null
    try {
      const { data } = await tagsApi.getAll()
      items.value = data
    } catch (e) {
      error.value = e.response?.data?.message || 'Failed to load tags'
    } finally {
      loading.value = false
    }
  }

  async function create(payload) {
    const { data } = await tagsApi.create(payload)
    items.value.push(data)
    return data
  }

  async function remove(id) {
    await tagsApi.delete(id)
    items.value = items.value.filter((t) => t.id !== id)
  }

  return { items, loading, error, fetchAll, create, remove }
})
