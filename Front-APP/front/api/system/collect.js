// 查询收藏列表
import request from "../../utils/request";

export function listCollects(query) {
  return request({
    url: '/collects/collects/list',
    method: 'get',
    params: query
  })
}


// 新增收藏
export function addCollects(data) {
  return request({
    url: '/collects/collects',
    method: 'post',
    data: data
  })
}

// 删除收藏
export function delCollects(id) {
  return request({
    url: '/collects/collects/' + id,
    method: 'delete'
  })
}
