package com.yhkj.vo.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Loulq
 * @Date: 2019/3/15 15:00
 */
@ApiModel
public class UpdateUserDetailReqVo {

    @ApiModelProperty(value = "昵称",name = "username")
    private String username;

    @ApiModelProperty(value = "邮箱",name = "email")
    private String email;

    @ApiModelProperty(value = "头像",name = "avatarURL")
    private String avatarURL;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }


//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
}
