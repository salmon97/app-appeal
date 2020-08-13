import {API_PREFIX, TOKEN_NAME} from "../contants/contants";
import axios from "axios";

const bolta = (uroq) => {
  let {
    method = 'get',
    data,
    url,
    headers = {}
  } = uroq;
  url = API_PREFIX + url;

  let tokenHeader = '';
  try {
    const orderAppToken = localStorage.getItem(TOKEN_NAME);
    tokenHeader = {
      'Authorization': orderAppToken,
      ...headers
    };
  } catch (e) {
  }


  if (data && data.path) {
    url = url + '/' + data.path;
  }

  switch (method.toLocaleLowerCase()) {
    case "get":
      if (data) {
        let kk = JSON.stringify(data);
        kk = kk.replace(/["{}]/g, "").replace(/,/g, "&").replace(/:/g, '=');
        url = url + '?' + kk;
      }
      return axios.get(url, {
        headers: tokenHeader
      });
    case "delete":
      return axios.delete(url, {
        headers: tokenHeader
      });
    case "put":
      return axios.put(url, data,
        {headers: tokenHeader}
      );
    case "patch":
      return axios.patch(url, data,
        {headers: tokenHeader}
      );
    case "post":
      return axios.post(url,
        data,
        {headers: tokenHeader});
    default:
      return axios(uroq);
  }
};

export default function request(ketmon) {
  return bolta(ketmon).then(res => {
    const {message, status, data} = res;
    return Promise.resolve({
      success: true,
      message,
      statusCode: status,
      ...data
    })
  }).catch(err => {
    const {response, message} = err;
    return Promise.resolve({
      success: false,
      statusText: response.data.message,
      statusCode: response.data.status,
      errors: response.data.errors,
      error: response.data.error
    })
  })
}
