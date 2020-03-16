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
public class LongEvent {

    DataItem data;
}