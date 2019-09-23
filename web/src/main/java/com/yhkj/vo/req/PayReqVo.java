package com.yhkj.vo.req;

/**
 * @Author: Loulq
 * @Date: 2019/3/5 0005 15:34
 */
public class PayReqVo {

    private Long userId;

    private String payTime;

    private Long payOrderId;

    private String payType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
