import React, { useEffect, useState } from 'react';
import Slider from 'react-slick';
import axios from 'axios';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import '../src/DatingPage.css';
import { server_url } from "../constants/server_contants";
import { userModel } from '../models/UserModel';

const DatingPage: React.FC = () => {
    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: true,
        autoplay: true,
        autoplaySpeed: 3000,
        pauseOnHover: true
    };

    const [recommendedUsers, setRecommendedUsers] = useState<userModel[]>([]);
    const [currentUserIndex, setCurrentUserIndex] = useState(0);
    const [recommendedUser, setRecommendedUser] = useState<userModel | null>(null);
    const [userImages, setUserImages] = useState<string[]>([]);

    useEffect(() => {
        const getRecommended = async () => {
            const token = localStorage.getItem('token');
            try {
                const response = await axios.get(`${server_url}/users/recommended-users`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setRecommendedUsers(response.data);
                setRecommendedUser(response.data[0]);
            } catch (err) {
                console.log("Error fetching recommended users:", err);
            }
        };

        getRecommended();
    }, []);

    useEffect(() => {
        if (recommendedUser) {
            fetchUserImages(recommendedUser.id);
        }
    }, [recommendedUser]);

    const fetchUserImages = async (userId: string) => {
        const token = localStorage.getItem('token');
        try {
            const response = await axios.get(`${server_url}/users/${userId}/images`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Assuming the base64 strings already include the necessary prefix
            setUserImages(response.data);
            console.log("User images:", response.data)
        } catch (error) {
            console.log("Error fetching user images:", error);
        }
    };

    const swipeUser = async () => {
        const token = localStorage.getItem('token');
        try {
            await axios.post(`${server_url}/users/swipe/${recommendedUser?.id}`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            updateCurrentUser();
        } catch (error) {
            console.log("Couldn't swipe user", error);
        }
    };

    const likeUser = async () => {
        const token = localStorage.getItem('token');
        try {
            await axios.post(`${server_url}/users/like/${recommendedUser?.id}`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            updateCurrentUser();
        } catch (error) {
            console.log("Couldn't like user", error);
        }
    };

    const updateCurrentUser = () => {
        const nextUserIndex = currentUserIndex + 1;
        if (nextUserIndex < recommendedUsers.length) {
            setCurrentUserIndex(nextUserIndex);
            setRecommendedUser(recommendedUsers[nextUserIndex]);
        } else {
            setRecommendedUser(null);
        }
    };

    return (
        <div className="dating-page">
            {recommendedUser ? (
                <Slider {...settings}>
                    {userImages.map((image, index) => (
                        <div className="date-pic-card" key={index}>
                            <img src={image} alt={`User ${recommendedUser?.firstName}`} className="user-image"/>
                            <div className="date-pic-name">{recommendedUser.firstName}, {recommendedUser.age}</div>
                            <div className="date-pic-bio">
                                <div className="date-pic-icon" style={{fontSize: '20px'}}>
                                    <div className="tashak">
                                        <h2>Location</h2>
                                    </div>
                                    <div className="tashak-tekst">
                                        {recommendedUser.address}
                                    </div>
                                </div>
                                <div className="date-pic-icon" style={{fontSize: '20px'}}>
                                    <div className="tashak">
                                        <h2>Interests</h2>
                                    </div>
                                    <div className="tashak-tekst">
                                        {recommendedUser.interests.map(interest => interest.name).join(', ')}
                                    </div>
                                </div>
                                <div className="date-pic-icon" style={{fontSize: '20px'}}>
                                    <div className="tashak">
                                        <h2>Bio</h2>
                                    </div>
                                    <div className="tashak-tekst">
                                        {recommendedUser.description}
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </Slider>
            ) : (
                <div>No more users to show</div>
            )}
            {recommendedUser && (
                <div style={{ display: 'flex', position: 'absolute', bottom: '1%', width: '400px', justifyContent: 'space-between' }}>
                    <button className="butoncheta btn mr-2" style={{ marginRight: '150px' }} onClick={swipeUser}>
                        <img src="../src/assets/decline.png" alt="decline" style={{ width: '70px', height: '70px' }} />
                    </button>
                    <button className=" butoncheta btn" onClick={likeUser}>
                        <img src="../src/assets/love.png" alt="love" style={{ width: '50px', height: '50px' }} />
                    </button>
                </div>
            )}
        </div>
    );
};

export default DatingPage;