package com.customized.libs.core.libs.dubbo.spi;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.config.spring.extension.SpringExtensionFactory;
import com.alibaba.dubbo.container.Container;
import com.alibaba.dubbo.container.spring.SpringContainer;
import com.customized.libs.core.libs.dispatcher.Register;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.alibaba.dubbo.container.Main.CONTAINER_KEY;

/**
 * @author yan
 */
@Slf4j
public class DubboSPIDemo {

    private static final ExtensionLoader<Container> loader = ExtensionLoader.getExtensionLoader(Container.class);

    public static void main(String[] args) {

        ExtensionLoader<Register> extensionLoader =
                ExtensionLoader.getExtensionLoader(Register.class);

        // com.alibaba.dubbo.container.Main.main(args);

        if (args == null || args.length == 0) {
            String config = ConfigUtils.getProperty(CONTAINER_KEY, loader.getDefaultExtensionName());
            args = Constants.COMMA_SPLIT_PATTERN.split(config);
        }

        final List<Container> containers = new ArrayList<Container>();
        for (int i = 0; i < args.length; i++) {
            containers.add(loader.getExtension(args[i]));
        }
        log.info("Use container type(" + Arrays.toString(args) + ") to run dubbo serivce.");

        for (Container container : containers) {
            container.start();
            log.info("Dubbo " + container.getClass().getSimpleName() + " started!");
        }

        /**
         * TODO
         * 该方法很重要，当非Dubbo服务通过Dubbo方式启动时，并不会初始化ApplicationContext
         *
         * 初始化Context的有两处地方：
         * com.alibaba.dubbo.config.spring.ReferenceBean.setApplicationContext()
         * com.alibaba.dubbo.config.spring.ServiceBean.setApplicationContext()
         *
         * 当Dubbo初始化完成后，SpringContainer中静态的Context对象已完成初始化，可赋值，已达到dubbo能通过Context获取Bean的目的
         */
        SpringExtensionFactory.addApplicationContext(SpringContainer.getContext());

        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");

        Register register0 = extensionLoader.getExtension("UserRegister");
        register0.doRegister();

        Register register1 = extensionLoader.getExtension("VIPRegister");
        register1.doRegister();
    }

}
