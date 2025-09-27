package com.nan19.roleplaying.controller;

import com.nan19.roleplaying.pojo.Conversation;
import com.nan19.roleplaying.pojo.Message;
import com.nan19.roleplaying.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin // 确保跨域
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        // 这里 message 需要包含 conversation.id
        return messageService.sendMessage(message);
    }

    @GetMapping("/{conversationId}")
    public List<Message> getMessages(@PathVariable Long conversationId) {
        return messageService.getMessages(conversationId);
    }
}