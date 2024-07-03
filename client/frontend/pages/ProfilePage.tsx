// src/pages/ProfilePage.tsx
import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';

const ProfilePage: React.FC = () => {
    const { user, logout } = useAuth();
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState(user);

    if (!user) {
        return <p>Loading...</p>;
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value, files } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: files ? files[0] : value
        }));
    };

    const handleSave = (e: React.FormEvent) => {
        e.preventDefault();
        // Save the updated user information logic
        setIsEditing(false);
    };

    return (
        <div className="container mt-5">
            <h2>Profile</h2>
            {!isEditing ? (
                <>
                    <p><strong>First Name:</strong> {user.firstName}</p>
                    <p><strong>Last Name:</strong> {user.lastName}</p>
                    <p><strong>Email:</strong> {user.email}</p>
                    <p><strong>Age:</strong> {user.age}</p>
                    <p><strong>Sex:</strong> {user.sex}</p>
                    <p><strong>Bio:</strong> {user.bio}</p>
                    {user.image && <img src={URL.createObjectURL(user.image)} alt="Profile" width="100" />}
                    <button className="btn btn-primary mt-3" onClick={() => setIsEditing(true)}>Edit</button>
                    <button className="btn btn-secondary mt-3" onClick={logout}>Logout</button>
                </>
            ) : (
                <form onSubmit={handleSave}>
                    <div className="mb-3">
                        <label htmlFor="firstName" className="form-label">First Name</label>
                        <input type="text" className="form-control" id="firstName" name="firstName" value={formData?.firstName || ''} onChange={handleChange} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="lastName" className="form-label">Last Name</label>
                        <input type="text" className="form-control" id="lastName" name="lastName" value={formData?.lastName || ''} onChange={handleChange} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">E-mail</label>
                        <input type="email" className="form-control" id="email" name="email" value={formData?.email || ''} onChange={handleChange} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="age" className="form-label">Age</label>
                        <input type="number" className="form-control" id="age" name="age" value={formData?.age || ''} onChange={handleChange} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="sex" className="form-label">Sex</label>
                        <input type="text" className="form-control" id="sex" name="sex" value={formData?.sex || ''} onChange={handleChange} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="bio" className="form-label">Bio</label>
                        <textarea className="form-control" id="bio" name="bio" value={formData?.bio || ''} onChange={handleChange} required></textarea>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="image" className="form-label">Image</label>
                        <input type="file" className="form-control" id="image" name="image" onChange={handleChange} />
                    </div>
                    <button type="submit" className="btn btn-primary">Save</button>
                    <button type="button" className="btn btn-secondary" onClick={() => setIsEditing(false)}>Cancel</button>
                </form>
            )}
        </div>
    );
};

export default ProfilePage;
