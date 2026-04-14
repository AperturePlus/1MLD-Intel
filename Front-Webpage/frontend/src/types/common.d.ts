export interface ApiEnvelope<T> {
  code: number
  message: string
  data: T
}

export interface PagedResult<T> {
  page: number
  size: number
  total: number
  items: T[]
}
