import request from '@/utils/request'
export function GetFollowships(params) {
  return request({
    url: '/followship/followees',
    method: 'get',
    params
  })
}

export function GetFollowers(params) {
  return request({
    url: '/followship/followers',
    method: 'get',
    params
  })
}

export function AddFollowship(data) {
  return request({
    url: '/followship/add',
    method: 'post',
    data
  })
}

export function DeleteFollowship(data) {
  return request({
    url: '/followship/delete',
    method: 'post',
    data
  })
}

export function CheckFollowship(params) {
  return request({
    url: '/followship/check',
    method: 'get',
    params
  })
}

