package com.customized.libs.core.libs.tree;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/25 17:06
 */
public class TreeBuilder {

    public static List<Dept> buildTree(List<Dept> depts, Long pid) {
        List<Dept> deptList = new ArrayList<>();
        for (Dept dept : depts) {
            if (pid.equals(dept.getPid())) {
                doPrint("查找【" + dept.getName() + "】下的子部门");
                List<Dept> children = buildTree(depts, dept.getId());
                if (!children.isEmpty()) {
                    dept.setChildren(children);
                    doPrint("【" + dept.getName() + "】下的子部门 ==> " + children);
                } else {
                    doPrint("【" + dept.getName() + "】下无子部门！");
                }
                doPrint("====================  分隔符  ====================");
                deptList.add(dept);
            }
        }
        return deptList;
    }

    /**
     * 通过StreamAPI分组后过滤
     * <p>
     * https://www.jianshu.com/p/14366e59c485
     *
     * @param depts
     * @param pid
     * @return
     */
    public static List<Dept> buildTreeV2(List<Dept> depts, Long pid) {
        // 按照PID分组
        Map<Long, List<Dept>> grouped = depts.stream().filter(d -> !d.getPid().equals(pid))
                .collect(Collectors.groupingBy(Dept::getPid));
        doPrint(JSON.toJSONString(grouped));
        for (Dept dept : depts) {
            dept.setChildren(grouped.get(dept.getId()));
        }
        doPrint(JSON.toJSONString(depts));
        return depts.stream().filter(d -> d.getPid().equals(pid)).collect(Collectors.toList());
    }

    private static void doPrint(String chars) {
        // System.out.println(chars);
    }

    public static void main(String[] args) {
        long beginCurrentTimes = System.currentTimeMillis();
        buildTree(initDepts(), 0L);
        System.out.println("V1耗时：" + (System.currentTimeMillis() - beginCurrentTimes));

        beginCurrentTimes = System.currentTimeMillis();
        buildTreeV2(initDepts(), 0L);
        System.out.println("V2耗时：" + (System.currentTimeMillis() - beginCurrentTimes));
    }

    private static List<Dept> initDepts() {
        List<Dept> depts = new ArrayList<>();
        depts.add(Dept.builder().name("部门1").id(1L).pid(0L).build());
        depts.add(Dept.builder().name("部门2").id(2L).pid(1L).build());
        depts.add(Dept.builder().name("部门3").id(3L).pid(1L).build());
        depts.add(Dept.builder().name("部门4").id(4L).pid(0L).build());
        depts.add(Dept.builder().name("部门5").id(5L).pid(4L).build());
        depts.add(Dept.builder().name("部门6").id(6L).pid(5L).build());
        for (int i = 7; i < 20000; i++) {
            depts.add(Dept.builder().name("部门" + i).id((long) i)
                    .pid(RandomUtils.nextLong(0, i)).build());
        }
        return depts;
    }
}
