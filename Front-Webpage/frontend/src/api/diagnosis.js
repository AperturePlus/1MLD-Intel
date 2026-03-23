import service from './base'

export default {
  getAiQueue() {
    return service({
      url: '/api/v1/diagnosis/ai-queue/',
      method: 'get'
    })
  },

  runAiDiagnosis(patientId) {
    return service({
      url: '/api/v1/diagnosis/ai-reports/',
      method: 'post',
      data: { patientId }
    })
  },

  getExpertReports() {
    return service({
      url: '/api/v1/diagnosis/expert-reports/',
      method: 'get'
    })
  },

  signExpertReport(reportId, payload) {
    return service({
      url: `/api/v1/diagnosis/expert-reports/${reportId}/sign/`,
      method: 'post',
      data: payload
    })
  }
}
