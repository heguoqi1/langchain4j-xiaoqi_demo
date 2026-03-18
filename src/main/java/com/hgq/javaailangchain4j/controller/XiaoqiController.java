package com.hgq.javaailangchain4j.controller;

import com.hgq.javaailangchain4j.assistant.XiaoqiAgent;
import com.hgq.javaailangchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * XiaoqiController
 *
 * @author hegq
 * @since 1.0.0
 */

@Tag(name="智能小奇")
@RestController
@RequestMapping("/xiaoqi")
public class XiaoqiController {

    @Autowired
    private XiaoqiAgent xiaoqiAgent;
    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        return xiaoqiAgent.chat(chatForm.getMemoryId(), chatForm.getUserMassage());
    }
}
