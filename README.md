# BoilerEvent 

BoilerEvent is a social media web app for events at Purdue. Users can **post, scroll & like** purdue events.
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

### 1. Backend (Spring Boot)
  1. Navigate into the backend folder:
     ```bash
     cd backend
  2. Copy the example properties file into a working configuration:
     ```bash
     cp src/main/resources/application-example.properties src/main/resources/application.properties
     

  3. Open application.properties and fill in your details:
    Database settings → PostgreSQL URL, username, password
    JWT secret → secure random string for signing tokens
    Other configs → (e.g., server port if you want to override defaults)
      ```bash
     spring.datasource.url=jdbc:postgresql://localhost:5432/boilerevent
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     jwt.secret=replace_with_secure_secret
  4. Run the Spring Boot server.

Backend will start at http://localhost:8080

### 1. Frontend (React)
  1. Navigate into the frontend React app (inside the nested folder):
     ```bash
     cd frontend/frontend
  2. Install dependencies:
     ```bash
     npm install\
  3. Start the development server:
     ```bash
     npm run dev

Frontend will run at http://localhost:3000 and connect to the backend.

---

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


