import React from 'react';
import { Link } from 'react-router-dom';
import datePic from '../src/assets/dating_pic.png';

const DatingPage: React.FC = () => {
    return (
        <div className="container">
            <div className="text-center mt-5">
                <img src={datePic} alt="Sample Date" style={{ width: '300px', height: '300px' }} />
                <div className="mt-3">
                    <button className="btn btn-success mr-2">Accept</button>
                    <button className="btn btn-warning mr-2">Favorite</button>
                    <button className="btn btn-danger">Decline</button>
                </div>
            </div>
            <Link to="/messages">
                <button className="btn btn-primary position-fixed" style={{ bottom: '20px', right: '20px' }}>
                    Go to Messages
                </button>
            </Link>
        </div>
    );
};

export default DatingPage;
