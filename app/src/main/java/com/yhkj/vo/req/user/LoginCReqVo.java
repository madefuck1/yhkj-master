package com.yhkj.vo.req.user;

import com.yhkj.vo.req.user.LoginBaseReqVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Loulq
 * @Date: 2019/3/11 0011 16:35
 */
@Getter
@Setter
public class LoginCReqVo extends LoginBaseReqVo {

    private String code;
}
