import type { RouteRecordRaw } from 'vue-router'
import { authRouteDefinition, centerRouteDefinitions } from './routeCatalog'

const centerChildren = Object.values(centerRouteDefinitions).map<RouteRecordRaw>((definition) => ({
  path: definition.path,
  name: definition.name,
  component: definition.component,
  meta: {
    title: definition.title,
    sectionTitle: definition.sectionTitle
  }
}))

export const routes: RouteRecordRaw[] = [
  {
    path: authRouteDefinition.path,
    name: authRouteDefinition.name,
    component: authRouteDefinition.component,
    meta: {
      title: authRouteDefinition.title,
      sectionTitle: authRouteDefinition.sectionTitle
    }
  },
  {
    path: '/center',
    name: 'center',
    component: () => import('@/layouts/CenterLayout.vue'),
    redirect: centerRouteDefinitions.welcome.fullPath,
    meta: {
      title: centerRouteDefinitions.welcome.title,
      sectionTitle: centerRouteDefinitions.welcome.sectionTitle
    },
    children: centerChildren
  }
]
