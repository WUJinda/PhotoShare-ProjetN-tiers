import { Message } from 'element-ui'
import request from '@/utils/request'
const basicRequest = async(config, showError = true) => {
  try {
    return await request(config)
  } catch (e) {
    // {code, msg } ,Unified exception handling, prompting error information or not prompting
    console.log(e)
    showError && await Message.error(e.toString())
    return Promise.reject(e)
  }
}

export default class RestTemplate {
  static get = (url, config) => {
    return basicRequest({ url: url, method: 'GET', ...config })
  }
  static post = (url, config) => {
    return basicRequest({ url: url, method: 'POST', ...config })
  }
  static put = (url, config) => {
    return basicRequest({ url: url, method: 'PUT', ...config })
  }
  static delete = (url, config) => {
    return basicRequest({ url: url, method: 'DELETE', ...config })
  }
}
