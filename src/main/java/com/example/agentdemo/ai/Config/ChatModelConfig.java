package com.example.agentdemo.ai.Config;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatModelConfig {

    @Value("${langchain4j.community.dashscope.api-key}")
    private String apiKey;

    @Value("${langchain4j.community.dashscope.model-name}")
    private String modelName;
    @Bean
    public ChatModel chatModel(){
        return QwenChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }
    @Bean
    public StreamingChatModel streamingChatModel(){
        return QwenStreamingChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }
}
