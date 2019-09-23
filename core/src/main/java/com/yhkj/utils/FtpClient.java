package com.yhkj.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FtpClient {

    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                System.out.println("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                System.out.println("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    // 上传图片
    public static Map<String,Object> uploadImage(FTPClient ftpClient, MultipartFile multipartFile, String url) throws IOException {
        Map<String,Object> map = new HashMap<>();
        boolean success ;
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
        }
        ftpClient.setControlEncoding("UTF-8"); // 中文支持
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(url);

        InputStream local = multipartFile.getInputStream();
        String name = multipartFile.getOriginalFilename();
        // 转换后的目录名或文件名。ftp中文乱码问题 , 获取后缀名  重新生成随机数，防止重复
        String uploadName = new String(name.getBytes("UTF-8"), "iso-8859-1");
        String suffix = uploadName.substring(uploadName.lastIndexOf(".") + 1);
        uploadName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;
        success = ftpClient.storeFile(uploadName, local);
        local.close();
        ftpClient.logout();

        map.put("uploadName",uploadName);
        map.put("success",success);
        return map;
    }

}
