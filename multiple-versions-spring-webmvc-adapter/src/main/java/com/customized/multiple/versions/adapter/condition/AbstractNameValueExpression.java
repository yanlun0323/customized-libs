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

package com.customized.multiple.versions.adapter.condition;

import com.customized.multiple.versions.adapter.util.VersionsUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;

import javax.servlet.http.HttpServletRequest;

/**
 * Supports "name=value" style expressions as described in:
 * {@link org.springframework.web.bind.annotation.RequestMapping#params()} and
 * {@link org.springframework.web.bind.annotation.RequestMapping#headers()}.
 *
 * @author Rossen Stoyanchev
 * @author Arjen Poutsma
 * @since 3.1
 */
@Deprecated
abstract class AbstractNameValueExpression<T> implements NameValueExpression<T> {

    protected final String name;

    @Nullable
    protected final String expression;

    @Nullable
    protected final T value;

    protected final boolean isNegated;

    /**
     * memeber variable: equal mark.
     */
    public static final String EQUAL = "=";

    /**
     * memeber variable: operation ne.
     */
    public static final String NE = "!=";

    /**
     * memeber variable: operation le.
     */
    public static final String LE = "<=";

    /**
     * memeber variable: operation ge.
     */
    public static final String GE = ">=";

    /**
     * memeber variable: operation lt.
     */
    public static final String LT = "<";

    /**
     * memeber variable: operation gt.
     */
    public static final String GT = ">";

    private static final String[] ALL_OP = "=!|=<|=>|=|<|>".split("\\|");


    /**
     * Supported >. <. <=. >=. =
     *
     * @param expression
     */
    AbstractNameValueExpression(String expression) {
        if (!containsAny(expression, ALL_OP)) {
            this.isNegated = expression.startsWith("!");
            this.name = (this.isNegated ? expression.substring(1) : expression);
            this.value = null;
            this.expression = "";
        } else {
            String[] components = expression.split("=!|<=|>=|=|<|>");

            this.isNegated = Boolean.FALSE;
            this.name = components[0];
            this.value = parseValue(components[1]);
            this.expression = VersionsUtil.convertVersionWithExpression(expression);
        }
    }

    private boolean containsAny(String expression, String[] allOp) {
        for (int i = 0; i < allOp.length; i++) {
            if (expression.contains(allOp[i])) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @Nullable
    public T getValue() {
        return this.value;
    }

    @Nullable
    public String getExpression() {
        return this.expression;
    }

    @Override
    public boolean isNegated() {
        return this.isNegated;
    }

    public final boolean match(HttpServletRequest request) {
        boolean isMatch;
        if (this.value != null) {
            isMatch = matchValue(request);
        } else {
            isMatch = matchName(request);
        }
        return (this.isNegated ? !isMatch : isMatch);
    }


    protected abstract boolean isCaseSensitiveName();

    protected abstract T parseValue(String valueExpression);

    protected abstract boolean matchName(HttpServletRequest request);

    protected abstract boolean matchValue(HttpServletRequest request);


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AbstractNameValueExpression) {
            AbstractNameValueExpression<?> other = (AbstractNameValueExpression<?>) obj;
            String thisName = (isCaseSensitiveName() ? this.name : this.name.toLowerCase());
            String otherName = (isCaseSensitiveName() ? other.name : other.name.toLowerCase());
            return (thisName.equalsIgnoreCase(otherName) &&
                    (this.value != null ? this.value.equals(other.value) : other.value == null) &&
                    this.isNegated == other.isNegated);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = (isCaseSensitiveName() ? this.name.hashCode() : this.name.toLowerCase().hashCode());
        result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
        result = 31 * result + (this.isNegated ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (this.value != null) {
            builder.append(this.name);
            if (this.isNegated) {
                builder.append('!');
            }
            builder.append('=');
            builder.append(this.value);
        } else {
            if (this.isNegated) {
                builder.append('!');
            }
            builder.append(this.name);
        }
        return builder.toString();
    }

}
