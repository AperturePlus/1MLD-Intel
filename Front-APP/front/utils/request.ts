import store from '@/store'
import config from '@/config'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import { toast, showConfirm } from '@/utils/common'

interface RequestOptions {
  url: string
  method?: string
  timeout?: number
  baseUrl?: string
  params?: Record<string, unknown>
  data?: Record<string, unknown> | unknown
  header?: Record<string, any>
  headers?: Record<string, any>
}

const timeout = 10000
const baseUrl = config.baseUrl

const request = (options: RequestOptions): Promise<any> => {
  const isToken = (options.headers || {}).isToken === false
  const requestConfig: RequestOptions = {
    ...options,
    header: options.header || {}
  }

  if (getToken() && !isToken) {
    requestConfig.header = requestConfig.header || {}
    requestConfig.header.Authorization = `Bearer ${getToken()}`
  }

  return new Promise((resolve, reject) => {
    uni.request({
      method: requestConfig.method || 'get',
      timeout: requestConfig.timeout || timeout,
      url: requestConfig.baseUrl || `${baseUrl}${requestConfig.url}`,
      data: requestConfig.params || requestConfig.data || {},
      header: requestConfig.header,
      dataType: 'json'
    })
      .then((response: [any, any]) => {
        const [error, res] = response

        if (error) {
          toast('后端接口连接异常')
          reject('后端接口连接异常')
          return
        }

        const responseData = (res && res.data) || {}
        const code = Number(responseData.code || 200)
        const msg = errorCode[String(code)] || responseData.msg || errorCode.default

        if (code === 401) {
          showConfirm('登录状态已过期，您可以继续留在该页面，或者重新登录?').then((modalRes: any) => {
            if (modalRes.confirm) {
              store.dispatch('LogOut').then(() => {
                uni.reLaunch({
                  url: '/pages/login'
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

        if (code !== 200) {
          toast(msg)
          reject(code)
          return
        }

        resolve(responseData)
      })
      .catch((error: any) => {
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
      })
  })
}

export default request
