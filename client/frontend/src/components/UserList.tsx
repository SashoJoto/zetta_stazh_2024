import React from 'react';
import { User } from '../types';

interface UserListProps {
    users: User[];
    onSelectUser: (userId: string) => void;
}

const UserList: React.FC<UserListProps> = ({ users, onSelectUser }) => {
    return (
        <div className="user-list">
            <h2>Users</h2>
            <ul>
                {users.map(user => (
                    <li key={user.nickName} onClick={() => onSelectUser(user.nickName)}>
                        <img src="../img/user_icon.png" alt={user.fullName} />
                        <span>{user.fullName}</span>
                        <span className="nbr-msg hidden">0</span>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default UserList;