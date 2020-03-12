package com.customized.libs.core.libs.java.lambada;

public class LambadaDemo {

    public static final int DEFAULT_MAX = 5;
    private Integer counter;

    public LambadaDemo() {
        this.counter = 0;
    }

    public static void main(String[] args) {
        LambadaDemo demo = new LambadaDemo();
        demo.bindBean("target0");
    }

    public <T> T bindBean(String target0) {
        LambadaDemo that = this;
        // 此处仅仅为BeanPropertyBinder的实现
        BeanPropertyBinder binder = (name) -> that.bind(name, target0);

        binder.bindProperty("target");
        return (T) binder;
    }

    @SuppressWarnings("unchecked")
    private final <T> T bind(String arg0, String arg1) {
        System.out.println(arg0 + "\t" + arg1);
        if (this.counter <= DEFAULT_MAX) {
            this.counter = this.counter + 1;
            return this.bindBean(arg0 + this.counter);
        }
        return null;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}
