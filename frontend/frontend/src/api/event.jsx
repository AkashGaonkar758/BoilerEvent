import api from './axios';


export async function getFeed(page=0,size=5){
  try {
    const { data } = await api.get('/events/feed', { params: { page, size } });
    return data;
  } catch (e) {
    const s = e.response?.status;
    const retried = e.config?._retry;  // set by interceptor
    if (!(s === 401 || s === 403) || retried) {
      console.error('Feed error:', s, e.response?.data);
    }
    throw e;
  }
}
export async function likeEvent(id)   { await api.post(`/events/${id}/like`); }
export async function unlikeEvent(id) { await api.post(`/events/${id}/unlike`); }

export async function toggleLike(id, likedNow) {
  if (likedNow) {
    await unlikeEvent(id);
    return false; // now unliked
  } else {
    await likeEvent(id);
    return true;  // now liked
  }
}

export async function getEventById(eventId) {
  try {
    const { data } = await api.get(`/events/${eventId}`);
    return data; 
  } catch (e) {
    console.error('Get event error:', e.response?.status, e.response?.data);
    throw e;
  }
}

/*
 * Create a new event
 * Backend: POST /events
 * payload example:
 * {
 *   name, description, imageUrl, eventDate, location
 * }
 */
export async function createEvent(payload) {
  try {
    const { data } = await api.post('/events', payload, {
      headers: { 'Content-Type': 'application/json' },
    });
    return data;
  } catch (e) {
    console.error('Create event error:', e.response?.status, e.response?.data);
    throw e;
  }
}

/*
 * Update an existing event
 * Backend: PUT /events/{id}
 */
export async function updateEvent(eventId, payload) {
  try {
    const { data } = await api.put(`/events/${eventId}`, payload, {
      headers: { 'Content-Type': 'application/json' },
    });
    return data;
  } catch (e) {
    console.error('Update event error:', e.response?.status, e.response?.data);
    throw e;
  }
}

export async function deleteEvent(eventId) {
  try {
    const { data } = await api.delete(`/events/${eventId}`);
    return data;
  } catch (e) {
    console.error('Delete event error:', e.response?.status, e.response?.data);
    throw e;
  }
}

/*
 * (Optional) Upload image to get an imageUrl (if your backend supports it)
 * Backend: POST /events/{id}/image  (or /uploads)
 * Expects a multipart/form-data upload of a File (Blob)
 */
export async function uploadEventImage(eventId, file) {
  const form = new FormData();
  form.append('file', file);
  try {
    const { data } = await api.post(`/events/${eventId}/image`, form, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    
    return data;
  } catch (e) {
    console.error('Upload image error:', e.response?.status, e.response?.data);
    throw e;
  }
}


/*
 * Build a payload the Spring Boot controller expects.
 * - eventDate / eventEndDate must be LocalDateTime strings: "YYYY-MM-DDTHH:MM:SS"
 * - type must be EXACTLY one of: "MEMBER_ONLY" | "OPEN_TO_ALL"
 * - omit backend-managed fields (datePosted, postedBy, active, likedByUsers)
 */
export function buildEventPayload({
  name,
  location,
  description = '',
  imageUrl = '',
  startDate,   // "YYYY-MM-DD"
  startTime,   // "HH:MM"
  endDate,     // "YYYY-MM-DD" (REQUIRED by backend)
  endTime,     // "HH:MM"      (REQUIRED by backend)
  type = 'OPEN_TO_ALL',
}) {
  // convert "date" + "time" to  "YYYY-MM-DDTHH:MM:SS" 
  const toLDT = (d, t) => (d && t) ? `${d}T${String(t).padStart(5, '0')}:00` : null;

  return {
    name: name?.trim(),
    description: description ?? '',
    imageUrl: imageUrl || null,
    eventDate: toLDT(startDate, startTime),       // LocalDateTime
    eventEndDate: toLDT(endDate, endTime),        // LocalDateTime (REQUIRED)
    location: location?.trim(),
    type,                                         // "MEMBER_ONLY" | "OPEN_TO_ALL"
  };
}





