package com.yhkj.enums;

/**
 * @Auther: chen
 * @Date: 2019/3/1
 * @Description: 账户类型
 */
public enum OfferStatusEnum {

    Reference(0,"参考中") ,
    Accept(1,"待付款"),
    Closed(2,"关闭"),
    Paid (3,"买家已付款"),
    Send (4,"卖家已发货"),
    Success (5,"交易成功"),
    Reimburse (6,"退款中"),
    ReimburseAndRefund (7,"退货退款中"),
    ReimburseOrRefundFail (8,"退货/退款失败"),
    TradingClosed (9,"交易关闭");

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

    OfferStatusEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public static String getMessage(int type){
        for(OfferStatusEnum offerStatusEnum : OfferStatusEnum.values()){
            if(offerStatusEnum.getType() == type){
                return offerStatusEnum.getMessage();
            }
        }
        return null ;
    }
}
