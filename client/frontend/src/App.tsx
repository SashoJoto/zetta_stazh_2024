// src/App.tsx
import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Header from '../components/Header';
import DatingPage from '../pages/DatingPage';
import MessagesPage from '../pages/MessagesPage';
import ProfilePage from '../pages/ProfilePage';
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import { useAuth } from '../contexts/AuthContext';

const App: React.FC = () => {
    const { user } = useAuth();

    return (
        <div style={{ minHeight: '100vh' }}>
            <Header />
            <Routes>
                <Route path="/" element={<DatingPage />} />
                <Route path="/messages" element={<MessagesPage />} />
                <Route path="/profile" element={user ? <ProfilePage /> : <Navigate to="/login" />} />
                <Route path="/login" element={!user ? <LoginPage /> : <Navigate to="/profile" />} />
                <Route path="/register" element={!user ? <RegisterPage /> : <Navigate to="/profile" />} />
            </Routes>
        </div>
    );
};

export default App;
