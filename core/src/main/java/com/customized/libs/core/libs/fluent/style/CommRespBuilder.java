package com.customized.libs.core.libs.fluent.style;

import com.customized.libs.core.model.CommResp;

/**
 * @author yan
 */
public class CommRespBuilder {

    public static void main(String[] args) {

        CommResp.CommRespBuilder builder = CommResp.builder();
        CommResp commResp = builder.
                data("Java").
                key("type").
                msg("HelloWord").
                build();

        System.out.println(commResp.toString());
        System.out.println(builder.toString());
    }
}
