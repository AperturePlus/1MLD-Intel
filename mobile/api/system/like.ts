import request from '@/utils/request'

export function listLikes(query?: Record<string, unknown>) {
  return request({
    url: '/likes/likes/list',
    method: 'get',
    params: query
  })
}

export function addLike(data: Record<string, unknown>) {
  return request({
    url: '/likes/likes',
    method: 'post',
    data
  })
}

export function deleteLike(id: string) {
  return request({
    url: `/likes/likes/${id}`,
    method: 'delete'
  })
}

