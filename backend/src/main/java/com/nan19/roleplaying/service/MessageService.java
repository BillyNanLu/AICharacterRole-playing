package com.nan19.roleplaying.service;

import com.nan19.roleplaying.pojo.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Message message);
    List<Message> getMessages(Long conversationId);
}
