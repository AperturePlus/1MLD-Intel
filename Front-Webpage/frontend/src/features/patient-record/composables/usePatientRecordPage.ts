import { reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import patientApi from '@/api/patient'
import type {
  EncounterType,
  ImportSourceType,
  PatientImportPreview,
  PatientRecordPayload
} from '@/api/types'

const DRAFT_STORAGE_KEY = 'imld_patient_record_draft'

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
  familyHistory: boolean
  familyHistoryDetail: string
  chiefComplaint: string
  presentIllness: string
  jaundice: string
  hepatomegaly: boolean
  splenomegaly: boolean
  kfRing: string
  neuroSymptoms: string[]
  alt: string
  ast: string
  tbil: string
  ceruloplasmin: string
  urineCopper: string
  ferritin: string
  transferrinSat: string
  imagingResult: string
  biopsyResult: string
  geneticTested: boolean
  mutatedGene: string
  diagnosis: string
  treatmentPlan: string
}

export interface PatientRecordImportMeta {
  sourceType: ImportSourceType | ''
  traceId: string
  confidence: number | null
  importedAt: string
}

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
  familyHistory: false,
  familyHistoryDetail: '',
  chiefComplaint: '',
  presentIllness: '',
  jaundice: '无',
  hepatomegaly: false,
  splenomegaly: false,
  kfRing: '未查',
  neuroSymptoms: [],
  alt: '',
  ast: '',
  tbil: '',
  ceruloplasmin: '',
  urineCopper: '',
  ferritin: '',
  transferrinSat: '',
  imagingResult: '',
  biopsyResult: '',
  geneticTested: false,
  mutatedGene: '',
  diagnosis: '',
  treatmentPlan: ''
})

const createInitialImportMeta = (): PatientRecordImportMeta => ({
  sourceType: '',
  traceId: '',
  confidence: null,
  importedAt: ''
})

const rules: FormRules<PatientRecordFormModel> = {
  patientNo: [{ required: true, message: '病人ID号（院内病历号/患者号）不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '患者姓名不能为空', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  visitDate: [{ required: true, message: '请选择就诊日期', trigger: 'change' }],
  occupation: [{ required: true, message: '职业不能为空', trigger: 'blur' }],
  currentAddress: [{ required: true, message: '现住址不能为空', trigger: 'blur' }],
  nativePlace: [{ required: true, message: '籍贯不能为空', trigger: 'blur' }],
  department: [{ required: true, message: '科室不能为空', trigger: 'blur' }],
  encounterType: [{ required: true, message: '请选择就诊方式', trigger: 'change' }],
  chiefComplaint: [{ required: true, message: '主诉不能为空', trigger: 'blur' }],
  diagnosis: [{ required: true, message: '请输入初步诊断', trigger: 'blur' }]
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

export const usePatientRecordPage = () => {
  const visitId = ref(`VISIT-${Date.now().toString().slice(-8)}`)
  const activeTab = ref('basic')
  const formRef = ref<FormInstance>()
  const submitting = ref(false)
  const formData = reactive<PatientRecordFormModel>(createInitialFormData())
  const importMeta = reactive<PatientRecordImportMeta>(createInitialImportMeta())

  const normalizePayload = (): PatientRecordPayload => ({
    ...formData,
    encounterType: formData.encounterType as EncounterType,
    visitId: visitId.value,
    visitDate: normalizeVisitDate(formData.visitDate),
    neuroSymptoms: [...formData.neuroSymptoms],
    importMeta: {
      ...importMeta
    }
  })

  const restoreDraftIfNeeded = (): void => {
    const raw = localStorage.getItem(DRAFT_STORAGE_KEY)
    if (!raw) {
      return
    }

    try {
      const parsed = JSON.parse(raw) as Partial<PatientRecordPayload> & {
        importMeta?: Partial<PatientRecordImportMeta>
      }
      const nextDefaults = createInitialFormData()

      const mutableFormData = formData as Record<string, unknown>
      ;(Object.keys(nextDefaults) as Array<keyof PatientRecordFormModel>).forEach((key) => {
        const incoming = parsed[key] as PatientRecordFormModel[typeof key] | undefined
        if (incoming !== undefined && incoming !== null) {
          mutableFormData[key] = incoming
        }
      })

      if (typeof parsed.visitDate === 'string' && parsed.visitDate) {
        formData.visitDate = parsed.visitDate
      }

      if (typeof parsed.visitId === 'string' && parsed.visitId.trim()) {
        visitId.value = parsed.visitId.trim()
      }

      if (parsed.importMeta) {
        const nextImportMeta = createInitialImportMeta()
        importMeta.sourceType = parsed.importMeta.sourceType || nextImportMeta.sourceType
        importMeta.traceId = parsed.importMeta.traceId || nextImportMeta.traceId
        importMeta.confidence =
          typeof parsed.importMeta.confidence === 'number' ? parsed.importMeta.confidence : nextImportMeta.confidence
        importMeta.importedAt = parsed.importMeta.importedAt || nextImportMeta.importedAt
      }
    } catch {
      localStorage.removeItem(DRAFT_STORAGE_KEY)
    }
  }

  restoreDraftIfNeeded()

  const applyImportPreview = (preview: PatientImportPreview): void => {
    formData.patientNo = preview.patientNo || formData.patientNo
    formData.name = preview.name || formData.name
    formData.gender = preview.gender || formData.gender
    formData.age = typeof preview.age === 'number' ? preview.age : formData.age
    formData.visitDate = preview.visitDate || formData.visitDate
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

  const saveDraft = (): void => {
    localStorage.setItem(DRAFT_STORAGE_KEY, JSON.stringify(normalizePayload()))
    ElMessage({
      message: '草稿已保存至本地缓存',
      type: 'success'
    })
  }

  const submitForm = (): void => {
    if (!formRef.value || submitting.value) {
      return
    }

    formRef.value.validate(async (valid: boolean) => {
      if (!valid) {
        ElMessage.error('基础信息或必填项未完善，请检查红框字段')
        activeTab.value = 'basic'
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
    })
  }

  const resetForm = (): void => {
    ElMessageBox.confirm('清空后当前录入的所有数据将丢失，是否继续？', '警告', {
      confirmButtonText: '确认清空',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        formRef.value?.resetFields()
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
    clearImportMeta
  }
}
