import React from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {Link} from "react-router-dom";

interface StepTwoProps {
    nextStep: () => void;
    prevStep: () => void;
    handleDateChange: (date: Date) => void;
    handleChange: (input: string) => (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement>) => void;
    handleInterestsChange: (selectedInterests: string[]) => void;
    values: {
        dob: Date | null;
        sex: string;
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
                        <DatePicker className="input-register" selected={values.dob} onChange={handleDateChange} required />
                    </div>
                    <div className="input-group" style={{marginBottom:'0'}}>
                        <label>Sex:</label>
                        <select  className="input-group" onChange={handleChange('sex')} value={values.sex} required>
                            <option value="">Select</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
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
                            <option value="music">Music</option>
                            <option value="sports">Sports</option>
                            <option value="reading">Reading</option>
                            <option value="reading1">Reading</option>
                            <option value="reading2">Reading</option>
                            <option value="reading3">Reading</option>
                            <option value="reading4">Reading</option>
                            <option value="reading5">Reading</option>
                            <option value="reading6">Reading</option>
                            <option value="reading7">Reading</option>
                            <option value="reading8">Reading</option>

                        </select>
                    </div>{/*
                    <div className="buttons-back-next">
                        <button className="button-register" type="button" onClick={prevStep} style={{marginRight:'80px'}}>Back</button>
                        <button className="button-register" type="submit">Next</button>
                    </div>*/}
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
