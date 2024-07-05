package dev.zettalove.zettalove.chat.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ChatUser {
    private final String nickName;
    private final String fullName;
    private Status status;
}
