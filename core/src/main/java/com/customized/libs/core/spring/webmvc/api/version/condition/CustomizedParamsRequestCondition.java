/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.customized.libs.core.spring.webmvc.api.version.condition;

import com.customized.libs.core.spring.webmvc.api.version.util.VersionsUtil;
import com.googlecode.aviator.AviatorEvaluator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * A logical conjunction (' && ') request condition that matches a request against
 * a set parameter expressions with syntax defined in {@link RequestMapping#params()}.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.1
 */
@Deprecated
public final class CustomizedParamsRequestCondition extends AbstractRequestCondition<CustomizedParamsRequestCondition> {

    private final Set<ParamExpression> expressions;


    /**
     * Create a new instance from the given param expressions.
     *
     * @param params expressions with syntax defined in {@link RequestMapping#params()};
     *               if 0, the condition will match to every request.
     */
    public CustomizedParamsRequestCondition(String... params) {
        this(parseExpressions(params));
    }

    private CustomizedParamsRequestCondition(Collection<ParamExpression> conditions) {
        this.expressions = Collections.unmodifiableSet(new LinkedHashSet<>(conditions));
    }


    private static Collection<ParamExpression> parseExpressions(String... params) {
        Set<ParamExpression> expressions = new LinkedHashSet<>();
        for (String param : params) {
            expressions.add(new ParamExpression(param));
        }
        return expressions;
    }


    /**
     * Return the contained request parameter expressions.
     */
    public Set<NameValueExpression<String>> getExpressions() {
        return new LinkedHashSet<>(this.expressions);
    }

    @Override
    protected Collection<ParamExpression> getContent() {
        return this.expressions;
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }

    /**
     * Returns a new instance with the union of the param expressions
     * from "this" and the "other" instance.
     */
    @Override
    public CustomizedParamsRequestCondition combine(CustomizedParamsRequestCondition other) {
        Set<ParamExpression> set = new LinkedHashSet<>(this.expressions);
        set.addAll(other.expressions);
        return new CustomizedParamsRequestCondition(set);
    }

    /**
     * 检查当前请求匹配条件和指定请求request是否匹配，如果不匹配返回null，
     * 如果匹配，生成一个新的请求匹配条件，该新的请求匹配条件是当前请求匹配条件
     * 针对指定请求request的剪裁。
     * 举个例子来讲，如果当前请求匹配条件是一个路径匹配条件，包含多个路径匹配模板，
     * 并且其中有些模板和指定请求request匹配，那么返回的新建的请求匹配条件将仅仅
     * 包含和指定请求request匹配的那些路径模板。
     *
     * @param request
     * @return
     */
    @Override
    @Nullable
    public CustomizedParamsRequestCondition getMatchingCondition(HttpServletRequest request) {
        for (ParamExpression expression : expressions) {
            if (!expression.match(request)) {
                return null;
            }
        }
        return this;
    }

    /**
     * Returns:
     * <ul>
     * <li>0 if the two conditions have the same number of parameter expressions
     * <li>Less than 0 if "this" instance has more parameter expressions
     * <li>Greater than 0 if the "other" instance has more parameter expressions
     * </ul>
     * <p>It is assumed that both instances have been obtained via
     * {@link #getMatchingCondition(HttpServletRequest)} and each instance
     * contains the matching parameter expressions only or is otherwise empty.
     */
    @Override
    public int compareTo(CustomizedParamsRequestCondition other, HttpServletRequest request) {
        return (other.expressions.size() - this.expressions.size());
    }


    /**
     * Parses and matches a single param expression to a request.
     */
    static class ParamExpression extends AbstractNameValueExpression<String> {

        ParamExpression(String expression) {
            super(expression);
        }

        @Override
        protected boolean isCaseSensitiveName() {
            return true;
        }

        @Override
        protected String parseValue(String valueExpression) {
            return valueExpression;
        }

        @Override
        protected boolean matchName(HttpServletRequest request) {
            return (WebUtils.hasSubmitParameter(request, this.name) ||
                    request.getParameterMap().containsKey(this.name));
        }

        @Override
        protected boolean matchValue(HttpServletRequest request) {
            if (StringUtils.isNotBlank(this.expression)) {
                Map<String, Object> env = new HashMap<>(16);
                env.put("version", VersionsUtil.convertVersion(request.getParameter(this.name)));
                return (boolean) AviatorEvaluator.execute(this.expression, env);
            } else {
                return ObjectUtils.nullSafeEquals(this.value, request.getParameter(this.name));
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (this.value != null) {
                builder.append(this.expression);
            } else {
                if (this.isNegated) {
                    builder.append('!');
                }
                builder.append(this.name);
            }
            return builder.toString();
        }
    }
}
