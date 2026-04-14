import type { PatientRecordLaboratoryScreening } from '@/types/patient'

export type LaboratoryFieldInputType = 'text' | 'select'

export interface LaboratoryFieldOption {
  label: string
  value: string
}

export interface LaboratoryFieldConfig {
  key: string
  label: string
  unit?: string
  inputType?: LaboratoryFieldInputType
  options?: readonly LaboratoryFieldOption[]
}

export interface LaboratorySectionConfig {
  key: string
  title: string
  items: readonly LaboratoryFieldConfig[]
  emptyState?: string
}

export interface LaboratoryGroupConfig {
  key: keyof PatientRecordLaboratoryScreening
  title: string
  sections: readonly LaboratorySectionConfig[]
}

export const ANA_OPTIONS = [
  { label: '阴性', value: '阴性' },
  { label: '阳性', value: '阳性' }
] as const satisfies readonly LaboratoryFieldOption[]

const liverFunctionFields = [
  { key: 'tbil', label: 'TBIL', unit: 'μmol/L' },
  { key: 'dbil', label: 'DBIL', unit: 'μmol/L' },
  { key: 'ibil', label: 'IBIL', unit: 'μmol/L' },
  { key: 'alt', label: 'ALT', unit: 'U/L' },
  { key: 'ast', label: 'AST', unit: 'U/L' },
  { key: 'astAltRatio', label: 'AST/ALT' },
  { key: 'tp', label: 'TP', unit: 'g/L' },
  { key: 'alb', label: 'ALB', unit: 'g/L' },
  { key: 'glob', label: 'GLB', unit: 'g/L' },
  { key: 'albGlobRatio', label: 'ALB/GLB' },
  { key: 'glu', label: 'GLU', unit: 'mmol/L' },
  { key: 'tg', label: 'TG', unit: 'mmol/L' },
  { key: 'chol', label: 'CHOL', unit: 'mmol/L' },
  { key: 'hdlC', label: 'HDL-C', unit: 'mmol/L' },
  { key: 'ldlC', label: 'LDL-C', unit: 'mmol/L' },
  { key: 'alp', label: 'ALP', unit: 'U/L' },
  { key: 'ggt', label: 'GGT', unit: 'U/L' },
  { key: 'ck', label: 'CK', unit: 'U/L' },
  { key: 'ldh', label: 'LDH', unit: 'U/L' },
  { key: 'hbdh', label: 'HBDH', unit: 'U/L' },
  { key: 'tba', label: 'TBA', unit: 'μmol/L' },
  { key: 'nh3', label: 'NH3', unit: 'μmol/L' }
] as const satisfies readonly LaboratoryFieldConfig[]

const renalFunctionFields = [
  { key: 'urea', label: 'UREA', unit: 'mmol/L' },
  { key: 'crea', label: 'CREA', unit: 'μmol/L' },
  { key: 'eGfr', label: 'eGFR', unit: 'mL/min/1.73m^2' },
  { key: 'cysC', label: 'Cys-C', unit: 'mg/L' },
  { key: 'uric', label: 'URIC', unit: 'μmol/L' }
] as const satisfies readonly LaboratoryFieldConfig[]

const metabolismFields = [
  { key: 'cer', label: 'CER', unit: 'mg/L' },
  { key: 'aat', label: 'AAT', unit: 'mg/L' }
] as const satisfies readonly LaboratoryFieldConfig[]

const tumorMarkerFields = [
  { key: 'afp', label: 'AFP', unit: 'ng/mL' },
  { key: 'cea', label: 'CEA', unit: 'ng/mL' },
  { key: 'ca19_9', label: 'CA19-9', unit: 'ng/mL' }
] as const satisfies readonly LaboratoryFieldConfig[]

const inflammationFields = [
  { key: 'crp', label: 'CRP', unit: 'mg/L' },
  { key: 'pct', label: 'PCT', unit: 'ng/mL' }
] as const satisfies readonly LaboratoryFieldConfig[]

const bloodRoutineFields = [
  { key: 'rbc', label: 'RBC', unit: '10^12/L' },
  { key: 'hgb', label: 'HGB', unit: 'g/L' },
  { key: 'hct', label: 'HCT' },
  { key: 'mcv', label: 'MCV', unit: 'fL' },
  { key: 'mch', label: 'MCH', unit: 'pg' },
  { key: 'mchc', label: 'MCHC', unit: 'g/L' },
  { key: 'plt', label: 'PLT', unit: '10^9/L' },
  { key: 'wbc', label: 'WBC', unit: '10^9/L' },
  { key: 'neutPercent', label: 'NEUT', unit: '%' },
  { key: 'lymphPercent', label: 'LYMPH', unit: '%' },
  { key: 'monoPercent', label: 'MONO', unit: '%' },
  { key: 'eoPercent', label: 'EO', unit: '%' },
  { key: 'basoPercent', label: 'BASO', unit: '%' }
] as const satisfies readonly LaboratoryFieldConfig[]

const reticulocyteFields = [
  { key: 'retAbsolute', label: 'RET', unit: '10^12/L' },
  { key: 'retPermillage', label: 'RET', unit: '‰' },
  { key: 'lfrPercent', label: 'LFR', unit: '%' },
  { key: 'mfrPercent', label: 'MFR', unit: '%' },
  { key: 'hfrPercent', label: 'HFR', unit: '%' }
] as const satisfies readonly LaboratoryFieldConfig[]

const coagulationFields = [
  { key: 'pivka', label: 'PIVKA', unit: 'mAU/mL' },
  { key: 'pt', label: 'PT', unit: 's' },
  { key: 'inr', label: 'INR' },
  { key: 'aptt', label: 'APTT', unit: 's' },
  { key: 'tt', label: 'TT', unit: 's' },
  { key: 'fdp', label: 'FDP', unit: 'g/L' }
] as const satisfies readonly LaboratoryFieldConfig[]

const antibodyFields = [
  { key: 'igg', label: 'IgG', unit: 'g/L' },
  { key: 'iga', label: 'IgA', unit: 'g/L' },
  { key: 'igm', label: 'IgM', unit: 'g/L' },
  { key: 'igg4', label: 'IgG4', unit: 'g/L' }
] as const satisfies readonly LaboratoryFieldConfig[]

const autoantibodyFields = [
  { key: 'ana', label: 'ANA', inputType: 'select', options: ANA_OPTIONS }
] as const satisfies readonly LaboratoryFieldConfig[]

export const LABORATORY_SCREENING_CONFIG = [
  {
    key: 'clinicalBiochemistry',
    title: '临床生化检验',
    sections: [
      { key: 'liverFunction', title: '肝功能', items: liverFunctionFields },
      { key: 'renalFunction', title: '肾功能', items: renalFunctionFields },
      { key: 'metabolism', title: '代谢', items: metabolismFields },
      { key: 'tumorMarkers', title: '肿瘤标志物', items: tumorMarkerFields },
      { key: 'inflammation', title: '炎症相关', items: inflammationFields }
    ]
  },
  {
    key: 'clinicalBasic',
    title: '临床基础检验',
    sections: [
      { key: 'bloodRoutine', title: '血常规', items: bloodRoutineFields },
      { key: 'reticulocyte', title: '网织红细胞', items: reticulocyteFields },
      { key: 'coagulation', title: '凝血指标', items: coagulationFields }
    ]
  },
  {
    key: 'clinicalImmunology',
    title: '临床免疫检验',
    sections: [
      { key: 'antibody', title: '抗体检测', items: antibodyFields },
      { key: 'autoantibody', title: '自身免疫性抗体', items: autoantibodyFields }
    ]
  },
  {
    key: 'clinicalMicrobiology',
    title: '临床微生物检验',
    sections: [
      {
        key: 'placeholder',
        title: '临床微生物检验',
        items: [],
        emptyState: '待补充具体微生物指标定义'
      }
    ]
  }
] as const satisfies readonly LaboratoryGroupConfig[]

const LEGACY_FIELD_MAPPINGS = [
  { legacyKey: 'alt', groupKey: 'clinicalBiochemistry', sectionKey: 'liverFunction', fieldKey: 'alt' },
  { legacyKey: 'ast', groupKey: 'clinicalBiochemistry', sectionKey: 'liverFunction', fieldKey: 'ast' },
  { legacyKey: 'tbil', groupKey: 'clinicalBiochemistry', sectionKey: 'liverFunction', fieldKey: 'tbil' },
  { legacyKey: 'ceruloplasmin', groupKey: 'clinicalBiochemistry', sectionKey: 'metabolism', fieldKey: 'cer' }
] as const

const hasRecord = (value: unknown): value is Record<string, unknown> => !!value && typeof value === 'object'

const normalizeLaboratoryValue = (value: unknown): string => (typeof value === 'string' ? value.trim() : '')

const createInitialSectionValues = <TKey extends string>(
  items: readonly (LaboratoryFieldConfig & { key: TKey })[]
): Record<TKey, string> =>
  items.reduce<Record<TKey, string>>((acc, item) => {
    acc[item.key] = ''
    return acc
  }, {} as Record<TKey, string>)

export const createInitialLaboratoryScreening = (): PatientRecordLaboratoryScreening => ({
  clinicalBiochemistry: {
    liverFunction: createInitialSectionValues(liverFunctionFields),
    renalFunction: createInitialSectionValues(renalFunctionFields),
    metabolism: createInitialSectionValues(metabolismFields),
    tumorMarkers: createInitialSectionValues(tumorMarkerFields),
    inflammation: createInitialSectionValues(inflammationFields)
  },
  clinicalBasic: {
    bloodRoutine: createInitialSectionValues(bloodRoutineFields),
    reticulocyte: createInitialSectionValues(reticulocyteFields),
    coagulation: createInitialSectionValues(coagulationFields)
  },
  clinicalImmunology: {
    antibody: createInitialSectionValues(antibodyFields),
    autoantibody: createInitialSectionValues(autoantibodyFields)
  },
  clinicalMicrobiology: {}
})

export const normalizeLaboratoryScreening = (
  value: unknown,
  legacySource?: Record<string, unknown>
): PatientRecordLaboratoryScreening => {
  const normalized = createInitialLaboratoryScreening()
  const rootSource = hasRecord(value) ? value : {}

  for (const group of LABORATORY_SCREENING_CONFIG) {
    if (group.key === 'clinicalMicrobiology') {
      continue
    }

    const normalizedGroup = normalized[group.key] as unknown as Record<string, Record<string, string>>
    const groupSource = hasRecord(rootSource[group.key]) ? (rootSource[group.key] as Record<string, unknown>) : {}
    for (const section of group.sections) {
      const normalizedSection = normalizedGroup[section.key] as Record<string, string>
      const sectionSource = hasRecord(groupSource[section.key]) ? (groupSource[section.key] as Record<string, unknown>) : {}
      for (const item of section.items) {
        normalizedSection[item.key] = normalizeLaboratoryValue(sectionSource[item.key])
      }
    }
  }

  if (!legacySource) {
    return normalized
  }

  for (const mapping of LEGACY_FIELD_MAPPINGS) {
    const legacyValue = normalizeLaboratoryValue(legacySource[mapping.legacyKey])
    if (!legacyValue) {
      continue
    }
    const normalizedGroup = normalized[mapping.groupKey] as unknown as Record<string, Record<string, string>>
    const normalizedSection = normalizedGroup[mapping.sectionKey] as Record<string, string>
    if (!normalizedSection[mapping.fieldKey]) {
      normalizedSection[mapping.fieldKey] = legacyValue
    }
  }

  return normalized
}
