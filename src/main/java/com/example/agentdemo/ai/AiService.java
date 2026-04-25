package com.example.agentdemo.ai;


import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;

import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    @Resource
    private ChatModel qwenChatModel;


    public String chat(String Message){
        UserMessage userMessage = UserMessage.from(Message);
        ChatResponse response = qwenChatModel.chat(userMessage);
        System.out.printf(response.aiMessage().text());
        return response.aiMessage().text();
    }

}
