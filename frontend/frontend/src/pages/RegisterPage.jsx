import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { registerUser } from '../api/users';

const YEARS = ['FRESHMAN', 'SOPHOMORE', 'JUNIOR', 'SENIOR'];

export default function RegisterPage() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    username: '',
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    year: YEARS[0],
  });

  const [error, setError] = useState('');
  const [details, setDetails] = useState([]);     // optional: list of field errors
  const [submitting, setSubmitting] = useState(false);

  const onChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const validate = () => {
    if (!form.username.trim() || form.username.trim().length < 3 || form.username.trim().length > 20)
      return 'Username must be 3–20 characters.';
    if (!form.email.trim() || !/^\S+@\S+\.\S+$/.test(form.email.trim()))
      return 'Enter a valid email.';
    if (!form.password || form.password.length < 6)
      return 'Password must be at least 6 characters.';
    if (!form.firstName.trim()) return 'First name is required.';
    if (!form.lastName.trim()) return 'Last name is required.';
    if (!form.year) return 'Year is required.';
    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setDetails([]);

    const v = validate();
    if (v) { setError(v); return; }

    // build trimmed payload (enum stays uppercase)
    const payload = {
      username: form.username.trim(),
      email: form.email.trim(),
      password: form.password, 
      firstName: form.firstName.trim(),
      lastName: form.lastName.trim(),
      year: form.year,        
    };

    try {
      setSubmitting(true);
      const res = await registerUser(payload);
      setSubmitting(false);

      if (res.success) {
        navigate('/'); // go to login
      } else {
        setError(res.message || 'Registration failed');
        if (Array.isArray(res.details) && res.details.length) setDetails(res.details);
      }
    } catch (err) {
      setSubmitting(false);
      setError('Registration failed');
    }
  };

  return (
    <div
      style={{
        padding: 'var(--s-7)',
        maxWidth: 420,
        margin: 'var(--s-7) auto',
        background: 'var(--surface)',
        border: '1px solid var(--border)',
        borderRadius: 'var(--radius)',
        boxShadow: 'var(--shadow)',
      }}
    >
      <h2 style={{ margin: '0 0 var(--s-4)' }}>Sign Up</h2>

      <form onSubmit={handleSubmit} style={{ display: 'grid', gap: 'var(--s-3)' }}>
        <input
          name="username"
          placeholder="Username"
          value={form.username}
          onChange={onChange}
          autoComplete="username"
          required
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
          name="email"
          type="email"
          placeholder="Email (must be @purdue.edu)"
          value={form.email}
          onChange={onChange}
          autoComplete="email"
          required
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
          name="password"
          type="password"
          placeholder="Password (min 6)"
          value={form.password}
          onChange={onChange}
          autoComplete="new-password"
          required
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
          name="firstName"
          placeholder="First name"
          value={form.firstName}
          onChange={onChange}
          required
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
          name="lastName"
          placeholder="Last name"
          value={form.lastName}
          onChange={onChange}
          required
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
        <select
          name="year"
          value={form.year}
          onChange={onChange}
          required
          style={{
            width: '100%',
            padding: '10px 12px',
            border: '1px solid var(--border)',
            borderRadius: '12px',
            background: 'var(--surface)',
            color: 'var(--text)',
            font: 'inherit',
          }}
        >
          {YEARS.map((y) => (
            <option key={y} value={y}>{y}</option>
          ))}
        </select>

        <button
          type="submit"
          disabled={submitting}
          style={{
            padding: '10px 14px',
            border: 'none',
            borderRadius: '12px',
            background: 'var(--accent)',
            color: 'var(--accent-ink)',
            cursor: 'pointer',
            opacity: submitting ? 0.8 : 1,
          }}
        >
          {submitting ? 'Creating…' : 'Register'}
        </button>
      </form>

      {error && <p style={{ color: '#DC2626', marginTop: 'var(--s-3)' }}>{error}</p>}
      {details.length > 0 && (
        <ul style={{ color: '#DC2626', marginTop: 'var(--s-3)', paddingLeft: 'var(--s-5)' }}>
          {details.map((d, i) => <li key={i}>{d}</li>)}
        </ul>
      )}

      <p style={{ marginTop: 'var(--s-4)' }}>
        Already have an account? <Link to="/">Log in</Link>
      </p>
    </div>
  );
}
