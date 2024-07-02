import React from 'react';
import {Link} from 'react-router-dom';
import logo from '../src/assets/zettalove_logo.png';
import profilePic from '../src/assets/profile_pic_empty.png';
import '../src/Header.css'

const Header: React.FC = () => {
    return (
        <div>
            <Link to="/">
                <div className="logo">
                    <img src={logo} alt="Logo" style={{width: '150px', height: '50px'}}/>
                </div>
            </Link>
            <Link to="/profile">
                <div className="profile-pic">
                    <img src="../src/assets/profile_pic_empty.png" alt="Profile pic" style={{borderRadius: '15px'}}/>
                </div>
            </Link>
        </div>
    );
};

export default Header;
