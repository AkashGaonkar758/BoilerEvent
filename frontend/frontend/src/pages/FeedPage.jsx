import React, { useEffect, useRef, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { getFeed, toggleLike } from '../api/event';

const PAGE_SIZE = 5;

export default function FeedPage() {
  const nav = useNavigate();

  const [items, setItems] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(null);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState('');
  const [reachedEnd, setReachedEnd] = useState(false);

  const sentinelRef = useRef(null);
  const loadingRef = useRef(false);

  const loadPage = useCallback(async (p) => {
    if (loadingRef.current || reachedEnd) return;
    loadingRef.current = true;
    setLoading(true);
    setErr('');

    try {
      const data = await getFeed(p, PAGE_SIZE);
      const content = Array.isArray(data?.content) ? data.content : [];
      setItems(prev => {
        const seen = new Set(prev.map(x => x.id));
        return [...prev, ...content.filter(x => !seen.has(x.id))];
      });
      setTotalPages(data?.totalPages ?? 0);
      setReachedEnd(content.length === 0 || p >= (data?.totalPages ?? 1) - 1);
      setPage(p);
    } catch (e) {
      console.error(e);
      setErr('Failed to load feed');
    } finally {
      setLoading(false);
      loadingRef.current = false;
    }
  }, [reachedEnd]);

  useEffect(() => { loadPage(0); }, [loadPage]);

  useEffect(() => {
    if (!sentinelRef.current) return;
    const el = sentinelRef.current;

    const io = new IntersectionObserver(
      (entries) => {
        const first = entries[0];
        if (first.isIntersecting && !loadingRef.current && !reachedEnd) {
          const next = (page ?? 0) + 1;
          if (totalPages == null || next < totalPages) {
            loadPage(next);
          } else {
            setReachedEnd(true);
          }
        }
      },
      { root: null, rootMargin: '200px', threshold: 0 }
    );

    io.observe(el);
    return () => io.disconnect();
  }, [page, totalPages, loadPage, reachedEnd]);

  async function onLike(id, likedNow) {
    const liked = !!likedNow;
    const delta = liked ? -1 : +1;

    setItems(prev =>
      prev.map(ev =>
        ev.id === id
          ? {
              ...ev,
              likedByCurrentUser: !liked,
              likeCount: Math.max(0, (ev.likeCount ?? 0) + delta),
            }
          : ev
      )
    );

    try {
      await toggleLike(id, liked);
    } catch (e) {
      setItems(prev =>
        prev.map(ev =>
          ev.id === id
            ? {
                ...ev,
                likedByCurrentUser: liked,
                likeCount: Math.max(0, (ev.likeCount ?? 0) - delta),
              }
            : ev
        )
      );
      console.error('toggleLike failed:', e.response?.status, e.response?.data);
      alert('Could not update like.');
    }
  }

  return (
    <div style={{ padding: 24, maxWidth: 820, margin: '0 auto', position: 'relative' }}>
      {/* Top bar */}
      <div style={topBar}>
        <div style={brand}>BoilerEvent</div>
        <div style={actionsRight}>
          <button
            onClick={() => nav('/logout')}
            style={logoutBtn}
            aria-label="Log out"
            title="Log out"
          >
            Log out
          </button>
          <button
            onClick={() => nav('/profile')}
            aria-label="Profile"
            title="Profile"
            style={avatarBtn}
          >
            U
          </button>
        </div>
      </div>

      {items.length === 0 && !loading && !err && <div>No events yet.</div>}
      {err && <div style={{ color: 'red', marginBottom: 12 }}>{err}</div>}

      <div style={{ display: 'grid', gap: 16 }}>
        {items.map(ev => (
          <EventCard key={ev.id} event={ev} onLike={onLike} />
        ))}
      </div>

      {loading && (
        <div style={{ padding: 12, textAlign: 'center', opacity: 0.7 }}>Loading…</div>
      )}

      {!reachedEnd && <div ref={sentinelRef} style={{ height: 1 }} />}

      {reachedEnd && items.length > 0 && (
        <div style={{ padding: 12, textAlign: 'center', opacity: 0.6 }}>
          You’re all caught up.
        </div>
      )}

      {/* FAB */}
      <button
        onClick={() => nav('/create')}
        aria-label="Create event"
        title="Create event"
        style={fabStyle}
      >
        +
      </button>
    </div>
  );
}

// custom styles
const topBar = {
  position: 'sticky',
  top: 0,
  zIndex: 20,
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
  padding: '8px 0 16px',
};

const brand = { fontWeight: 800, fontSize: 18 };
const actionsRight = { display: 'flex', alignItems: 'center', gap: 10 };

const avatarBtn = {
  width: 36,
  height: 36,
  borderRadius: '50%',
  border: '1px solid #e5e7eb',
  background: '#fff',
  display: 'grid',
  placeItems: 'center',
  fontWeight: 800,
  cursor: 'pointer',
};

const logoutBtn = {
  padding: '8px 12px',
  borderRadius: 8,
  border: '1px solid #e5e7eb',
  background: '#fff',
  cursor: 'pointer',
};

const fabStyle = {
  position: 'fixed',
  right: 24,
  bottom: 24,
  width: 56,
  height: 56,
  borderRadius: '50%',
  border: 'none',
  outline: 'none',
  background: '#111',
  color: '#fff',
  fontSize: 28,
  lineHeight: '56px',
  textAlign: 'center',
  cursor: 'pointer',
  boxShadow: '0 6px 18px rgba(0,0,0,0.2)',
};

const card = {
  border: '1px solid #eee',
  borderRadius: 16,
  padding: 14,
  background: '#fff',
  display: 'flex',
  gap: 14,
  alignItems: 'stretch',
};

const imageWrap = {
  width: 140,
  height: 140,
  background: '#f3f3f3',
  borderRadius: 12,
  overflow: 'hidden',
  flexShrink: 0,
};

const metaCol = {
  display: 'flex',
  flexDirection: 'column',
  gap: 4,
  marginTop: 6,
  marginBottom: 6,
};

const postedByRow = {
  fontSize: 15,
  
};

const posterHandle = {
  fontWeight: 300,
  fontSize: 16,
  color: '#111',
};

const chipRow = { display: 'flex', alignItems: 'center', gap: 8 };
const chipLabel = { fontSize: 13, color: '#666' };
const chip = {
  fontSize: 13,
  padding: '4px 8px',
  borderRadius: 999,
  border: '1px solid #e5e7eb',
  background: '#fafafa',
};

const descClamp = {
  marginTop: 6,
  marginBottom: 12,
  display: '-webkit-box',
  WebkitLineClamp: 3,
  WebkitBoxOrient: 'vertical',
  overflow: 'hidden',
};

const FALLBACK = '/placeholder.jpeg';


function EventImage({ src, alt }) {
  const onErr = (e) => {
    e.currentTarget.onerror = null;
    e.currentTarget.src = FALLBACK;
  };
  return (
    <img
      src={src || FALLBACK}
      alt={alt}
      loading="lazy"
      onError={onErr}
      style={{ width: '100%', height: '100%', objectFit: 'cover' }}
    />
  );
}

function EventCard({ event, onLike }) {
  const {
    id,
    name,
    description,
    imageUrl,
    eventDate,
    location,
    likeCount,
    likedByCurrentUser,
    postedBy,
  } = event;

  const posterName = postedBy?.username || postedBy?.name || 'Unknown';

  const dt = new Date(eventDate);
  const dateStr = isNaN(dt)
    ? ''
    : dt.toLocaleString(undefined, {
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
      });

  return (
    <div style={card}>
      <div style={imageWrap}>
        <EventImage src={imageUrl} alt={name} />
      </div>

      <div style={{ flex: 1, minWidth: 0 }}>
        <h3 style={{ margin: 0, fontSize: 19 }}>{name}</h3>

        {/* Column meta: Posted by, Location, When */}
        <div style={metaCol}>
          <div style={postedByRow}>
            <span>Posted by </span>
            <span style={posterHandle}>{posterName}</span>
          </div>
          <div style={chipRow}>
            <span style={chipLabel}>Location</span>
            <span style={chip}>{location}</span>
          </div>
          <div style={chipRow}>
            <span style={chipLabel}>When</span>
            <span style={chip}>{dateStr}</span>
          </div>
        </div>

        <p style={descClamp}>{description}</p>

        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <button
            onClick={() => onLike(id, !!likedByCurrentUser)}
            style={{
              padding: '6px 12px',
              borderRadius: 999,
              border: '1px solid #ddd',
              background: likedByCurrentUser ? '#ffe9ec' : '#fff',
              cursor: 'pointer',
            }}
            aria-label={likedByCurrentUser ? 'Unlike' : 'Like'}
          >
            {likedByCurrentUser ? '♥' : '♡'} Like
          </button>
          <span>{likeCount} like{likeCount === 1 ? '' : 's'}</span>
        </div>
      </div>
    </div>
  );
}
