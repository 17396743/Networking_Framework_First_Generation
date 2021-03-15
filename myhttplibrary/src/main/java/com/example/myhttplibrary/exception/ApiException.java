package com.example.myhttplibrary.exception;

/**
 * 项目名： My Application20
 * 包名：   com.example.myhttplibrary.exception
 * 文件名： ApiException
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 11:53
 * 描述：TODO
 */
public class ApiException extends Throwable{
    String msg;
    int code;

    public ApiException(){

    }
    public ApiException(String msg,int code){
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
