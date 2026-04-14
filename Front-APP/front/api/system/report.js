import request from '@/utils/request'

export function getMyUserToMeEvaluate(nickname) {
  return request({
    url: '/report/my_user_evaluate',
    method: 'get',
	params: {
	      param: nickname
	    }
  })
}

// 获取我的 user_evaluate 类型评价
export function getMyEvaluates(nickname) {
  return request({
    url: '/report/my_evaluate',
    method: 'get',
    params: {
      param: nickname
    }
  })
}

// 删除评价
// 删除评价
export function deleteEvaluateById(id, nickname) {
  return request({
    url: '/report/delete_evaluate',
    method: 'post',
    data: {
      id,
      nickname
    }
  })
}
// 获取我的所有举报（包括 user_report / post_report / evaluate_report）
export function getMyReports(nickname) {
  return request({
    url: '/report/my_report',
    method: 'get',
    params: { nickname }
  })
}

export function getMyCollect(nickname) {
  return request({
    url: '/report/my_collect',
    method: 'get',
    params: { nickname }
  })
}

export function deleteCollectById(id, nickname) {
  return request({
    url: '/report/delete_collect',
    method: 'delete',
    params: { id, nickname }
  })
}
