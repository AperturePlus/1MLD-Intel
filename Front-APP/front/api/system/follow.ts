import request from '@/utils/request'

export function listFollowRelations(params: Record<string, unknown>) {
  return request({
    url: '/followship/followees',
    method: 'get',
    params
  })
}

export function listFollowerRelations(params: Record<string, unknown>) {
  return request({
    url: '/followship/followers',
    method: 'get',
    params
  })
}

export function addFollowRelation(data: Record<string, unknown>) {
  return request({
    url: '/followship/add',
    method: 'post',
    data
  })
}

export function removeFollowRelation(data: Record<string, unknown>) {
  return request({
    url: '/followship/delete',
    method: 'post',
    data
  })
}

export function checkFollowRelation(params: Record<string, unknown>) {
  return request({
    url: '/followship/check',
    method: 'get',
    params
  })
}

