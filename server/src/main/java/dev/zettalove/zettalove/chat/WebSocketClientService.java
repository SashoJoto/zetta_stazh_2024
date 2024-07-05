package dev.zettalove.zettalove.chat;

import dev.zettalove.zettalove.chat.entities.ChatMessage;
import dev.zettalove.zettalove.chat.entities.ChatUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class WebSocketClientService {

    private StompSession stompSession;
    private final RestTemplate restTemplate;

    @Value("${chat.socket_url}")
    String socketURL;

    @Value("${chat.users_url}")
    String usersURL;
    @PostConstruct
    public void connect() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        try {

            this.stompSession = stompClient.connect(socketURL, new WebSocketHttpHeaders(), new StompSessionHandlerAdapter() {}).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to WebSocket server: " + e.getMessage());
        }
    }

    public void addUserToChatSystem(ChatUser user) {
        restTemplate.postForObject(usersURL, user, ChatUser.class);
    }

    public void disconnectUser(ChatUser user) {
        stompSession.send("/app/user.disconnectUser", user);
    }

    public void sendMessage(ChatMessage message) {
        stompSession.send("/app/chat", message);
    }
}
