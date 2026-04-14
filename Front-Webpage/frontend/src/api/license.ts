import type { AxiosResponse } from 'axios'
import service from './base'
import type {
  ActivateLicenseRequest,
  FingerprintResponse,
  LicenseStatusResponse,
  LicenseValidationResponse
} from '@/types/license'
import type { ApiEnvelope } from '@/types/common'

const licenseApi = {
  getLicenseStatus(): Promise<AxiosResponse<ApiEnvelope<LicenseStatusResponse>>> {
    return service({
      url: '/api/v1/web/license/status',
      method: 'get'
    })
  },

  getFingerprint(): Promise<AxiosResponse<ApiEnvelope<FingerprintResponse>>> {
    return service({
      url: '/api/v1/web/license/fingerprint',
      method: 'get'
    })
  },

  activateLicense(
    data: ActivateLicenseRequest
  ): Promise<AxiosResponse<ApiEnvelope<LicenseValidationResponse>>> {
    return service({
      url: '/api/v1/web/license/activate',
      method: 'post',
      data
    })
  }
}

export default licenseApi
