package com.yhkj.utils;

import com.yhkj.sms.SendCodeTool;
import com.yhkj.sms.response.SmsSendResponse;
import org.springframework.beans.factory.annotation.Value;

public class MessageUtil {
    @Value("${message.account}")
    private static String account;
    @Value("${message.password}")
    private static String password;
    @Value("${message.smsSingleRequestServerUrl}")
    private static String smsSingleRequestServerUrl;
    @Value("${message.report}")
    private static String report;

    public static SmsSendResponse setMessage(String content, String phone) {
        SmsSendResponse smsSendResponse = SendCodeTool.setCode(account, password, smsSingleRequestServerUrl, content, phone, report);
        return smsSendResponse;
    }


}
