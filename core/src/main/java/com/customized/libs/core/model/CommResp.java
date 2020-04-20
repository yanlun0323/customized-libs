package com.customized.libs.core.model;

import lombok.*;

/**
 * @author yan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommResp {

    private String key;

    private String msg;

    private Object data;

    public CommResp(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }
}
