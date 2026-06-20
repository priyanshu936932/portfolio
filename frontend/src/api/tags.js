import apiClient from './client'

export const tagsApi = {
  getAll:  ()     => apiClient.get('/tags'),
  create:  (data) => apiClient.post('/tags', data),
  delete:  (id)   => apiClient.delete(`/tags/${id}`),
}
