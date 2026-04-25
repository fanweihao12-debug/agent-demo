package com.example.agentdemo.ai.Config;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QwenEmbeddingModelConfig {

    @Value("${langchain4j.community.dashscope.api-key}")
    private String apiKey;


    @Bean
    public QwenEmbeddingModel qwenEmbeddingModel(){
        return QwenEmbeddingModel.builder()
                .apiKey(apiKey)
                .build();
    }
}
