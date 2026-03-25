import request from '@/utils/request'

export function login(username: string, password: string, code: string, uuid: string, role: string): Promise<any> {
  const data = {
    username,
    password,
    code,
    uuid,
    role
  }

  return request({
    url: '/user/login',
    headers: {
      isToken: false
    },
    method: 'get',
    data
  })
}

export function register(username: string, password: string, code: string, role: string): Promise<any> {
  const data = {
    username,
    password,
    code,
    role
  }

  return request({
    url: '/user/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data
  })
}

export function getInfo(data?: Record<string, unknown>): Promise<any> {
  return request({
    url: '/user',
    method: 'get',
    data
  })
}

export function logout(_token?: string): Promise<any> {
  return request({
    url: '/logout',
    method: 'post'
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
