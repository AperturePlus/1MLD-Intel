import request from '@/utils/request'

export function fetchDailyRecipe() {
  return request({
    url: '/recipe/daily',
    method: 'get'
  })
}
