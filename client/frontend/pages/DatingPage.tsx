import React from 'react';
import Slider from 'react-slick';
import datePic from '../src/assets/dating_pic.png';
import datePic1 from '../src/assets/toni_kalashnika.png';
import datePic2 from '../src/assets/toni_kalashnika_2.png';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import '../src/DatingPage.css';

const cardsData = [
    { id: 1, image: datePic, name: 'Toni Kalashnika', age: 25, address: 'Filipovtsi', interests: 'reading, wanking, boxing' ,description: 'скъпа изглеждаш добре, ма не по+добре от хартията! за хартия бати лакомията. таа твойта бати мръсотията! ki + kilian по шията top тупалка, бати возията горчи, ама е бати вкусутията глей как блести, ебати газарията!' },
    { id: 2, image: datePic1, name: 'Toni Kalashnika', age: 25, address: 'Filipovtsi', interests: 'reading, wanking, boxing'  ,description: 'Bio for Toni Kalashnika' },
    { id: 3, image: datePic2, name: 'Toni Kalashnika', age: 25, address: 'Filipovtsi', interests: 'reading, wanking, boxing' ,description: 'Bio for Toni Kalashnika' },
];

const DatingPage: React.FC = () => {
    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: true
    };

    return (
        <div className="dating-page">
            <Slider {...settings}>
                {cardsData.map(card => (
                    <div className="date-pic-card" key={card.id}>
                        <img src={card.image} alt={`Card ${card.id}`} className="date-pic-image" />
                        <div className="date-pic-name">{card.name}, {card.age}</div>
                        <div className="date-pic-bio">
                            <div className="date-pic-icon" style={{fontSize:'20px'}}>
                                <div className="tashak">
                                    <img src="../src/assets/location.png" alt="location"/>
                                    <h2>Location</h2>
                                </div>
                                <div className="tashak-tekst">
                                    {card.address}
                                </div>
                            </div>
                            <div className="date-pic-icon" style={{fontSize:'20px'}}>
                                <div className="tashak">
                                    <img src="../src/assets/hobbies.png" alt="interests"/>
                                    <h2>Interests</h2>
                                </div>
                                <div className="tashak-tekst">
                                    {card.interests}
                                </div>
                            </div>
                            <div className="date-pic-icon" style={{fontSize:'20px'}}>
                                <div className="tashak">
                                    <img style={{marginRight:'10px'}} src="../src/assets/profile.png" alt="profile"/>
                                    <h2>Bio</h2>
                                </div>
                                <div className="tashak-tekst">
                                    {card.description}
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </Slider>
            <div style={{display: 'flex', position: 'absolute', bottom: '1%', width:'400px', justifyContent:'space-between'}}>
                <button className="butoncheta btn mr-2" style={{marginRight:'150px'}}>
                    <img src="../src/assets/decline.png" alt="decline" style={{width: '70px', height: '70px'}}/>
                </button>
                <button className=" butoncheta btn">
                    <img src="../src/assets/love.png" alt="love" style={{width: '50px', height: '50px'}}/>
                </button>
            </div>
        </div>
    );
};

export default DatingPage;


{/*<div style={{display: 'flex', position: 'absolute', bottom: '1%', width:'400px', justifyContent:'space-between'}}>
                <button className="butoncheta btn mr-2" style={{marginRight:'150px'}}>
                    <img src="../src/assets/decline.png" alt="decline" style={{width: '80px', height: '80px'}}/>
                </button>
                <button className=" butoncheta btn">
                    <img src="../src/assets/love.png" alt="love" style={{width: '60px', height: '60px'}}/>
                </button>
            </div>*/}