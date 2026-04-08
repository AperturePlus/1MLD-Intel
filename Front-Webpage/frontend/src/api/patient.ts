import type { AxiosResponse } from 'axios'
import service from './base'
import type {
  CreatePatientRecordResponse,
  HisLisPatientImportRequest,
  OcrPatientImportRequest,
  PatientImportPreview,
  PatientListQuery,
  PatientListResponse,
  PatientRecordPayload
} from './types'

const parseTenantId = (value: unknown): number | null => {
  if (typeof value !== 'string' || !value.trim()) {
    return null
  }

  const parsed = Number.parseInt(value, 10)
  if (!Number.isFinite(parsed) || parsed <= 0) {
    return null
  }

  return parsed
}

const resolveTenantId = (): number => {
  const fromStorage = parseTenantId(localStorage.getItem('tenantId'))
  if (fromStorage) {
    return fromStorage
  }

  const fromEnv = parseTenantId(import.meta.env.VITE_TENANT_ID)
  if (fromEnv) {
    return fromEnv
  }

  return 1
}

const tenantHeaders = () => ({
  'X-Tenant-Id': resolveTenantId()
})

const patientApi = {
  list(params: PatientListQuery = {}): Promise<AxiosResponse<PatientListResponse>> {
    return service({
      url: '/api/v1/web/patients/',
      method: 'get',
      params,
      headers: tenantHeaders()
    })
  },

  createRecord(data: PatientRecordPayload): Promise<AxiosResponse<CreatePatientRecordResponse>> {
    return service({
      url: '/api/v1/web/patient-records/',
      method: 'post',
      data,
      headers: tenantHeaders()
    })
  },

  importPatientFromHisLis(
    data: HisLisPatientImportRequest
  ): Promise<AxiosResponse<PatientImportPreview>> {
    return service({
      url: '/api/v1/web/integration/imports/patient/his-lis',
      method: 'post',
      data,
      headers: tenantHeaders()
    })
  },

  importPatientFromImage(
    data: OcrPatientImportRequest
  ): Promise<AxiosResponse<PatientImportPreview>> {
    return service({
      url: '/api/v1/web/integration/imports/patient/image',
      method: 'post',
      data,
      headers: tenantHeaders()
    })
  },

  importPatientFromPdf(
    data: OcrPatientImportRequest
  ): Promise<AxiosResponse<PatientImportPreview>> {
    return service({
      url: '/api/v1/web/integration/imports/patient/pdf',
      method: 'post',
      data,
      headers: tenantHeaders()
    })
  }
}

export default patientApi

