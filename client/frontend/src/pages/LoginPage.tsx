import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../contexts/AuthContext.tsx';
import '../LoginPage.css';
import {keycloak_url} from "../constants/server_contants.ts";

const LoginPage: React.FC = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');

        const params = new URLSearchParams();
        params.append('username', email);
        params.append('password', password);
        params.append('grant_type', 'password');
        params.append('client_id', 'zettalove-rest-api-auth');

        try {
            const response = await axios.post(keycloak_url, params, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            const { access_token } = response.data;

            login({ email }, access_token);

            navigate('/');
            window.location.reload();
        } catch (err) {
            setError('Login failed. Please check your credentials and try again.');
        }
    };

    return (
        <div className="container">
            <div className="login-container">
                <h1>Login</h1>
                <form id="loginForm" onSubmit={handleSubmit}>
                    <div className="input-group">
                        <label htmlFor="email">Email</label>
                        <input
                            className="input-login"
                            type="text"
                            id="email"
                            name="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input
                            className="input-login"
                            type="password"
                            id="password"
                            name="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button className="button-login" type="submit">Login</button>
                </form>
                {error && <p className="error-message">{error}</p>}
                <p style={{ fontSize: '12px', marginTop: '10px', textAlign: 'left' }}>
                    New user?
                    <Link to="/register" id="link-to-register">Register here</Link>
                </p>
            </div>
        </div>
    );
}

export default LoginPage;
