package com.yhkj.vo.rep.user;

import com.yhkj.vo.rep.AdminBaseRepVo;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/3/9
 * @Description:
 */
public class SuggestRepVo extends AdminBaseRepVo {

    private List<Suggest> data ;

    public List<Suggest> getData() {
        return data;
    }

    public void setData(List<Suggest> data) {
        this.data = data;
    }
}
