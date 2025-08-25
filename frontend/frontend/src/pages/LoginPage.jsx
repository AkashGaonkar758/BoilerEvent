import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../api/auth';

const LoginPage = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await login(username, password);
    if (res.success) {
      navigate('/feed');
    } else {
      setError(res.message);
    }
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        padding: 'var(--s-7)', 
        background: 'var(--bg)',
      }}
    >
      <div
        style={{
          width: '100%',
          maxWidth: 360,
          background: 'var(--surface)',
          border: '1px solid var(--border)',
          borderRadius: 'var(--radius)',
          boxShadow: 'var(--shadow)',
          padding: 'var(--s-6)',
        }}
      >
        <h2 style={{ margin: '0 0 var(--s-4)' }}>Login</h2>

        <form onSubmit={handleSubmit} style={{ display: 'grid', gap: 'var(--s-3)' }}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            style={{
              width: '100%',
              padding: '10px 12px',
              border: '1px solid var(--border)',
              borderRadius: '12px',
              background: 'var(--surface)',
              color: 'var(--text)',
              font: 'inherit',
            }}
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{
              width: '100%',
              padding: '10px 12px',
              border: '1px solid var(--border)',
              borderRadius: '12px',
              background: 'var(--surface)',
              color: 'var(--text)',
              font: 'inherit',
            }}
          />

          <button
            type="submit"
            style={{
              padding: '10px 14px',
              border: 'none',
              borderRadius: '12px',
              background: 'var(--accent)',
              color: 'var(--accent-ink)',
              cursor: 'pointer',
            }}
          >
            Login
          </button>
        </form>

        {error && (
          <p style={{ color: '#DC2626', marginTop: 'var(--s-3)' }}>
            {error}
          </p>
        )}
      </div>
    </div>
  );
};

export default LoginPage;
