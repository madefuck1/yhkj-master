package com.yhkj.enums;

/**
 * @Author: Loulq
 * @Date: 2019/3/6 0006 15:28
 */
public enum  UserStatusEnum {

    using(0,"使用中"),
    disabled(9,"禁用");


    private int type ;
    private String message ;

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

    UserStatusEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
