package com.customized.libs.core.spring.listener;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 这里Spring并不会去自动扫描ApplicationListener接口，加入component注解后标记扫描后生效
 * <p>
 * 不管是内置监听还是外部自定义监听一定要把实现ApplicationListener的类定义成一个bean才行，可以是通过注解@Component等也可以通过xml的方式去执行。
 *
 * @author yan
 */
@Component
public class UserRegisterApplicationListener implements ApplicationListener<UserRegisterEvent> {

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        System.out.println("Event.Object ==> " + event.getSource());
        System.out.println(JSON.toJSONString(event));
    }
}
