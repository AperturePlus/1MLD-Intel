<template>
  <div class="account-manage-page">
    <el-card class="account-card">
      <template #header>
        <div class="header-row">
          <div class="title-group">
            <span class="title">账号权限</span>
            <span class="subtitle">SYSTEM_ADMIN 角色可查看与管理账号授权关系</span>
          </div>
          <el-button :loading="isLoading" @click="loadUsers">刷新</el-button>
        </div>
      </template>

      <el-alert
        v-if="loadErrorMessage"
        :title="loadErrorMessage"
        type="warning"
        :closable="false"
        class="mb-16"
      />

      <el-form :inline="true" class="query-form">
        <el-form-item label="用户名">
          <el-input
            v-model.trim="queryForm.usernameKeyword"
            clearable
            placeholder="输入用户名关键字"
            @keyup.enter="handleSearch"
          />
        </el-form-item>

        <el-form-item label="用户类型">
          <el-select v-model="queryForm.userType" clearable placeholder="全部类型" style="width: 160px">
            <el-option label="医生" value="DOCTOR" />
            <el-option label="护士" value="NURSE" />
            <el-option label="管理员" value="ADMIN" />
            <el-option label="患者" value="PATIENT" />
          </el-select>
        </el-form-item>

        <el-form-item label="状态">
          <el-select v-model="queryForm.status" clearable placeholder="全部状态" style="width: 160px">
            <el-option label="启用" value="ACTIVE" />
            <el-option label="禁用" value="INACTIVE" />
            <el-option label="锁定" value="LOCKED" />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="isLoading" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="isLoading"
        :data="tableRows"
        border
        empty-text="暂无账号数据"
      >
        <el-table-column prop="username" label="用户名" min-width="140" />
        <el-table-column prop="displayName" label="姓名" min-width="140" />
        <el-table-column prop="userType" label="用户类型" min-width="120" />
        <el-table-column prop="deptName" label="所属科室" min-width="160" />
        <el-table-column prop="email" label="邮箱" min-width="210" />
        <el-table-column label="角色权限" min-width="320">
          <template #default="{ row }">
            <div class="roles-cell">
              <el-tag
                v-for="role in row.roles"
                :key="`${row.id}-${role.id}`"
                size="small"
                type="info"
              >
                {{ role.roleName || role.roleCode }}
              </el-tag>
              <el-text v-if="!row.roles || row.roles.length === 0" type="info">未配置角色</el-text>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="statusTagType(row.status)">
              {{ row.status || '-' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="最近登录" min-width="190">
          <template #default="{ row }">
            {{ formatDateTime(row.lastLoginAt) }}
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-row">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="pagination.total"
          :page-sizes="[10, 20, 50]"
          :current-page="pagination.page"
          :page-size="pagination.size"
          @current-change="handlePageChange"
          @size-change="handleSizeChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import type { AxiosError } from 'axios'
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import identityApi from '@/api/identity'
import type { ApiEnvelope, PagedResult } from '@/types/common'
import type { UserAccountResponse } from '@/types/identity'

type QueryFormModel = {
  usernameKeyword: string
  userType: string
  status: string
}

const isLoading = ref(false)
const loadErrorMessage = ref('')
const tableRows = ref<UserAccountResponse[]>([])

const queryForm = reactive<QueryFormModel>({
  usernameKeyword: '',
  userType: '',
  status: ''
})

const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

const unwrapApiEnvelope = <T>(envelope: ApiEnvelope<T>): T => {
  if (!envelope || envelope.code !== 200 || envelope.data === null || envelope.data === undefined) {
    throw new Error(envelope?.message || 'Request failed')
  }
  return envelope.data
}

const formatDateTime = (value: string | null): string => {
  if (!value) {
    return '-'
  }

  const date = new Date(value)
  if (Number.isNaN(date.getTime())) {
    return value
  }
  return date.toLocaleString()
}

const statusTagType = (status: string): '' | 'success' | 'warning' | 'info' | 'danger' => {
  if (status === 'ACTIVE') {
    return 'success'
  }
  if (status === 'LOCKED') {
    return 'danger'
  }
  if (status === 'INACTIVE') {
    return 'warning'
  }
  return 'info'
}

const resolveErrorMessage = (error: unknown): string => {
  const axiosError = error as AxiosError<{ message?: string }>
  const status = axiosError?.response?.status

  if (status === 401) {
    return '登录已失效，请重新登录后重试。'
  }
  if (status === 403) {
    return '当前账号缺少 SYSTEM_ADMIN 权限，无法访问账号权限列表。'
  }
  if (status === 404) {
    return '后端未提供账号权限接口（/api/v1/web/identity/users）。'
  }

  const backendMessage = axiosError?.response?.data?.message
  if (backendMessage) {
    return backendMessage
  }
  if (axiosError?.message) {
    return axiosError.message
  }
  return '加载账号权限数据失败，请稍后重试。'
}

const buildQueryParams = (): {
  page: number
  size: number
  usernameKeyword?: string
  userType?: string
  status?: string
} => {
  const params: {
    page: number
    size: number
    usernameKeyword?: string
    userType?: string
    status?: string
  } = {
    page: pagination.page - 1,
    size: pagination.size
  }

  if (queryForm.usernameKeyword) {
    params.usernameKeyword = queryForm.usernameKeyword
  }
  if (queryForm.userType) {
    params.userType = queryForm.userType
  }
  if (queryForm.status) {
    params.status = queryForm.status
  }
  return params
}

const loadUsers = async (): Promise<void> => {
  isLoading.value = true
  loadErrorMessage.value = ''

  try {
    const response = await identityApi.listUsers(buildQueryParams())
    const result = unwrapApiEnvelope<PagedResult<UserAccountResponse>>(response.data)
    tableRows.value = result.items || []
    pagination.total = result.total || 0
  } catch (error) {
    tableRows.value = []
    pagination.total = 0
    loadErrorMessage.value = resolveErrorMessage(error)
    ElMessage.error(loadErrorMessage.value)
  } finally {
    isLoading.value = false
  }
}

const handleSearch = async (): Promise<void> => {
  pagination.page = 1
  await loadUsers()
}

const handleReset = async (): Promise<void> => {
  queryForm.usernameKeyword = ''
  queryForm.userType = ''
  queryForm.status = ''
  pagination.page = 1
  await loadUsers()
}

const handlePageChange = async (page: number): Promise<void> => {
  pagination.page = page
  await loadUsers()
}

const handleSizeChange = async (size: number): Promise<void> => {
  pagination.size = size
  pagination.page = 1
  await loadUsers()
}

onMounted(() => {
  loadUsers()
})
</script>

<style scoped>
.account-manage-page {
  padding: 20px;
}

.account-card {
  width: 100%;
}

.header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.title-group {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.title {
  font-size: 16px;
  font-weight: 600;
  color: #1f2a37;
}

.subtitle {
  font-size: 12px;
  color: #6b7280;
}

.query-form {
  margin-bottom: 12px;
}

.roles-cell {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.pagination-row {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}

.mb-16 {
  margin-bottom: 16px;
}
</style>
