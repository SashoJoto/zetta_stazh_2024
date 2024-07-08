import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Header from '../components/Header';
import DatingPage from '../pages/DatingPage';
import MessagesPage from '../pages/MessagesPage';
import ProfilePage from '../pages/ProfilePage';
import { useAuth } from '../contexts/AuthContext';
import LoginPage from '../pages/LoginPage'
import RegisterPage from '../pages/RegisterPage'

const App: React.FC = () => {
    const { user } = useAuth();

    return (
        <div style={{ minHeight: '100vh' }}>
            <Header />
            <Routes>
                <Route path="/" element={<DatingPage />} />
                <Route path="/messages" element={<MessagesPage />} />
                <Route path="/profile" element={<ProfilePage/>} />
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/register" element={<RegisterPage/>}/>
            </Routes>
        </div>
    );
};

export default App;
