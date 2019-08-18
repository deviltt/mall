package com.mall.common;

import com.mall.enums.ResponseStatus;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @author tt
 */
//目的是null字段的属性不会显示在json里面
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponseBody<T> implements Serializable {
    //返回状态
    private int status;
    //返回状态信息
    private String msg;
    //返回数据(泛型)
    private T data;

    private ServerResponseBody(int status) {
        this.status = status;
    }

    private ServerResponseBody(int status, T data) {
        this.data = data;
    }

    private ServerResponseBody(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponseBody(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    //序列化的时候不会显示再json里
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseStatus.SUCCESS.getStatus();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponseBody<T> createBySuccess() {
        return new ServerResponseBody<>(ResponseStatus.SUCCESS.getStatus());
    }

    public static <T> ServerResponseBody<T> createBySuccessMessage(String msg){
        return new ServerResponseBody<>(ResponseStatus.SUCCESS.getStatus(), msg);
    }

    public static <T> ServerResponseBody<T> createBySuccess(T data){
        return new ServerResponseBody<>(ResponseStatus.SUCCESS.getStatus(), data);
    }

    public static <T> ServerResponseBody<T> createBySuccess(String msg, T data){
        return new ServerResponseBody<>(ResponseStatus.SUCCESS.getStatus(), msg, data);
    }

    public static <T> ServerResponseBody<T> createByError(){
        return new ServerResponseBody<>(ResponseStatus.ERROR.getStatus());
    }

    public static <T> ServerResponseBody<T> createByError(String msg){
        return new ServerResponseBody<>(ResponseStatus.ERROR.getStatus(), msg);
    }

    public static <T> ServerResponseBody<T> createByError(int errorStatus, String errorMsg){
        return new ServerResponseBody<>(errorStatus, errorMsg);
    }
}
