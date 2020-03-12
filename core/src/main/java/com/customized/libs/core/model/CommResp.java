package com.customized.libs.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommResp {

    private String key;

    private String msg;

    private Object data;

    public CommResp(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }
}
