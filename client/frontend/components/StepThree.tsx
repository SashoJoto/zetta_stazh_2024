// import React, { useState, useCallback } from 'react';
// import { useDropzone, DropzoneOptions } from 'react-dropzone';
// import { Link } from 'react-router-dom';
// import './StepThree.css';
//
// interface StepThreeProps {
//     prevStep: () => void;
//     handleChange: (input: string) => (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
//     handleImageUpload: (uploadedImages: File[]) => void;
//     values: {
//         bio: string;
//         images: File[];
//     };
// }
//
// const StepThree: React.FC<StepThreeProps> = ({ prevStep, handleChange, handleImageUpload, values }) => {
//     const [filePreviews, setFilePreviews] = useState<string[]>([]);
//
//     const onDrop = useCallback((acceptedFiles: File[]) => {
//         setFilePreviews(acceptedFiles.map(file => URL.createObjectURL(file)));
//         handleImageUpload(acceptedFiles);
//     }, [handleImageUpload]);
//
//     const dropzoneOptions: DropzoneOptions = {
//         onDrop,
//         accept: 'image/*',
//         multiple: true,
//     };
//
//     const { getRootProps, getInputProps, isDragActive } = useDropzone(dropzoneOptions);
//
//     const continueToNext = (e: React.FormEvent) => {
//         e.preventDefault();
//         // Submit final form data to backend
//         // e.g., submitFormData(values);
//     };
//
//     return (
//         <div className="container">
//             <div className="registration-container">
//                 <form onSubmit={continueToNext}>
//                     <h1>Step 3: Profile</h1>
//                     <div className="input-group">
//                         <label>Bio:</label>
//                         <textarea
//                             className="input-register"
//                             onChange={handleChange('bio')}
//                             value={values.bio}
//                             required
//                         />
//                     </div>
//                     <div className="input-group">
//                         <label>Images:</label>
//                         <div {...getRootProps()} className={`dropzone ${isDragActive ? 'active' : ''}`}>
//                             <input {...getInputProps()} />
//                             {isDragActive ? (
//                                 <p>Drop the files here ...</p>
//                             ) : (
//                                 <p>Drag 'n' drop some files here, or click to select files</p>
//                             )}
//                         </div>
//                         <div className="previews">
//                             {filePreviews.map((file, index) => (
//                                 <img key={index} src={file} alt={`Preview ${index}`} className="preview-image" />
//                             ))}
//                         </div>
//                     </div>
//                     <div className="buttons-back-next">
//                         <button className="button-register" type="button" onClick={prevStep} style={{ marginRight: '80px' }}>
//                             Back
//                         </button>
//                         <button className="button-register" type="submit">
//                             Submit
//                         </button>
//                     </div>
//                     <p style={{ fontSize: '12px', marginTop: '10px', textAlign: 'left' }}>
//                         Already have an account? <Link to="/login" id="link-to-login">Login here</Link>
//                     </p>
//                 </form>
//             </div>
//         </div>
//     );
// };
//
// export default StepThree;
import React from 'react';
import {Link} from "react-router-dom";

interface StepThreeProps {
    prevStep: () => void;
    handleChange: (input: string) => (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
    handleImageUpload: (uploadedImages: File[]) => void;
    values: {
        bio: string;
        images: File[];
    };
}

const StepThree: React.FC<StepThreeProps> = ({ prevStep, handleChange, handleImageUpload, values }) => {
    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            handleImageUpload(Array.from(e.target.files));
        }
    };

    const continueToNext = (e: React.FormEvent) => {
        e.preventDefault();
        // Submit final form data to backend
        // e.g., submitFormData(values);
    };

    return (
        <div className="container">
            <div className="registration-container">
                <form onSubmit={continueToNext}>
                    <h1>Step 3: Profile</h1>
                    <div className="input-group">
                        <label>Bio:</label>
                        <textarea className="input-register" onChange={handleChange('bio')} value={values.bio} required />
                    </div>
                    <div className="input-group">
                        <label>Images:</label>
                        <input type="file" multiple onChange={handleImageChange} required />
                    </div>
                    {/*<button type="button" onClick={prevStep}>Back</button>*/}
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