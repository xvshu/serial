package com.xs.frame.iserial;

import com.xs.frame.iserial.enums.EnumMsgType;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Administrator on 2018/9/3 0003.
 */
public class PublicResult<T extends Serializable> implements Serializable {
    public static final String DEFAULT_ERROR_CODE = "0";
    private boolean success;
    private T result;
    private Map<String, String> errorMap;
    private EnumMsgType enumMsgType;
    private List<EnumMsgType> msgList;
    private String errorCode;
    private String errorMsg;
    private List<String[]> errorList;

    public PublicResult() {
    }

    public PublicResult(boolean success) {
        this.success = success;
    }

    public PublicResult(String errorCode, String msg, boolean isOk) {
        this.addErrorMsg(errorCode, msg);
        this.setSuccess(isOk);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /** @deprecated */
    @Deprecated
    public Map<String, String> getErrorMap() {
        return this.errorMap;
    }

    /** @deprecated */
    @Deprecated
    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }

    /** @deprecated */
    @Deprecated
    public void addErrorMsg(String msg) {
        if(null == this.errorMap) {
            synchronized(this) {
                if(null == this.errorMap) {
                    this.errorMap = new HashMap();
                }
            }
        }

        this.errorMap.put("0", msg);
    }

    /** @deprecated */
    @Deprecated
    public void addErrorMsg(String errorCode, String msg) {
        if(null == this.errorMap) {
            synchronized(this) {
                if(null == this.errorMap) {
                    this.errorMap = new HashMap();
                }
            }
        }

        this.errorMap.put(errorCode, msg);
    }

    public String getErrorMsg() {
        if(null == this.errorMap) {
            return this.errorMsg;
        } else {
            StringBuilder msg = new StringBuilder();
            Iterator i$ = this.errorMap.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                msg.append("errorCode:").append((String)entry.getKey()).append(",value:").append((String)entry.getValue()).append(",");
            }

            if(msg.length() > 0) {
                msg.deleteCharAt(msg.length() - 1);
            }

            return msg.toString();
        }
    }

    public EnumMsgType getEnumMsgType() {
        return this.enumMsgType;
    }

    public List<EnumMsgType> getMsgList() {
        return this.msgList;
    }

    public void setMsgList(List<EnumMsgType> msgList) {
        this.msgList = msgList;
        if(null != this.msgList && !this.msgList.isEmpty()) {
            this.errorList = new ArrayList(msgList.size());
            Iterator i$ = msgList.iterator();

            while(i$.hasNext()) {
                EnumMsgType type = (EnumMsgType)i$.next();
                this.errorList.add(new String[]{type.getMsgCode(), type.getMsg()});
            }
        }

    }

    public void setEnumMsgType(EnumMsgType enumMsgType) {
        this.enumMsgType = enumMsgType;
        if(this.enumMsgType != null) {
            this.setErrorCode(enumMsgType.getMsgCode());
            this.setErrorMsg(enumMsgType.getMsg());
        }

    }

    public List<String[]> getErrorList() {
        return this.errorList;
    }

    public void setErrorList(List<String[]> errorList) {
        this.errorList = errorList;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
