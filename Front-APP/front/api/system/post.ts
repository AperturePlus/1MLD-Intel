import request from '@/utils/request'

export function createPost(data: Record<string, unknown>) {
  return request({
    url: '/post/add',
    method: 'post',
    data
  })
}

export function queryPosts(params?: Record<string, unknown>) {
  return request({
    url: '/post/five',
    method: 'get',
    params
  })
}

export function getMyPosts(nickname: string) {
  return request({
    url: '/post/my',
    method: 'get',
    params: { nickname }
  })
}

export function deletePostById(id: string) {
  return request({
    url: `/post/delete/${id}`,
    method: 'delete'
  })
}

export function listPendingPosts() {
  return request({
    url: '/post/pending',
    method: 'get'
  })
}

export function reviewPostById(id: string, state: string) {
  return request({
    url: '/post/review',
    method: 'post',
    data: {
      id: id.toString(),
      state: state.toString()
    }
  })
}

