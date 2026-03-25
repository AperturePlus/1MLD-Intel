import constant from './constant'

const storageKey = 'storage_data'
const storageNodeKeys = Object.values(constant)

const storage = {
  set(key: string, value: unknown): void {
    if (!storageNodeKeys.includes(key)) {
      return
    }

    const tmp = uni.getStorageSync(storageKey) || {}
    tmp[key] = value
    uni.setStorageSync(storageKey, tmp)
  },
  get(key: string): any {
    const storageData = uni.getStorageSync(storageKey) || {}
    return storageData[key] || ''
  },
  remove(key: string): void {
    const storageData = uni.getStorageSync(storageKey) || {}
    delete storageData[key]
    uni.setStorageSync(storageKey, storageData)
  },
  clean(): void {
    uni.removeStorageSync(storageKey)
  }
}

export default storage
