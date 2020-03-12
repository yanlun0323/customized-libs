package com.customized.libs.core.libs.disruptor;

import lombok.*;


/**
 * @author yan
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataItem {

    private long value;

    private long startTime;
}
