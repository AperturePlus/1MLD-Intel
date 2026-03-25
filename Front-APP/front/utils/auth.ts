const tokenKey = 'App-Token'

export function getToken(): string {
  return uni.getStorageSync(tokenKey) || ''
}

export function setToken(token: string): void {
  uni.setStorageSync(tokenKey, token)
}

export function removeToken(): void {
  uni.removeStorageSync(tokenKey)
}
