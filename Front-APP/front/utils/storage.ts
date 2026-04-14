import constant from './constant'

/**
 * 核心修改 1：利用 TypeScript 提取 constant 对象中所有值的联合类型
 * 这样 StorageKey 的类型就会自动变为：
 * 'vuex_avatar' | 'vuex_name' | 'vuex_roles' | 'vuex_permissions' | 'vuex_session_id'
 */
type StorageKey = typeof constant[keyof typeof constant]

const storageKey = 'storage_data'
const storageNodeKeys = Object.values(constant)

const storage = {
  /**
   * 核心修改 2：将 key 的类型从 string 改为 StorageKey
   */
  set(key: StorageKey, value: unknown): void {
    // includes 现在可以正常对比了，因为 key 和 storageNodeKeys 的类型是匹配的
    if (!(storageNodeKeys as any[]).includes(key)) {
      return
    }

    const tmp = uni.getStorageSync(storageKey) || {}
    tmp[key] = value
    uni.setStorageSync(storageKey, tmp)
  },

  get(key: StorageKey): any {
    const storageData = uni.getStorageSync(storageKey) || {}
    return storageData[key] || ''
  },

  remove(key: StorageKey): void {
    const storageData = uni.getStorageSync(storageKey) || {}
    if (storageData[key]) {
      delete storageData[key]
      uni.setStorageSync(storageKey, storageData)
    }
  },

  clean(): void {
    uni.removeStorageSync(storageKey)
  }
}

export default storage