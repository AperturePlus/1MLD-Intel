import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import { authGuard } from './guards/authGuard'
import { routes } from './routes'

const isFileProtocol = typeof window !== 'undefined' && window.location.protocol === 'file:'

const router = createRouter({
  history: isFileProtocol ? createWebHashHistory() : createWebHistory(),
  routes
})

router.beforeEach(authGuard)

export default router
