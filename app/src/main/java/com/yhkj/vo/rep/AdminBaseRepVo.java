package com.yhkj.vo.rep;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: chen
 * @Date: 2019/3/5
 * @Description:
 */
@Setter
@Getter
public class AdminBaseRepVo {

    private String code ;
    private String msg ;
    private int count ;

    public AdminBaseRepVo() {
        this.code = "0";
    }
}
