import type { AxiosResponse } from 'axios'
import service from './base'
import type {
  EmailCodeSendResponse,
  ForgotPasswordRequest,
  LoginRequest,
  LoginResponse,
  RegisterRequest,
  RegisterResponse,
  ResetPasswordRequest,
  SendRegistrationEmailCodeRequest
} from '@/types/auth'
import type { ApiEnvelope } from '@/types/common'

const userApi = {
  login(data: LoginRequest): Promise<AxiosResponse<ApiEnvelope<LoginResponse>>> {
    return service<ApiEnvelope<LoginResponse>>({
      url: '/api/v1/web/identity/auth/login',
      method: 'post',
      data
    })
  },

  sendRegistrationEmailCode(
    data: SendRegistrationEmailCodeRequest
  ): Promise<AxiosResponse<ApiEnvelope<EmailCodeSendResponse>>> {
    return service<ApiEnvelope<EmailCodeSendResponse>>({
      url: '/api/v1/web/identity/auth/register/email-code',
      method: 'post',
      data
    })
  },

  register(data: RegisterRequest): Promise<AxiosResponse<ApiEnvelope<RegisterResponse>>> {
    return service<ApiEnvelope<RegisterResponse>>({
      url: '/api/v1/web/identity/auth/register',
      method: 'post',
      data
    })
  },

  sendPasswordResetEmailCode(
    data: ForgotPasswordRequest
  ): Promise<AxiosResponse<ApiEnvelope<EmailCodeSendResponse>>> {
    return service<ApiEnvelope<EmailCodeSendResponse>>({
      url: '/api/v1/web/identity/auth/password/forgot',
      method: 'post',
      data
    })
  },

  resetPassword(
    data: ResetPasswordRequest
  ): Promise<AxiosResponse<ApiEnvelope<void>>> {
    return service<ApiEnvelope<void>>({
      url: '/api/v1/web/identity/auth/password/reset',
      method: 'post',
      data
    })
  }
}

export default userApi
