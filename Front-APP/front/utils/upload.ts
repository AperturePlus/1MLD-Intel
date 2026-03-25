import store from '@/store'
import config from '@/config'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import { toast, showConfirm, tansParams } from '@/utils/common'

interface UploadOptions {
  url: string
  filePath: string
  name?: string
  timeout?: number
  params?: Record<string, unknown>
  formData?: Record<string, unknown>
  header?: Record<string, any>
  headers?: Record<string, any>
}

const timeout = 10000
const baseUrl = config.baseUrl

const upload = (options: UploadOptions): Promise<any> => {
  const isToken = (options.headers || {}).isToken === false
  const uploadConfig: UploadOptions = {
    ...options,
    header: options.header || {}
  }

  if (getToken() && !isToken) {
    uploadConfig.header = uploadConfig.header || {}
    uploadConfig.header.Authorization = `Bearer ${getToken()}`
  }

  if (uploadConfig.params) {
    let url = `${uploadConfig.url}?${tansParams(uploadConfig.params as Record<string, any>)}`
    url = url.slice(0, -1)
    uploadConfig.url = url
  }

  return new Promise((resolve, reject) => {
    console.log('即将调用 uni.uploadFile，完整配置：', {
      url: `${baseUrl}${uploadConfig.url}`,
      filePath: uploadConfig.filePath,
      header: uploadConfig.header,
      formData: uploadConfig.formData
    })

    uni.uploadFile({
      timeout: uploadConfig.timeout || timeout,
      url: `${baseUrl}${uploadConfig.url}`,
      filePath: uploadConfig.filePath,
      name: uploadConfig.name || 'file',
      header: uploadConfig.header,
      formData: uploadConfig.formData,
      success: (res: any) => {
        console.log('upload 成功回调：', res)

        const result = JSON.parse(res.data || '{}')
        const code = Number(result.code || 200)
        const msg = errorCode[String(code)] || result.msg || errorCode.default

        if (code === 200) {
          resolve(result)
          return
        }

        if (code === 401) {
          showConfirm('登录状态已过期，您可以继续留在该页面，或者重新登录?').then((modalRes: any) => {
            if (modalRes.confirm) {
              store.dispatch('LogOut').then(() => {
                uni.reLaunch({
                  url: '/pages/login/login'
                })
              })
            }
          })
          reject('无效的会话，或者会话已过期，请重新登录。')
          return
        }

        if (code === 500) {
          toast(msg)
          reject('500')
          return
        }

        toast(msg)
        reject(code)
      },
      fail: (error: any) => {
        let message = error?.message || ''

        if (message === 'Network Error') {
          message = '后端接口连接异常'
        } else if (message.includes('timeout')) {
          message = '系统接口请求超时'
        } else if (message.includes('Request failed with status code')) {
          message = `系统接口${message.substr(message.length - 3)}异常`
        }

        toast(message)
        reject(error)
      }
    })
  })
}

export default upload
