package com.yhkj.enums;

/**
 * @Auther: chen
 * @Date: 2019/3/25
 * @Description:
 */
public enum  OrderPageStatusEnum {

    all(0,"全部"),
    to_pay(1,"待付款"),
    success(2,"已完成"),
    refund(3,"已退货");

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

    OrderPageStatusEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
