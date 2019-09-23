package com.yhkj.vo.rep.user;

import com.yhkj.model.User;
import com.yhkj.vo.rep.AdminBaseRepVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/3/5
 * @Description:
 */
@Setter
@Getter
public class UserListRepVo extends AdminBaseRepVo {

    private List<User> data ;

    public UserListRepVo() {
        super();
    }
}
