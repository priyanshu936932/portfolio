import apiClient from './client'

export const profileApi = {
  get:    ()     => apiClient.get('/profile'),
  update: (data) => apiClient.put('/profile', data),
}
