import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE,
  withCredentials: true // send cookies like refresh token
});

// endpoints which don't need Authorization
const isPublic = (url = '') =>
  url.endsWith('/login') ||
  url.endsWith('/users/register') ||
  url.endsWith('/logout') ||
  url.includes('/auth/refresh');

// Request: attach access token if needed
api.interceptors.request.use((config) => {
  if (!isPublic(config.url)) {
    const token = localStorage.getItem('accessToken');
    if (token) {
      config.headers = config.headers || {};
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

let refreshPromise = null;

// Response: if token expired, refresh once and retry
api.interceptors.response.use(
  (res) => res,
  async (error) => {
    const original = error.config;
    const status = error.response?.status;

    if ((status === 401 || status === 403) && !original._retry && !isPublic(original.url)) {
      original._retry = true;

      try {
        refreshPromise =
          refreshPromise || axios.post(`${API_BASE}/auth/refresh`, {}, { withCredentials: true });

        const { data } = await refreshPromise.finally(() => (refreshPromise = null));

        const newToken = data?.accessToken ?? data?.token;
        if (newToken) {
          localStorage.setItem('accessToken', newToken);
          original.headers = original.headers || {};
          original.headers.Authorization = `Bearer ${newToken}`;
          return api(original); // retry with new token
        }
      } catch {
        // clear token and redirect
        localStorage.removeItem('accessToken');
        window.location.href = '/';
      }
    }

    return Promise.reject(error);
  }
);

export default api;
