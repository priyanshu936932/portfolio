import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest'
import { useToast } from '../useToast'

describe('useToast', () => {
  let toast

  beforeEach(() => {
    vi.useFakeTimers()
    toast = useToast()
    // The composable stores toasts in a module-level ref; clear between tests.
    toast.toasts.value.splice(0)
  })

  afterEach(() => {
    vi.useRealTimers()
  })

  // --- show ---

  it('show adds a toast with the correct message and type', () => {
    toast.show('Hello world', 'success')

    expect(toast.toasts.value).toHaveLength(1)
    expect(toast.toasts.value[0]).toMatchObject({ message: 'Hello world', type: 'success' })
  })

  it('show assigns a unique id to each toast', () => {
    toast.show('First', 'info')
    toast.show('Second', 'info')

    const [a, b] = toast.toasts.value
    expect(a.id).toBeDefined()
    expect(b.id).toBeDefined()
    expect(a.id).not.toBe(b.id)
  })

  // --- dismiss ---

  it('dismiss removes a toast by id', () => {
    toast.show('Remove me', 'info')
    const id = toast.toasts.value[0].id

    toast.dismiss(id)

    expect(toast.toasts.value).toHaveLength(0)
  })

  it('dismiss is a no-op for an unknown id', () => {
    toast.show('Keep me', 'info')

    toast.dismiss(99999) // id that was never assigned

    expect(toast.toasts.value).toHaveLength(1)
  })

  // --- helper shortcuts ---

  it('success() adds a toast with type success', () => {
    toast.success('Saved!')
    expect(toast.toasts.value[0].type).toBe('success')
    expect(toast.toasts.value[0].message).toBe('Saved!')
  })

  it('error() adds a toast with type error', () => {
    toast.error('Something went wrong')
    expect(toast.toasts.value[0].type).toBe('error')
  })

  it('info() adds a toast with type info', () => {
    toast.info('FYI')
    expect(toast.toasts.value[0].type).toBe('info')
  })

  // --- auto-dismiss ---

  it('auto-dismisses after the specified duration', () => {
    toast.show('Temporary', 'info', 3500)

    expect(toast.toasts.value).toHaveLength(1)

    vi.advanceTimersByTime(3499)
    expect(toast.toasts.value).toHaveLength(1) // still there

    vi.advanceTimersByTime(1)
    expect(toast.toasts.value).toHaveLength(0) // gone
  })

  it('does not dismiss a different toast when timer fires', () => {
    toast.show('First',  'info', 3500)
    toast.show('Second', 'info', 7000)

    vi.advanceTimersByTime(3500)

    expect(toast.toasts.value).toHaveLength(1)
    expect(toast.toasts.value[0].message).toBe('Second')
  })
})
