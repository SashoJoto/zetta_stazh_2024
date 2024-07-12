import React, { useEffect, useState } from 'react';
import '../ProfilePage.css';
import axios from 'axios';
import { server_url } from "../constants/server_contants.ts";
import { UserModel } from "../models/UserModel.ts";

const fetchUserImage = async (userId: string): Promise<string | undefined> => {
    const token = localStorage.getItem('token');
    try {
        const response = await axios.get(`${server_url}/users/${userId}/images`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data[0].image_base64;
    } catch (error) {
        console.log("Error fetching user images:", error);
        return undefined;
    }
};

const fetchCurrentUser = async (): Promise<UserModel | null> => {
    try {
        const response = await axios.get(`${server_url}/users/self`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        const user: UserModel = {
            id: response.data.id,
            firstName: response.data.firstName,
            lastName: response.data.lastName,
            description: response.data.description,
            address: response.data.address,
            phoneNumber: response.data.phoneNumber,
            gender: response.data.gender,
            desiredGender: response.data.desiredGender,
            dateOfBirth: response.data.dateOfBirth,
            age: response.data.age,
            interests: response.data.interests // Assuming interests are directly assignable
        };
        return user;
    } catch (error) {
        console.error('Error fetching current user:', error);
        return null;
    }
};

const ProfilePage: React.FC = () => {
    const [user, setUser] = useState<UserModel | null>(null);
    const [userImage, setUserImage] = useState<string | undefined>(undefined);

    useEffect(() => {
        const getUserData = async () => {
            const userData = await fetchCurrentUser();
            setUser(userData);
            if (userData) {
                const imageData = await fetchUserImage(userData.id);
                setUserImage(imageData);
            }
        };

        getUserData();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        window.location.reload();
    };

    return (
        <div className="page-content page-container" id="page-content">
            <div className="padding" style={{ margin: '0' }}>
                <div className="row container d-flex justify-content-center" style={{ margin: '0' }}>
                    <div className="col-xl-6 col-md-12">
                        <div className="card user-card-full">
                            <div className="row m-l-0 m-r-0">
                                <div className="col-sm-4 bg-c-lite-green user-profile">
                                    <div className="card-block text-center text-white">
                                        <div className="m-b-25">
                                            <img
                                                src={userImage}
                                                className="img-radius" alt="User-Profile-Image"
                                            />
                                        </div>
                                        <h6 className="f-w-600" style={{ color: 'black' }}>
                                            {user ? `${user.firstName} ${user.lastName}` : 'Loading...'}
                                        </h6>
                                        <p style={{ color: 'black', fontSize: '10px' }}>
                                            {user ? user.phoneNumber : ''}
                                        </p>
                                        <i className="mdi mdi-square-edit-outline feather icon-edit m-t-10 f-16"></i>
                                    </div>
                                </div>
                                <div className="col-sm-8">
                                    <div className="card-block">
                                        <h6 className="m-b-20 p-b-5 b-b-default f-w-600">Information</h6>
                                        <div className="row">
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Age</p>
                                                <h6 className="text-muted f-w-400">{user ? user.age : 'Loading...'}</h6>
                                            </div>
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Date of Birth</p>
                                                <h6 className="text-muted f-w-400">{user ? user.dateOfBirth : 'Loading...'}</h6>
                                            </div>
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Address</p>
                                                <h6 className="text-muted f-w-400">{user ? user.address : 'Loading...'}</h6>
                                            </div>
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Looking to date</p>
                                                <h6 className="text-muted f-w-400">{user ? user.desiredGender : 'Loading...'}</h6>
                                            </div>
                                        </div>
                                        <h6 className="m-b-20 m-t-40 p-b-5 b-b-default f-w-600">Interests</h6>
                                        <p className="text-muted" style={{ color: 'black', fontSize: '15px' }}>
                                            {user ? user.interests.map(interest => interest.name).join(', ') : 'Loading...'}
                                        </p>
                                        <h6 className="m-b-20 m-t-40 p-b-5 b-b-default f-w-600">Bio</h6>
                                        <h6 className="text-muted f-w-400">
                                            {user ? user.description : 'Loading...'}
                                        </h6>
                                        <button onClick={handleLogout} style={{ marginTop: '20px', padding: '10px', backgroundColor: 'pink', color: 'white', border: 'none', borderRadius: '5px' }}>
                                            Logout
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;
