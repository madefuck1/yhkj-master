package com.yhkj.enums;

/**
 * @Auther: chen
 * @Date: 2019/3/1
 * @Description: 账户类型
 */
public enum UserLevelEnum {

    buyer(1,"买家") ,
    seller(2,"卖家") ,
    admin(99,"管理员");


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

    UserLevelEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
