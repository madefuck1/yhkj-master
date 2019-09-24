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
    public User login_Phone(String phone,String password) {
        List<User> list = userMapper.selectByPhone(phone);
        if (list != null && list.size() > 0) {
            String md5Password = MD5Utils.md5(password);
            if (md5Password.equals(list.get(0).getUserPassword())) {
                return list.get(0);
            } else {
                //密码错误
                return null;
            }
        } else {
            //用户不存在
            return null;
        }
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean checkByPhone(String phone) {
        return userMapper.selectByPhone(phone)!=null;
    }

    @Override
    public User getByPhone(String phone) {
        List<User> list = userMapper.selectByPhone(phone);
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
        pageHelp.setCount(users.size());
        pageHelp.setData(users);
        return pageHelp;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

}
