import apiClient from './client'

export const contactApi = {
  submit:   (data) => apiClient.post('/contact', data),
  getAll:   ()     => apiClient.get('/contact'),
  markRead: (id)   => apiClient.patch(`/contact/${id}/read`),
  delete:   (id)   => apiClient.delete(`/contact/${id}`),
}
