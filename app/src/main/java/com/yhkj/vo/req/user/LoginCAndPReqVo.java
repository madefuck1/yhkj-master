package com.yhkj.vo.req.user;

import com.yhkj.vo.req.user.LoginBaseReqVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Loulq
 * @Date: 2019/3/11 18:56
 */
@Setter
@Getter
public class LoginCAndPReqVo extends LoginBaseReqVo {

    private String code;
    private String Password;
}
