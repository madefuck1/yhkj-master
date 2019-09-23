package com.yhkj.enums;

/**
 * @Author: Loulq
 * @Date: 2019/3/28 8:51
 */
public enum  FavoritesTargetTypeEnum {

    product(0, "产品"),
    shop(1, "店铺");


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

    FavoritesTargetTypeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
