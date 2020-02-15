package com.customized.libs.libs.dispatcher;

import com.customized.libs.libs.dispatcher.impl.UserRegister;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * @author yan
 */
public class RegisterDispatcher {

    public Register getRegister() {
        ServiceLoader<Register> impls = ServiceLoader.load(Register.class);

        impls.forEach(Register::doRegister);

        System.out.println();

        final Optional<Register> register = StreamSupport.stream(impls.spliterator(), false)
                .findAny();
        return register.orElse(new UserRegister());
    }

    public static void main(String[] args) {
        RegisterDispatcher dispatcher = new RegisterDispatcher();
        dispatcher.getRegister().doRegister();
    }
}
