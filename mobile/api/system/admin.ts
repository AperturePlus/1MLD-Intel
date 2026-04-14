import request from '@/utils/request'

export function listManageableUsers(keyword = '') {
  return request({
    url: '/user/adminList',
    method: 'get',
    data: { keyword }
  })
}

export function banUserById(userId: string) {
  return request({
    url: `/user/${userId}/ban`,
    method: 'post'
  })
}

export function unbanUserById(userId: string) {
  return request({
    url: `/user/${userId}/unban`,
    method: 'post'
  })
}

