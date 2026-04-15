import request from '@/utils/request'

export function listReceivedUserEvaluations(nickname: string) {
  return request({
    url: '/report/my_user_evaluate',
    method: 'get',
    params: {
      param: nickname
    }
  })
}

export function listMyEvaluations(nickname: string) {
  return request({
    url: '/report/my_evaluate',
    method: 'get',
    params: {
      param: nickname
    }
  })
}

export function deleteEvaluationById(id: string, nickname: string) {
  return request({
    url: '/report/delete_evaluate',
    method: 'post',
    data: {
      id,
      nickname
    }
  })
}

export function listMyReports(nickname: string) {
  return request({
    url: '/report/my_report',
    method: 'get',
    params: { nickname }
  })
}

export function listMyCollections(nickname: string) {
  return request({
    url: '/report/my_collect',
    method: 'get',
    params: { nickname }
  })
}

export function deleteCollectionById(id: string, nickname: string) {
  return request({
    url: '/report/delete_collect',
    method: 'delete',
    params: { id, nickname }
  })
}

export function createReport(data: Record<string, unknown>) {
  return request({
    url: '/report/add',
    method: 'post',
    data
  })
}

export function listPendingReports() {
  return request({
    url: '/report/all_pending',
    method: 'get'
  })
}

export function updateReportState(data: { id: string; state: string }) {
  return request({
    url: '/report/update_state',
    method: 'post',
    data
  })
}

