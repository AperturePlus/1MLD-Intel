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

export interface UserDetailResponse {
  username: string
  email: string
  role: string
}

export interface PatientListQuery {
  keyword?: string
}

export interface PatientSummary {
  id: string
  name: string
  gender: string
  age: number
  riskLevel: string
  avatar?: string
}

export interface PatientListResponse {
  items: PatientSummary[]
}

export type EncounterType = 'OUTPATIENT' | 'EMERGENCY' | 'INPATIENT'

export type ImportSourceType = 'HIS_LIS' | 'IMAGE_OCR' | 'PDF_OCR'

export interface PatientRecordPayload {
  patientNo: string
  name: string
  gender: string
  age: number | null
  visitDate: string
  occupation: string
  currentAddress: string
  nativePlace: string
  department: string
  encounterType: EncounterType
  chiefComplaint: string
  diagnosis: string
  visitId?: string
  [key: string]: unknown
}

export interface HisLisPatientImportRequest {
  patientNo?: string
  visitNo?: string
}

export interface OcrPatientImportRequest {
  fileName: string
  fileContentBase64: string
}

export interface PatientImportPreview {
  sourceType: ImportSourceType
  traceId: string
  confidence: number
  patientNo: string
  name: string
  gender: string
  age: number | null
  visitDate: string
  phone: string
  idCard: string
  occupation: string
  currentAddress: string
  nativePlace: string
  department: string
  encounterType: EncounterType
}

export interface CreatePatientRecordResponse {
  recordId: string
  visitId: string
  message: string
}

export interface ScreeningOverviewQuery {
  from?: string
  to?: string
}

export interface StatCard {
  title: string
  value: number
  color: string
  icon: string
  trend: number
  suffix?: string
}

export interface RiskDistributionItem {
  level: string
  count: number
  percentage: number
  color: string
}

export interface TopGeneItem {
  name: string
  desc: string
  percentage: number
}

export interface AiEfficiencyMetrics {
  diagnosisMatchRate: number
  missRate: string
  avgDuration: string
}

export interface HighRiskPatientItem {
  date: string
  name: string
  age: number
  clue: string
  aiSuggest: string
}

export interface ScreeningOverviewResponse {
  updatedAt: string
  statCards: StatCard[]
  riskDistribution: RiskDistributionItem[]
  topGenes: TopGeneItem[]
  aiEfficiency: AiEfficiencyMetrics
  highRiskPatients: HighRiskPatientItem[]
}

export interface DietPatient {
  id: string
  name: string
  gender: string
  age: number
  avatar: string
  disease: string
  compliance: string
}

export interface DietPatientsResponse {
  items: DietPatient[]
}

export interface DietTarget {
  label: string
  value: string | number
  unit: string
  color: string
  desc: string
}

export interface DietFoods {
  red: string[]
  yellow: string[]
  green: string[]
}

export interface MealPlanItem {
  type: string
  time: string
  menu: string
  nutrition: string
}

export interface DietPlanResponse {
  targets: DietTarget[]
  foods: DietFoods
  mealPlan: MealPlanItem[]
}

export interface RegenerateDietPlanResponse {
  mealPlan: MealPlanItem[]
  regeneratedAt: string
}

export interface PushDietPlanResponse {
  delivered: boolean
  patientId: string
  deliveredAt: string
}

export interface DiagnosisQueuePatient {
  id: string
  name: string
  gender: string
  age: number
  avatar: string
  aiStatus: string
  encounterId?: number
  doctorId?: number
  modelRegistryId?: number
}

export interface DiagnosisQueueResponse {
  items: DiagnosisQueuePatient[]
}

export type ProgressStatus = '' | 'success' | 'warning' | 'exception'

export interface DiagnosisIndicator {
  name: string
  value: number
  unit: string
  normal: string
  percentage: number
  status: ProgressStatus
}

export interface DiagnosisResult {
  diseaseName: string
  probability: number
  indicators: DiagnosisIndicator[]
  genes: string[]
  diet: string
  sequencing: string
}

export interface AiFindings {
  biochemical: string
  clinical: string
  probability: string
  disease: string
}

export interface ExpertReport {
  id: string
  patientId: string
  visitId: string
  patientName: string
  gender: string
  age: number
  date: string
  status: string
  aiFindings: AiFindings
  expertConclusion: string
  treatmentPlan: string
  signedAt?: string
  sessionId?: number
  resultId?: number
  doctorId?: number
}

export interface ExpertReportListResponse {
  items: ExpertReport[]
}

export interface SignExpertReportPayload {
  expertConclusion: string
  treatmentPlan: string
}

export interface SignExpertReportResponse {
  report: ExpertReport
}

export interface ApiEnvelope<T> {
  code: number
  message: string
  data: T
}

export interface PagedResult<T> {
  page: number
  size: number
  total: number
  items: T[]
}

export interface RoleItem {
  id: number
  roleCode: string
  roleName: string
  status: string
}

export interface UserAccountResponse {
  id: number
  userNo: string
  username: string
  displayName: string
  userType: string
  deptName: string
  email: string
  status: string
  lastLoginAt: string | null
  roles: RoleItem[]
}

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
