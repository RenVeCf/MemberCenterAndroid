package com.liantong.membercenter.membercenter.base;

/**
 * 作者：rmy on 2017/12/28 11:19
 * 邮箱：942685687@qq.com
 */

public class BaseResponse<T> {

    private String errorCode;
    private String errorMsg;
    private String displayedMsg;
    private String timestamp;
    private String status;
    private T data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getDisplayedMsg() {
        return displayedMsg;
    }

    public void setDisplayedMsg(String displayedMsg) {
        this.displayedMsg = displayedMsg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
