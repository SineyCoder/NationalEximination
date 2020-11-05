//这里写请求
import axios from './base'
import url from './url'

export function add(param) {  /*用户登录判断*/
  return axios.post(url.ADD, param)
    .then(data => data.data);
}

export function getExamType() {
  return axios.get(url.GET_EXAM_TYPE)
    .then(data => data.data);
}

export function searchTitleContent(value) {
  return axios.post(url.SEARCH_TITLE, {content:value})
    .then(data => data.data);
}

export function getExamProject(id) {
  return axios.get(url.GET_EXAM_PROJECT + `/${id}`)
    .then(data => data.data);
}

export function login(obj) {
  return axios.post(url.LOGIN, obj)
    .then(data => data.data);
}

export function check() {
  return axios.post(url.CHECK)
    .then(data => data.data);
}
