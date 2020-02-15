package com.customized.libs.libs.lemur.map.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class DataRow implements Serializable {

    private static final long serialVersionUID = 8806167626288849135L;

    @Excel(name = "经度")
    private String lon;

    @Excel(name = "纬度", orderNum = "1")
    private String lat;

    @Excel(name = "上游商户编号", orderNum = "2")
    private String upMercId;

    @Excel(name = "交易金额", orderNum = "3")
    private String txmAmt;

    @Excel(name = "归属省", orderNum = "4")
    private String provName;

    @Excel(name = "归属市", orderNum = "5")
    private String cityName;
}
