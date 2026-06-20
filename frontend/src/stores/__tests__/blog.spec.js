import { describe, it, expect, beforeEach, vi } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'

vi.mock('@/api/blog', () => ({
  blogApi: {
    getPublished: vi.fn(),
    getDrafts:    vi.fn(),
    getBySlug:    vi.fn(),
    create:       vi.fn(),
    update:       vi.fn(),
    delete:       vi.fn(),
  },
}))

import { useBlogStore } from '../blog'
import { blogApi } from '@/api/blog'

const post1 = { id: '1', title: 'Post One', slug: 'post-one', published: true }
const post2 = { id: '2', title: 'Post Two', slug: 'post-two', published: true }
const draft  = { id: '3', title: 'Draft',    slug: 'draft',    published: false }

describe('useBlogStore', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    setActivePinia(createPinia())
  })

  // --- fetchPublished ---

  it('fetchPublished populates items and clears error on success', async () => {
    blogApi.getPublished.mockResolvedValue({ data: [post1, post2] })

    const store = useBlogStore()
    await store.fetchPublished()

    expect(store.items).toEqual([post1, post2])
    expect(store.loading).toBe(false)
    expect(store.error).toBeNull()
  })

  it('fetchPublished sets error and leaves items empty on failure', async () => {
    blogApi.getPublished.mockRejectedValue({ response: { data: { message: 'Unavailable' } } })

    const store = useBlogStore()
    await store.fetchPublished()

    expect(store.error).toBe('Unavailable')
    expect(store.items).toEqual([])
  })

  it('fetchPublished falls back to generic error when response has no message', async () => {
    blogApi.getPublished.mockRejectedValue(new Error('timeout'))

    const store = useBlogStore()
    await store.fetchPublished()

    expect(store.error).toBe('Failed to load posts')
  })

  // --- fetchAllAdmin ---

  it('fetchAllAdmin calls getDrafts and populates items', async () => {
    blogApi.getDrafts.mockResolvedValue({ data: [post1, draft] })

    const store = useBlogStore()
    await store.fetchAllAdmin()

    expect(store.items).toEqual([post1, draft])
    expect(blogApi.getDrafts).toHaveBeenCalledOnce()
  })

  // --- fetchBySlug ---

  it('fetchBySlug populates current on success', async () => {
    blogApi.getBySlug.mockResolvedValue({ data: post1 })

    const store = useBlogStore()
    await store.fetchBySlug('post-one')

    expect(store.current).toEqual(post1)
    expect(store.error).toBeNull()
  })

  it('fetchBySlug resets current to null before fetching', async () => {
    blogApi.getBySlug.mockRejectedValue({ response: { data: { message: 'Post not found' } } })

    const store = useBlogStore()
    store.current = post1 // simulate stale data

    await store.fetchBySlug('missing')

    expect(store.current).toBeNull()
    expect(store.error).toBe('Post not found')
  })

  // --- create ---

  it('create prepends the new post to items (newest first)', async () => {
    blogApi.create.mockResolvedValue({ data: post2 })

    const store = useBlogStore()
    store.items = [post1]

    await store.create({ title: 'Post Two', slug: 'post-two' })

    expect(store.items[0]).toEqual(post2) // prepended
    expect(store.items).toHaveLength(2)
  })

  // --- update ---

  it('update replaces the matching post in-place', async () => {
    const updated = { ...post1, title: 'Post One Updated' }
    blogApi.update.mockResolvedValue({ data: updated })

    const store = useBlogStore()
    store.items = [post1, post2]

    await store.update('1', { title: 'Post One Updated' })

    expect(store.items[0]).toEqual(updated)
    expect(store.items[1]).toEqual(post2)
  })

  it('update is a no-op on items when the id is not found', async () => {
    const updated = { id: '99', title: 'Ghost', slug: 'ghost' }
    blogApi.update.mockResolvedValue({ data: updated })

    const store = useBlogStore()
    store.items = [post1]

    await store.update('99', { title: 'Ghost' })

    // Item with id '99' was not in the list, so items is unchanged
    expect(store.items).toEqual([post1])
  })

  // --- remove ---

  it('remove filters out the post by id', async () => {
    blogApi.delete.mockResolvedValue({})

    const store = useBlogStore()
    store.items = [post1, post2]

    await store.remove('1')

    expect(store.items).toHaveLength(1)
    expect(store.items[0]).toEqual(post2)
  })

  it('remove calls the API with the correct id', async () => {
    blogApi.delete.mockResolvedValue({})

    const store = useBlogStore()
    store.items = [post2]
    await store.remove('2')

    expect(blogApi.delete).toHaveBeenCalledWith('2')
  })
})
