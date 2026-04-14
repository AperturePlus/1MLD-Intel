export interface LicenseStatusResponse {
  licenseId: string | null
  hospitalId: string | null
  deploymentMode: string | null
  issuer: string | null
  issuedAt: string | null
  supportStartDate: string | null
  supportEndDate: string | null
  features: string[]
  scenarios: string[]
  machineBound: boolean
  activated: boolean
}

export interface FingerprintResponse {
  cpuIdHash: string
  macAddrHash: string
  hdIdHash: string
  osInfoHash: string
  machineFingerprintHash: string
}

export interface ActivateLicenseRequest {
  activationCode: string
  machineFingerprintHash: string
}

export interface LicenseValidationResponse {
  valid: boolean
  reason: string
  machineFingerprintHash: string
  supportActive: boolean
}
