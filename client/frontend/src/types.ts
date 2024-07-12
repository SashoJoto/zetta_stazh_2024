export interface User {
    nickName: string;
    fullName: string;
    status: string;
}

export interface Message {
    senderId: string;
    recipientId?: string;
    content: string;
}