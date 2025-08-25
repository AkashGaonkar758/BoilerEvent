import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import FeedPage from './pages/FeedPage';
import CreateEventPage from './pages/CreateEventPage';
import EditEventPage from './pages/EditEventPage';
import ProfilePage from './pages/ProfilePage';
import LogoutPage from './pages/LogoutPage';
import "./styles/theme.css";


function App() {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/feed" element={<FeedPage />} />
      <Route path="/create" element={<CreateEventPage />} />
      <Route path="/edit-event/:id" element={<EditEventPage />} />
      <Route path="/profile" element={<ProfilePage />} />
      <Route path="/logout" element = {<LogoutPage/>} />
    </Routes>
  );
}
export default App;
