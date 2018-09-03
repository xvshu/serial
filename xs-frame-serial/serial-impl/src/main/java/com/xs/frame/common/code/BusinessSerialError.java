package com.xs.frame.common.code;


/**
 * Created by Administrator on 2016/3/7.
 */
public enum BusinessSerialError {

    E_SYS_CONNECTION_FAILED("E-SYS-CONNECTION-FAILED","系统类-网络-连接失败-901",""),
    E_SYS_CONNECTION_TIMEOUT("E-SYS-CONNECTION-TIMEOUT","系统类-网络-连接超时-902",""),
    E_DB_CONNECTION_FAILED("E-DB-CONNECTION-FAILED","系统类-DB-连接失败",""),
    E_DB_CONNECTION_TIMEOUT("E-DB-CONNECTION-TIMEOUT","系统类-DB-连接超时",""),
    E_DB_PASSWORK_FAILED("E-DB-PASSWORK-FAILED","系统类-DB-密码不正确",""),
    E_DB_TABLE_NOT_EXIST("E-DB-TABLE-NOT-EXIST","系统类-DB-表不存在",""),
    E_DB_FIELD_NOT_EXIST("E-DB-FIELD-NOT-EXIST","系统类-DB-字段不存在",""),
    E_DB_INSERT_PRIMARY_KEY_CONFLICT("E-DB-INSERT-PRIMARY-KEY-CONFLICT","系统类-DB插入-主键冲突",""),
    E_DB_INSERT_FIELD_TOO_LONG("E-DB-INSERT-FIELD-TOO-LONG","系统类-DB插入-字段超长",""),
    E_DB_INSERT_FIELD_TYPE_ERROR("E-DB-INSERT-FIELD-TYPE-ERROR","系统类-DB插入-类型不符",""),
    E_BUSINESS_BALANCE_NOT_ENOUGH("E-BUSINESS-BALANCE-NOT-ENOUGH","业务类-余额不足",""),

    E_BUSINESS_BUSINESSSERIAL_ISNULL("E-BUSINESS-BUSINESSSERIAL-ISNULL","object [businessserial ] is null","[序列号对象]为空"),
    E_BUSINESS_PARAMETER_PLATFORM_ISNULL("E-BUSINESS-PARAMETER-PLATFORM-ISNULL","parameter[platform] is null","必要参数[平台区分]为空"),
    E_BUSINESS_PARAMETER_BUSINESSNO_ISNULL("E-BUSINESS-PARAMETER-BUSINESSNO-ISNULL","parameter[businessno] is null","必要参数[业务编码]为空"),
    E_BUSINESS_PARAMETER_BUSINESSNO_SIZE_ERROR("E-BUSINESS-PARAMETER-BUSINESSNO-SIZE-ERROR","parameter[businessno] size must be "+ BusinessSerialValueRange.countForBusinessNo,"必要参数[业务编码]长度为"+BusinessSerialValueRange.countForBusinessNo+"，您超过范围了"),

    E_BUSINESS_PARAMETER_SYSTEMNO_ISNULL("E-BUSINESS-PARAMETER-SYSTEMNO-ISNULL","parameter[systemno] is null","必要参数[发生系统]为空"),
    E_BUSINESS_PARAMETER_SERVICENO_ISNULL("E-BUSINESS-PARAMETER-SERVICENO-ISNULL","parameter[serviceno] is null","必要参数[发生服务]为空"),
    E_BUSINESS_PARAMETER_TERMINALTYPE_ISNULL("E-BUSINESS-PARAMETER-TERMINALTYPE-ISNULL","parameter[terminaltype] is null","必要参数[终端类型]为空"),
    E_BUSINESS_PARAMETER_DATEOFHAPPEN_ISNULL("E-BUSINESS-PARAMETER-DATEOFHAPPEN-ISNULL","parameter[dateofhappen] is null","必要参数[发生日期]为空"),
    E_BUSINESS_PARAMETER_SERIALNUMBER_ISNULL("E-BUSINESS-PARAMETER-SERIALNUMBER-ISNULL","parameter[serialnumber] is null","必要参数[各系统顺号]为空"),
    E_BUSINESS_PARAMETER_NUMBER_ISNULL("E-BUSINESS-PARAMETER-NUMBER-ISNULL","parameter[number] is null","必要参数[获取个数]为空"),
    E_BUSINESS_PARAMETER_DETAILSERIALNUMBER_ISNULL("E-BUSINESS-PARAMETER-DETAILSERIALNUMBER-ISNULL","parameter[detailserialnumber] is null","必要参数[明细序号]为空"),

    E_BUSINESS_PARAMETER_PLATFORM_OUTOFRANGE("E-BUSINESS-PARAMETER-PLATFORM-OUTOFRANGE","parameter[platform] out of range","必要参数[平台区分]超出范围"),
    E_BUSINESS_PARAMETER_BUSINESSNO_OUTOFRANGE("E-BUSINESS-PARAMETER-BUSINESSNO-OUTOFRANGE","parameter[businessno] out of range","必要参数[业务编码]超出范围"),
    E_BUSINESS_PARAMETER_SYSTEMNO_OUTOFRANGE("E-BUSINESS-PARAMETER-SYSTEMNO-OUTOFRANGE","parameter[systemno] out of range","必要参数[发生系统]超出范围"),
    E_BUSINESS_PARAMETER_TERMINALTYPE_OUTOFRANGE("E-BUSINESS-PARAMETER-TERMINALTYPE-OUTOFRANGE","parameter[terminaltype] out of range","必要参数[终端类型]超出范围"),
    E_BUSINESS_PARAMETER_DATEOFHAPPEN_OUTOFRANGE("E-BUSINESS-PARAMETER-DATEOFHAPPEN-OUTOFRANGE","parameter[dateofhappen] out of range","必要参数[发生日期]超出范围"),
    E_BUSINESS_PARAMETER_SERIALNUMBER_OUTOFRANGE("E-BUSINESS-PARAMETER-SERIALNUMBER-OUTOFRANGE","parameter[serialnumber] out of range","必要参数[各系统顺号]超出范围"),
    E_BUSINESS_PARAMETER_NUMBER_OUTOFRANGE("E-BUSINESS-PARAMETER-NUMBER-OUTOFRANGE","parameter[number] out of range","必要参数[获取个数]超出范围"),
    E_BUSINESS_PARAMETER_DETAILSERIALNUMBER_OUTOFRANGE("E-BUSINESS-PARAMETER-DETAILSERIALNUMBER-OUTOFRANGE","parameter[detailserialnumber] out of range ","必要参数[明细序号]超出范围");


    private BusinessSerialError(String errorCode, String errorMsg, String remark ){
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
        this.remark=remark;
    }



    private String errorCode ;
    private String errorMsg;
    private String remark;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getRemark() {
        return remark;
    }
}
