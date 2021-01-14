import axios from '@/utils/request'
import qs from 'qs'

var url = 'http://127.0.0.1:8762/user'
export function getInfo () {
  return axios.get('/blog/getCurrentUser')
}

export function login (username, password) {
  return axios.post('/blog/login', qs.stringify({ username, password }), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
}

export function logout () {
  return axios({
    url: '/blog/logout',
    method: 'post'
  })
}
export function getUserImg (data) {
  return axios({
    url: url + '/getUserImgUrl',
    method: 'POST',
    data
  })
 // return axios.get(url + '/getUserImgUrl', JSON.stringify({data}), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
}
