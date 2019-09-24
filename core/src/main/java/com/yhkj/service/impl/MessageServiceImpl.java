package com.yhkj.service.impl;

import com.github.pagehelper.PageHelper;
import com.yhkj.mapper.MessageMapper;
import com.yhkj.model.Message;
import com.yhkj.service.MessageService;
import com.yhkj.utils.PageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.yhkj.utils.DateUtils.getDateDifference;

/**
 * @Auther: lou
 * @Date: 2019/3/3
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public boolean insertMessage(Message e) {
        return messageMapper.insert(e) > 0 ? true : false;
    }

    @Override
    public void updateMessage(Message e) {
        messageMapper.updateByPrimaryKeySelective(e);
    }

    @Override
    public void deleteMessage(Long id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageHelp<Message> getMessageList(Message message, int page, int limit) {
        PageHelp pageHelp = new PageHelp();
        pageHelp.setCount(messageMapper.getMessageListCount(message));
        PageHelper.startPage(page, limit);
        pageHelp.setData(messageMapper.getMessageList(message));
        return pageHelp;
    }

    @Override
    public boolean isExpiredOrCorrect(String phone, String code, int type) throws Exception {
        Message message = new Message();
        message.setPhone(phone);
        message.setContent(code);
        message.setMessageType(type);
        Message e = messageMapper.getCodeByPhone(message);
        if (e == null) {
            return false; // 还么有注册码
        } else {
            int time = getDateDifference(new Date(), e.getMessageTime(), "min");
            if (time <= 3) {
                if (e.getContent().equals(code)) {
                    return true;//验证通过
                }
            } else {
                return false;  // 验证码超时！
            }
        }
        return true;
    }

    @Override
    public Message getMessageDetail(Long id) {
        return messageMapper.selectByPrimaryKey(id);
    }
}
