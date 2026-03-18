package com.hgq.javaailangchain4j.bean;

import lombok.Data;

/**
 * ChatForm
 *
 * @author hegq
 * @since 1.0.0
 */

@Data
public class ChatForm {
    private Long memoryId;//对话id

    private String userMassage;//用户问题
}
