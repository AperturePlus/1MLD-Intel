<template>
  <div class="record-container">
    <div class="page-header">
      <el-row justify="space-between" align="middle" :gutter="12">
        <el-col :span="12">
          <el-text tag="b" class="header-title">
            <el-icon class="mr-2"><DocumentAdd /></el-icon>
            专病电子病历录入 - 遗传代谢性肝病（IMLD）
          </el-text>
        </el-col>
        <el-col :span="12" class="header-actions">
          <el-space wrap alignment="center" size="small">
            <el-tag v-if="importMeta.sourceType" type="success" effect="light">
              已导入：{{ importSourceLabel }}
            </el-tag>
            <el-button type="primary" plain :icon="UploadFilled" @click="openImportDrawer()">
              导入基础信息
            </el-button>
            <el-text type="info" size="small">就诊单号：{{ visitId }}</el-text>
          </el-space>
        </el-col>
      </el-row>
    </div>

    <el-alert
      type="info"
      :closable="false"
      show-icon
      class="record-flow-alert"
      title="录入流程：先导入、后核对、再补录。优先从院内系统导入，医生仅需核对并完善临床信息。"
    />

    <el-alert
      v-if="importMeta.sourceType"
      type="success"
      :closable="false"
      show-icon
      class="record-flow-alert"
      :title="`最近导入：${importSourceLabel}｜置信度 ${importConfidenceText}｜追踪号 ${importMeta.traceId}`"
    />

    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-position="right"
        label-width="140px"
      >
        <el-tabs v-model="activeTab" type="border-card" class="custom-tabs">
          <el-tab-pane label="基础档案" name="basic">
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="病人ID号" prop="patientNo">
                  <el-input v-model="formData.patientNo" placeholder="请输入院内病历号/患者号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="患者姓名" prop="name">
                  <el-input v-model="formData.name" placeholder="请输入姓名" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" prop="gender">
                  <el-radio-group v-model="formData.gender">
                    <el-radio label="男">男</el-radio>
                    <el-radio label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="年龄" prop="age">
                  <el-input-number v-model="formData.age" :min="0" :max="120" style="width: 100%;" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="就诊日期" prop="visitDate">
                  <el-date-picker
                    v-model="formData.visitDate"
                    type="date"
                    placeholder="选择日期"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="就诊方式" prop="encounterType">
                  <el-select v-model="formData.encounterType" placeholder="请选择就诊方式">
                    <el-option
                      v-for="item in encounterTypeOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="科室" prop="department">
                  <el-input v-model="formData.department" placeholder="如：肝病医学科" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职业" prop="occupation">
                  <el-input v-model="formData.occupation" placeholder="请输入职业" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话">
                  <el-input v-model="formData.phone" placeholder="请输入手机号" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="现住址" prop="currentAddress">
                  <el-input v-model="formData.currentAddress" placeholder="请输入现住址" />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="籍贯" prop="nativePlace">
                  <el-input v-model="formData.nativePlace" placeholder="请输入籍贯" />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="身份证号">
                  <el-input v-model="formData.idCard" placeholder="请输入身份证号" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-divider content-position="left">遗传背景</el-divider>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="父母是否近亲婚配">
                  <el-switch v-model="formData.consanguinity" active-text="是" inactive-text="否" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane label="临床特征" name="clinical">
            <el-form-item label="主诉" prop="chiefComplaint">
              <el-input
                v-model="formData.chiefComplaint"
                type="textarea"
                :rows="2"
                placeholder="请简明扼要记录患者就诊主要症状及持续时间..."
              />
            </el-form-item>

            <el-form-item label="现病史" prop="presentIllness">
              <el-input
                v-model="formData.presentIllness"
                type="textarea"
                :rows="4"
                placeholder="详细记录疾病发生、发展、诊疗经过..."
              />
            </el-form-item>

            <el-divider content-position="left">既往史</el-divider>

            <el-row :gutter="24">
              <el-col v-for="item in diseaseHistoryOptions" :key="item.key" :span="6">
                <el-form-item :label="item.label" :prop="`history.diseaseHistory.${item.key}`">
                  <el-select
                    v-model="formData.history.diseaseHistory[item.key]"
                    placeholder="请选择"
                    style="width: 100%;"
                  >
                    <el-option
                      v-for="option in triStateOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="手术史" prop="history.surgeryHistory.status">
                  <el-select
                    v-model="formData.history.surgeryHistory.status"
                    placeholder="请选择"
                    style="width: 100%;"
                  >
                    <el-option
                      v-for="option in triStateOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item
                  v-if="formData.history.surgeryHistory.status === 'YES'"
                  label="手术史明细"
                  prop="history.surgeryHistory.detail"
                >
                  <el-input
                    v-model="formData.history.surgeryHistory.detail"
                    type="textarea"
                    :rows="3"
                    placeholder="请填写手术名称、时间及相关说明"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="输血史" prop="history.transfusionHistory.status">
                  <el-select
                    v-model="formData.history.transfusionHistory.status"
                    placeholder="请选择"
                    style="width: 100%;"
                  >
                    <el-option
                      v-for="option in triStateOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item
                  v-if="formData.history.transfusionHistory.status === 'YES'"
                  label="输血史明细"
                  prop="history.transfusionHistory.detail"
                >
                  <el-input
                    v-model="formData.history.transfusionHistory.detail"
                    type="textarea"
                    :rows="3"
                    placeholder="请填写输血时间、原因及相关说明"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="过敏史" prop="history.allergyHistory">
                  <el-input
                    v-model="formData.history.allergyHistory"
                    type="textarea"
                    :rows="3"
                    placeholder="请填写药物、食物或其他过敏史；无可填写“无”"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="用药史" prop="history.medicationHistory">
                  <el-input
                    v-model="formData.history.medicationHistory"
                    type="textarea"
                    :rows="3"
                    placeholder="请填写长期用药、近期用药或特殊治疗；无可填写“无”"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="家族史" prop="history.familyHistory">
                  <el-input
                    v-model="formData.history.familyHistory"
                    type="textarea"
                    :rows="3"
                    placeholder="请填写家族相关疾病史；无可填写“无”"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-divider content-position="left">生命体征</el-divider>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="身高(cm)" prop="physicalExam.heightCm">
                  <el-input-number
                    v-model="formData.physicalExam.heightCm"
                    :min="1"
                    :precision="1"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="体重(kg)" prop="physicalExam.weightKg">
                  <el-input-number
                    v-model="formData.physicalExam.weightKg"
                    :min="1"
                    :precision="1"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="BMI(kg/m²)" prop="physicalExam.bmi">
                  <el-input
                    :model-value="bmiDisplay"
                    readonly
                    placeholder="根据身高体重自动计算"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="6">
                <el-form-item label="收缩压(mmHg)" prop="physicalExam.bloodPressureSystolic">
                  <el-input-number
                    v-model="formData.physicalExam.bloodPressureSystolic"
                    :min="1"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="舒张压(mmHg)" prop="physicalExam.bloodPressureDiastolic">
                  <el-input-number
                    v-model="formData.physicalExam.bloodPressureDiastolic"
                    :min="1"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="呼吸频率(次/分)" prop="physicalExam.respiratoryRate">
                  <el-input-number
                    v-model="formData.physicalExam.respiratoryRate"
                    :min="1"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="心率(次/分)" prop="physicalExam.heartRate">
                  <el-input-number
                    v-model="formData.physicalExam.heartRate"
                    :min="1"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-divider content-position="left">补充体格检查</el-divider>

            <el-row :gutter="24">
              <el-col v-for="item in supplementalExamOptions" :key="item.key" :span="8">
                <el-form-item :label="item.label" :prop="`physicalExam.${item.key}`">
                  <el-select
                    v-model="formData.physicalExam[item.key]"
                    placeholder="请选择"
                    style="width: 100%;"
                  >
                    <el-option
                      v-for="option in triStateOptions"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane label="实验室筛查指标" name="laboratory">
            <section
              v-for="group in laboratoryScreeningConfig"
              :key="group.key"
              class="laboratory-screening-group"
            >
              <el-divider content-position="left">{{ group.title }}</el-divider>

              <template v-for="section in group.sections" :key="`${group.key}-${section.key}`">
                <div class="section-title">{{ section.title }}</div>
                <el-alert
                  v-if="section.emptyState"
                  type="info"
                  :closable="false"
                  show-icon
                  :title="section.emptyState"
                  class="record-flow-alert"
                />
                <el-row v-else :gutter="24">
                  <el-col
                    v-for="item in section.items"
                    :key="`${group.key}-${section.key}-${item.key}`"
                    :xs="24"
                    :sm="12"
                    :md="8"
                    :xl="6"
                  >
                    <el-form-item :label="item.label">
                      <el-select
                        v-if="item.inputType === 'select'"
                        :model-value="getLaboratoryFieldValue(group.key, section.key, item.key)"
                        placeholder="请选择"
                        style="width: 100%;"
                        @update:model-value="updateLaboratoryFieldValue(group.key, section.key, item.key, $event)"
                      >
                        <el-option
                          v-for="option in item.options ?? []"
                          :key="option.value"
                          :label="option.label"
                          :value="option.value"
                        />
                      </el-select>
                      <el-input
                        v-else
                        :model-value="getLaboratoryFieldValue(group.key, section.key, item.key)"
                        placeholder="0.00"
                        @update:model-value="updateLaboratoryFieldValue(group.key, section.key, item.key, $event)"
                      >
                        <template v-if="item.unit" #append>{{ item.unit }}</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </template>
            </section>
          </el-tab-pane>

          <el-tab-pane label="影像/基因与诊断" name="diagnosis">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="影像学表现">
                  <el-input
                    v-model="formData.imagingResult"
                    type="textarea"
                    :rows="3"
                    placeholder="录入超声/CT/MRI肝脏及脾脏影像学描述..."
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="肝穿刺活检">
                  <el-input
                    v-model="formData.biopsyResult"
                    type="textarea"
                    :rows="3"
                    placeholder="如：肝细胞内大量铜颗粒沉积 / 铁沉积..."
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-divider content-position="left">遗传学检测</el-divider>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="是否进行基因检测">
                  <el-radio-group v-model="formData.geneticTested">
                    <el-radio :label="true">已测</el-radio>
                    <el-radio :label="false">未测</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="formData.geneticTested">
                <el-form-item label="致病基因突变">
                  <el-select v-model="formData.mutatedGene" placeholder="选择已知突变基因" filterable allow-create>
                    <el-option label="ATP7B (肝豆状核变性)" value="ATP7B" />
                    <el-option label="HFE (遗传性血色病)" value="HFE" />
                    <el-option label="SERPINA1 (α1-抗胰蛋白酶缺乏)" value="SERPINA1" />
                    <el-option label="G6PC / SLC37A4 (糖原累积病)" value="G6PC" />
                    <el-option label="阴性/未发现" value="阴性" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-divider content-position="left">临床诊断与干预</el-divider>
            <el-form-item label="初步诊断" prop="diagnosis">
              <el-input v-model="formData.diagnosis" placeholder="如：肝豆状核变性（Wilson病）、肝硬化代偿期..." />
            </el-form-item>
            <el-form-item label="治疗干预方案">
              <el-input
                v-model="formData.treatmentPlan"
                type="textarea"
                :rows="4"
                placeholder="如：1. 低铜饮食；2. 青霉胺 0.25g tid 驱铜治疗；3. 保肝对症处理..."
              />
            </el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </div>

    <div class="action-footer">
      <el-button @click="resetForm" size="large">清空重置</el-button>
      <el-button type="warning" plain size="large" @click="saveDraft">保存草稿</el-button>
      <el-button type="primary" size="large" @click="submitForm" :icon="Select" :loading="submitting">
        提交归档
      </el-button>
    </div>

    <el-drawer
      v-model="importDrawerVisible"
      title="导入基础信息（仅本地/院内处理）"
      size="480px"
      destroy-on-close
    >
      <el-alert
        type="warning"
        :closable="false"
        show-icon
        class="import-safety-alert"
        title="导入数据仅用于预填，提交前请医生复核关键字段。当前默认院内/本地识别，不出域。"
      />

      <el-tabs v-model="importTab">
        <el-tab-pane label="HIS/LIS 查询" name="hisLis">
          <el-form label-width="110px" class="import-form">
            <el-form-item label="病人ID号">
              <el-input v-model="hisLisQuery.patientNo" placeholder="请输入院内病历号/患者号" clearable />
            </el-form-item>
            <el-form-item label="就诊号">
              <el-input v-model="hisLisQuery.visitNo" placeholder="请输入就诊号（可选）" clearable />
            </el-form-item>
          </el-form>
          <el-button type="primary" :loading="importing" @click="handleHisLisImport">
            查询并导入
          </el-button>
        </el-tab-pane>

        <el-tab-pane label="图片识别" name="image">
          <div class="import-file-field">
            <label class="import-file-label">选择图片文件（JPG/PNG）</label>
            <input type="file" accept="image/*" @change="onImageFileChange" />
            <p v-if="imageFileName" class="import-file-name">已选择：{{ imageFileName }}</p>
          </div>
          <el-button type="primary" :loading="importing" @click="handleImageImport">
            识别并导入
          </el-button>
        </el-tab-pane>

        <el-tab-pane label="PDF 识别" name="pdf">
          <div class="import-file-field">
            <label class="import-file-label">选择 PDF 文件</label>
            <input type="file" accept="application/pdf" @change="onPdfFileChange" />
            <p v-if="pdfFileName" class="import-file-name">已选择：{{ pdfFileName }}</p>
          </div>
          <el-button type="primary" :loading="importing" @click="handlePdfImport">
            识别并导入
          </el-button>
        </el-tab-pane>
      </el-tabs>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { DocumentAdd, Select, UploadFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import patientApi from '@/api/patient'
import type { ImportSourceType, PatientImportPreview, TernaryFlag } from '@/api/types'
import {
  LABORATORY_SCREENING_CONFIG,
  type LaboratoryGroupConfig
} from '@/features/patient-record/constants/laboratoryScreening'
import { usePatientRecordPage } from '@/features/patient-record/composables/usePatientRecordPage'

const route = useRoute()
const router = useRouter()

const {
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
  applyImportPreview
} = usePatientRecordPage()

const importDrawerVisible = ref(false)
const importTab = ref<'hisLis' | 'image' | 'pdf'>('hisLis')
const importing = ref(false)

const hisLisQuery = reactive({
  patientNo: '',
  visitNo: ''
})

const imageFile = ref<File | null>(null)
const pdfFile = ref<File | null>(null)

const encounterTypeOptions = [
  { label: '普通门诊', value: 'OUTPATIENT' },
  { label: '急诊', value: 'EMERGENCY' },
  { label: '住院', value: 'INPATIENT' }
] as const

const triStateOptions: Array<{ label: string; value: TernaryFlag }> = [
  { label: '有', value: 'YES' },
  { label: '无', value: 'NO' },
  { label: '未查', value: 'UNKNOWN' }
]

const diseaseHistoryOptions = [
  { key: 'smokingHistory', label: '吸烟史' },
  { key: 'drinkingHistory', label: '饮酒史' },
  { key: 'diabetesHistory', label: '糖尿病史' },
  { key: 'hypertensionHistory', label: '高血压史' },
  { key: 'hyperuricemiaHistory', label: '高尿酸血症史' },
  { key: 'hyperlipidemiaHistory', label: '高脂血症史' },
  { key: 'coronaryHeartDiseaseHistory', label: '冠心病史' },
  { key: 'hepatitisBHistory', label: '乙肝病史' }
] as const

const supplementalExamOptions = [
  { key: 'liverFibrosis', label: '肝纤维化' },
  { key: 'cirrhosis', label: '肝硬化' },
  { key: 'fattyLiver', label: '脂肪肝' },
  { key: 'liverFailure', label: '肝衰竭' },
  { key: 'cholestasis', label: '胆汁淤积' },
  { key: 'viralHepatitis', label: '病毒性肝炎' }
] as const

const laboratoryScreeningConfig: readonly LaboratoryGroupConfig[] = LABORATORY_SCREENING_CONFIG

const importSourceLabelMap: Record<ImportSourceType, string> = {
  HIS_LIS: 'HIS/LIS',
  IMAGE_OCR: '图片识别',
  PDF_OCR: 'PDF 识别'
}

const importSourceLabel = computed(() => {
  if (!importMeta.sourceType) {
    return '未导入'
  }
  return importSourceLabelMap[importMeta.sourceType]
})

const importConfidenceText = computed(() => {
  if (typeof importMeta.confidence !== 'number') {
    return 'N/A'
  }
  return `${Math.round(importMeta.confidence * 100)}%`
})

const bmiDisplay = computed(() => {
  if (formData.physicalExam.bmi === null) {
    return ''
  }
  return formData.physicalExam.bmi.toFixed(1)
})

const getLaboratoryFieldValue = (groupKey: string, sectionKey: string, fieldKey: string): string => {
  const group = formData.laboratoryScreening[groupKey as keyof typeof formData.laboratoryScreening] as
    | Record<string, Record<string, string>>
    | undefined
  const section = group?.[sectionKey] as Record<string, string> | undefined
  return section?.[fieldKey] ?? ''
}

const updateLaboratoryFieldValue = (groupKey: string, sectionKey: string, fieldKey: string, value: string): void => {
  const group = formData.laboratoryScreening[groupKey as keyof typeof formData.laboratoryScreening] as
    | Record<string, Record<string, string>>
    | undefined
  if (!group || !(sectionKey in group)) {
    return
  }
  const section = group[sectionKey] as Record<string, string>
  section[fieldKey] = typeof value === 'string' ? value : ''
}

const imageFileName = computed(() => imageFile.value?.name ?? '')
const pdfFileName = computed(() => pdfFile.value?.name ?? '')

const readFileAsBase64 = (file: File): Promise<string> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = () => {
      const result = typeof reader.result === 'string' ? reader.result : ''
      const base64 = result.includes(',') ? result.split(',')[1] : result
      resolve(base64)
    }
    reader.onerror = () => reject(new Error('文件读取失败'))
    reader.readAsDataURL(file)
  })

const openImportDrawer = (tab: 'hisLis' | 'image' | 'pdf' = 'hisLis') => {
  importTab.value = tab
  importDrawerVisible.value = true
}

const applyImportedData = (message: string, preview: PatientImportPreview) => {
  applyImportPreview(preview)
  importDrawerVisible.value = false
  ElMessage.success(message)
}

const handleHisLisImport = async () => {
  if (!hisLisQuery.patientNo.trim() && !hisLisQuery.visitNo.trim()) {
    ElMessage.warning('请至少输入病人ID号或就诊号')
    return
  }
  importing.value = true
  try {
    const res = await patientApi.importPatientFromHisLis({
      patientNo: hisLisQuery.patientNo.trim() || undefined,
      visitNo: hisLisQuery.visitNo.trim() || undefined
    })
    applyImportedData('已从 HIS/LIS 预填基础信息，请核对后继续补录', res.data)
  } catch {
    ElMessage.error('HIS/LIS 导入失败，请检查输入后重试')
  } finally {
    importing.value = false
  }
}

const handleImageImport = async () => {
  if (!imageFile.value) {
    ElMessage.warning('请先选择图片文件')
    return
  }
  importing.value = true
  try {
    const base64 = await readFileAsBase64(imageFile.value)
    const res = await patientApi.importPatientFromImage({
      fileName: imageFile.value.name,
      fileContentBase64: base64
    })
    applyImportedData('图片识别完成，基础信息已回填，请核对后继续补录', res.data)
    imageFile.value = null
  } catch {
    ElMessage.error('图片识别导入失败，请稍后重试')
  } finally {
    importing.value = false
  }
}

const handlePdfImport = async () => {
  if (!pdfFile.value) {
    ElMessage.warning('请先选择 PDF 文件')
    return
  }
  importing.value = true
  try {
    const base64 = await readFileAsBase64(pdfFile.value)
    const res = await patientApi.importPatientFromPdf({
      fileName: pdfFile.value.name,
      fileContentBase64: base64
    })
    applyImportedData('PDF 识别完成，基础信息已回填，请核对后继续补录', res.data)
    pdfFile.value = null
  } catch {
    ElMessage.error('PDF 识别导入失败，请稍后重试')
  } finally {
    importing.value = false
  }
}

const onImageFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  imageFile.value = target.files?.[0] ?? null
}

const onPdfFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  pdfFile.value = target.files?.[0] ?? null
}

const openImportByRouteQuery = () => {
  const shouldOpen = route.query.openImport === '1' || route.query.openImport === 'true'
  if (!shouldOpen) {
    return
  }

  const queryTab = route.query.importTab
  if (queryTab === 'image' || queryTab === 'pdf' || queryTab === 'hisLis') {
    openImportDrawer(queryTab)
  } else {
    openImportDrawer()
  }

  const nextQuery = { ...route.query }
  delete nextQuery.openImport
  delete nextQuery.importTab
  router.replace({ path: route.path, query: nextQuery })
}

openImportByRouteQuery()
</script>

<style scoped src="@/features/patient-record/styles/patient-record-page.css"></style>
