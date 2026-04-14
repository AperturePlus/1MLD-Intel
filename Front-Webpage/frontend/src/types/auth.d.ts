export interface LoginRequest {
  username: string
  password: string
  tenantCode?: string
}

export interface LoginResponse {
  accessToken: string
  refreshToken: string
  expiresAt: string
  user?: AuthenticatedUserItem | null
}

export interface RegisterRequest {
  username: string
  email: string
  password: string
  emailCode: string
  displayName?: string
  userType?: string
  tenantCode?: string
}

export interface RegisterResponse {
  accessToken: string
  refreshToken: string
  expiresAt: string
  user?: AuthenticatedUserItem | null
}

export interface AuthenticatedUserItem {
  userId: number
  tenantId: number
  username: string
  displayName: string
  userType: string
  roleCodes: string[]
  avatar?: string
  avatarUrl?: string
}

export interface SendRegistrationEmailCodeRequest {
  username: string
  email: string
  tenantCode?: string
}

export interface ForgotPasswordRequest {
  username: string
  email: string
  tenantCode?: string
}

export interface ResetPasswordRequest {
  username: string
  email: string
  newPassword: string
  emailCode: string
  tenantCode?: string
}

export interface EmailCodeSendResponse {
  email: string
  purpose: string
  expiresAt: string
  resendAfterSeconds: number
}
