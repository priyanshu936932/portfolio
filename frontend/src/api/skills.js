import apiClient from './client'

export const skillsApi = {
  getAll:   ()          => apiClient.get('/skills'),
  create:   (data)      => apiClient.post('/skills', data),
  update:   (id, data)  => apiClient.put(`/skills/${id}`, data),
  delete:   (id)        => apiClient.delete(`/skills/${id}`),
}
