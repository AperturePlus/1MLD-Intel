export type MockMode = 'full' | 'off'

export interface ApiResponse<T = unknown> {
  code: number
  msg: string
  data: T
}

export interface RequestConfig {
  url: string
  method?: string
  timeout?: number
  baseUrl?: string
  params?: Record<string, unknown>
  data?: Record<string, unknown> | unknown
  header?: Record<string, unknown>
  headers?: Record<string, unknown>
}

export interface MockRequestContext {
  method: string
  url: string
  path: string
  data: Record<string, any>
  headers: Record<string, any>
}

export type MockHandler = (context: MockRequestContext) => ApiResponse<any> | Promise<ApiResponse<any>>

export interface MockUploadContext {
  url: string
  filePath: string
  name: string
  formData?: Record<string, unknown>
  params?: Record<string, unknown>
}

