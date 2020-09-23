package com.customized.lib.web.chain.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class Command3 implements Command {

    @Override
    public boolean execute(Context arg0) throws Exception {
        System.out.println("Command3 is done! the chain not going.");
        return Command.PROCESSING_COMPLETE;
    }
}
