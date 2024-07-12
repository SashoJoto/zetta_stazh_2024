import React, { useState, useEffect } from 'react';
import api from '../services/api';
import stompClient from '../services/socket';
import UserList from './UserList';
import MessageList from './MessageList';
import { User, Message } from '../types';
import axios from "axios";
import { UserModel } from "../models/UserModel.ts";
import { server_url } from "../constants/server_contants.ts";

const Chat: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [messages, setMessages] = useState<Message[]>([]);
    const [nickname, setNickname] = useState<string>('');
    const [fullname, setFullname] = useState<string>('');
    const [connected, setConnected] = useState<boolean>(false);
    const [selectedUserId, setSelectedUserId] = useState<string | null>(null);

    const fetchCurrentUser = async (): Promise<UserModel | null> => {
        try {
            const response = await axios.get(`${server_url}/users/self`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            });
            const user: UserModel = {
                id: response.data.id,
                firstName: response.data.firstName,
                lastName: response.data.lastName,
                description: response.data.description,
                address: response.data.address,
                phoneNumber: response.data.phoneNumber,
                gender: response.data.gender,
                desiredGender: response.data.desiredGender,
                dateOfBirth: response.data.dateOfBirth,
                age: response.data.age,
                interests: response.data.interests // Assuming interests are directly assignable
            };
            return user;
        } catch (error) {
            console.error('Error fetching current user:', error);
            return null;
        }
    };

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await api.get('/users');
                setUsers(response.data);
            } catch (error) {
                console.error('Error fetching users', error);
            }
        };
        fetchUsers();
    }, []);

    useEffect(() => {
        const getUserData = async () => {
            const userData = await fetchCurrentUser();
            if (userData) {
                setFullname(userData.firstName + " " + userData.lastName);
                setNickname(userData.id);
                connect(userData.id, userData.firstName + " " + userData.lastName);
            }
        };

        getUserData();
    }, []);

    useEffect(() => {
        if (connected) {
            stompClient.onConnect = () => {
                console.log('Connected to WebSocket');
                stompClient.subscribe(`/user/${nickname}/queue/messages`, onMessageReceived);
                stompClient.subscribe(`/user/public`, onMessageReceived);
                stompClient.publish({
                    destination: "/app/user.addUser",
                    body: JSON.stringify({ nickName: nickname, fullName: fullname, status: 'ONLINE' }),
                });
            };

            stompClient.onStompError = (error) => {
                console.error('STOMP error:', error);
                setConnected(false);
            };

            stompClient.activate();
        }

        return () => {
            if (connected) {
                stompClient.deactivate();
                setConnected(false);
            }
        };
    }, [connected, nickname, fullname]);

    const connect = async (nick: string, full: string) => {
        try {
            await api.post('/users', { nickName: nick, fullName: full });
            setConnected(true);
        } catch (error) {
            console.error('Error connecting user', error);
        }
    };

    const sendMessage = (event: React.FormEvent) => {
        event.preventDefault();
        const messageContent = (document.querySelector('#message') as HTMLInputElement).value.trim();
        if (messageContent && connected) {
            const chatMessage = {
                senderId: nickname,
                recipientId: selectedUserId,
                content: messageContent,
                timestamp: new Date(),
            };
            stompClient.publish({
                destination: "/app/chat",
                body: JSON.stringify(chatMessage),
            });
            displayMessage(nickname, messageContent);
            (document.querySelector('#message') as HTMLInputElement).value = '';
        }
    };

    const displayMessage = (senderId: string, content: string) => {
        setMessages((prevMessages) => [...prevMessages, { senderId, content }]);
    };

    const onMessageReceived = async (payload: any) => {
        await findAndDisplayConnectedUsers();
        const message = JSON.parse(payload.body);
        if (selectedUserId && selectedUserId === message.senderId) {
            displayMessage(message.senderId, message.content);
        }
    };

    const findAndDisplayConnectedUsers = async () => {
        try {
            const response = await api.get('/users');
            const connectedUsers = response.data.filter((user: User) => user.nickName !== nickname);
            setUsers(connectedUsers);
        } catch (error) {
            console.error('Error fetching connected users', error);
        }
    };

    const handleUserSelect = (userId: string) => {
        setSelectedUserId(userId);
        fetchAndDisplayUserChat(userId);
    };

    const fetchAndDisplayUserChat = async (userId: string) => {
        try {
            const response = await api.get(`/messages/${nickname}/${userId}`);
            setMessages(response.data);
        } catch (error) {
            console.error('Error fetching user chat', error);
        }
    };

    const onLogout = () => {
        if (connected) {
            stompClient.publish({
                destination: "/app/user.disconnectUser",
                body: JSON.stringify({ nickName: nickname, fullName: fullname, status: 'OFFLINE' }),
            });
            window.location.reload();
        }
    };

    return (
        <div className="chat-container">
            <div id="chat-page" className={connected ? '' : 'hidden'}>
                <div id="chat-messages">
                    <MessageList messages={messages} nickname={nickname} />
                </div>
                <div>
                    <UserList users={users} onSelectUser={handleUserSelect} />
                </div>
                <form id="messageForm" onSubmit={sendMessage}>
                    <input type="text" id="message" placeholder="Type a message" required />
                    <button type="submit">Send</button>
                </form>
                <button id="logout" onClick={onLogout}>Logout</button>
            </div>
        </div>
    );
};

export default Chat;