import React, { useEffect, useState } from 'react';
import Slider from 'react-slick';
import axios from 'axios';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import '../src/DatingPage.css';
import { server_url } from "../constants/server_contants";
import { userModel } from '../models/UserModel';

// const cardsData = [
//     { id: 1, image: datePic, name: 'Toni Kalashnika', age: 25, address: 'Filipovtsi', interests: 'reading, wanking, boxing', description: 'скъпа изглеждаш добре, ма не по+добре от хартията! за хартия бати лакомията. таа твойта бати мръсотията! ki + kilian по шията top тупалка, бати возията горчи, ама е бати вкусутията глей как блести, ебати газарията!' },
//     { id: 2, image: datePic1, name: 'Toni Kalashnika', age: 25, address: 'Filipovtsi', interests: 'reading, wanking, boxing', description: 'Bio for Toni Kalashnika' },
//     { id: 3, image: datePic2, name: 'Toni Kalashnika', age: 25, address: 'Filipovtsi', interests: 'reading, wanking, boxing', description: 'Bio for Toni Kalashnika' },
// ];

const DatingPage: React.FC = () => {
    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: true,
        autoplay: true, // Enables auto sliding
        autoplaySpeed: 3000, // Auto slide every 2 seconds
        pauseOnHover: true // Pause on hover
    };

    const [recommendedUsers, setRecommendedUsers] = useState<userModel[]>([]);
    const [currentUserIndex, setCurrentUserIndex] = useState(0);
    const [recommendedUser, setRecommendedUser] = useState<userModel | null>(null);

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

    const swipeUser = async () => {
        const token = localStorage.getItem('token');
        try {
            const response = await axios.get(`${server_url}/users/self`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const { user_id } = response.data;
            await axios.post(`${server_url}/users/${user_id}/swipe/${recommendedUser?.id}`, null, {
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
            const response = await axios.get(`${server_url}/users/self`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const { user_id } = response.data;
            await axios.post(`${server_url}/users/${user_id}/like/${recommendedUser?.id}`, null, {
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
            setRecommendedUser(null); // No more users to show
        }
    };

    return (
        <div className="dating-page">
            {recommendedUser ? (
                <Slider {...settings}>
                    <div className="date-pic-card" key={recommendedUser.id}>
                        {/*<img src={card.image} alt={Card${card.id}}/>*/}
                        <div className="date-pic-name">{recommendedUser.first_name}, {recommendedUser.age}</div>
                        <div className="date-pic-bio">
                            <div className="date-pic-icon" style={{ fontSize: '20px' }}>
                                <div className="tashak">
                                    <h2>Location</h2>
                                </div>
                                <div className="tashak-tekst">
                                    {recommendedUser.address}
                                </div>
                            </div>
                            <div className="date-pic-icon" style={{ fontSize: '20px' }}>
                                <div className="tashak">
                                    <h2>Interests</h2>
                                </div>
                                <div className="tashak-tekst">
                                    {recommendedUser.interests}
                                </div>
                            </div>
                            <div className="date-pic-icon" style={{ fontSize: '20px' }}>
                                <div className="tashak">
                                    <h2>Bio</h2>
                                </div>
                                <div className="tashak-tekst">
                                    {recommendedUser.description}
                                </div>
                            </div>
                        </div>
                    </div>
                </Slider>
            ) : (
                <div>No more users to show</div>
            )}
            <div style={{ display: 'flex', position: 'absolute', bottom: '1%', width: '400px', justifyContent: 'space-between' }}>
                <button className="butoncheta btn mr-2" style={{ marginRight: '150px' }} onClick={swipeUser}>
                    <img src="../src/assets/decline.png" alt="decline" style={{ width: '70px', height: '70px' }} />
                </button>
                <button className=" butoncheta btn" onClick={likeUser}>
                    <img src="../src/assets/love.png" alt="love" style={{ width: '50px', height: '50px' }} />
                </button>
            </div>
        </div>
    );
};

export default DatingPage;
