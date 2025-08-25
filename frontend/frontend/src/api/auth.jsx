import axiosInstance from './axios';

// Login user and store access token
export const login = async (username, password) => {
  try {
    const response = await axiosInstance.post('/login', {
      username,
      password,
    });

    // Store the access token in local storage
    localStorage.setItem('accessToken', response.data.token);

    return { success: true };
  } catch (error) {
    console.error('Login error:', error.response?.data || error.message);
    return {
      success: false,
      message: error.response?.data || 'Login failed',
    };
  }
};

// Logout: remove access token and redirect
export const logout = async () => {
  try {
    await axiosInstance.post('/logout', null, { withCredentials: true });
  } finally {
    localStorage.removeItem('accessToken');
    delete axiosInstance.defaults.headers.common.Authorization;
  }
};


// Check if user is authenticated
export const isAuthenticated = () => {
  const token = localStorage.getItem('accessToken');
  return !!token;
};
