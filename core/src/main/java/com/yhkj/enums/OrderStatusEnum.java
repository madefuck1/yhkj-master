package com.yhkj.enums;

/**
 * @Auther: chen
 * @Date: 2019/3/1
 * @Description: 账户类型
 */
public enum OrderStatusEnum {

    PreOrder(0, "预订单"),
    Cancel(1, "交易关闭"),
    Unpaid(2, "等待买家付款"),
    Paid(3, "买家已付款"),
    Unreceived(4, "卖家已发货"),
    Received(5, "交易完成"),
    BuyerInitiateReturn(6, "买家发起退货"),
    SellerAgreeReturn(7, "卖家同意退货"),
    SellerOpposition(8, "卖家不同意退货"),
    BuyerReturnGoods(9, "买家退货"),
    SellerReimburse(10, "卖家确认收货退款");


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

    OrderStatusEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public static String getMessage(int type) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getType() == type) {
                return orderStatusEnum.getMessage();
            }
        }
        return null;
    }
}
