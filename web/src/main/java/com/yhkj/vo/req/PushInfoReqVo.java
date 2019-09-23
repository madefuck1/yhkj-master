package com.yhkj.vo.req;

import io.swagger.models.auth.In;

/**
 * @Author: Loulq
 * @Date: 2019/3/5 0005 14:37
 */
public class PushInfoReqVo {

    private Long userId;
    private int pushStatus;
    private int pushType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }
}
