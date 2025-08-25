import React, { useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createEvent, buildEventPayload } from '../api/event';

export default function CreateEventPage() {
  const nav = useNavigate();

  
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [location, setLocation] = useState('');
  const [startDate, setStartDate] = useState(''); // YYYY-MM-DD
  const [startTime, setStartTime] = useState(''); // HH:MM
  const [endDate, setEndDate] = useState('');     // YYYY-MM-DD
  const [endTime, setEndTime] = useState('');     // HH:MM
  const [imageUrl, setImageUrl] = useState('');
  const [type, setType] = useState('OPEN_TO_ALL'); 

  const [submitting, setSubmitting] = useState(false);
  const [err, setErr] = useState('');
  const [ok, setOk] = useState('');

  // simple client-side check that end >= start
  const timeError = useMemo(() => {
    if (!startDate || !startTime || !endDate || !endTime) return '';
    const start = new Date(`${startDate}T${startTime}:00`);
    const end = new Date(`${endDate}T${endTime}:00`);
    return end < start ? 'End time must be after start time.' : '';
  }, [startDate, startTime, endDate, endTime]);

  function validateRequired() {
    if (!name.trim()) return 'Name is required.';
    if (!description.trim()) return 'Description is required.';
    if (!location.trim()) return 'Location is required.';
    if (!startDate || !startTime) return 'Start date and time are required.';
    if (!endDate || !endTime) return 'End date and time are required.';
    if (timeError) return timeError;
    if (!type) return 'Event type is required.';
    return '';
  }

  async function onSubmit(e) {
    e.preventDefault();
    setErr('');
    setOk('');

    const v = validateRequired();
    if (v) { setErr(v); return; }

    
    const payload = buildEventPayload({
      name,
      location,
      description,
      imageUrl,
      startDate,
      startTime,
      endDate,
      endTime,
      type, // "OPEN_TO_ALL" | "MEMBER_ONLY"
    });

    setSubmitting(true);
    try {
      await createEvent(payload);
      setOk('Event created!');
      // small delay so the success text flashes, then back to feed
      setTimeout(() => nav('/feed'), 600);
    } catch (ex) {
      console.error('Create event failed:', ex.response?.status, ex.response?.data);
      setErr(ex.response?.data || ex.response?.data?.error || 'Could not create event.');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div style={{ padding: 24, maxWidth: 720, margin: '0 auto' }}>
      <h2 style={{ marginTop: 0 }}>Create Event</h2>

      {err && <div style={banner('#d33')}>{String(err)}</div>}
      {ok &&  <div style={banner('#2c7a7b')}>{ok}</div>}

      <form onSubmit={onSubmit} style={{ display: 'grid', gap: 12 }}>
        <label style={lbl}>Name *</label>
        <input
          value={name}
          onChange={e => setName(e.target.value)}
          required
          maxLength={120}
          placeholder="Club Social"
          style={input}
        />

        <label style={lbl}>Description *</label>
        <textarea
          value={description}
          onChange={e => setDescription(e.target.value)}
          required
          rows={5}
          placeholder="What’s happening, who should come, any notes…"
          style={{ ...input, resize: 'vertical' }}
        />

        <label style={lbl}>Location *</label>
        <input
          value={location}
          onChange={e => setLocation(e.target.value)}
          required
          placeholder="Student Center, Room 101"
          style={input}
        />

        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 12 }}>
          <div>
            <label style={lbl}>Start date *</label>
            <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} required style={input} />
          </div>
          <div>
            <label style={lbl}>Start time *</label>
            <input type="time" value={startTime} onChange={e => setStartTime(e.target.value)} required style={input} />
          </div>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 12 }}>
          <div>
            <label style={lbl}>End date *</label>
            <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} required style={input} />
          </div>
          <div>
            <label style={lbl}>End time *</label>
            <input type="time" value={endTime} onChange={e => setEndTime(e.target.value)} required style={input} />
          </div>
        </div>

        {timeError && <div style={{ color: '#b00020', fontSize: 13 }}>{timeError}</div>}

        <label style={lbl}>Event type *</label>
        <select value={type} onChange={e => setType(e.target.value)} required style={input}>
          <option value="OPEN_TO_ALL">Open to all</option>
          <option value="MEMBER_ONLY">Member only</option>
        </select>

        <label style={lbl}>Image URL (optional)</label>
        <input
          value={imageUrl}
          onChange={e => setImageUrl(e.target.value)}
          placeholder="https://…"
          style={input}
        />

        <div style={{ display: 'flex', gap: 10, marginTop: 8 }}>
          <button type="submit" disabled={submitting} style={primary(submitting)}>
            {submitting ? 'Saving…' : 'Create'}
          </button>
          <button type="button" disabled={submitting} onClick={() => nav('/feed')} style={secondary}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

const input = {
  width: '100%', padding: '10px 12px', borderRadius: 10, border: '1px solid #ddd', outline: 'none'
};
const lbl = { display: 'block', fontWeight: 600, margin: '6px 0' };
const primary = (dis) => ({
  padding: '10px 16px', borderRadius: 10, border: '1px solid #111',
  background: dis ? '#f2f2f2' : '#111', color: dis ? '#777' : '#fff',
  cursor: dis ? 'not-allowed' : 'pointer'
});
const secondary = {
  padding: '10px 16px', borderRadius: 10, border: '1px solid #ddd', background: '#fff', cursor: 'pointer'
};
const banner = (bg) => ({
  color: '#fff', background: bg, padding: 8, borderRadius: 8, marginBottom: 12
});
