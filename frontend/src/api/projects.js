import apiClient from './client'

export const projectsApi = {
  getAll:      ()           => apiClient.get('/projects'),
  getBySlug:   (slug)       => apiClient.get(`/projects/${slug}`),
  create:      (data)       => apiClient.post('/projects', data),
  update:      (id, data)   => apiClient.put(`/projects/${id}`, data),
  delete:      (id)         => apiClient.delete(`/projects/${id}`),
}
