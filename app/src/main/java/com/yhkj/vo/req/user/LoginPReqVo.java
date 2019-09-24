package com.yhkj.vo.req.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: chen
 * @Date: 2019/3/4
 * @Description:
 */
@Setter
@Getter
public class LoginPReqVo {
    //手机号
    private String phone;
    //邮箱
    private String email;
    //密码
    private String password;
    //验证码
    private String verMessage;

}
