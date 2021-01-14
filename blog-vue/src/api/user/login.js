import request from '@/utils/request'
import {abAction} from '@/utils/utils'

var url = 'http://127.0.0.1:8762/user'

// 验证码
export function getVerification () {
  return abAction(url + '/getVerification', null)
}

export function doLogin (data) {
  return request({
    url: url + '/doLogin',
    method: 'POST',
    data
  })
}

export function logout (data) {
  return request({
    url: url + '/logout',
    method: 'POST',
    data
  })
}
