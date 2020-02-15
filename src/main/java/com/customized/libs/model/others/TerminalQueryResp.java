package com.customized.libs.model.others;

/**
 * @Description: 终端查询响应参数
 * @Author: 吴世攀
 * @CreateDate: 2019/8/24 18:05
 */
public class TerminalQueryResp {

    /**
     * 厂商编号
     */
    private String mfrNo;
    /**
     * 代理商名称
     */
    private String orgNm;

    /**
     * 厂商
     */
    private String mfrNm;

    /**
     * 型号
     */
    private String modNm;

    /**
     * sn号
     */
    private String trmSn;

    /**
     * 商户名称
     */
    private String mercName;

    /**
     * 商户编号
     */
    private String mercId;

    /**
     * 绑定状态(SUCCESS绑定 INIT未绑定)
     */
    private String bindStatus;


    /**
     * 是否有效(SUCCESS有效 INIT无效)
     */
    private String validStatus;

    /**
     * 参与活动名称
     */
    private String activityName;

    public String getMfrNo() {
        return mfrNo;
    }

    public void setMfrNo(String mfrNo) {
        this.mfrNo = mfrNo;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public String getMfrNm() {
        return mfrNm;
    }

    public void setMfrNm(String mfrNm) {
        this.mfrNm = mfrNm;
    }

    public String getModNm() {
        return modNm;
    }

    public void setModNm(String modNm) {
        this.modNm = modNm;
    }

    public String getTrmSn() {
        return trmSn;
    }

    public void setTrmSn(String trmSn) {
        this.trmSn = trmSn;
    }

    public String getMercName() {
        return mercName;
    }

    public void setMercName(String mercName) {
        this.mercName = mercName;
    }

    public String getMercId() {
        return mercId;
    }

    public void setMercId(String mercId) {
        this.mercId = mercId;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
