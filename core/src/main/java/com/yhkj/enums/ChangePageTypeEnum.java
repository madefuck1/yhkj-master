package com.yhkj.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Loulq
 * @Date: 2019/3/12 13:59
 */
public enum ChangePageTypeEnum {


    normal(0, "NORMAL"),        //正常页面，不跳转
    register(1, "REGISTER"),    //注册页面跳转
    login(2, "LOGIN");          //登录页面跳转


    private int type;

    private String message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ChangePageTypeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public static String getMessage(int type) {
        for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
            if (messageTypeEnum.getType() == type) {
                return messageTypeEnum.getMessage();
            }
        }
        return null;
    }

    public static Map<Integer, String> getAll() {
        Map<Integer, String> map = new HashMap<>();
        for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
            map.put(messageTypeEnum.getType(), messageTypeEnum.getMessage());
        }
        return map;
    }

}
