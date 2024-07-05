import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import '../src/RegisterPage.css';

const RegisterPage: React.FC = () => {
    const [dob, setDob] = useState<Date | null>(null);
    const [error, setError] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        if (!dob || !isValidAge(dob)) {
            setError('You must be at least 18 years old to register.');
            return;
        }

        // Perform the registration logic here
        // ...

        setError('');
    };

    const isValidAge = (dob: Date) => {
        const today = new Date();
        let age = today.getFullYear() - dob.getFullYear();
        const monthDifference = today.getMonth() - dob.getMonth();

        if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < dob.getDate())) {
            age--;
        }

        return age >= 18;
    };

    return (
        <div className="container">
            <div className="registration-container">
                <h1>Register</h1>
                <form id="registrationForm" onSubmit={handleSubmit}>
                    <div className="input-group">
                        <label htmlFor="firstName">First Name</label>
                        <input className="input-register" type="text" id="firstName" name="firstName" required />
                    </div>
                    <div className="input-group">
                        <label htmlFor="lastName">Last Name</label>
                        <input className="input-register" type="text" id="lastName" name="lastName" required />
                    </div>
                    <div className="input-group">
                        <label htmlFor="username">Username</label>
                        <input className="input-register" type="text" id="username" name="username" required />
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input className="input-register" type="password" id="password" name="password" required />
                    </div>
                    <div className="input-group">
                        <label htmlFor="dob">Date of Birth</label>
                        <DatePicker
                            selected={dob}
                            onChange={(date) => setDob(date)}
                            dateFormat="yyyy/MM/dd"
                            className="input-register"
                            placeholderText="Select Date of Birth"
                            maxDate={new Date()}
                            showYearDropdown
                            scrollableYearDropdown
                            yearDropdownItemNumber={100}
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="gender">Gender</label>
                        <select className="input-register" id="gender" name="gender" required>
                            <option value="">Select Gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="other">Other</option>
                        </select>
                    </div>
                    <div className="input-group">
                        <label htmlFor="images">Images</label>
                        <input className="input-register" type="file" id="images" name="images" multiple accept="image/*" />
                    </div>
                    <div className="input-group">
                        <label htmlFor="bio">Bio</label>
                        <textarea
                            className="input-register"
                            id="bio"
                            name="bio"
                            placeholder="Tell us about yourself..."
                            required
                        ></textarea>
                    </div>
                    <button className="button-register" type="submit">Register</button>
                    {error && <p className="error-message">{error}</p>}
                </form>
                <p style={{ fontSize: '12px', marginTop: '10px', textAlign: 'left' }}>
                    Already have an account? <Link to="/login" id="link-to-login">Login here</Link>
                </p>
            </div>
        </div>
    );
};

export default RegisterPage;
