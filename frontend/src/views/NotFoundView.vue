<template>
  <div class="relative flex min-h-[80vh] flex-col items-center justify-center overflow-hidden px-6 text-center">

    <!-- Background glow -->
    <div class="pointer-events-none absolute inset-0">
      <div class="absolute left-1/2 top-1/2 h-[500px] w-[500px] -translate-x-1/2 -translate-y-1/2 rounded-full opacity-10 blur-3xl"
        style="background: radial-gradient(circle, var(--color-brand-500), transparent 70%)" />
    </div>

    <div class="relative">
      <!-- Big number -->
      <p
        class="select-none font-display font-bold leading-none text-[var(--border-strong)]"
        style="font-size: clamp(8rem, 25vw, 16rem); opacity: 0.15"
      >
        404
      </p>

      <!-- Overlay text -->
      <div class="absolute inset-0 flex flex-col items-center justify-center gap-4">
        <div class="flex h-16 w-16 items-center justify-center rounded-2xl bg-[var(--bg-surface)] border border-[var(--border-default)] shadow-lg">
          <svg class="h-8 w-8 text-[var(--color-brand-500)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
              d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <div>
          <h1 class="font-display text-2xl font-bold text-[var(--text-primary)]">Page not found</h1>
          <p class="mt-2 text-sm text-[var(--text-secondary)]">
            Looks like this page wandered off. Let's get you back.
          </p>
        </div>
        <div class="flex flex-wrap items-center justify-center gap-3">
          <RouterLink
            to="/"
            class="inline-flex items-center gap-2 rounded-xl bg-[var(--color-brand-500)] px-6 py-2.5 text-sm font-semibold text-white transition-all hover:bg-[var(--color-brand-600)] active:scale-95"
          >
            <svg class="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
            Go home
          </RouterLink>
          <RouterLink
            to="/contact"
            class="inline-flex items-center gap-2 rounded-xl border border-[var(--border-strong)] px-6 py-2.5 text-sm font-semibold text-[var(--text-secondary)] transition-all hover:border-[var(--color-brand-400)] hover:text-[var(--color-brand-400)] active:scale-95"
          >
            Contact me
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>
