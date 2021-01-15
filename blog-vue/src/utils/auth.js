import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken () {
  return sessionStorage.getItem('token')
}

export function setToken (token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}
