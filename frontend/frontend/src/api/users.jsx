import api from './axios';

export async function registerUser(payload) {
  try {
    const { data } = await api.post('/users/register', payload, {
      headers: { 'Content-Type': 'application/json' },
    });
    return { success: true, data };
  } catch (e) {
    console.log('register error:', e.response?.status, e.response?.data);
    const body = e.response?.data;
    let details = [];

    if (Array.isArray(body)) details = body;
    else if (Array.isArray(body?.errors)) details = body.errors;

    const message =
      body?.message ||
      body?.error ||
      details.join(', ') ||
      'Registration failed';

    return { success: false, message, details };
  }
}

export async function getMe() {
  try {
    const { data } = await api.get('/users/me'); 
    return data; 
  } catch (e) {
    console.error('getMe error:', e.response?.status, e.response?.data);
    throw e; 
  }
}