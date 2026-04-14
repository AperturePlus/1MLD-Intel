export function toast(content: string): void {
  uni.showToast({
    icon: 'none',
    title: content
  })
}

export function showConfirm(content: string): Promise<any> {
  return new Promise((resolve) => {
    uni.showModal({
      title: '提示',
      content,
      cancelText: '取消',
      confirmText: '确定',
      success(res: any) {
        resolve(res)
      }
    })
  })
}

export function serializeParams(params: Record<string, any>): string {
  let result = ''

  Object.keys(params || {}).forEach((propName) => {
    const value = params[propName]
    const part = `${encodeURIComponent(propName)}=`

    if (value === null || value === '' || typeof value === 'undefined') {
      return
    }

    if (typeof value === 'object') {
      Object.keys(value).forEach((key) => {
        const nestedValue = value[key]
        if (nestedValue === null || nestedValue === '' || typeof nestedValue === 'undefined') {
          return
        }

        const nestedKey = `${propName}[${key}]`
        const subPart = `${encodeURIComponent(nestedKey)}=`
        result += `${subPart}${encodeURIComponent(nestedValue)}&`
      })
      return
    }

    result += `${part}${encodeURIComponent(value)}&`
  })

  return result
}
