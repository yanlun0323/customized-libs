package com.customized.libs.core.libs.memory.leak;

public class JavaMemoryTest {

    static class NewObject {
        int count;
        boolean flag;
        Object ob;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public Object getOb() {
            return ob;
        }

        public void setOb(Object ob) {
            this.ob = ob;
        }
    }

    public static void main(String[] args) {

    }
}
