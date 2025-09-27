package com.nan19.roleplaying.mapper;

import com.nan19.roleplaying.pojo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Insert("INSERT INTO messages (conversation_id, sender, content, created_at) " +
            "VALUES (#{conversation.id}, #{sender}, #{content}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Message message);

    @Select("SELECT * FROM messages WHERE conversation_id = #{conversationId} ORDER BY created_at ASC")
    List<Message> findByConversationId(@Param("conversationId") Long conversationId);
}