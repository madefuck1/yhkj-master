package com.yhkj.mapper;

import com.yhkj.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByKey(User user);

    List<User> getAllUserList(User user);

    int getAllUserCount(User user);

    List<User> getAllUserListOnAdmin(User user);

    int getAllUserCountOnAdmin(User user);

    User selectByPhone(User user);
}