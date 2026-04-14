export interface UserDetailResponse {
  username: string
  email: string
  role: string
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

export interface UserAccountPageQuery {
  page?: number
  size?: number
  usernameKeyword?: string
  userType?: string
  deptName?: string
  status?: string
}
