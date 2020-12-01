package com.customized.libs.core.libs.javase;

import com.customized.libs.core.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List内容一致，但是承载数据的容器发生了变化，collect(Collectors.toList())，导致了System.identityHashCode计算发生变化
 * <p>
 * https://github.com/apache/dubbo/issues/5429'
 * <p>
 * <p>
 * ConsistentHashLoadBalance 用到了 System.identityHashCode(invokers); 来判断 hash 值，从而判断是否有服务端的上下线操作。
 * 而TagRouter的org.apache.dubbo.rpc.cluster.router.tag.TagRouter#filterUsingStaticTag方法中的 stream 操作会修改 invokers 的值，所以 ConsistentHashLoadBalance 受到了影响。
 * 导致即使在服务端没有上下线操作的时候，一致性hash负载均衡算法每次都需要重新进行hash环的映射。
 *
 * @author yan
 */
public class IdentityHashCodeTest {

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("key");

        System.out.println(System.identityHashCode(data));

        data = data.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        System.out.println(System.identityHashCode(data));

        System.out.println(CommonUtils.buildLogString("分隔符", "#", 32));

        List<KeyValues> data2 = new ArrayList<>();
        data2.add(new KeyValues());


        // java.util.AbstractList.hashCode
        System.out.println(data2.hashCode() + " ==> " + System.identityHashCode(data2));
        data2 = data2.stream().filter(c -> StringUtils.isBlank(c.getKey())).collect(Collectors.toList());
        System.out.println(data2.hashCode() + " ==> " + System.identityHashCode(data2));

        System.out.println(CommonUtils.buildLogString("分隔符", "#", 32));

        KeyValues kv = new KeyValues();
        kv.setKey("123");
        System.out.println("System.identityHashCode ==> " + System.identityHashCode(kv));
        System.out.println("hashCode ==> " + kv.hashCode());

        System.out.println(CommonUtils.buildLogString("分隔符", "#", 32));

        KeyValues kv2 = new KeyValues();
        kv2.setKey("123");
        System.out.println("System.identityHashCode ==> " + System.identityHashCode(kv2));
        System.out.println("hashCode ==> " + kv2.hashCode());
    }

    public static class KeyValues {

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            KeyValues keyValues = (KeyValues) o;

            return new EqualsBuilder()
                    .append(getKey(), keyValues.getKey())
                    .append(getValue(), keyValues.getValue())
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(getKey())
                    .append(getValue())
                    .toHashCode();
        }
    }
}
