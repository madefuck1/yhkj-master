package com.yhkj.vo.req.user;

import com.yhkj.vo.req.PageReqVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Loulq
 * @Date: 2019/3/5 0005 14:03
 */
@Setter
@Getter
public class UserReqVo extends PageReqVo {

    private String phone;

    private String username;


}
