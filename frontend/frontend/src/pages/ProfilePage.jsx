import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getMe } from '../api/users';

export default function ProfilePage() {
  const [me, setMe] = useState(null);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState('');
  const nav = useNavigate();

  useEffect(() => {
    (async () => {
      setLoading(true);
      setErr('');
      try {
        const data = await getMe();
        setMe(data);
      } catch (e) {
        console.error('getMe failed:', e.response?.status, e.response?.data);
        setErr('Could not load profile.');
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const Wrapper = ({ children }) => (
    <div style={{ padding: 40, maxWidth: 960, margin: '0 auto', position: 'relative' }}>
      <button
        onClick={() => nav('/feed')}
        aria-label="Back to feed"
        title="Back to feed"
        style={closeBtn}
      >
        ×
      </button>
      {children}
    </div>
  );

  if (loading) {
    return (
      <Wrapper>
        <h2 style={h2}>Profile</h2>
        <div style={{ fontSize: 18, opacity: 0.8 }}>Loading…</div>
      </Wrapper>
    );
  }

  if (err) {
    return (
      <Wrapper>
        <h2 style={h2}>Profile</h2>
        <div style={errorBox}>{err}</div>
      </Wrapper>
    );
  }

  const username = me?.username ?? 'unknown';
  const initial = (username?.[0] || '?').toUpperCase();
  const fullName = [me?.firstName, me?.lastName].filter(Boolean).join(' ');
  const role = me?.role ?? ''; // e.g. "USER" | "ADMIN"

  return (
    <Wrapper>
     
      <div style={card}>
        <div style={avatar}>{initial}</div>

        <div>
          
          <div style={usernameStyle}>{username}</div>

          
          {fullName && <div style={nameStyle}>{fullName}</div>}

          
          {me?.email && <div style={subText}>{me.email}</div>}

          
          {me?.year !== undefined && me?.year !== null && (
            <div style={subText}>Year: {me.year}</div>
          )}

          {role && <div style={subText}>Role: {role}</div>}
        </div>
      </div>
    </Wrapper>
  );
}

const h2 = { marginTop: 0, fontSize: 30, letterSpacing: 0.3 };

const card = {
  display: 'flex',
  alignItems: 'center',
  gap: 24,
  border: '1px solid #eee',
  borderRadius: 22,
  padding: 28,
  background: '#fff',
  boxShadow: '0 8px 24px rgba(0,0,0,0.06)',
};

const avatar = {
  width: 92,
  height: 92,
  borderRadius: '50%',
  background: '#111',
  color: '#fff',
  display: 'grid',
  placeItems: 'center',
  fontWeight: 800,
  fontSize: 34,
  flexShrink: 0,
};

const usernameStyle = { fontSize: 26, fontWeight: 800, lineHeight: 1.2 };
const nameStyle = { fontSize: 18, color: '#222', marginTop: 4, fontWeight: 600 };
const subText = { fontSize: 15, color: '#555', marginTop: 4 };

const errorBox = {
  background: '#fee2e2',
  color: '#7f1d1d',
  padding: 14,
  borderRadius: 12,
  fontSize: 16,
};

const closeBtn = {
  position: 'fixed',
  top: 18,
  right: 18,
  width: 40,
  height: 40,
  borderRadius: '50%',
  border: '1px solid #ddd',
  background: '#fff',
  cursor: 'pointer',
  fontSize: 22,
  lineHeight: '38px',
  textAlign: 'center',
  boxShadow: '0 6px 18px rgba(0,0,0,0.08)',
};
