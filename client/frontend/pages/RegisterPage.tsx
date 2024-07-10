import React, { useState } from 'react';
import StepOne from '../components/StepOne';
import StepTwo from '../components/StepTwo';
import StepThree from '../components/StepThree';
import axios from 'axios';
import '../src/RegisterPage.css';
import {keycloak_url, server_url} from "../constants/server_contants.ts";

interface FormData {
    first_name: string;
    last_name: string;
    email: string;
    password: string;
    dateOfBirth: Date | null;
    description: string;
    address: string;
    phoneNumber: string;
    desiredMinAge: number;
    desiredMaxAge: number;
    gender: string;
    desiredGender: string;
    interests: string[];
    images: File[];
}

const RegisterPage: React.FC = () => {
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState<FormData>({
        first_name: '',
        last_name: '',
        email: '',
        password: '',
        dateOfBirth: null,
        description: '',
        address: '',
        phoneNumber: '',
        desiredMinAge: 18,
        desiredMaxAge: 99,
        gender: '',
        desiredGender: '',
        interests: [],
        images: [],
    });

    const nextStep = async () => {
        if (step === 1) {
            await handleFirstStepSubmit();
        } else if (step === 2) {
            await handleSecondStepSubmit();
        }
        setStep(step + 1);
    };

    const prevStep = () => setStep(step - 1);

    const handleChange = (input: string) => (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        setFormData({ ...formData, [input]: e.target.value });
    };

    const handleDateChange = (date: Date) => {
        setFormData({ ...formData, dateOfBirth: date });
    };

    const handleInterestsChange = (selectedInterests: string[]) => {
        setFormData({ ...formData, interests: selectedInterests });
    };

    const handleImageUpload = (uploadedImages: File[]) => {
        setFormData({ ...formData, images: uploadedImages });
    };

    const handleFirstStepSubmit = async () => {
        try {
            await axios.post(server_url + "/users/register", {
                first_name: formData.first_name,
                last_name: formData.last_name,
                email: formData.email,
                password: formData.password,
            });

            const params = new URLSearchParams();
            params.append('username', formData.email);
            params.append('password', formData.password);
            params.append('grant_type', 'password');
            params.append('client_id', 'zettalove-rest-api-auth');

            const response = await axios.post(keycloak_url, params, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });
            // Extracting the access token from the response
            const { access_token } = response.data;
            console.log('Access token:', access_token);
            // Saving the access token to local storage
            localStorage.setItem('token', access_token);
            console.log('Token saved to local storage:', localStorage.getItem('token'));
        } catch (error) {
            console.error('Error during first step registration:', error);
        }
    };

    const handleSecondStepSubmit = async () => {
        try {
            const token = localStorage.getItem('token'); // Retrieve the token from local storage
            await axios.post(server_url + "/users/interests-setup", {
                dateOfBirth: formData.dateOfBirth,
                description: formData.description,
                address: formData.address,
                phoneNumber: formData.phoneNumber,
                desiredMinAge: formData.desiredMinAge,
                desiredMaxAge: formData.desiredMaxAge,
                gender: formData.gender.toUpperCase(),
                desiredGender: formData.desiredGender.toUpperCase(),
                interests: formData.interests,
            }, {
                headers: {
                    Authorization: `Bearer ${token}` // Include the token in the Authorization header
                }
            });
        } catch (error) {
            console.error('Error during second step registration:', error);
        }
    };

    const handleFinalSubmit = async () => {
        const formDataToSend = new FormData();
        /*formData.images.forEach((image) => {
            formDataToSend.append('images', image);
        });*/

        try {
            await axios.post(server_url + "/users/images-setup", formDataToSend, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
        } catch (error) {
            console.error('Error during image upload:', error);
        }
    };

    switch (step) {
        case 1:
            return (
                <StepOne
                    nextStep={nextStep}
                    handleChange={handleChange}
                    values={formData}
                />
            );
        case 2:
            return (
                <StepTwo
                    nextStep={nextStep}
                    prevStep={prevStep}
                    handleDateChange={handleDateChange}
                    handleChange={handleChange}
                    handleInterestsChange={handleInterestsChange}
                    values={formData}
                />
            );
        case 3:
            return (
                <StepThree
                    prevStep={prevStep}
                    handleChange={handleChange}
                    handleImageUpload={handleImageUpload}
                    values={formData}
                    handleFinalSubmit={handleFinalSubmit}
                />
            );
        default:
            return null;
    }
};

export default RegisterPage;
