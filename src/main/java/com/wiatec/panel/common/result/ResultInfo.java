package com.wiatec.panel.common.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * the result that return to user after user's request
 * @author patrick
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultInfo<T> {

    private int code;
    private String message;
    private T data;
    private List<T> dataList;


    @JsonIgnore
    public boolean isSuccess(){
        return this.code == EnumResult.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", dataList=" + dataList +
                '}';
    }
}
