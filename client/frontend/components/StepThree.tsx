import React from 'react';
import { Link } from 'react-router-dom';

interface StepThreeProps {
    prevStep: () => void;
    handleChange: (input: string) => (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
    handleImageUpload: (uploadedImages: File[]) => void;
    values: {
        images: File[];
    };
    handleFinalSubmit: () => void;
}

const StepThree: React.FC<StepThreeProps> = ({
                                                 prevStep,
                                                 handleChange,
                                                 handleImageUpload,
                                                 values,
                                                 handleFinalSubmit,
                                             }) => {
    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            handleImageUpload(Array.from(e.target.files));
        }
    };

    const submitForm = async (e: React.FormEvent) => {
        e.preventDefault();
        await handleFinalSubmit();
    };

    return (
        <div className="container">
            <div className="registration-container">
                <form onSubmit={submitForm}>
                    <h1>Step 3: Images</h1>
                    <div className="input-group">
                        <label>Upload images of yourself</label>
                        <input type="file" multiple onChange={handleImageChange} required />
                    </div>
                    <button className="button-register" type="submit">Submit</button>
                    <p style={{ fontSize: '12px', marginTop: '10px', textAlign: 'left' }}>
                        Already have an account? <Link to="/login" id="link-to-login">Login here</Link>
                    </p>
                </form>
            </div>
        </div>
    );
};

export default StepThree;
