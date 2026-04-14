// 查询喜爱列表
import request from "../../utils/request";

export function listLikes(query) {
  return request({
    url: '/likes/likes/list',
    method: 'get',
    params: query
  })
}


// 新增喜欢
export function addCollects(data) {
  return request({
    url: '/likes/likes',
    method: 'post',
    data: data
  })
}

// 删除喜欢
export function delCollects(id) {
  return request({
    url: '/likes/likes/' + id,
    method: 'delete'
  })
}
