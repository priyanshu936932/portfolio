import { defineStore } from 'pinia'
import { ref } from 'vue'
import { blogApi } from '@/api/blog'

export const useBlogStore = defineStore('blog', () => {
  const items   = ref([])
  const current = ref(null)
  const loading = ref(false)
  const error   = ref(null)

  async function fetchPublished() {
    loading.value = true
    error.value   = null
    try {
      const { data } = await blogApi.getPublished()
      items.value = data
    } catch (e) {
      error.value = e.response?.data?.message || 'Failed to load posts'
    } finally {
      loading.value = false
    }
  }

  async function fetchAllAdmin() {
    loading.value = true
    error.value   = null
    try {
      const { data } = await blogApi.getDrafts()
      items.value = data
    } catch (e) {
      error.value = e.response?.data?.message || 'Failed to load posts'
    } finally {
      loading.value = false
    }
  }

  async function fetchBySlug(slug) {
    loading.value = true
    error.value   = null
    current.value = null
    try {
      const { data } = await blogApi.getBySlug(slug)
      current.value = data
    } catch (e) {
      error.value = e.response?.data?.message || 'Post not found'
    } finally {
      loading.value = false
    }
  }

  async function create(payload) {
    const { data } = await blogApi.create(payload)
    items.value.unshift(data)
    return data
  }

  async function update(id, payload) {
    const { data } = await blogApi.update(id, payload)
    const idx = items.value.findIndex((p) => p.id === id)
    if (idx !== -1) items.value[idx] = data
    return data
  }

  async function remove(id) {
    await blogApi.delete(id)
    items.value = items.value.filter((p) => p.id !== id)
  }

  return { items, current, loading, error, fetchPublished, fetchAllAdmin, fetchBySlug, create, update, remove }
})
