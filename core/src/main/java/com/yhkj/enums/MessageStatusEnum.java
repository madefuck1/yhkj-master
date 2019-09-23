package com.yhkj.enums;

/**
 * @Auther: lou
 * @Date: 2019/3/2
 */
public enum MessageStatusEnum {

    UNREAD(0, "未读"),
    ENREAD(1, "已读");

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

    MessageStatusEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
