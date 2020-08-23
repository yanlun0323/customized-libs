package com.customized.libs.core.libs.limiter;

import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author yan
 */
public class TestSemaphore implements Runnable {

    private static final String DEFAULT_LIMIT_KEY = "Customer-Rate-Limiter";

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 允许并发的任务量限制为5个
     * <p>
     * 5、线程公平性
     * <p>
     * 　　上面用的 Semaphore  构造方法是 Semaphore semaphore = new Semaphore(int permits)
     * <p>
     * 　　其实，还有一个构造方法： Semaphore semaphore = new Semaphore(int permits , boolean isFair)
     * <p>
     * 　　isFair 的意思就是，是否公平，获得锁的顺序与线程启动顺序有关，就是公平，先启动的线程，先获得锁。isFair 不能100% 保证公平，只能是大概率公平。
     * <p>
     * 　　isFair 为 true，则表示公平，先启动的线程先获得锁。
     */
    private final Semaphore semaphore = new Semaphore(1, true);

    /**
     * 此方法结合Sentinel和实现每秒并发限流（貌似sentinel就能玩的转，semaphore作用不大？）
     *
     * @param arg
     * @throws InterruptedException
     */
    @SuppressWarnings("all")
    public static void main(String[] arg) throws InterruptedException {
        // initDegradeRule();
        initFlowRule();
        for (int i = 0; i < 200; i++) {
            Thread.sleep(RandomUtils.nextInt(10, 20));
            Thread t = new Thread(new TestSemaphore());
            t.start();
        }
    }

    private static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(DEFAULT_LIMIT_KEY);
        // set threshold RT, 10 ms
        rule.setCount(20);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    private static void initFlowRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(DEFAULT_LIMIT_KEY);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to N.
        rule.setCount(10);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }


    /**
     * 先semaphore信号量控制并发，再Sentinel限流
     * <p>
     * 应该在限流之前不要做过多的业务操作，才能保证限流的准确性
     */
    @Override
    public void run() {
        try {
            semaphore.acquire(); // 获取信号量,不足会阻塞
            // 资源名可使用任意有业务语义的字符串
            if (SphO.entry(DEFAULT_LIMIT_KEY)) {
                // 务必保证finally会被执行
                try {
                    /*
                     * 被保护的业务逻辑
                     */
                    this.doBizProcessing();
                } finally {
                    SphO.exit();
                }
            } else {
                // 资源访问阻止，被限流或被降级
                // 进行相应的处理操作
                System.out.println("<ERROR> 被限流");
            }
        } catch (InterruptedException e) {
            // ignored
        } finally {
            semaphore.release(); // 释放信号量
        }
    }

    private void doBizProcessing() throws InterruptedException {
        System.out.println("<DEBUG> " + SDF.format(new Date()) + " Task Start..");
        Thread.sleep(RandomUtils.nextInt(10, 50));
    }
}