import config from '@/config'
import request from '@/utils/request'

export interface AppealContactInfo {
  phone: string
  email: string
}

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

export async function getAppealContact(): Promise<AppealContactInfo | null> {
  try {
    const [error, res]: [any, any] = await uni.request({
      url: `${config.baseUrl}/api/v1/app/identity/auth/appeal-contact`,
      method: 'GET',
      timeout: 5000
    })

    if (error) {
      return null
    }

    const responseData = (res && res.data) || {}
    const payload = responseData.data || responseData
    const phone = typeof payload.phone === 'string' ? payload.phone.trim() : ''
    const email = typeof payload.email === 'string' ? payload.email.trim() : ''

    if (!phone || !email) {
      return null
    }

    return {
      phone,
      email
    }
  } catch (_error) {
    return null
  }
}
