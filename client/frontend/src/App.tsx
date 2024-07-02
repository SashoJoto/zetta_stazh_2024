import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from '../components/Header';
import DatingPage from '../pages/DatingPage';
import MessagesPage from '../pages/MessagingPage';
import ProfilePage from '../pages/ProfilePage';

const App: React.FC = () => {
    return (
        <Router>
            <Header />
            <Routes>
                <Route path="/" element={<DatingPage />} />
                <Route path="/messages" element={<MessagesPage />} />
                <Route path="/profile" element={<ProfilePage />} />
            </Routes>
        </Router>
    );
};

export default App;
