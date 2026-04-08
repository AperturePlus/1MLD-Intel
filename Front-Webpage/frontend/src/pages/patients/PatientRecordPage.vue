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

            <el-divider content-position="left">遗传病史特征</el-divider>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="父母是否近亲婚配">
                  <el-switch v-model="formData.consanguinity" active-text="是" inactive-text="否" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="肝病家族史">
                  <el-switch v-model="formData.familyHistory" active-text="有" inactive-text="无" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="家族病史详情" v-if="formData.familyHistory">
                  <el-input v-model="formData.familyHistoryDetail" placeholder="如：父亲患有肝豆状核变性" />
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

            <el-divider content-position="left">体格检查 (阳性体征筛查)</el-divider>

            <el-row :gutter="24">
              <el-col :span="6">
                <el-form-item label="皮肤/巩膜黄染">
                  <el-select v-model="formData.jaundice" placeholder="请选择">
                    <el-option label="无" value="无" />
                    <el-option label="轻度" value="轻度" />
                    <el-option label="中重度" value="中重度" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="肝脏肿大">
                  <el-switch v-model="formData.hepatomegaly" />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="脾脏肿大">
                  <el-switch v-model="formData.splenomegaly" />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="角膜 K-F 环" title="肝豆状核变性特征">
                  <el-select v-model="formData.kfRing" placeholder="裂隙灯检查结果">
                    <el-option label="未见" value="未见" />
                    <el-option label="阳性 (+)" value="阳性" />
                    <el-option label="未查" value="未查" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="24">
                <el-form-item label="神经系统症状">
                  <el-checkbox-group v-model="formData.neuroSymptoms">
                    <el-checkbox label="震颤" />
                    <el-checkbox label="肌张力障碍" />
                    <el-checkbox label="步态异常" />
                    <el-checkbox label="发音困难" />
                    <el-checkbox label="精神行为异常" />
                    <el-checkbox label="无" />
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane label="生化与代谢筛查" name="metabolic">
            <div class="section-title">常规肝功能</div>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="ALT (谷丙转氨酶)">
                  <el-input v-model="formData.alt" placeholder="0.00"><template #append>U/L</template></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="AST (谷草转氨酶)">
                  <el-input v-model="formData.ast" placeholder="0.00"><template #append>U/L</template></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="TBIL (总胆红素)">
                  <el-input v-model="formData.tbil" placeholder="0.00"><template #append>μmol/L</template></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <div class="section-title">铜代谢 (Wilson病筛查)</div>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="血清铜蓝蛋白">
                  <el-input v-model="formData.ceruloplasmin" placeholder="< 0.2 提示异常"><template #append>g/L</template></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="24h 尿铜">
                  <el-input v-model="formData.urineCopper" placeholder="> 100 提示异常"><template #append>μg/24h</template></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <div class="section-title">铁代谢 (血色病筛查)</div>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="血清铁蛋白">
                  <el-input v-model="formData.ferritin" placeholder="0.00"><template #append>ng/mL</template></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="转铁蛋白饱和度">
                  <el-input v-model="formData.transferrinSat" placeholder="0.00"><template #append>%</template></el-input>
                </el-form-item>
              </el-col>
            </el-row>
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
      <el-button type="primary" size="large" @click="submitForm" :icon="Select" :loading="submitting">提交归档</el-button>
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
import type { ImportSourceType, PatientImportPreview } from '@/api/types'
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
