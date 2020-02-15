package com.customized.libs.libs.disruptor;

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
