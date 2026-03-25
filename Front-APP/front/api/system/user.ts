import request from '@/utils/request'
import upload from '@/utils/upload'

export function listUser(data: { param: string; [key: string]: unknown }): Promise<any> {
  return request({
    url: `/user/list/${data.param}`,
    method: 'post',
    data
  })
}

export function getUser(params: Record<string, unknown>): Promise<any> {
  return request({
    url: '/user/five',
    method: 'get',
    params
  })
}

export function getOneUser(nickname: string): Promise<any> {
  return request({
    url: '/user/one',
    method: 'get',
    params: {
      param: nickname
    }
  })
}

export function updateUserPwd(data: Record<string, unknown>): Promise<any> {
  return request({
    url: '/user/updatePassword',
    method: 'get',
    data
  })
}

export function getUserProfile(): Promise<any> {
  return request({
    url: '/user',
    method: 'get'
  })
}

export function updateUserProfile(data: Record<string, unknown>): Promise<any> {
  return request({
    url: '/user',
    method: 'put',
    data
  })
}

export function uploadAvatar(data: { name: string; filePath: string }): Promise<any> {
  return upload({
    url: '/system/user/profile/avatar',
    name: data.name,
    filePath: data.filePath
  })
}

export function setDiet(data: Record<string, unknown>): Promise<any> {
  return request({
    url: '/user/setDiet',
    method: 'get',
    data
  })
}

export function register(data: Record<string, unknown>): Promise<any> {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function getCodeImg(): Promise<any> {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}
