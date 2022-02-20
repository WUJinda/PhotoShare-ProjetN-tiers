import RestTemplate from '@/services/base/restTemplate'

export default class userService {
  static login = (params = {}, showError = false) => {
    return RestTemplate.post('/user/login', { data: params })
  }
  static getInfo = (params = {}, showError = false) => {
    return RestTemplate.get('/user/info', { params: params })
  }
  static logout = (params = {}, showError = false) => {
    return RestTemplate.post('/user/logout', { params: params })
  }
  static updatePassword = (params = {}, showError = false) => {
    return RestTemplate.put('/user/password', { params: params })
  }
  static addUser = (params = {}, showError = false) => {
    return RestTemplate.post('/user/add', { data: params })
  }
  static addComment = (params = {}, showError = false) => {
    return RestTemplate.post('/image/addComment', { data: params })
  }
  static getComments = (params = {}, showError = false) => {
    return RestTemplate.get('/image/getComments', { params: params })
  }
  static deleteImg = (params = {}, showError = false) => {
    return RestTemplate.post('/image/delete', { params: params })
  }
  static updateImg = (params = {}, showError = false) => {
    return RestTemplate.post('/image/update', { data: params })
  }
}
