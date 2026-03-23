import service from './base'

export default {
  list(params = {}) {
    return service({
      url: '/api/v1/patients/',
      method: 'get',
      params
    })
  },

  createRecord(data) {
    return service({
      url: '/api/v1/patient-records/',
      method: 'post',
      data
    })
  }
}
