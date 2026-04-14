import { nextTick, reactive, ref, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormItemRule, FormRules } from 'element-plus'
import patientApi from '@/api/patient'
import type {
  EncounterType,
  ImportSourceType,
  PatientImportPreview,
  PatientRecordClinicalDecision,
  PatientRecordGeneticMethod,
  PatientRecordGeneticSequencing,
  PatientRecordGeneticSourceType,
  PatientRecordGeneticVariantItem,
  PatientRecordImagingModality,
  PatientRecordImagingReportItem,
  PatientRecordImagingSourceType,
  PatientRecordImportMeta,
  PatientRecordLaboratoryScreening,
  PatientRecordPathology,
  PatientRecordPathologySourceType,
  PatientRecordPayload,
  TernaryFlag
} from '@/types/patient'
import {
  createInitialLaboratoryScreening,
  normalizeLaboratoryScreening
} from '@/features/patient-record/constants/laboratoryScreening'

const DRAFT_STORAGE_KEY = 'imld_patient_record_draft'
const TRI_STATE_VALUES = ['YES', 'NO', 'UNKNOWN'] as const
const IMAGING_SOURCE_TYPES = ['MANUAL', 'IMAGE_OCR', 'PDF_OCR', 'PACS_IMPORT'] as const
const PATHOLOGY_SOURCE_TYPES = ['MANUAL', 'IMAGE_OCR', 'PDF_OCR', 'PACS_IMPORT'] as const
const GENETIC_SOURCE_TYPES = ['MANUAL', 'IMAGE_OCR', 'PDF_OCR', 'HIS_LIS'] as const
const GENETIC_METHODS = ['PANEL', 'WES', 'WGS', 'OTHER', ''] as const
const IMAGING_MODALITIES = ['CT', 'ULTRASOUND', 'MRI', 'OTHER'] as const

type TriStateFormValue = TernaryFlag | ''

interface PatientRecordDiseaseHistoryFormModel {
  smokingHistory: TriStateFormValue
  drinkingHistory: TriStateFormValue
  diabetesHistory: TriStateFormValue
  hypertensionHistory: TriStateFormValue
  hyperuricemiaHistory: TriStateFormValue
  hyperlipidemiaHistory: TriStateFormValue
  coronaryHeartDiseaseHistory: TriStateFormValue
  hepatitisBHistory: TriStateFormValue
}

interface PatientRecordConditionalHistoryFormModel {
  status: TriStateFormValue
  detail: string
}

interface PatientRecordHistoryFormModel {
  diseaseHistory: PatientRecordDiseaseHistoryFormModel
  surgeryHistory: PatientRecordConditionalHistoryFormModel
  transfusionHistory: PatientRecordConditionalHistoryFormModel
  allergyHistory: string
  medicationHistory: string
  familyHistory: string
}

interface PatientRecordPhysicalExamFormModel {
  heightCm: number | null
  weightKg: number | null
  bmi: number | null
  bloodPressureSystolic: number | null
  bloodPressureDiastolic: number | null
  respiratoryRate: number | null
  heartRate: number | null
  liverFibrosis: TriStateFormValue
  cirrhosis: TriStateFormValue
  fattyLiver: TriStateFormValue
  liverFailure: TriStateFormValue
  cholestasis: TriStateFormValue
  viralHepatitis: TriStateFormValue
}

export interface PatientRecordImagingReportFormModel
  extends Omit<PatientRecordImagingReportItem, 'examinedAt'> {
  localId: string
  examinedAt: Date | string | null
}

export interface PatientRecordGeneticVariantFormModel extends PatientRecordGeneticVariantItem {
  localId: string
}

export interface PatientRecordPathologyFormModel extends Omit<PatientRecordPathology, 'reportedAt'> {
  reportedAt: Date | string | null
}

export interface PatientRecordGeneticSequencingFormModel
  extends Omit<PatientRecordGeneticSequencing, 'reportDate' | 'variants'> {
  reportDate: Date | string | null
  variants: PatientRecordGeneticVariantFormModel[]
}

export interface PatientRecordFormModel {
  patientNo: string
  name: string
  gender: string
  age: number | null
  visitDate: Date | string | null
  phone: string
  idCard: string
  occupation: string
  currentAddress: string
  nativePlace: string
  department: string
  encounterType: EncounterType | ''
  consanguinity: boolean
  chiefComplaint: string
  presentIllness: string
  history: PatientRecordHistoryFormModel
  physicalExam: PatientRecordPhysicalExamFormModel
  laboratoryScreening: PatientRecordLaboratoryScreening
  imagingReports: PatientRecordImagingReportFormModel[]
  pathology: PatientRecordPathologyFormModel
  geneticSequencing: PatientRecordGeneticSequencingFormModel
  clinicalDecision: PatientRecordClinicalDecision
}

const createLocalId = (prefix: string): string =>
  `${prefix}-${Date.now()}-${Math.random().toString(36).slice(2, 8)}`

const createInitialDiseaseHistory = (): PatientRecordDiseaseHistoryFormModel => ({
  smokingHistory: '',
  drinkingHistory: '',
  diabetesHistory: '',
  hypertensionHistory: '',
  hyperuricemiaHistory: '',
  hyperlipidemiaHistory: '',
  coronaryHeartDiseaseHistory: '',
  hepatitisBHistory: ''
})

const createInitialConditionalHistory = (): PatientRecordConditionalHistoryFormModel => ({
  status: '',
  detail: ''
})

const createInitialHistory = (): PatientRecordHistoryFormModel => ({
  diseaseHistory: createInitialDiseaseHistory(),
  surgeryHistory: createInitialConditionalHistory(),
  transfusionHistory: createInitialConditionalHistory(),
  allergyHistory: '',
  medicationHistory: '',
  familyHistory: ''
})

const createInitialPhysicalExam = (): PatientRecordPhysicalExamFormModel => ({
  heightCm: null,
  weightKg: null,
  bmi: null,
  bloodPressureSystolic: null,
  bloodPressureDiastolic: null,
  respiratoryRate: null,
  heartRate: null,
  liverFibrosis: '',
  cirrhosis: '',
  fattyLiver: '',
  liverFailure: '',
  cholestasis: '',
  viralHepatitis: ''
})

const createInitialImagingReport = (
  modality: PatientRecordImagingModality = 'CT',
  overrides: Partial<PatientRecordImagingReportFormModel> = {}
): PatientRecordImagingReportFormModel => ({
  localId: createLocalId('img'),
  modality,
  reportText: '',
  examinedAt: null,
  fileId: null,
  sourceType: 'MANUAL',
  ...overrides
})

const createInitialPathology = (): PatientRecordPathologyFormModel => ({
  performed: false,
  reportText: '',
  nasScore: null,
  reportedAt: null,
  fileId: null,
  sourceType: 'MANUAL'
})

const createInitialGeneticVariant = (
  overrides: Partial<PatientRecordGeneticVariantFormModel> = {}
): PatientRecordGeneticVariantFormModel => ({
  localId: createLocalId('variant'),
  gene: '',
  hgvsC: '',
  hgvsP: '',
  variantType: '',
  zygosity: '',
  classification: '',
  evidence: '',
  ...overrides
})

const createInitialGeneticSequencing = (): PatientRecordGeneticSequencingFormModel => ({
  tested: false,
  method: '',
  reportSource: '',
  reportDate: null,
  summary: '',
  conclusion: '',
  fileId: null,
  sourceType: 'MANUAL',
  variants: []
})

const createInitialClinicalDecision = (): PatientRecordClinicalDecision => ({
  diagnosis: '',
  treatmentPlan: ''
})

const createInitialFormData = (): PatientRecordFormModel => ({
  patientNo: '',
  name: '',
  gender: '',
  age: null,
  visitDate: new Date(),
  phone: '',
  idCard: '',
  occupation: '',
  currentAddress: '',
  nativePlace: '',
  department: '',
  encounterType: '',
  consanguinity: false,
  chiefComplaint: '',
  presentIllness: '',
  history: createInitialHistory(),
  physicalExam: createInitialPhysicalExam(),
  laboratoryScreening: createInitialLaboratoryScreening(),
  imagingReports: [],
  pathology: createInitialPathology(),
  geneticSequencing: createInitialGeneticSequencing(),
  clinicalDecision: createInitialClinicalDecision()
})

const createInitialImportMeta = (): PatientRecordImportMeta => ({
  sourceType: '',
  traceId: '',
  confidence: null,
  importedAt: ''
})

const BASIC_TAB_FIELD_PREFIXES = [
  'patientNo',
  'name',
  'gender',
  'age',
  'visitDate',
  'occupation',
  'currentAddress',
  'nativePlace',
  'department',
  'encounterType'
] as const

const isRecord = (value: unknown): value is Record<string, unknown> =>
  typeof value === 'object' && value !== null && !Array.isArray(value)

const isTernaryFlag = (value: unknown): value is TernaryFlag =>
  typeof value === 'string' && TRI_STATE_VALUES.includes(value as TernaryFlag)

const normalizeText = (value: unknown): string => (typeof value === 'string' ? value.trim() : '')

const normalizeNullableText = (value: unknown): string | null => {
  const text = normalizeText(value)
  return text ? text : null
}

const normalizeNumber = (value: unknown): number | null => {
  if (typeof value === 'number' && Number.isFinite(value)) {
    return value
  }

  if (typeof value === 'string' && value.trim()) {
    const parsed = Number.parseFloat(value)
    return Number.isFinite(parsed) ? parsed : null
  }

  return null
}

const normalizeNasScore = (value: unknown): number | null => {
  const parsed = normalizeNumber(value)
  if (parsed === null) {
    return null
  }
  return Math.max(0, Math.min(8, Math.round(parsed)))
}

const normalizeTriStateFormValue = (value: unknown): TriStateFormValue => {
  if (isTernaryFlag(value)) {
    return value
  }

  if (value === true) {
    return 'YES'
  }

  if (value === false) {
    return 'NO'
  }

  if (typeof value === 'string') {
    const trimmed = value.trim()
    if (trimmed === '有' || trimmed === '是' || trimmed === '阳性') {
      return 'YES'
    }
    if (trimmed === '无' || trimmed === '否' || trimmed === '阴性' || trimmed === '未见') {
      return 'NO'
    }
    if (trimmed === '未查') {
      return 'UNKNOWN'
    }
  }

  return ''
}

const normalizeEncounterType = (value: unknown): EncounterType | '' => {
  if (value === 'OUTPATIENT' || value === 'EMERGENCY' || value === 'INPATIENT') {
    return value
  }
  return ''
}

const normalizeVisitDate = (value: Date | string | null): string => {
  if (!value) {
    return ''
  }

  const parsed = new Date(value)
  if (Number.isNaN(parsed.getTime())) {
    return ''
  }

  return parsed.toISOString().slice(0, 10)
}

const normalizeOptionalDate = (value: Date | string | null | undefined): string | undefined => {
  const normalized = normalizeVisitDate(value ?? null)
  return normalized || undefined
}

const toFormVisitDate = (value: unknown): Date | string | null => {
  if (typeof value !== 'string' || !value.trim()) {
    return null
  }

  const parsed = new Date(value)
  return Number.isNaN(parsed.getTime()) ? value : parsed
}

const computeBmi = (heightCm: number | null, weightKg: number | null): number | null => {
  if (!heightCm || !weightKg || heightCm <= 0 || weightKg <= 0) {
    return null
  }

  const heightMeter = heightCm / 100
  const bmi = weightKg / (heightMeter * heightMeter)
  return Number.isFinite(bmi) ? Number(bmi.toFixed(1)) : null
}

const normalizeDiseaseHistoryDraft = (value: unknown): PatientRecordDiseaseHistoryFormModel => {
  const defaults = createInitialDiseaseHistory()
  if (!isRecord(value)) {
    return defaults
  }

  return {
    smokingHistory: normalizeTriStateFormValue(value.smokingHistory),
    drinkingHistory: normalizeTriStateFormValue(value.drinkingHistory),
    diabetesHistory: normalizeTriStateFormValue(value.diabetesHistory),
    hypertensionHistory: normalizeTriStateFormValue(value.hypertensionHistory),
    hyperuricemiaHistory: normalizeTriStateFormValue(value.hyperuricemiaHistory),
    hyperlipidemiaHistory: normalizeTriStateFormValue(value.hyperlipidemiaHistory),
    coronaryHeartDiseaseHistory: normalizeTriStateFormValue(value.coronaryHeartDiseaseHistory),
    hepatitisBHistory: normalizeTriStateFormValue(value.hepatitisBHistory)
  }
}

const normalizeConditionalHistoryDraft = (value: unknown): PatientRecordConditionalHistoryFormModel => {
  const defaults = createInitialConditionalHistory()
  if (!isRecord(value)) {
    return defaults
  }

  return {
    status: normalizeTriStateFormValue(value.status),
    detail: normalizeText(value.detail)
  }
}

const normalizeHistoryDraft = (value: unknown, legacyFamilyHistoryDetail?: unknown): PatientRecordHistoryFormModel => {
  const defaults = createInitialHistory()
  const source = isRecord(value) ? value : {}

  return {
    diseaseHistory: normalizeDiseaseHistoryDraft(source.diseaseHistory),
    surgeryHistory: normalizeConditionalHistoryDraft(source.surgeryHistory),
    transfusionHistory: normalizeConditionalHistoryDraft(source.transfusionHistory),
    allergyHistory: normalizeText(source.allergyHistory),
    medicationHistory: normalizeText(source.medicationHistory),
    familyHistory: normalizeText(source.familyHistory) || normalizeText(legacyFamilyHistoryDetail) || defaults.familyHistory
  }
}

const normalizePhysicalExamDraft = (value: unknown): PatientRecordPhysicalExamFormModel => {
  const defaults = createInitialPhysicalExam()
  if (!isRecord(value)) {
    return defaults
  }

  return {
    heightCm: normalizeNumber(value.heightCm),
    weightKg: normalizeNumber(value.weightKg),
    bmi: normalizeNumber(value.bmi),
    bloodPressureSystolic: normalizeNumber(value.bloodPressureSystolic),
    bloodPressureDiastolic: normalizeNumber(value.bloodPressureDiastolic),
    respiratoryRate: normalizeNumber(value.respiratoryRate),
    heartRate: normalizeNumber(value.heartRate),
    liverFibrosis: normalizeTriStateFormValue(value.liverFibrosis),
    cirrhosis: normalizeTriStateFormValue(value.cirrhosis),
    fattyLiver: normalizeTriStateFormValue(value.fattyLiver),
    liverFailure: normalizeTriStateFormValue(value.liverFailure),
    cholestasis: normalizeTriStateFormValue(value.cholestasis),
    viralHepatitis: normalizeTriStateFormValue(value.viralHepatitis)
  }
}

const normalizeImportMetaDraft = (value: unknown): PatientRecordImportMeta => {
  const defaults = createInitialImportMeta()
  if (!isRecord(value)) {
    return defaults
  }

  return {
    sourceType:
      value.sourceType === 'HIS_LIS' || value.sourceType === 'IMAGE_OCR' || value.sourceType === 'PDF_OCR'
        ? value.sourceType
        : defaults.sourceType,
    traceId: normalizeText(value.traceId),
    confidence: normalizeNumber(value.confidence),
    importedAt: normalizeText(value.importedAt)
  }
}

const normalizeConditionalHistoryPayload = (
  value: PatientRecordConditionalHistoryFormModel
): { status: TernaryFlag; detail: string } => ({
  status: value.status as TernaryFlag,
  detail: value.status === 'YES' ? normalizeText(value.detail) : ''
})

const normalizeImagingSourceType = (value: unknown): PatientRecordImagingSourceType =>
  IMAGING_SOURCE_TYPES.includes(value as PatientRecordImagingSourceType)
    ? (value as PatientRecordImagingSourceType)
    : 'MANUAL'

const normalizePathologySourceType = (value: unknown): PatientRecordPathologySourceType =>
  PATHOLOGY_SOURCE_TYPES.includes(value as PatientRecordPathologySourceType)
    ? (value as PatientRecordPathologySourceType)
    : 'MANUAL'

const normalizeGeneticSourceType = (value: unknown): PatientRecordGeneticSourceType =>
  GENETIC_SOURCE_TYPES.includes(value as PatientRecordGeneticSourceType)
    ? (value as PatientRecordGeneticSourceType)
    : 'MANUAL'

const normalizeGeneticMethod = (value: unknown): PatientRecordGeneticMethod => {
  if (typeof value !== 'string') {
    return ''
  }
  const normalized = value.trim().toUpperCase()
  return GENETIC_METHODS.includes(normalized as PatientRecordGeneticMethod)
    ? (normalized as PatientRecordGeneticMethod)
    : ''
}

const normalizeImagingModality = (value: unknown): PatientRecordImagingModality => {
  if (typeof value !== 'string') {
    return 'OTHER'
  }
  const normalized = value.trim().toUpperCase()
  if (normalized === 'US' || normalized === 'ULTRASOUND' || normalized === '超声') {
    return 'ULTRASOUND'
  }
  if (normalized === 'CT') {
    return 'CT'
  }
  if (normalized === 'MRI') {
    return 'MRI'
  }
  return IMAGING_MODALITIES.includes(normalized as PatientRecordImagingModality)
    ? (normalized as PatientRecordImagingModality)
    : 'OTHER'
}

const normalizeImagingReportDraft = (value: unknown): PatientRecordImagingReportFormModel => {
  if (!isRecord(value)) {
    return createInitialImagingReport('CT')
  }

  return createInitialImagingReport(normalizeImagingModality(value.modality), {
    reportText: normalizeText(value.reportText),
    examinedAt: toFormVisitDate(value.examinedAt),
    fileId: normalizeNullableText(value.fileId),
    sourceType: normalizeImagingSourceType(value.sourceType)
  })
}

const normalizeImagingReportsDraft = (
  value: unknown,
  legacyImagingResult?: unknown
): PatientRecordImagingReportFormModel[] => {
  const reports = Array.isArray(value)
    ? value.map((item) => normalizeImagingReportDraft(item))
    : []

  if (reports.length > 0) {
    return reports
  }

  const legacyText = normalizeText(legacyImagingResult)
  if (!legacyText) {
    return []
  }

  return [
    createInitialImagingReport('OTHER', {
      reportText: legacyText
    })
  ]
}

const normalizePathologyDraft = (
  value: unknown,
  legacyBiopsyResult?: unknown
): PatientRecordPathologyFormModel => {
  const defaults = createInitialPathology()
  if (!isRecord(value)) {
    const legacyText = normalizeText(legacyBiopsyResult)
    return {
      ...defaults,
      performed: Boolean(legacyText),
      reportText: legacyText
    }
  }

  const reportText = normalizeText(value.reportText) || normalizeText(legacyBiopsyResult)
  const performed =
    typeof value.performed === 'boolean'
      ? value.performed
      : Boolean(reportText)

  return {
    performed,
    reportText,
    nasScore: normalizeNasScore(value.nasScore),
    reportedAt: toFormVisitDate(value.reportedAt),
    fileId: normalizeNullableText(value.fileId),
    sourceType: normalizePathologySourceType(value.sourceType)
  }
}

const normalizeGeneticVariantDraft = (value: unknown): PatientRecordGeneticVariantFormModel => {
  if (!isRecord(value)) {
    return createInitialGeneticVariant()
  }

  return createInitialGeneticVariant({
    gene: normalizeText(value.gene),
    hgvsC: normalizeText(value.hgvsC),
    hgvsP: normalizeText(value.hgvsP),
    variantType: normalizeText(value.variantType),
    zygosity: normalizeText(value.zygosity),
    classification: normalizeText(value.classification),
    evidence: normalizeText(value.evidence)
  })
}

const normalizeGeneticSequencingDraft = (
  value: unknown,
  legacyGeneticTested?: unknown,
  legacyMutatedGene?: unknown
): PatientRecordGeneticSequencingFormModel => {
  const defaults = createInitialGeneticSequencing()
  if (!isRecord(value)) {
    const tested = typeof legacyGeneticTested === 'boolean' ? legacyGeneticTested : false
    const mutatedGene = normalizeText(legacyMutatedGene)
    return {
      ...defaults,
      tested,
      conclusion: mutatedGene,
      variants: mutatedGene ? [createInitialGeneticVariant({ gene: mutatedGene })] : []
    }
  }

  const variants = Array.isArray(value.variants)
    ? value.variants.map((item) => normalizeGeneticVariantDraft(item))
    : []
  const tested =
    typeof value.tested === 'boolean'
      ? value.tested
      : typeof legacyGeneticTested === 'boolean'
        ? legacyGeneticTested
        : variants.length > 0

  const legacyGene = normalizeText(legacyMutatedGene)
  return {
    tested,
    method: normalizeGeneticMethod(value.method),
    reportSource: normalizeText(value.reportSource),
    reportDate: toFormVisitDate(value.reportDate),
    summary: normalizeText(value.summary),
    conclusion: normalizeText(value.conclusion) || legacyGene,
    fileId: normalizeNullableText(value.fileId),
    sourceType: normalizeGeneticSourceType(value.sourceType),
    variants: variants.length > 0 ? variants : legacyGene ? [createInitialGeneticVariant({ gene: legacyGene })] : []
  }
}

const normalizeClinicalDecisionDraft = (
  value: unknown,
  legacyDiagnosis?: unknown,
  legacyTreatmentPlan?: unknown
): PatientRecordClinicalDecision => {
  if (!isRecord(value)) {
    return {
      diagnosis: normalizeText(legacyDiagnosis),
      treatmentPlan: normalizeText(legacyTreatmentPlan)
    }
  }

  return {
    diagnosis: normalizeText(value.diagnosis) || normalizeText(legacyDiagnosis),
    treatmentPlan: normalizeText(value.treatmentPlan) || normalizeText(legacyTreatmentPlan)
  }
}

const requiredTextRule = (message: string): FormItemRule[] => [
  {
    trigger: 'blur',
    validator: (_rule, value, callback) => {
      if (!normalizeText(value)) {
        callback(new Error(message))
        return
      }
      callback()
    }
  }
]

const requiredSelectRule = (message: string): FormItemRule[] => [{ required: true, message, trigger: 'change' }]

const createPositiveNumberRule = (message: string): FormItemRule[] => [
  {
    trigger: 'change',
    validator: (_rule, value, callback) => {
      if (typeof value !== 'number' || Number.isNaN(value) || value <= 0) {
        callback(new Error(message))
        return
      }
      callback()
    }
  }
]

const hasGeneticVariantContent = (item: PatientRecordGeneticVariantFormModel): boolean =>
  Boolean(
    normalizeText(item.gene) ||
      normalizeText(item.hgvsC) ||
      normalizeText(item.hgvsP) ||
      normalizeText(item.variantType) ||
      normalizeText(item.zygosity) ||
      normalizeText(item.classification) ||
      normalizeText(item.evidence)
  )

const hasImagingContent = (item: PatientRecordImagingReportFormModel): boolean =>
  Boolean(normalizeText(item.reportText) || normalizeOptionalDate(item.examinedAt) || item.fileId)

export interface PatientRecordImportMetaState {
  sourceType: ImportSourceType | ''
  traceId: string
  confidence: number | null
  importedAt: string
}

export const usePatientRecordPage = () => {
  const visitId = ref(`VISIT-${Date.now().toString().slice(-8)}`)
  const activeTab = ref('basic')
  const formRef = ref<FormInstance>()
  const submitting = ref(false)
  const formData = reactive<PatientRecordFormModel>(createInitialFormData())
  const importMeta = reactive<PatientRecordImportMetaState>(createInitialImportMeta())

  const rules: FormRules<PatientRecordFormModel> = {
    patientNo: requiredTextRule('病人ID号（院内病历号/患者号）不能为空'),
    name: requiredTextRule('患者姓名不能为空'),
    gender: requiredSelectRule('请选择性别'),
    age: createPositiveNumberRule('请输入年龄'),
    visitDate: requiredSelectRule('请选择就诊日期'),
    occupation: requiredTextRule('职业不能为空'),
    currentAddress: requiredTextRule('现住址不能为空'),
    nativePlace: requiredTextRule('籍贯不能为空'),
    department: requiredTextRule('科室不能为空'),
    encounterType: requiredSelectRule('请选择就诊方式'),
    chiefComplaint: requiredTextRule('主诉不能为空'),
    presentIllness: requiredTextRule('现病史不能为空'),
    'history.diseaseHistory.smokingHistory': requiredSelectRule('请选择吸烟史'),
    'history.diseaseHistory.drinkingHistory': requiredSelectRule('请选择饮酒史'),
    'history.diseaseHistory.diabetesHistory': requiredSelectRule('请选择糖尿病史'),
    'history.diseaseHistory.hypertensionHistory': requiredSelectRule('请选择高血压史'),
    'history.diseaseHistory.hyperuricemiaHistory': requiredSelectRule('请选择高尿酸血症史'),
    'history.diseaseHistory.hyperlipidemiaHistory': requiredSelectRule('请选择高脂血症史'),
    'history.diseaseHistory.coronaryHeartDiseaseHistory': requiredSelectRule('请选择冠心病史'),
    'history.diseaseHistory.hepatitisBHistory': requiredSelectRule('请选择乙肝病史'),
    'history.surgeryHistory.status': requiredSelectRule('请选择手术史'),
    'history.surgeryHistory.detail': [
      {
        trigger: 'blur',
        validator: (_rule, value, callback) => {
          if (formData.history.surgeryHistory.status === 'YES' && !normalizeText(value)) {
            callback(new Error('手术史为“有”时必须填写明细'))
            return
          }
          callback()
        }
      }
    ],
    'history.transfusionHistory.status': requiredSelectRule('请选择输血史'),
    'history.transfusionHistory.detail': [
      {
        trigger: 'blur',
        validator: (_rule, value, callback) => {
          if (formData.history.transfusionHistory.status === 'YES' && !normalizeText(value)) {
            callback(new Error('输血史为“有”时必须填写明细'))
            return
          }
          callback()
        }
      }
    ],
    'history.allergyHistory': requiredTextRule('过敏史不能为空'),
    'history.medicationHistory': requiredTextRule('用药史不能为空'),
    'history.familyHistory': requiredTextRule('家族史不能为空'),
    'physicalExam.heightCm': createPositiveNumberRule('请输入身高'),
    'physicalExam.weightKg': createPositiveNumberRule('请输入体重'),
    'physicalExam.bmi': [
      {
        trigger: 'change',
        validator: (_rule, value, callback) => {
          if (typeof value !== 'number' || Number.isNaN(value) || value <= 0) {
            callback(new Error('BMI 无法计算，请检查身高和体重'))
            return
          }
          callback()
        }
      }
    ],
    'physicalExam.bloodPressureSystolic': createPositiveNumberRule('请输入收缩压'),
    'physicalExam.bloodPressureDiastolic': createPositiveNumberRule('请输入舒张压'),
    'physicalExam.respiratoryRate': createPositiveNumberRule('请输入呼吸频率'),
    'physicalExam.heartRate': createPositiveNumberRule('请输入心率'),
    'physicalExam.liverFibrosis': requiredSelectRule('请选择肝纤维化情况'),
    'physicalExam.cirrhosis': requiredSelectRule('请选择肝硬化情况'),
    'physicalExam.fattyLiver': requiredSelectRule('请选择脂肪肝情况'),
    'physicalExam.liverFailure': requiredSelectRule('请选择肝衰竭情况'),
    'physicalExam.cholestasis': requiredSelectRule('请选择胆汁淤积情况'),
    'physicalExam.viralHepatitis': requiredSelectRule('请选择病毒性肝炎情况'),
    'pathology.reportText': [
      {
        trigger: 'blur',
        validator: (_rule, value, callback) => {
          if (formData.pathology.performed && !normalizeText(value)) {
            callback(new Error('已执行肝穿刺活检时必须填写病理结果'))
            return
          }
          callback()
        }
      }
    ],
    'geneticSequencing.method': [
      {
        trigger: 'change',
        validator: (_rule, value, callback) => {
          if (formData.geneticSequencing.tested && !normalizeGeneticMethod(value)) {
            callback(new Error('已进行基因检测时必须选择检测方法'))
            return
          }
          callback()
        }
      }
    ],
    'clinicalDecision.diagnosis': requiredTextRule('请输入初步诊断')
  }

  const applyFormData = (nextFormData: PatientRecordFormModel): void => {
    Object.assign(formData, nextFormData)
  }

  const serializeDraft = (): Record<string, unknown> => ({
    ...formData,
    visitDate: normalizeVisitDate(formData.visitDate),
    imagingReports: formData.imagingReports.map((item) => ({
      ...item,
      examinedAt: normalizeOptionalDate(item.examinedAt)
    })),
    pathology: {
      ...formData.pathology,
      reportedAt: normalizeOptionalDate(formData.pathology.reportedAt)
    },
    geneticSequencing: {
      ...formData.geneticSequencing,
      reportDate: normalizeOptionalDate(formData.geneticSequencing.reportDate)
    },
    visitId: visitId.value,
    importMeta: {
      ...importMeta
    }
  })

  const normalizePayload = (): PatientRecordPayload => {
    const imagingReports = formData.imagingReports
      .filter((item) => hasImagingContent(item))
      .map<PatientRecordImagingReportItem>((item) => ({
        modality: item.modality,
        reportText: normalizeText(item.reportText),
        examinedAt: normalizeOptionalDate(item.examinedAt),
        fileId: item.fileId,
        sourceType: item.sourceType
      }))

    const pathology: PatientRecordPathology = {
      performed: formData.pathology.performed,
      reportText: formData.pathology.performed ? normalizeText(formData.pathology.reportText) : '',
      nasScore: formData.pathology.performed ? normalizeNasScore(formData.pathology.nasScore) : null,
      reportedAt: formData.pathology.performed ? normalizeOptionalDate(formData.pathology.reportedAt) : undefined,
      fileId: formData.pathology.performed ? formData.pathology.fileId : null,
      sourceType: formData.pathology.sourceType
    }

    const geneticVariants = formData.geneticSequencing.variants
      .filter((item) => hasGeneticVariantContent(item))
      .map<PatientRecordGeneticVariantItem>((item) => ({
        gene: normalizeText(item.gene),
        hgvsC: normalizeText(item.hgvsC) || undefined,
        hgvsP: normalizeText(item.hgvsP) || undefined,
        variantType: normalizeText(item.variantType) || undefined,
        zygosity: normalizeText(item.zygosity) || undefined,
        classification: normalizeText(item.classification) || undefined,
        evidence: normalizeText(item.evidence) || undefined
      }))

    const geneticSequencing: PatientRecordGeneticSequencing = formData.geneticSequencing.tested
      ? {
          tested: true,
          method: normalizeGeneticMethod(formData.geneticSequencing.method),
          reportSource: normalizeText(formData.geneticSequencing.reportSource),
          reportDate: normalizeOptionalDate(formData.geneticSequencing.reportDate),
          summary: normalizeText(formData.geneticSequencing.summary),
          conclusion: normalizeText(formData.geneticSequencing.conclusion),
          fileId: formData.geneticSequencing.fileId,
          sourceType: formData.geneticSequencing.sourceType,
          variants: geneticVariants
        }
      : {
          tested: false,
          method: '',
          reportSource: '',
          summary: '',
          conclusion: '',
          fileId: null,
          sourceType: 'MANUAL',
          variants: []
        }

    return {
      patientNo: normalizeText(formData.patientNo),
      name: normalizeText(formData.name),
      gender: normalizeText(formData.gender),
      age: formData.age,
      visitDate: normalizeVisitDate(formData.visitDate),
      phone: normalizeText(formData.phone),
      idCard: normalizeText(formData.idCard),
      occupation: normalizeText(formData.occupation),
      currentAddress: normalizeText(formData.currentAddress),
      nativePlace: normalizeText(formData.nativePlace),
      department: normalizeText(formData.department),
      encounterType: formData.encounterType as EncounterType,
      consanguinity: formData.consanguinity,
      chiefComplaint: normalizeText(formData.chiefComplaint),
      presentIllness: normalizeText(formData.presentIllness),
      history: {
        diseaseHistory: {
          smokingHistory: formData.history.diseaseHistory.smokingHistory as TernaryFlag,
          drinkingHistory: formData.history.diseaseHistory.drinkingHistory as TernaryFlag,
          diabetesHistory: formData.history.diseaseHistory.diabetesHistory as TernaryFlag,
          hypertensionHistory: formData.history.diseaseHistory.hypertensionHistory as TernaryFlag,
          hyperuricemiaHistory: formData.history.diseaseHistory.hyperuricemiaHistory as TernaryFlag,
          hyperlipidemiaHistory: formData.history.diseaseHistory.hyperlipidemiaHistory as TernaryFlag,
          coronaryHeartDiseaseHistory: formData.history.diseaseHistory.coronaryHeartDiseaseHistory as TernaryFlag,
          hepatitisBHistory: formData.history.diseaseHistory.hepatitisBHistory as TernaryFlag
        },
        surgeryHistory: normalizeConditionalHistoryPayload(formData.history.surgeryHistory),
        transfusionHistory: normalizeConditionalHistoryPayload(formData.history.transfusionHistory),
        allergyHistory: normalizeText(formData.history.allergyHistory),
        medicationHistory: normalizeText(formData.history.medicationHistory),
        familyHistory: normalizeText(formData.history.familyHistory)
      },
      physicalExam: {
        heightCm: formData.physicalExam.heightCm,
        weightKg: formData.physicalExam.weightKg,
        bmi: computeBmi(formData.physicalExam.heightCm, formData.physicalExam.weightKg),
        bloodPressureSystolic: formData.physicalExam.bloodPressureSystolic,
        bloodPressureDiastolic: formData.physicalExam.bloodPressureDiastolic,
        respiratoryRate: formData.physicalExam.respiratoryRate,
        heartRate: formData.physicalExam.heartRate,
        liverFibrosis: formData.physicalExam.liverFibrosis as TernaryFlag,
        cirrhosis: formData.physicalExam.cirrhosis as TernaryFlag,
        fattyLiver: formData.physicalExam.fattyLiver as TernaryFlag,
        liverFailure: formData.physicalExam.liverFailure as TernaryFlag,
        cholestasis: formData.physicalExam.cholestasis as TernaryFlag,
        viralHepatitis: formData.physicalExam.viralHepatitis as TernaryFlag
      },
      laboratoryScreening: normalizeLaboratoryScreening(formData.laboratoryScreening),
      imagingReports,
      pathology,
      geneticSequencing,
      clinicalDecision: {
        diagnosis: normalizeText(formData.clinicalDecision.diagnosis),
        treatmentPlan: normalizeText(formData.clinicalDecision.treatmentPlan)
      },
      visitId: visitId.value,
      importMeta: {
        ...importMeta
      }
    }
  }

  const restoreDraftIfNeeded = (): void => {
    const raw = localStorage.getItem(DRAFT_STORAGE_KEY)
    if (!raw) {
      return
    }

    try {
      const parsed = JSON.parse(raw) as Record<string, unknown>
      const nextFormData = createInitialFormData()

      nextFormData.patientNo = normalizeText(parsed.patientNo)
      nextFormData.name = normalizeText(parsed.name)
      nextFormData.gender = normalizeText(parsed.gender)
      nextFormData.age = normalizeNumber(parsed.age)
      nextFormData.visitDate = toFormVisitDate(parsed.visitDate) ?? nextFormData.visitDate
      nextFormData.phone = normalizeText(parsed.phone)
      nextFormData.idCard = normalizeText(parsed.idCard)
      nextFormData.occupation = normalizeText(parsed.occupation)
      nextFormData.currentAddress = normalizeText(parsed.currentAddress)
      nextFormData.nativePlace = normalizeText(parsed.nativePlace)
      nextFormData.department = normalizeText(parsed.department)
      nextFormData.encounterType = normalizeEncounterType(parsed.encounterType)
      nextFormData.consanguinity =
        typeof parsed.consanguinity === 'boolean' ? parsed.consanguinity : nextFormData.consanguinity
      nextFormData.chiefComplaint = normalizeText(parsed.chiefComplaint)
      nextFormData.presentIllness = normalizeText(parsed.presentIllness)
      nextFormData.history = normalizeHistoryDraft(parsed.history, parsed.familyHistoryDetail)
      nextFormData.physicalExam = normalizePhysicalExamDraft(parsed.physicalExam)
      nextFormData.laboratoryScreening = normalizeLaboratoryScreening(parsed.laboratoryScreening, parsed)
      nextFormData.imagingReports = normalizeImagingReportsDraft(parsed.imagingReports, parsed.imagingResult)
      nextFormData.pathology = normalizePathologyDraft(parsed.pathology, parsed.biopsyResult)
      nextFormData.geneticSequencing = normalizeGeneticSequencingDraft(
        parsed.geneticSequencing,
        parsed.geneticTested,
        parsed.mutatedGene
      )
      nextFormData.clinicalDecision = normalizeClinicalDecisionDraft(
        parsed.clinicalDecision,
        parsed.diagnosis,
        parsed.treatmentPlan
      )

      applyFormData(nextFormData)

      if (typeof parsed.visitId === 'string' && parsed.visitId.trim()) {
        visitId.value = parsed.visitId.trim()
      }

      Object.assign(importMeta, normalizeImportMetaDraft(parsed.importMeta))
    } catch {
      localStorage.removeItem(DRAFT_STORAGE_KEY)
    }
  }

  restoreDraftIfNeeded()

  watch(
    () => [formData.physicalExam.heightCm, formData.physicalExam.weightKg],
    ([heightCm, weightKg]) => {
      formData.physicalExam.bmi = computeBmi(heightCm, weightKg)
    },
    { immediate: true }
  )

  const applyImportPreview = (preview: PatientImportPreview): void => {
    formData.patientNo = preview.patientNo || formData.patientNo
    formData.name = preview.name || formData.name
    formData.gender = preview.gender || formData.gender
    formData.age = typeof preview.age === 'number' ? preview.age : formData.age
    formData.visitDate = toFormVisitDate(preview.visitDate) ?? formData.visitDate
    formData.phone = preview.phone || formData.phone
    formData.idCard = preview.idCard || formData.idCard
    formData.occupation = preview.occupation || formData.occupation
    formData.currentAddress = preview.currentAddress || formData.currentAddress
    formData.nativePlace = preview.nativePlace || formData.nativePlace
    formData.department = preview.department || formData.department
    formData.encounterType = (preview.encounterType as EncounterType) || formData.encounterType

    importMeta.sourceType = preview.sourceType
    importMeta.traceId = preview.traceId
    importMeta.confidence = preview.confidence
    importMeta.importedAt = new Date().toISOString()
  }

  const clearImportMeta = (): void => {
    Object.assign(importMeta, createInitialImportMeta())
  }

  const addImagingReport = (modality: PatientRecordImagingModality = 'CT'): void => {
    formData.imagingReports.push(createInitialImagingReport(modality))
  }

  const removeImagingReport = (localId: string): void => {
    formData.imagingReports = formData.imagingReports.filter((item) => item.localId !== localId)
  }

  const addGeneticVariant = (): void => {
    formData.geneticSequencing.variants.push(createInitialGeneticVariant())
  }

  const removeGeneticVariant = (localId: string): void => {
    formData.geneticSequencing.variants = formData.geneticSequencing.variants.filter((item) => item.localId !== localId)
  }

  const saveDraft = (): void => {
    localStorage.setItem(DRAFT_STORAGE_KEY, JSON.stringify(serializeDraft()))
    ElMessage({
      message: '草稿已保存至本地缓存',
      type: 'success'
    })
  }

  const resolveTabByInvalidField = (field: string): string => {
    if (BASIC_TAB_FIELD_PREFIXES.some((item) => field === item || field.startsWith(`${item}.`))) {
      return 'basic'
    }

    if (
      field === 'chiefComplaint' ||
      field === 'presentIllness' ||
      field.startsWith('history.') ||
      field.startsWith('physicalExam.')
    ) {
      return 'clinical'
    }

    if (field.startsWith('laboratoryScreening.')) {
      return 'laboratory'
    }

    if (field.startsWith('imagingReports.')) {
      return 'imaging'
    }

    if (field.startsWith('pathology.')) {
      return 'pathology'
    }

    if (field.startsWith('geneticSequencing.')) {
      return 'genetic'
    }

    if (field.startsWith('clinicalDecision.')) {
      return 'clinicalDecision'
    }

    return 'basic'
  }

  const submitForm = async (): Promise<void> => {
    if (!formRef.value || submitting.value) {
      return
    }

    try {
      await formRef.value.validate()
    } catch (invalidFields) {
      const firstInvalidField = Object.keys((invalidFields as Record<string, unknown>) || {})[0] || 'patientNo'
      activeTab.value = resolveTabByInvalidField(firstInvalidField)
      await nextTick()
      formRef.value.scrollToField(firstInvalidField)
      ElMessage.error('基础信息或必填项未完善，请检查红框字段')
      return
    }

    try {
      await ElMessageBox.confirm('确认核对无误并归档该患者病历吗？', '系统提示', {
        confirmButtonText: '确认提交',
        cancelButtonText: '返回修改',
        type: 'warning'
      })
    } catch {
      return
    }

    submitting.value = true
    try {
      const res = await patientApi.createRecord(normalizePayload())
      visitId.value = res.data.visitId || visitId.value
      ElMessage({
        type: 'success',
        message: '病历归档成功，已同步至 AI 辅助诊断中台',
        duration: 2800
      })
      localStorage.removeItem(DRAFT_STORAGE_KEY)
    } catch {
      ElMessage.error('病历归档失败，请稍后重试')
    } finally {
      submitting.value = false
    }
  }

  const resetForm = (): void => {
    ElMessageBox.confirm('清空后当前录入的所有数据将丢失，是否继续？', '警告', {
      confirmButtonText: '确认清空',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        applyFormData(createInitialFormData())
        activeTab.value = 'basic'
        formRef.value?.clearValidate()
        clearImportMeta()
        localStorage.removeItem(DRAFT_STORAGE_KEY)
        ElMessage.info('表单已重置')
      })
      .catch(() => {
        // Ignore cancel action.
      })
  }

  return {
    visitId,
    activeTab,
    formRef,
    submitting,
    formData,
    importMeta,
    rules,
    saveDraft,
    submitForm,
    resetForm,
    applyImportPreview,
    clearImportMeta,
    addImagingReport,
    removeImagingReport,
    addGeneticVariant,
    removeGeneticVariant
  }
}
