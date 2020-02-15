package com.customized.libs.libs.hashmap;

import java.util.Objects;

class Student implements Comparable<Student> {
    final String name;
    final int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student o = (Student) obj;
            return Objects.equals(this.name, o.name) && this.score == o.score;
        }
        return false;
    }

    /**
     * This is so because the Map interface is defined in terms of the equals operation, <br/>
     * but a sorted map performs all key comparisons using its compareTo (or compare) method
     * <p>
     * <p>
     * 上面这个定义，用来排序是没问题的，但是，没法判断相等。TreeMap根据key.compareTo(anther)==0判断是否相等，而不是equals()。
     * <p>
     * 所以，解决问题的关键是正确实现compareTo()，该相等的时候，必须返回0：
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Student o) {
        int n = Integer.compare(this.score, o.score);
        return n != 0 ? n : this.name.compareTo(o.name);
    }
}