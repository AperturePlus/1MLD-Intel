import type { AxiosResponse } from 'axios'
import service from './base'
import type { ApiEnvelope, PagedResult, UserAccountResponse } from './types'

export interface UserAccountPageQuery {
  page?: number
  size?: number
  usernameKeyword?: string
  userType?: string
  deptName?: string
  status?: string
}

const parseTenantId = (value: unknown): number | null => {
  if (typeof value !== 'string' || !value.trim()) {
    return null
  }

  const parsed = Number.parseInt(value, 10)
  if (!Number.isFinite(parsed) || parsed <= 0) {
    return null
  }

  return parsed
}

const resolveTenantId = (): number => {
  const fromStorage = parseTenantId(localStorage.getItem('tenantId'))
  if (fromStorage) {
    return fromStorage
  }

  const fromEnv = parseTenantId(import.meta.env.VITE_TENANT_ID)
  if (fromEnv) {
    return fromEnv
  }

  return 1
}

const identityApi = {
  listUsers(
    params: UserAccountPageQuery
  ): Promise<AxiosResponse<ApiEnvelope<PagedResult<UserAccountResponse>>>> {
    return service({
      url: '/api/v1/identity/users',
      method: 'get',
      params,
      headers: {
        'X-Tenant-Id': resolveTenantId()
      }
    })
  }
}

export default identityApi
