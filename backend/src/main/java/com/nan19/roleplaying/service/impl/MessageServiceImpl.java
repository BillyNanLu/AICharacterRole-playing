package com.nan19.roleplaying.service.impl;

import com.nan19.roleplaying.mapper.MessageMapper;
import com.nan19.roleplaying.pojo.Message;
import com.nan19.roleplaying.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Message sendMessage(Message message) {
        messageMapper.insert(message);
        return message;
    }

    @Override
    public List<Message> getMessages(Long conversationId) {
        return messageMapper.findByConversationId(conversationId);
    }
}
