import apiClient from './client'

export const blogApi = {
  getPublished: ()          => apiClient.get('/blog'),
  getDrafts:    ()          => apiClient.get('/blog/drafts'),
  getBySlug:    (slug)      => apiClient.get(`/blog/${slug}`),
  create:       (data)      => apiClient.post('/blog', data),
  update:       (id, data)  => apiClient.put(`/blog/${id}`, data),
  delete:       (id)        => apiClient.delete(`/blog/${id}`),
}
