import service from './base'

export default {
  getScreeningOverview(params = {}) {
    return service({
      url: '/api/v1/screening/overview/',
      method: 'get',
      params
    })
  },

  getDietPatients(params = {}) {
    return service({
      url: '/api/v1/diet/patients/',
      method: 'get',
      params
    })
  },

  getDietPlan(patientId) {
    return service({
      url: `/api/v1/diet/patients/${patientId}/plan/`,
      method: 'get'
    })
  },

  regenerateDietPlan(patientId) {
    return service({
      url: `/api/v1/diet/patients/${patientId}/regenerate/`,
      method: 'post'
    })
  },

  pushDietPlan(patientId) {
    return service({
      url: `/api/v1/diet/patients/${patientId}/push/`,
      method: 'post'
    })
  }
}
