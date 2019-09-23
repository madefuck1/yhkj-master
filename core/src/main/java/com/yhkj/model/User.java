package com.yhkj.model;

import com.yhkj.utils.DateUtils;

import java.util.Date;

public class User {
    private Long userId;

    private String phone;

    private String password;

    private String username;

    private String email;

    private String address;

    private Date createTime;

    private String avatar;

    private Integer userStatus;

    private Integer userLevel;

    private UserShop userShop;

    private String createTimeString;

    public String getCreateTimeString() {
        return createTime == null ? null : DateUtils.date2String(createTime, DateUtils.format2);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public UserShop getUserShop() {
        return userShop;
    }

    public void setUserShop(UserShop userShop) {
        this.userShop = userShop;
    }
}