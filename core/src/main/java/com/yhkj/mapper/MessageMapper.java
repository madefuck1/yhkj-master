package com.yhkj.mapper;

import com.yhkj.model.Message;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> getMessageList(Message e);

    int getMessageListCount(Message e);

    Message getCodeByPhone(Message message);
}