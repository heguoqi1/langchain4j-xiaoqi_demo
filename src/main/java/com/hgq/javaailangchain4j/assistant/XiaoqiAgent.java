package com.hgq.javaailangchain4j.assistant;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * XiaoqiAgent
 *
 * @author hegq
 * @since 1.0.0
 */

@AiService(
        wiringMode = EXPLICIT,
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaoqi",
        tools = "appointmentTools",
        contentRetriever = "contentRetrieverXiaoqiPincone")
    public interface XiaoqiAgent {
    @SystemMessage(fromResource = "tishici.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
