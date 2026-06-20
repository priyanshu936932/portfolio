import { describe, it, expect, beforeEach, vi } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'

// Must be hoisted before any store import so the circular client ↔ auth dep is broken.
vi.mock('@/api/client', () => ({
  default: {
    post: vi.fn(),
    interceptors: {
      request:  { use: vi.fn() },
      response: { use: vi.fn() },
    },
  },
}))

import { useAuthStore } from '../auth'
import apiClient from '@/api/client'

describe('useAuthStore', () => {
  beforeEach(() => {
    localStorage.clear()
    vi.clearAllMocks()
    setActivePinia(createPinia())
  })

  // --- isAuthenticated ---

  it('isAuthenticated is false when localStorage has no token', () => {
    const store = useAuthStore()
    expect(store.isAuthenticated).toBe(false)
    expect(store.token).toBeNull()
  })

  it('isAuthenticated is true when localStorage already has a token', () => {
    localStorage.setItem('token', 'persisted-token')
    localStorage.setItem('userEmail', 'me@example.com')

    const store = useAuthStore()
    expect(store.isAuthenticated).toBe(true)
    expect(store.token).toBe('persisted-token')
  })

  // --- login ---

  it('login sets token and userEmail in store state', async () => {
    apiClient.post.mockResolvedValue({ data: { token: 'jwt-abc', email: 'me@test.com' } })

    const store = useAuthStore()
    await store.login('me@test.com', 'secret')

    expect(store.token).toBe('jwt-abc')
    expect(store.userEmail).toBe('me@test.com')
    expect(store.isAuthenticated).toBe(true)
  })

  it('login persists token and userEmail to localStorage', async () => {
    apiClient.post.mockResolvedValue({ data: { token: 'jwt-abc', email: 'me@test.com' } })

    const store = useAuthStore()
    await store.login('me@test.com', 'secret')

    expect(localStorage.getItem('token')).toBe('jwt-abc')
    expect(localStorage.getItem('userEmail')).toBe('me@test.com')
  })

  it('login calls POST /auth/login with the supplied credentials', async () => {
    apiClient.post.mockResolvedValue({ data: { token: 'x', email: 'y@y.com' } })

    const store = useAuthStore()
    await store.login('y@y.com', 'pass123')

    expect(apiClient.post).toHaveBeenCalledWith('/auth/login', {
      email: 'y@y.com',
      password: 'pass123',
    })
  })

  it('login propagates errors so the caller can handle them', async () => {
    apiClient.post.mockRejectedValue(new Error('401 Unauthorized'))

    const store = useAuthStore()
    await expect(store.login('bad@test.com', 'wrong')).rejects.toThrow('401 Unauthorized')
  })

  // --- logout ---

  it('logout clears token and userEmail from store state', async () => {
    apiClient.post.mockResolvedValue({ data: { token: 'jwt-abc', email: 'me@test.com' } })

    const store = useAuthStore()
    await store.login('me@test.com', 'secret')

    store.logout()

    expect(store.token).toBeNull()
    expect(store.userEmail).toBeNull()
    expect(store.isAuthenticated).toBe(false)
  })

  it('logout removes token and userEmail from localStorage', async () => {
    apiClient.post.mockResolvedValue({ data: { token: 'jwt-abc', email: 'me@test.com' } })

    const store = useAuthStore()
    await store.login('me@test.com', 'secret')
    store.logout()

    expect(localStorage.getItem('token')).toBeNull()
    expect(localStorage.getItem('userEmail')).toBeNull()
  })
})
