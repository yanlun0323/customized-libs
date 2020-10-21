package com.customized.libs.sentinel.expand.solt;

import com.alibaba.csp.sentinel.slotchain.ProcessorSlotChain;
import com.alibaba.csp.sentinel.slotchain.SlotChainBuilder;
import com.alibaba.csp.sentinel.slots.DefaultSlotChainBuilder;

/**
 * V1.7.1和V1.7.2实现上稍微有点差异，V1.7.2将SlotChainBuilder和ProcessorSlot都通过SPI动态加载
 *
 * @author yan
 */
public class CustomizedSoltChainBuilder implements SlotChainBuilder {

    @Override
    public ProcessorSlotChain build() {
        ProcessorSlotChain chain = new DefaultSlotChainBuilder().build();
        // 增加自定义solt
        chain.addLast(new FlowEarlyWarningSolt());
        chain.addLast(new DegradeEarlyWarningSlot());
        return chain;
    }
}
