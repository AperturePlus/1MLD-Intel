import request from '@/utils/request'

export function listCollections(query?: Record<string, unknown>) {
  return request({
    url: '/collects/collects/list',
    method: 'get',
    params: query
  })
}

export function addCollection(data: Record<string, unknown>) {
  return request({
    url: '/collects/collects',
    method: 'post',
    data
  })
}

export function deleteCollection(id: string) {
  return request({
    url: `/collects/collects/${id}`,
    method: 'delete'
  })
}

