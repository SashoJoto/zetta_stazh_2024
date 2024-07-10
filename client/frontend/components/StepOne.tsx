import React from 'react';
import { Link, Navigate } from 'react-router-dom';

interface StepOneProps {
    nextStep: () => void;
    handleChange: (input: string) => (e: React.ChangeEvent<HTMLInputElement>) => void;
    values: {
        firstName: string;
        lastName: string;
        email: string;
        password: string;
    };
}

const StepOne: React.FC<StepOneProps> = ({ nextStep, handleChange, values }) => {
    const continueToNext = (e: React.FormEvent) => {
        e.preventDefault();
        nextStep();
    };

    return (
        <div className="container">
            <div className="registration-container">
                <form onSubmit={continueToNext}>
                    <h1>Register</h1>
                    <div className="input-group">
                        <label>First Name:</label>
                        <input className="input-register" type="text" onChange={handleChange('firstName')} value={values.firstName} required />
                    </div>
                    <div className="input-group">
                        <label>Last Name:</label>
                        <input className="input-register" type="text" onChange={handleChange('lastName')} value={values.lastName} required />
                    </div>
                    <div className="input-group">
                        <label>Email:</label>
                        <input className="input-register" type="text" onChange={handleChange('email')} value={values.email} required />
                    </div>
                    <div className="input-group">
                        <label>Password:</label>
                        <input className="input-register" type="password" onChange={handleChange('password')} value={values.password} required />
                    </div>
                    <button className="button-register" type="submit">Next</button>
                    <p style={{ fontSize: '12px', marginTop: '10px', textAlign: 'left' }}>
                        Already have an account? <Link to="/login" id="link-to-login">Login here</Link>
                    </p>
                </form>
            </div>
        </div>
    );
};

export default StepOne;
