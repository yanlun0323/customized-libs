package com.customized.libs.core.libs.spring.bean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;

/**
 * @author LinJie
 * @Description:一个学生类(Bean)，能体现其生命周期的Bean
 */
@Component
public class Student implements BeanNameAware {
    private String name;

    public Student() {
        super();
    }

    /**
     * 设置对象属性
     *
     * @param name the name to set
     */
    public void setName(String name) {
        System.out.println("[WARN]设置对象属性setName()..");
        this.name = name;
    }

    @PostConstruct
    public void initStudent() {
        System.out.println("[WARN]Student这个Bean：初始化");
    }

    @PreDestroy
    public void destroyStudent() {
        System.out.println("[WARN]Student这个Bean：销毁");
    }

    public void play() {
        System.out.println("[WARN]Student这个Bean：使用");
    }

    /**
     * 重写toString
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[WARN]Student [name = " + name + "]";
    }

    /**
     * 调用BeanNameAware的setBeanName()
     * 传递Bean的ID。
     *
     * @param name
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("[WARN]调用BeanNameAware的setBeanName()..." + name);
    }

}
