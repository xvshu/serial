package com.xs.frame.iserial.enums;

/**
 * Created by Administrator on 2018/9/3 0003.
 */
public enum ErrorEnum implements EnumMsgType {
    PARAM_ERROR("用户输入参数错误", "E1005"),
    NO_DATA("无数据", "E8884"),
    NET_ERROR("网络异常，请稍后再试", "E1007"),
    SUCCESS("成功", "E8888");

    private String msg;
    private String msgCode;

    private ErrorEnum(String msg, String msgCode) {
        this.msg = msg;
        this.msgCode = msgCode;
    }

    public static String getName(String code) {
        ErrorEnum[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            ErrorEnum c = arr$[i$];
            if(c.getMsgCode().equals(code)) {
                return c.msg;
            }
        }

        return null;
    }

    public static ErrorEnum getErrorEnum(String code) {
        ErrorEnum[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            ErrorEnum c = arr$[i$];
            if(c.getMsgCode().equals(code)) {
                return c;
            }
        }

        return null;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgCode() {
        return this.msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}
