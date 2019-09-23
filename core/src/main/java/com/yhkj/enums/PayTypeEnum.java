package com.yhkj.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/3/7
 * @Description:
 */
public enum PayTypeEnum {

    aliPay(1,"支付宝"),
    wechatPay(2,"微信支付"),
    unionPay(3,"银联支付");

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

    PayTypeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }


    public static String getMessage(int type){
        for(PayTypeEnum payTypeEnum : PayTypeEnum.values()){
            if(payTypeEnum.getType() == type){
                return payTypeEnum.getMessage();
            }
        }
        return null ;
    }

    public static Map<Integer , String> getAll(){
        Map<Integer , String> map = new HashMap<>();
        for(PayTypeEnum payTypeEnum : PayTypeEnum.values()){
            map.put(payTypeEnum.getType(),payTypeEnum.getMessage());
        }
        return map ;
    }
}
