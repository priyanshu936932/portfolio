import { describe, it, expect, beforeEach, vi } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'

vi.mock('@/api/skills', () => ({
  skillsApi: {
    getAll:  vi.fn(),
    create:  vi.fn(),
    update:  vi.fn(),
    delete:  vi.fn(),
  },
}))

import { useSkillsStore } from '../skills'
import { skillsApi } from '@/api/skills'

const java   = { id: '1', name: 'Java',   category: 'Backend',  proficiency: 5 }
const spring = { id: '2', name: 'Spring', category: 'Backend',  proficiency: 4 }
const vue    = { id: '3', name: 'Vue',    category: 'Frontend', proficiency: 4 }

describe('useSkillsStore', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    setActivePinia(createPinia())
  })

  // --- byCategory computed ---

  it('byCategory groups skills under their category key', () => {
    const store = useSkillsStore()
    store.items = [java, spring, vue]

    expect(store.byCategory).toEqual({
      Backend:  [java, spring],
      Frontend: [vue],
    })
  })

  it('byCategory falls back to "Other" when category is missing', () => {
    const store = useSkillsStore()
    store.items = [{ id: '99', name: 'Mystery', category: undefined, proficiency: 3 }]

    expect(Object.keys(store.byCategory)).toContain('Other')
  })

  it('byCategory returns empty object when items is empty', () => {
    const store = useSkillsStore()
    expect(store.byCategory).toEqual({})
  })

  // --- fetchAll ---

  it('fetchAll populates items and clears error on success', async () => {
    skillsApi.getAll.mockResolvedValue({ data: [java, spring] })

    const store = useSkillsStore()
    await store.fetchAll()

    expect(store.items).toEqual([java, spring])
    expect(store.loading).toBe(false)
    expect(store.error).toBeNull()
  })

  it('fetchAll sets error message and leaves items empty on failure', async () => {
    skillsApi.getAll.mockRejectedValue({ response: { data: { message: 'Server error' } } })

    const store = useSkillsStore()
    await store.fetchAll()

    expect(store.error).toBe('Server error')
    expect(store.items).toEqual([])
    expect(store.loading).toBe(false)
  })

  it('fetchAll falls back to generic message when response has no message', async () => {
    skillsApi.getAll.mockRejectedValue(new Error('Network error'))

    const store = useSkillsStore()
    await store.fetchAll()

    expect(store.error).toBe('Failed to load skills')
  })

  // --- create ---

  it('create appends the returned skill to items', async () => {
    skillsApi.create.mockResolvedValue({ data: java })

    const store = useSkillsStore()
    store.items = [spring]

    await store.create({ name: 'Java', category: 'Backend', proficiency: 5 })

    expect(store.items).toHaveLength(2)
    expect(store.items).toContainEqual(java)
  })

  // --- update ---

  it('update replaces the matching item in-place without touching others', async () => {
    const updated = { ...java, name: 'Java 21' }
    skillsApi.update.mockResolvedValue({ data: updated })

    const store = useSkillsStore()
    store.items = [java, spring]

    await store.update('1', { name: 'Java 21' })

    expect(store.items[0]).toEqual(updated)
    expect(store.items[1]).toEqual(spring) // unchanged
  })

  // --- remove ---

  it('remove filters out the skill by id', async () => {
    skillsApi.delete.mockResolvedValue({})

    const store = useSkillsStore()
    store.items = [java, spring, vue]

    await store.remove('2') // remove spring

    expect(store.items).toHaveLength(2)
    expect(store.items.find((s) => s.id === '2')).toBeUndefined()
  })

  it('remove calls the API with the correct id', async () => {
    skillsApi.delete.mockResolvedValue({})

    const store = useSkillsStore()
    store.items = [java]
    await store.remove('1')

    expect(skillsApi.delete).toHaveBeenCalledWith('1')
  })
})
