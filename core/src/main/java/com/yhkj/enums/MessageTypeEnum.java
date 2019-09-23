package com.yhkj.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Loulq
 * @Date: 2019/3/5 0005 11:42
 */
public enum MessageTypeEnum {

    REGISTER(0, "注册"),
    LOGIN(1, "登录"),
    RESETPASSWORD(2, "重置"),
    REMINDMESSAGE(3, "提醒");

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

    MessageTypeEnum(int type, String message) {
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
