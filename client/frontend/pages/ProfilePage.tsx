import React, { useState } from 'react';
import '../src/ProfilePage.css'; // Import your CSS file for styling

const ProfilePage: React.FC = () => {
    // Dummy user data for demonstration
    const [user, setUser] = useState<{ username?: string; email: string } | null>(null);

    // Dummy form states
    const [signUpForm, setSignUpForm] = useState({ username: '', email: '', password: '' });
    const [signInForm, setSignInForm] = useState({ email: '', password: '' });

    const handleSignUp = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // Handle sign-up logic (e.g., API call, validation)
        console.log('Signing up with:', signUpForm);
        // Simulate user creation
        setUser({ username: signUpForm.username, email: signUpForm.email });
    };

    const handleSignIn = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // Handle sign-in logic (e.g., API call, validation)
        console.log('Signing in with:', signInForm);
        // Simulate user authentication
        setUser({ email: signInForm.email });
    };

    const handleSignOut = () => {
        // Handle sign-out logic (e.g., clear local storage, reset state)
        setUser(null);
    };

    return (
        <div className="profile-page">
            <div className="profile-container">
                {user ? (
                    <div className="profile-info">
                        <h2>Account Information</h2>
                        <p><strong>Email:</strong> {user.email}</p>
                        {user.username && <p><strong>Username:</strong> {user.username}</p>}
                        <button className="btn btn-primary" onClick={handleSignOut}>Sign Out</button>
                    </div>
                ) : (
                    <div className="auth-forms">
                        <div className="sign-up-form">
                            <h2>Sign Up</h2>
                            <form onSubmit={handleSignUp}>
                                <input
                                    type="text"
                                    placeholder="Username"
                                    value={signUpForm.username}
                                    onChange={(e) => setSignUpForm({ ...signUpForm, username: e.target.value })}
                                />
                                <input
                                    type="email"
                                    placeholder="Email"
                                    value={signUpForm.email}
                                    onChange={(e) => setSignUpForm({ ...signUpForm, email: e.target.value })}
                                />
                                <input
                                    type="password"
                                    placeholder="Password"
                                    value={signUpForm.password}
                                    onChange={(e) => setSignUpForm({ ...signUpForm, password: e.target.value })}
                                />
                                <button className="btn btn-primary" type="submit">Sign Up</button>
                            </form>
                        </div>
                        <div className="sign-in-form">
                            <h2>Sign In</h2>
                            <form onSubmit={handleSignIn}>
                                <input
                                    type="email"
                                    placeholder="Email"
                                    value={signInForm.email}
                                    onChange={(e) => setSignInForm({ ...signInForm, email: e.target.value })}
                                />
                                <input
                                    type="password"
                                    placeholder="Password"
                                    value={signInForm.password}
                                    onChange={(e) => setSignInForm({ ...signInForm, password: e.target.value })}
                                />
                                <button className="btn btn-primary" type="submit">Sign In</button>
                            </form>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default ProfilePage;
