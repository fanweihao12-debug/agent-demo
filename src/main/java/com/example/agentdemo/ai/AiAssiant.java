package com.example.agentdemo.ai;

import dev.langchain4j.service.TokenStream;
import org.springframework.stereotype.Service;


public interface AiAssiant {

    String chat(String message);

    TokenStream chatWithStream(String message);
}
