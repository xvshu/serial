/*
 * Powered By [rapid-framework]
 * Web Site: http://www.eloancn.com
 * By:eloancn
 * Since 2015 - 2016
 */

package com.xs.frame.iserial;

import java.io.Serializable;


public class BusinessSerial implements Serializable {

    /**
     * 平台区分
     */
    private String platform ;

    /**
     * 业务编码
     */
    private String businessNo	;

    /**
     * 发生系统
     */
    private String  systemNo	;

    /**
     * 发生服务
     */
    private String  serviceNo	;

    /**
     * 终端类型
     */
    private String  terminalType;

    /**
     * 发生日期
     */
    private String dateOfHappen;

    /**
     * 序号
     */
    private String serialNumber;

    /**
     * 明细序号
     */
    private String detailSerialNumber;


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getDateOfHappen() {
        return dateOfHappen;
    }

    public void setDateOfHappen(String dateOfHappen) {
        this.dateOfHappen = dateOfHappen;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDetailSerialNumber() {
        return detailSerialNumber;
    }

    public void setDetailSerialNumber(String detailSerialNumber) {
        this.detailSerialNumber = detailSerialNumber;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }
}
