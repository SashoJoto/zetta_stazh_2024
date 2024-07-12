import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext.tsx';
import { server_url } from "../constants/server_contants.ts";

interface StepTwoProps {
    nextStep: () => void;
    prevStep: () => void;
    handleDateChange: (date: Date | null) => void;
    handleChange: (input: string) => (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement | HTMLTextAreaElement>) => void;
    handleInterestsChange: (selectedInterests: string[]) => void;
    values: {
        dateOfBirth: Date | null;
        description: string;
        address: string;
        phoneNumber: string;
        desiredMinAge: number;
        desiredMaxAge: number;
        gender: string;
        desiredGender: string;
        interests: string[];
    };
}

const StepTwo: React.FC<StepTwoProps> = ({
                                             nextStep,
                                             prevStep,
                                             handleDateChange,
                                             handleChange,
                                             handleInterestsChange,
                                             values,
                                         }) => {
    const [availableInterests, setAvailableInterests] = useState<{ id: string; name: string }[]>([]);
    const { token } = useAuth();

    useEffect(() => {
        const fetchInterests = async () => {
            try {
                const response = await axios.get(server_url + "/interests");
                setAvailableInterests(response.data);
            } catch (error) {
                console.error('Error fetching interests:', error);
            }
        };

        fetchInterests();
    }, []); // Ensure token is in the dependency array

    const continueToNext = (e: React.FormEvent) => {
        e.preventDefault();
        nextStep();
    };

    const handleMultipleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const selectedOptions = Array.from(e.target.selectedOptions, option => option.value);
        handleInterestsChange(selectedOptions);
    };

    return (
        <div className="container">
            <div className="registration-container">
                <form onSubmit={continueToNext}>
                    <h1>Step 2: Personal Details</h1>
                    <div className="input-group">
                        <label>Date of Birth:</label>
                        <DatePicker
                            className="input-register"
                            selected={values.dateOfBirth}
                            onChange={(date) => handleDateChange(date)}
                            dateFormat="yyyy/MM/dd"
                            maxDate={new Date()}
                            showYearDropdown
                            scrollableYearDropdown
                            yearDropdownItemNumber={100}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label>Description:</label>
                        <textarea
                            className="input-register"
                            onChange={handleChange('description')}
                            value={values.description}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label>Address:</label>
                        <input
                            className="input-register"
                            type="text"
                            onChange={handleChange('address')}
                            value={values.address}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label>Phone Number:</label>
                        <input
                            className="input-register"
                            type="text"
                            onChange={handleChange('phoneNumber')}
                            value={values.phoneNumber}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label>Desired Minimum Age:</label>
                        <input
                            className="input-register"
                            type="number"
                            onChange={handleChange('desiredMinAge')}
                            value={values.desiredMinAge}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label>Desired Maximum Age:</label>
                        <input
                            className="input-register"
                            type="number"
                            onChange={handleChange('desiredMaxAge')}
                            value={values.desiredMaxAge}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label>Gender:</label>
                        <select
                            className="input-register"
                            onChange={handleChange('gender')}
                            value={values.gender}
                            required
                        >
                            <option value="">Select</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                        </select>
                    </div>
                    <div className="input-group">
                        <label>Desired Gender:</label>
                        <select
                            className="input-register"
                            onChange={handleChange('desiredGender')}
                            value={values.desiredGender}
                            required
                        >
                            <option value="">Select</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="BOTH">Both</option>
                        </select>
                    </div>
                    <div className="input-group">
                        <label>Interests:</label>
                        <select
                            className="input-register"
                            multiple
                            onChange={handleMultipleSelectChange}
                            value={values.interests}
                            required
                        >
                            {availableInterests.map((interest) => (
                                <option key={interest.id} value={interest.name}>{interest.name}</option>
                            ))}
                        </select>
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

export default StepTwo;
