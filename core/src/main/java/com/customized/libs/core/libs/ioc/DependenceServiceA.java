package com.customized.libs.core.libs.ioc;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/25 09:43
 */
public class DependenceServiceA implements DependenceService {

    private DependenceServiceB serviceB;

    @Override
    public void execute() {
        System.out.println("========================");
        System.out.println("execute DependenceServiceA");
        System.out.println();
    }
}
