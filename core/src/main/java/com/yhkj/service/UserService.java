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

    User getByPhone(String phone);

    void updateUser(User user);

    PageHelp getAllUserList(User user, int page, int limit);

    PageHelp getAllUserListOnAdmin(User user, int page, int limit);

    boolean delUserDetail(User user);

    void resetUserPassword(User user);

    boolean updUserAvatar(User user);

    User loginByPhone(String phone);
}
