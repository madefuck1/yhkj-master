package com.yhkj.service.impl;

import com.github.pagehelper.PageHelper;
import com.yhkj.enums.UserLevelEnum;
import com.yhkj.enums.UserStatusEnum;
import com.yhkj.mapper.UserMapper;
import com.yhkj.model.User;
import com.yhkj.service.UserService;
import com.yhkj.utils.MD5Utils;
import com.yhkj.utils.PageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/3/1
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void registerUser(User user) {
        if (user.getUserId() == null) {
            //新用户
            user.setUserStatus(UserStatusEnum.using.getType());
            user.setUserLevel(UserLevelEnum.buyer.getType());
            userMapper.insertSelective(user);
        }
    }

    @Override
    public boolean login(User user) {
        String password = user.getUserPassword();
        user.setUserPassword(null);
        List<User> list = userMapper.selectByKey(user);
        if (list != null && list.size() > 0) {
            String md5Password = MD5Utils.md5(password);
            if (md5Password.equals(list.get(0).getPassword())) {
                return true;
            } else {
                //密码错误
                return false;
            }
        } else {
            //用户不存在
            return false;
        }
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User loginByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setUserStatus(0);
        return userMapper.selectByPhone(user);
    }

    @Override
    public User getByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        List<User> list = userMapper.selectByKey(user);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    @Override
    public PageHelp getAllUserList(User user, int page, int limit) {
        PageHelper.startPage(page, limit);
        PageHelp pageHelp = new PageHelp();
        List<User> users = userMapper.getAllUserList(user);
        int count = userMapper.getAllUserCount(user);
        pageHelp.setCount(count);
        pageHelp.setData(users);
        return pageHelp;
    }

    @Override
    public PageHelp getAllUserListOnAdmin(User user, int page, int limit) {
        PageHelper.startPage(page, limit);
        PageHelp pageHelp = new PageHelp();
        List<User> users = userMapper.getAllUserListOnAdmin(user);
        int count = userMapper.getAllUserCountOnAdmin(user);
        pageHelp.setCount(count);
        pageHelp.setData(users);
        return pageHelp;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean updUserAvatar(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

    @Override
    public void resetUserPassword(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
