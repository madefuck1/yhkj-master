package com.yhkj.utils;

import com.yhkj.vo.rep.PicUrlRepVo;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Loulq
 * @Date: 2019/3/20 15:05
 */
public class UploadPicUtil {

    public static PicUrlRepVo uploadPic(MultipartFile multipartFile, String ftpHost, String ftpUserName,
                                        String ftpPassword, int ftpPort, String port, String url) {
        PicUrlRepVo repVo = new PicUrlRepVo();
        boolean success = false;
        FTPClient ftpClient = null;
        Map<String, Object> map;
        StringBuffer picUrl = new StringBuffer();
        try {
            // 连接ftp
            ftpClient = FtpClient.getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            // 上传图片
            map = FtpClient.uploadImage(ftpClient, multipartFile, url);
            success = (boolean) map.get("success");
            if (success) {
                repVo.setSuccess(true);
                picUrl.append(ftpHost);
                picUrl.append(port);
                picUrl.append(url);
                picUrl.append("/");
                picUrl.append(map.get("uploadName"));
                repVo.setUrl(picUrl.toString());
                return repVo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        repVo.setSuccess(false);
        return repVo;
    }


    public static PicUrlRepVo uploadPic(MultipartFile[] multipartFiles, String ftpHost, String ftpUserName,
                                        String ftpPassword, int ftpPort, String port, String url) {
        PicUrlRepVo repVo = new PicUrlRepVo();
        boolean success = false;
        FTPClient ftpClient = null;
        Map<String, Object> map;

        StringBuffer picUrl = new StringBuffer();
        try {
            // 连接ftp
            ftpClient = FtpClient.getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            // 上传图片
            for (MultipartFile multipartFile : multipartFiles) {
                map = FtpClient.uploadImage(ftpClient, multipartFile, url);
                success = (boolean) map.get("success");
                if (success) {
                    repVo.setSuccess(true);
                    // repVo.setMessage(ftpHost + port + url + "/" + multipartFile.getOriginalFilename());
                    picUrl.append(ftpHost);
                    picUrl.append(port);
                    picUrl.append(url);
                    picUrl.append("/");
                    picUrl.append(map.get("uploadName"));
                    picUrl.append(";");
                } else {
                    repVo.setSuccess(false);
                    return repVo;
                }
            }
            repVo.setUrl(picUrl.toString().substring(0, picUrl.toString().lastIndexOf(";")));
            return repVo;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        repVo.setSuccess(false);
        return repVo;
    }

}
