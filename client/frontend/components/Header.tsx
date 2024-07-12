import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../src/assets/zettalove_logo.png';
import profilePic from '../src/assets/profile_pic_empty.png';
import messageIcon from '../src/assets/chat.png';

const Header: React.FC = () => {
    return (
        <nav className="navbar navbar-light" style={{ backgroundColor: 'lightpink' }}>
            <div className="container d-flex justify-content-between align-items-center">
                <Link to="/profile">
                    <img src={profilePic} alt="Profile" className="rounded-circle" style={{ width: '30px', height: '30px' }} />
                </Link>

                <Link to="/">
                    <img src={logo} alt="Logo" style={{ width: '150px', height: '60px' }} />
                </Link>
                <Link to="/messages">
                    <img src={messageIcon} alt="Messages" style={{ width: '30px', height: '30px' }} />
                </Link>
            </div>
        </nav>
    );
};

export default Header;
