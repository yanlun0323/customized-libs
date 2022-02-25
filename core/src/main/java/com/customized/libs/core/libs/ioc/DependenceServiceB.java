package com.customized.libs.core.libs.ioc;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/25 09:43
 */
public class DependenceServiceB implements DependenceService {

    private DependenceServiceA serviceA;

    @Override
    public void execute() {
        this.serviceA.execute();
        System.out.println("========================");
        System.out.println("execute DependenceServiceB");
        System.out.println();
    }
}
