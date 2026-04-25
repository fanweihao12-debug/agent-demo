package com.example.agentdemo.ai.Config;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ContentRetrieverConfig {

    @Resource
    private QwenEmbeddingModel qwenEmbeddingModel;

    @Bean
    public ContentRetriever contentRetriever(){
        //RAG的整个流程
        String filepath = ".\\docs";
        //文档载入
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(filepath, new ApachePdfBoxDocumentParser());
        //文档切片
        DocumentByParagraphSplitter splitter = new DocumentByParagraphSplitter(1000, 200);

        List<TextSegment> textSegments = splitter.splitAll(documents);

        //文档切片后向量化
        List<Embedding> embeddings = qwenEmbeddingModel.embedAll(textSegments).content();

        //文档切片和文档向量入库
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.addAll(embeddings,textSegments);

        ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(qwenEmbeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();
        return retriever;
    }
}
