import config from '@/config'
import { dispatchMockRequest } from '@/mock/router'
import { ApiResponse, MockRequestContext, MockUploadContext } from '@/types/api'

const normalizePath = (url: string) => {
  if (!url) {
    return '/'
  }
  const [pathOnly] = url.split('?')
  const withoutHost = pathOnly.replace(/^https?:\/\/[^/]+/i, '')
  const withoutBaseUrl = withoutHost.startsWith(config.baseUrl) ? withoutHost.slice(config.baseUrl.length) : withoutHost
  const normalized = withoutBaseUrl || '/'
  return normalized.startsWith('/') ? normalized : `/${normalized}`
}

const toRecord = (value: unknown): Record<string, any> => {
  if (value && typeof value === 'object' && !Array.isArray(value)) {
    return value as Record<string, any>
  }
  return {}
}

export const handleMockRequest = async (input: {
  url: string
  method: string
  data?: unknown
  headers?: Record<string, unknown>
}): Promise<ApiResponse<any>> => {
  const context: MockRequestContext = {
    method: (input.method || 'GET').toUpperCase(),
    url: input.url,
    path: normalizePath(input.url),
    data: toRecord(input.data),
    headers: toRecord(input.headers)
  }
  return dispatchMockRequest(context)
}

export const handleMockUpload = async (input: MockUploadContext): Promise<ApiResponse<{ fileName: string; url: string }>> => {
  const path = normalizePath(input.url)
  const suffix = path.includes('report') ? 'report' : path.includes('advice') ? 'advice' : 'userinfo'
  const extension = (input.filePath.split('.').pop() || 'png').toLowerCase()
  const fileName = `${suffix}-mock.${extension}`
  const displayUrl = `${config.baseUrl}/${suffix}/${fileName}`
  return {
    code: 200,
    msg: 'upload success',
    data: {
      fileName,
      url: displayUrl
    }
  }
}

