import React from 'react';

interface ActionButtonProps {
    onClick: () => void;
    label: string;
    className?: string;
}

const ActionButton: React.FC<ActionButtonProps> = ({ onClick, label, className }) => {
    return (
        <button className={`btn ${className}`} onClick={onClick}>
            {label}
        </button>
    );
};

export default ActionButton;
