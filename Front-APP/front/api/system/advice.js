// 获取资讯列表
import request from "../../utils/request";

export function listAdvice(data) {
  return request({
    url: `/advice/list/${data.param}`,
    method: 'post',
    data
  })
}

// 搜索咨询
export function getAdvice(params) {
  return request({
    url: '/advice/five',
    method: 'get',
	params
  })
}

export function deleteAdvice(id) {
  return request({
    url: `/advice/delete/${id}`,
    method: 'delete'
  })
}


