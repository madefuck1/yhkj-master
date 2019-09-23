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

    List<User> selectByPhone(String userPhone);

    List<User> selectByWx_id(String wxId);

    List<User> selectByQq_id(String qqId);

    List<User> getAllUserList(User user);
}