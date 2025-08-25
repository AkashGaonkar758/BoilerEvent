# BoilerEvent 

BoilerEvent is a social media web app for events at Purdue.  
Users can ** post, scroll & like ** purdue events  
The app is built with a **React frontend** and a **Spring Boot + PostgreSQL backend** with JWT authentication.

---

## Features
- **JWT Authentication**: Secure login & registration
- **Events Feed**: Browse all events in a clean, card-style layout
- **Create Events**: Add new events with relevant details
- **Like Events**: Interact with event posts
- **Responsive Frontend**: React + Tailwind for a modern UI
- **Automatic Cleanup** – expired events are removed nightly
- **Smart Event Feed** – sorted by popularity (likes) and proximity to start time
- **Preloaded Events** – demo events included for easy testing
---

## Authentication
- Users must register/login with a valid @purdue.edu email
- JWT **access token** stored in localStorage for API requests
- JWT **refresh token** stored in secure httpOnly cookies for session renewal
---

## Tech Stack
### Frontend
- React
- Axios (for API requests)
- Tailwind CSS (UI styling)

### Backend
- Spring Boot
- PostgreSQL
- JWT (JSON Web Token) Authentication

---

## Getting Started
-will update later

## Future Improvements:
- Add email verification & password reset via email
- Deploy the app to the cloud (AWS)
- Containerize backend & frontend with Docker

---

## License
This project is licensed under the **MIT License** – feel free to use and modify for personal or educational purposes.

---

## Author
**Akash Gaonkar**  


