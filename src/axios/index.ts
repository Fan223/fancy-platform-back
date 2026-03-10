import axios from "axios";

// 创建 Axios 实例
const request = axios.create({
  timeout: 10000, // 请求超时时间
  withCredentials: true, // 允许携带 Cookie
});

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未认证，跳转到登录页
          break;
        case 403:
          // 未授权
          break;
        case 404:
          // 请求的资源不存在
          break;
        case 500:
          // 服务器错误
          break;
        default:
          // 其他错误
          break;
      }
    }
    return Promise.reject(error);
  },
);

export default request;
