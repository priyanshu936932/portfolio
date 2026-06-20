<script setup>
defineProps({
  modelValue: { type: String, default: '' },
  label: String,
  placeholder: String,
  rows: { type: Number, default: 4 },
  error: String,
  required: Boolean,
  disabled: Boolean,
})
defineEmits(['update:modelValue'])
</script>

<template>
  <div class="flex flex-col gap-1.5">
    <label v-if="label" class="text-sm font-medium text-[var(--text-secondary)]">
      {{ label }}<span v-if="required" class="ml-0.5 text-[var(--color-brand-500)]">*</span>
    </label>
    <textarea
      :value="modelValue"
      :placeholder="placeholder"
      :rows="rows"
      :required="required"
      :disabled="disabled"
      @input="$emit('update:modelValue', $event.target.value)"
      :class="[
        'rounded-xl border bg-[var(--bg-surface)] px-4 py-2.5 text-sm text-[var(--text-primary)] placeholder-[var(--text-tertiary)] transition-colors duration-150 outline-none resize-y',
        'focus:border-[var(--color-brand-400)] focus:ring-2 focus:ring-[var(--color-brand-400)]/20',
        error ? 'border-red-400' : 'border-[var(--border-default)]',
        disabled && 'opacity-50 cursor-not-allowed',
      ]"
    />
    <p v-if="error" class="text-xs text-red-500">{{ error }}</p>
  </div>
</template>
