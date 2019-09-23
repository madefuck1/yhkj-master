package com.yhkj.enums;

/**
 * @Auther: chen
 * @Date: 2019/3/18
 * @Description:
 */
public enum PurchaseEnum {

    purchasing(0,"采购中"),
    success(1,"采购成功"),
    cancel(2,"取消采购");
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

    PurchaseEnum(int type, String message) {
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
