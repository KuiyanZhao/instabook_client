package com.instabook.client.model.dos;

import com.instabook.client.context.StorageContext;

public class User {
    private Long userId;

    private String userName;

    private String password;

    private String headImg;

    private String token;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getHeadImg(int width, int height) {
        int min = Math.min(width, height);
        return headImg.concat("?x-oss-process=image/resize,m_mfit,s_" + min + "/crop,w_" + width
                + ",h_" + height + ",g_center");

    }


    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChatId() {
        return Math.min(StorageContext.user.getUserId(), userId) + ""
                + Math.max(StorageContext.user.getUserId(), userId);
    }
}
