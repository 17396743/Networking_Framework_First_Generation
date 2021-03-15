package com.example.myhttplibrary.exception;

/**
 * 项目名： My Application20
 * 包名：   com.example.myhttplibrary.exception
 * 文件名： ServerException
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 14:14
 * 描述：TODO
 */
public class ServerException extends RuntimeException{
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
