import React, { useEffect, useState, useRef } from 'react';
import { Link } from 'react-router-dom';
import { logout } from '../api/auth';

export default function LogoutPage() {
  const [done, setDone] = useState(false);
  const ran = useRef(false); 
  useEffect(() => {
    if (ran.current) return;
    ran.current = true;

    (async () => {
      try {
        await logout();            // server clears cookie; client clears access token
      } catch (e) {
        console.error('Logout failed:', e);
      } finally {
        setDone(true);             // always show completion UI
      }
    })();
  }, []);

  return (
    <div style={{ padding: 24, maxWidth: 640, margin: '0 auto', textAlign: 'center' }}>
      <h2>Logout</h2>
      {done ? (
        <>
          <div style={{ marginTop: 12, padding: 12, background: '#e6ffed', borderRadius: 10 }}>
            Successfully logged out.
          </div>
          <div style={{ marginTop: 16 }}>
            <Link to="/" style={{ padding: '8px 12px', border: '1px solid #ddd', borderRadius: 8 }}>
              Go to Login
            </Link>
          </div>
        </>
      ) : (
        <div>Signing you out…</div>
      )}
    </div>
  );
}
