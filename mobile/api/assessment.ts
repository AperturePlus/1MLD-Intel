import request from '@/utils/request'

export function submitAssessmentForm(data: Record<string, unknown>) {
  return request({
    url: '/assessment/submit',
    method: 'post',
    data
  })
}

export function fetchAssessmentResult() {
  return request({
    url: '/assessment/result',
    method: 'get'
  })
}

