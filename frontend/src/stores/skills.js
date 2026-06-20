import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { skillsApi } from '@/api/skills'

export const useSkillsStore = defineStore('skills', () => {
  const items   = ref([])
  const loading = ref(false)
  const error   = ref(null)

  const byCategory = computed(() => {
    return items.value.reduce((acc, skill) => {
      const cat = skill.category || 'Other'
      if (!acc[cat]) acc[cat] = []
      acc[cat].push(skill)
      return acc
    }, {})
  })

  async function fetchAll() {
    loading.value = true
    error.value   = null
    try {
      const { data } = await skillsApi.getAll()
      items.value = data
    } catch (e) {
      error.value = e.response?.data?.message || 'Failed to load skills'
    } finally {
      loading.value = false
    }
  }

  async function create(payload) {
    const { data } = await skillsApi.create(payload)
    items.value.push(data)
    return data
  }

  async function update(id, payload) {
    const { data } = await skillsApi.update(id, payload)
    const idx = items.value.findIndex((s) => s.id === id)
    if (idx !== -1) items.value[idx] = data
    return data
  }

  async function remove(id) {
    await skillsApi.delete(id)
    items.value = items.value.filter((s) => s.id !== id)
  }

  return { items, byCategory, loading, error, fetchAll, create, update, remove }
})
