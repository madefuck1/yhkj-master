package com.yhkj.vo.rep;

import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/3/7
 * @Description:
 */
public class EnumRepVo extends AdminBaseRepVo {

    Map<Integer , String> data ;

    public Map<Integer, String> getData() {
        return data;
    }

    public void setData(Map<Integer, String> data) {
        this.data = data;
    }

    public EnumRepVo() {
        super();
    }
}
