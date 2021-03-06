package com.yhkj.service;

import com.yhkj.model.User;
import com.yhkj.utils.PageHelp;

/**
 * @Auther: chen
 * @Date: 2019/3/1
 * @Description:
 */
public interface UserService {

    void registerUser(User user);

    User login_Phone(String phone,String password);

    User getById(Long id);

    Boolean checkByPhone(String phone);

    User getByPhone(String phone);

    PageHelp getAllUserList(User user, int page, int limit);

    boolean updateUser(User user);
}
