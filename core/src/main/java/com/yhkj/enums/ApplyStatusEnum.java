package com.yhkj.enums;

/**
 * @Auther: chen
 * @Date: 2019/3/1
 * @Description: 账户类型
 */
public enum ApplyStatusEnum {

    applying(1,"申请中") ,
    applySuccess(2,"申请成功"),
    applyFail(3,"申请失败"),
    closed(99,"店铺关闭");


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

    ApplyStatusEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public static String getMessage(int type){
        for(ApplyStatusEnum applyStatusEnum : ApplyStatusEnum.values()){
            if(applyStatusEnum.getType() == type){
                return applyStatusEnum.getMessage();
            }
        }
        return null ;
    }

}
