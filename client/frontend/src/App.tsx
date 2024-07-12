import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Header from '../components/Header';
import DatingPage from '../pages/DatingPage';
import MessagesPage from '../pages/MessagesPage';
import ProfilePage from '../pages/ProfilePage';
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import PrivateRoute from '../components/PrivateRoute';

const App: React.FC = () => {

    return (
        <div style={{ minHeight: '100vh' }}>
            <Header />
            <Routes>
                <Route path="/login" element={!localStorage.getItem("token") ? <LoginPage /> : <DatingPage />} />
                <Route path="/register" element={!localStorage.getItem("token") ? <RegisterPage /> : <DatingPage />} />
                <Route path="/" element={localStorage.getItem("token") ? <DatingPage /> : <Navigate to="/login" />} />
                <Route path="/" element={<PrivateRoute />}>
                    <Route path="messages" element={<MessagesPage />} />
                    <Route path="profile" element={<ProfilePage />} />
                </Route>
            </Routes>
        </div>
    );
};

export default App;
