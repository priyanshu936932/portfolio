import { defineStore } from 'pinia'
import { ref } from 'vue'
import { projectsApi } from '@/api/projects'

export const useProjectsStore = defineStore('projects', () => {
  const items   = ref([])
  const current = ref(null)
  const loading = ref(false)
  const error   = ref(null)

  async function fetchAll() {
    loading.value = true
    error.value   = null
    try {
      const { data } = await projectsApi.getAll()
      items.value = data
    } catch (e) {
      error.value = e.response?.data?.message || 'Failed to load projects'
    } finally {
      loading.value = false
    }
  }

  async function fetchBySlug(slug) {
    loading.value = true
    error.value   = null
    current.value = null
    try {
      const { data } = await projectsApi.getBySlug(slug)
      current.value = data
    } catch (e) {
      error.value = e.response?.data?.message || 'Project not found'
    } finally {
      loading.value = false
    }
  }

  async function create(payload) {
    const { data } = await projectsApi.create(payload)
    items.value.unshift(data)
    return data
  }

  async function update(id, payload) {
    const { data } = await projectsApi.update(id, payload)
    const idx = items.value.findIndex((p) => p.id === id)
    if (idx !== -1) items.value[idx] = data
    return data
  }

  async function remove(id) {
    await projectsApi.delete(id)
    items.value = items.value.filter((p) => p.id !== id)
  }

  return { items, current, loading, error, fetchAll, fetchBySlug, create, update, remove }
})
