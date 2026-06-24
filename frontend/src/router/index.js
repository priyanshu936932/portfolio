import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const SITE = 'Priyanshu'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior: () => ({ top: 0 }),
  routes: [
    { path: '/',              name: 'home',           meta: { title: SITE },                       component: () => import('@/views/HomeView.vue') },
    { path: '/projects',      name: 'projects',       meta: { title: 'Projects — ' + SITE },       component: () => import('@/views/public/ProjectsView.vue') },
    { path: '/projects/:slug',name: 'project-detail', meta: { title: 'Project — ' + SITE },        component: () => import('@/views/public/ProjectDetailView.vue') },
    { path: '/blog',          name: 'blog',           meta: { title: 'Blog — ' + SITE },           component: () => import('@/views/public/BlogView.vue') },
    { path: '/blog/:slug',    name: 'blog-post',      meta: { title: 'Post — ' + SITE },           component: () => import('@/views/public/BlogPostView.vue') },
    { path: '/cp',            name: 'cp',             meta: { title: 'Competitive Programming — ' + SITE }, component: () => import('@/views/public/CpView.vue') },
    { path: '/resume',        name: 'resume',         meta: { title: 'Resume — ' + SITE },         component: () => import('@/views/public/ResumeView.vue') },
    { path: '/contact',       name: 'contact',        meta: { title: 'Contact — ' + SITE },        component: () => import('@/views/public/ContactView.vue') },
    { path: '/admin/login',   name: 'login',          meta: { title: 'Sign in — ' + SITE },        component: () => import('@/views/admin/LoginView.vue') },
    {
      path: '/admin',
      meta: { requiresAuth: true },
      component: () => import('@/views/admin/AdminLayout.vue'),
      children: [
        { path: 'dashboard', name: 'dashboard',       meta: { title: 'Dashboard — Admin' },        component: () => import('@/views/admin/DashboardView.vue') },
        { path: 'projects',  name: 'admin-projects',  meta: { title: 'Projects — Admin' },         component: () => import('@/views/admin/AdminProjectsView.vue') },
        { path: 'blog',      name: 'admin-blog',      meta: { title: 'Blog — Admin' },             component: () => import('@/views/admin/AdminBlogView.vue') },
        { path: 'skills',    name: 'admin-skills',    meta: { title: 'Skills — Admin' },           component: () => import('@/views/admin/AdminSkillsView.vue') },
        { path: 'tags',      name: 'admin-tags',      meta: { title: 'Tags — Admin' },             component: () => import('@/views/admin/AdminTagsView.vue') },
        { path: 'messages',  name: 'admin-messages',  meta: { title: 'Messages — Admin' },         component: () => import('@/views/admin/AdminMessagesView.vue') },
        { path: 'profile',   name: 'admin-profile',   meta: { title: 'Profile — Admin' },          component: () => import('@/views/admin/AdminProfileView.vue') },
      ],
    },
    { path: '/:pathMatch(.*)*', name: 'not-found',    meta: { title: '404 — ' + SITE },            component: () => import('@/views/NotFoundView.vue') },
  ],
})

router.beforeEach((to) => {
  const authStore = useAuthStore()
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    return '/admin/login'
  }
  document.title = to.meta.title ?? SITE
})

export default router
