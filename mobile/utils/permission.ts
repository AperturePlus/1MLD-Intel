import store from '@/store'

export function checkPermi(value: string[]): boolean {
  if (!value || !(value instanceof Array) || value.length === 0) {
    console.error("need roles! Like checkPermi=['system:user:add','system:user:edit']")
    return false
  }

  const permissions = (store.getters && store.getters.permissions) || []
  const allPermission = '*:*:*'

  return permissions.some((permission: string) => {
    return allPermission === permission || value.includes(permission)
  })
}

export function checkRole(value: string[]): boolean {
  if (!value || !(value instanceof Array) || value.length === 0) {
    console.error("need roles! Like checkRole=['admin','editor']")
    return false
  }

  const roles = (store.getters && store.getters.roles) || []
  const superAdmin = 'admin'

  return roles.some((role: string) => {
    return superAdmin === role || value.includes(role)
  })
}
