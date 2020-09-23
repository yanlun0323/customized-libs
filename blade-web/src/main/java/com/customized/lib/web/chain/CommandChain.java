package com.customized.lib.web.chain;

import com.customized.lib.web.chain.command.Command1;
import com.customized.lib.web.chain.command.Command2;
import com.customized.lib.web.chain.command.Command3;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;

/**
 * @author yan
 */
public class CommandChain extends ChainBase {
    /**
     * 增加命令的顺序也决定了执行命令的顺序
     */
    public CommandChain() {
        addCommand(new Command1());
        addCommand(new Command2());
        addCommand(new Command3());
        // 这里的Command1不会执行, 因为Command3中声明流程终止
        addCommand(new Command1());
    }

    public static void main(String[] args) throws Exception {
        CommandChain chain = new CommandChain();
        Context ctx = new ContextBase();
        chain.execute(ctx);
    }
}