import React from 'react';
import { Message } from '../types';

interface MessageListProps {
    messages: Message[];
    nickname: string;
}

const MessageList: React.FC<MessageListProps> = ({ messages, nickname }) => {
    return (
        <div className="message-list">
            <h2>Messages</h2>
            <ul>
                {messages.map((message, index) => (
                    <li key={index} className={message.senderId === nickname ? 'sender' : 'receiver'}>
                        <p>{message.content}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default MessageList;