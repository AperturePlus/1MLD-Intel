import request from '@/utils/request'

export interface AppealContactInfo {
  phone: string
  email: string
}

export interface LoginPayload {
  username: string
  password: string
  code: string
  uuid: string
  role: string
}

export const loginByPassword = (payload: LoginPayload): Promise<any> =>
  request({
    url: '/user/login',
    headers: {
      isToken: false
    },
    method: 'get',
    data: payload
  })

export const registerAccount = (payload: { username: string; password: string; code: string; role: string }): Promise<any> =>
  request({
    url: '/user/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: payload
  })

export const fetchCurrentUserInfo = (data?: Record<string, unknown>): Promise<any> =>
  request({
    url: '/user',
    method: 'get',
    data
  })

export const logoutAccount = (_token?: string): Promise<any> =>
  request({
    url: '/logout',
    method: 'post'
  })

export const fetchCaptchaImage = (): Promise<any> =>
  request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })

export const sendPasswordResetCode = (phone: string): Promise<any> =>
  request({
    url: '/user/password/send-code',
    headers: {
      isToken: false
    },
    method: 'post',
    data: { phone }
  })

export const verifyPasswordResetCode = (phone: string, code: string): Promise<any> =>
  request({
    url: '/user/password/verify-code',
    headers: {
      isToken: false
    },
    method: 'post',
    data: { phone, code }
  })

export const resetPasswordByPhone = (payload: {
  phone: string
  code: string
  newPassword: string
  confirmPassword: string
}): Promise<any> =>
  request({
    url: '/user/password/reset',
    headers: {
      isToken: false
    },
    method: 'post',
    data: payload
  })

export const fetchAppealContact = async (): Promise<AppealContactInfo | null> => {
  try {
    const response = await request({
      url: '/api/v1/app/identity/auth/appeal-contact',
      method: 'get',
      headers: {
        isToken: false
      }
    })
    const payload = (response && response.data) || response || {}
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

