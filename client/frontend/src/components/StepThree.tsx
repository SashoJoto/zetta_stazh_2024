import React from 'react';

interface StepThreeProps {
    prevStep: () => void;
    handleChange: (input: string) => (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => void;
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
    const continueToNext = (e: React.FormEvent) => {
        e.preventDefault();
        handleFinalSubmit();
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            handleImageUpload(Array.from(e.target.files));
        }
    };

    return (
        <div className="container">
            <div className="registration-container">
                <form onSubmit={continueToNext}>
                    <h1>Step 3: Upload Images</h1>
                    <div className="input-group">
                        <label>Images:</label>
                        <input
                            className="input-register"
                            type="file"
                            onChange={handleFileChange}
                            multiple
                            required
                        />
                    </div>
                    <button className="button-register" type="submit">Submit</button>
                    <button className="button-register" type="button" onClick={prevStep}>Back</button>
                </form>
            </div>
        </div>
    );
};

export default StepThree;
