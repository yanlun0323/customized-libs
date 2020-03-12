package com.customized.libs.core.mvc.servlet;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.mvc.annotations.MvcRequestParam;
import com.customized.libs.core.mvc.bean.MvcArgs;
import com.customized.libs.core.mvc.bean.MvcBean;
import com.customized.libs.core.mvc.factory.MvcBeanFactory;
import com.customized.libs.core.utils.HttpUtils;
import com.customized.libs.core.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yan
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 929969409390358956L;

    private ApplicationContext context;

    public DispatcherServlet(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String uri = request.getRequestURI();

        log.warn(">>> URI ==> " + uri);

        MvcBean bean = this.context.getBean(MvcBeanFactory.class).
                getBean(StringUtils.removeStart(uri, "/dispatcher/"));
        if (bean == null) {
            throw new IllegalArgumentException(String.format("Not Found %s mapping", uri));
        }

        try {
            request.setCharacterEncoding("UTF-8");
            Object rt = bean.run(this.buildArgs(request, bean, request, response));
            this.processingResult(bean, response, rt);
        } catch (Exception e) {
            e.printStackTrace();
            this.processingResult(bean, response, "系统繁忙，请稍后重试");
        }
    }

    /**
     * Result Processing (JSTL/JSON/...)
     *
     * @param response
     * @param rt
     * @throws IOException
     */
    private void processingResult(MvcBean bean, HttpServletResponse response, Object rt)
            throws IOException {
        // setContentType解决中文乱码问题（JSP页面也定义page-encoding）
        response.setCharacterEncoding("UTF-8");
        if ("JSON".equals(bean.getContentType())) {
            if (ObjectUtil.isBasicType(rt)) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(rt);
            } else {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(JSON.toJSONString(rt));
            }
        } else {
            throw new IllegalArgumentException("Not Supported Content Type ==> " + bean.getContentType());
        }
    }

    /***
     * 此处参数封装可以根据RequestParam参数的顺序将参数放入Object数据，
     * 同时可拓展RequestBody、ModelAttribute的注解方式
     * @param req
     * @return
     */
    private Object[] buildArgs(HttpServletRequest req, MvcBean bean
            , HttpServletRequest request, HttpServletResponse response) {
        // 获取当前Controller的所有参数对象
        MvcArgs[] mvcArgs = this.buildMvcArgs(bean.getTargetMethod());

        Object[] params = new Object[mvcArgs.length];
        if (ArrayUtils.isNotEmpty(mvcArgs)) {
            Map parameterMap = HttpUtils.getParameterMap(req);
            for (int i = 0; i < params.length; i++) {
                MvcArgs args = mvcArgs[i];
                params[i] = ObjectUtils.
                        defaultIfNull(parameterMap.get(args.getName()), args.getDefaultValue());
            }
        }
        return params;
    }

    private MvcArgs[] buildMvcArgs(Method mtd) {
        DefaultParameterNameDiscoverer dpnd = new DefaultParameterNameDiscoverer();
        String[] params = dpnd.getParameterNames(mtd);
        Annotation[][] anos = mtd.getParameterAnnotations();

        // 注解绑定参数和默认参数解析
        if (params == null || params.length == 0) {
            return new MvcArgs[0];
        }
        MvcArgs[] mvcArgs = new MvcArgs[params.length];
        if (anos != null && anos.length > 0) {
            for (int i = 0; i < params.length; i++) {
                Annotation[] tempAno = anos[i];
                if (tempAno != null && tempAno.length > 0) {
                    MvcRequestParam mvcRequestParam = (MvcRequestParam) tempAno[0];
                    params[i] = StringUtils.
                            defaultIfEmpty(mvcRequestParam.name(), mvcRequestParam.value());
                    mvcArgs[i] = MvcArgs.builder().name(params[i]).
                            defaultValue(mvcRequestParam.defaultValue()).build();
                } else {
                    mvcArgs[i] = MvcArgs.builder().name(params[i]).build();
                }
            }
        }
        return mvcArgs;
    }
}
