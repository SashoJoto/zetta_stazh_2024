import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../src/assets/zettalove_logo.png';
import profilePic from '../src/assets/profile_pic_empty.png';

const Header: React.FC = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <Link className="navbar-brand" to="/">
                <img src={logo} alt="Logo" style={{ width: '40px', height: '40px' }} />
            </Link>
            <div className="collapse navbar-collapse">
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link" to="/profile">
                            <img src={profilePic} alt="Profile" className="rounded-circle" style={{ width: '40px', height: '40px' }} />
                        </Link>
                    </li>
                </ul>
            </div>
        </nav>
    );
};

export default Header;
