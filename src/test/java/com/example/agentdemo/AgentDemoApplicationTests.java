package com.example.agentdemo;

import com.example.agentdemo.ai.AiAssiant;
import com.example.agentdemo.ai.AiService;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.PartialThinking;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
class AgentDemoApplicationTests {

    @Resource
    private AiService aiService;

    @Resource
    private AiAssiant aiAssiant;
    @Test
    void testChat() {
        System.out.println(aiService.chat("你好呀，你是什么模型"));
    }

    @Test
    void testChatWithAssiant(){
        System.out.println(aiAssiant.chat("勾股定理是什么？"));
    }

    @Test
    void testChatWithMemory(){
        String result = aiAssiant.chat("我是一名程序员，你一定要记住");
        result = aiAssiant.chat("我是什么职业你还记得吗");
        System.out.println(result);
    }
    @Test
    void testWithRag(){
        String result = aiAssiant.chat("我想了解一下Danskin定理，请给我讲一下");
        System.out.println(result);
    }

    @Test
    void testWithStream(){
        TokenStream stream = aiAssiant.chatWithStream("我想了解一下鲁迅的生平，给我说一下");

        CompletableFuture<ChatResponse> futureResponse = new CompletableFuture<>();

        stream.onPartialResponse((String partialResponse) -> System.out.print(partialResponse))
                .onError((Throwable error)->futureResponse.completeExceptionally(error))
                .onCompleteResponse((ChatResponse response) -> futureResponse.complete(response))
                .start();

        futureResponse.join();
    }


}
