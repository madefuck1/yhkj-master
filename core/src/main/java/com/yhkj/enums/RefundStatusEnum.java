package com.yhkj.enums;

/**
 * @Auther: chen
 * @Date: 2019/3/1
 * @Description: 账户类型
 */
public enum RefundStatusEnum {

    agreed(1,"同意") ,
    opposition(2,"不同意");


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

    RefundStatusEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public static String getMessage(int type){
        for(RefundStatusEnum applyStatusEnum : RefundStatusEnum.values()){
            if(applyStatusEnum.getType() == type){
                return applyStatusEnum.getMessage();
            }
        }
        return null ;
    }

}
