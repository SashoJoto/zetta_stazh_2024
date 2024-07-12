import { Client } from '@stomp/stompjs';

const socketUrl = 'ws://localhost:8082/ws';
const stompClient = new Client({
    brokerURL: socketUrl,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    debug: (str) => {
        console.log(new Date(), str);
    },
});

export default stompClient;