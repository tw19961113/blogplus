
import { abAction } from '@/utils/utils'

var url = 'http://127.0.0.1:8762/user'

// 验证码
export function getVerification () {
  return abAction(url + '/getVerification', null)
}
