package com.yhkj.service;

import com.yhkj.model.Message;
import com.yhkj.utils.PageHelp;


/**
 * @Auther: lou
 * @Date: 2019/3/3
 */
public interface MessageService {

    boolean insertMessage(Message e);

    void updateMessage(Message e);

    void deleteMessage(Long id);

    PageHelp<Message> getMessageList(Message message, int page, int limit);

    Message getMessageDetail(Long id);

    boolean isExpiredOrCorrect(String phone, String code, int type) throws Exception;
}
