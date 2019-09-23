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

    boolean login(User user);

    User getById(Long id);

    User loginByPhone(String phone);

    User getByPhone(String phone);

    PageHelp getAllUserList(User user, int page, int limit);

    PageHelp getAllUserListOnAdmin(User user, int page, int limit);

    void updateUser(User user);
}
