import axios from "axios";
import {API_PREFIX} from "@/contants/contants";
import request from "./utils";


export function uploadFile(data) {
  let obj = new FormData();
  obj.append("file", data.file);
  return axios.post(API_PREFIX + 'attachment/upload', obj)
    .then(res => {
      return res;
    }).catch(err => err)
}

export function userMe() {
  return request({
    url: 'auth/userMe'
  })
}


export function login(data) {
  return request({
    url: 'auth/login',
    method: 'post',
    data
  })
}

export function register(data) {
  return request({
    url: 'auth/register',
    method: 'post',
    data
  })
}

export function getDay() {
  return request({
    url: 'getCount'
  })
}

export function getApplication(data) {
  return request({
    url: 'applications/' + data.source + '/?page=' + data.page + '&size=' + data.size
  })
}

export function getStaff() {
  return request({
    url: 'getStaff'
  })
}

export function editStaff(data) {
  return request({
    url: 'editStaff',
    method: 'put',
    data
  })
}

export function deleteStaff(data) {
  return request({
    url: 'deleteStaff/' + data.id,
    method: 'delete',
  })
}

export function registerCode(data) {
  return request({
    url: 'editRegisterCode',
    method: 'put',
    data
  })
}


export function deleteApplication(data) {
  return request({
    url: 'deleteApplication/' + data.id,
    method: 'delete'
  })
}

export function getDitrict(data) {
  return axios.get(API_PREFIX + 'district/search/getByRegion?id=' + data.id)
    .then(res => res)
    .catch(err => err)
}

export function getRegion() {
  return axios.get(API_PREFIX + 'region')
    .then(res => res)
    .catch(err => err)
}
