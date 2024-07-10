import React, { useState } from 'react';
import StepOne from '../components/StepOne';
import StepTwo from '../components/StepTwo';
import StepThree from '../components/StepThree';
import axios from 'axios';
import '../src/RegisterPage.css';

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
            await axios.post('http://localhost:8081/api/v1/users/register', {
                first_name: formData.first_name,
                last_name: formData.last_name,
                email: formData.email,
                password: formData.password,
            });
        } catch (error) {
            console.error('Error during first step registration:', error);
        }
    };

    const handleSecondStepSubmit = async () => {
        try {
            await axios.post('http://localhost:8081/api/v1/users/interests-setup', {
                dateOfBirth: formData.dateOfBirth,
                description: formData.description,
                address: formData.address,
                phoneNumber: formData.phoneNumber,
                desiredMinAge: formData.desiredMinAge,
                desiredMaxAge: formData.desiredMaxAge,
                gender: formData.gender,
                desiredGender: formData.desiredGender,
                interests: formData.interests,
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
            await axios.post('http://localhost:8081/api/v1/users/images-setup', formDataToSend, {
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
