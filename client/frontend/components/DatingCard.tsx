import React from 'react';

interface DatingCardProps {
    imageUrl: string;
    onAccept: () => void;
    onFavorite: () => void;
    onDecline: () => void;
}

const DatingCard: React.FC<DatingCardProps> = ({ imageUrl, onAccept, onFavorite, onDecline }) => {
    return (
        <div className="card" style={{ width: '18rem' }}>
            <img src={imageUrl} className="card-img-top" alt="User" />
            <div className="card-body">
                <button className="btn btn-success" onClick={onAccept}>Accept</button>
                <button className="btn btn-warning" onClick={onFavorite}>Favorite</button>
                <button className="btn btn-danger" onClick={onDecline}>Decline</button>
            </div>
        </div>
    );
};

export default DatingCard;
