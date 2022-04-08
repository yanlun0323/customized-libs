package com.customized.libs.core.libs.tree;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/25 17:07
 */
@Data
@Builder
public class Dept {

    private Long id;
    private String name;
    private Long pid;

    private List<Dept> children;
}
