package com.yhkj.vo.req.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Loulq
 * @Date: 2019/3/11 0011 16:56
 */
@Setter
@Getter
public class SendCodeAndTypeReqVo {
    private String phone;
    private int type;
}
