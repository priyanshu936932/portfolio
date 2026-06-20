import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import apiClient from '@/api/client'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || null)
  const userEmail = ref(localStorage.getItem('userEmail') || null)

  const isAuthenticated = computed(() => !!token.value)

  async function login(email, password) {
    const response = await apiClient.post('/auth/login', { email, password })
    token.value = response.data.token
    userEmail.value = response.data.email

    localStorage.setItem('token', token.value)
    localStorage.setItem('userEmail', userEmail.value)
  }

  function logout() {
    token.value = null
    userEmail.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userEmail')
  }

  return { token, userEmail, isAuthenticated, login, logout }
})