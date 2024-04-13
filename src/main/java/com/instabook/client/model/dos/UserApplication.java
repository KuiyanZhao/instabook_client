package com.instabook.client.model.dos;

import java.util.Date;

public class UserApplication {

    /**
     * key
     */
    private Long applicationId;

    /**
     * user id
     */
    private Long userId;

    /**
     * name
     */
    private String userName;

    private String userHeadImg;

    /**
     * another one
     */
    private Long anotherUserId;

    /**
     * 0:apply 1:pass -1:disapprove
     */
    private Integer status;

    /**
     * create time
     */
    private Date createTime;

    private Date updateTime;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public Long getAnotherUserId() {
        return anotherUserId;
    }

    public void setAnotherUserId(Long anotherUserId) {
        this.anotherUserId = anotherUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserHeadImg(int width, int height) {
        int min = Math.min(width, height);

        return userHeadImg.concat("?x-oss-process=image/resize,m_mfit,s_" + min + "/crop,w_" + width
                + ",h_" + height + ",g_center");
    }
}
