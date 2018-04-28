package com.wiatec.panel.common.result;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author patrick
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private int code;
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int count;
    private T data;



    private Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }


    private Result(EnumResult enumResult){
        this.code = enumResult.getCode();
        this.msg = enumResult.getMessage();
    }

    private Result(EnumResult enumResult, T data){
        this.code = enumResult.getCode();
        this.msg = enumResult.getMessage();
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.code == EnumResult.SUCCESS.getCode();
    }

    public static Result success(){
        return new Result(EnumResult.SUCCESS);
    }

    public static Result success(String msg){
        return new Result(EnumResult.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(EnumResult.SUCCESS, data);
    }

    public static Result error(){
        return new Result(EnumResult.ERROR_INTERNAL_SERVER_SQL);
    }

    public static Result error(EnumResult enumResult){
        return new Result(enumResult);
    }

    public static Result error(String msg){
        return new Result(EnumResult.ERROR_INTERNAL_SERVER.getCode(), msg);
    }

    public static Result error(int code, String msg){
        return new Result(code, msg);
    }







    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public Result<T> setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
