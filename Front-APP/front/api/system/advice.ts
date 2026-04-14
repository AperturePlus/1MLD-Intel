import request from '@/utils/request'

export function listArticles(data: { param: string; [key: string]: unknown }): Promise<any> {
  return request({
    url: `/advice/list/${data.param}`,
    method: 'post',
    data
  })
}

export function queryArticles(params?: Record<string, unknown>): Promise<any> {
  return request({
    url: '/advice/five',
    method: 'get',
    params
  })
}

export function deleteArticle(id: string): Promise<any> {
  return request({
    url: `/advice/delete/${id}`,
    method: 'delete'
  })
}

export function createArticle(data: Record<string, unknown>): Promise<any> {
  return request({
    url: '/advice/add',
    method: 'post',
    data
  })
}

export function updateArticle(data: Record<string, unknown>): Promise<any> {
  return request({
    url: '/advice/update',
    method: 'put',
    data
  })
}

