import request from '@/utils/request'
// localStorage的封装
export default {
  fetchData (key) {
    return JSON.parse(window.localStorage.getItem(key))
  },
  saveData (key, value) {
    window.localStorage.setItem(key, JSON.stringify(value))
  }
}
// abAction:ArrayBufferAction
export function abAction (url, parameter) {
  return request({
    url: url,
    params: parameter,
    responseType: 'arraybuffer'
  })
}

