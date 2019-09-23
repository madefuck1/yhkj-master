package com.yhkj.vo.req.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Loulq
 * @Date: 2019/3/12 11:27
 */
@Getter
@Setter
public class CheckCodeReqVo {

    private String phone;

    private String code;

}
