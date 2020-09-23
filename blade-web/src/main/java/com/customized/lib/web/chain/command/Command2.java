package com.customized.lib.web.chain.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class Command2 implements Command {

    @Override
    public boolean execute(Context arg0) throws Exception {
        System.out.println("Command2 is done! the chain will going.");
        return Command.CONTINUE_PROCESSING;
    }
}
