import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/zettalove_logo.png';
import profilePic from '../assets/profile_pic_empty.png';
import messageIcon from '../assets/chat.png';

const Header: React.FC = () => {
    const tokenExists = localStorage.getItem('token');
    return (
        <nav className="navbar navbar-light" style={{ backgroundColor: 'lightpink' }}>
            <div className="container d-flex justify-content-between align-items-center">
                {tokenExists && (
                    <>
                        <Link to="/profile">
                            <img src={profilePic} alt="Profile" className="rounded-circle"
                                 style={{width: '30px', height: '30px'}}/>
                        </Link>
                    </>
                )}
                <div className="d-flex justify-content-center flex-grow-1">
                    <Link to="/">
                        <img src={logo} alt="Logo" style={{width: '150px', height: '60px'}}/>
                    </Link>
                </div>
                {tokenExists && (
                    <>
                        <Link to="/messages">
                            <img src={messageIcon} alt="Messages" style={{width: '30px', height: '30px'}}/>
                        </Link>
                    </>
                )}
            </div>
        </nav>
    );
};

export default Header;
