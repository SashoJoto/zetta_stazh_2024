import React from 'react';
import { Route, Navigate, RouteProps } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const PrivateRoute: React.FC<RouteProps> = ({ children }) => {
    const { user } = useAuth();

    return user ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
