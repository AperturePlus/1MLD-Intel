import {
  fetchAppealContact,
  fetchCaptchaImage,
  fetchCurrentUserInfo,
  loginByPassword,
  logoutAccount,
  registerAccount
} from '@/api/auth'

export type { AppealContactInfo } from '@/api/auth'

export function login(username: string, password: string, code: string, uuid: string, role: string): Promise<any> {
  return loginByPassword({
    username,
    password,
    code,
    uuid,
    role
  })
}

export function register(username: string, password: string, code: string, role: string): Promise<any> {
  return registerAccount({
    username,
    password,
    code,
    role
  })
}

export function getInfo(data?: Record<string, unknown>): Promise<any> {
  return fetchCurrentUserInfo(data)
}

export function logout(token?: string): Promise<any> {
  return logoutAccount(token)
}

export function getCodeImg(): Promise<any> {
  return fetchCaptchaImage()
}

export function getAppealContact() {
  return fetchAppealContact()
}

