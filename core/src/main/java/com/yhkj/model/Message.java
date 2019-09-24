package com.yhkj.model;

import com.yhkj.enums.MessageTypeEnum;
import com.yhkj.utils.DateUtils;

import java.util.Date;

public class Message {
    private Long messageId;

    private String phone;

    private String content;

    private Integer messageStatus;

    private Date messageTime;

    private Integer messageType;

    private String messageSender;
    /**
     * 页面显示字段
     */
    private String messageTypeString;
    private String messageTimeString;

    /**
     * 查询字段
     */
    private Date beginDate;
    private Date endDate;

    public String getMessageTimeString() {
        return DateUtils.date2String(messageTime, DateUtils.format2);
    }

    public String getMessageTypeString() {
        return MessageTypeEnum.getMessage(this.messageType);
    }


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public void setMessageTypeString(String messageTypeString) {
        this.messageTypeString = messageTypeString;
    }

    public void setMessageTimeString(String messageTimeString) {
        this.messageTimeString = messageTimeString;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}