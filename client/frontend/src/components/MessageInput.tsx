import React, { useState } from 'react';

interface MessageInputProps {
    onSendMessage: (text: string) => void;
}

const MessageInput: React.FC<MessageInputProps> = ({ onSendMessage }) => {
    const [text, setText] = useState<string>('');

    const handleSend = (event: React.FormEvent) => {
        event.preventDefault();
        onSendMessage(text);
        setText('');
    };

    return (
        <form onSubmit={handleSend}>
            <input
                type="text"
                value={text}
                onChange={(e) => setText(e.target.value)}
                placeholder="Type a message"
                required
            />
            <button type="submit">Send</button>
        </form>
    );
};

export default MessageInput;