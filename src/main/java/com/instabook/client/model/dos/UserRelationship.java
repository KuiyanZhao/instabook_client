package com.instabook.client.model.dos;

import java.util.Date;

public class UserRelationship {

    private Long userRelationshipId;

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
     * name
     */
    private String anotherUserName;

    private String anotherUserHeadImg;

    /**
     * 0:normal;-1:block
     */
    private Integer relationStatus;

    /**
     * 0:false 1:true
     */
    private Integer friendStatus;

    /**
     * create_time
     */
    private Date createTime;

    public Long getUserRelationshipId() {
        return userRelationshipId;
    }

    public void setUserRelationshipId(Long userRelationshipId) {
        this.userRelationshipId = userRelationshipId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnotherUserId() {
        return anotherUserId;
    }

    public void setAnotherUserId(Long anotherUserId) {
        this.anotherUserId = anotherUserId;
    }

    public Integer getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Integer relationStatus) {
        this.relationStatus = relationStatus;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getAnotherUserName() {
        return anotherUserName;
    }

    public void setAnotherUserName(String anotherUserName) {
        this.anotherUserName = anotherUserName;
    }

    public String getAnotherUserHeadImg() {
        return anotherUserHeadImg;
    }

    public void setAnotherUserHeadImg(String anotherUserHeadImg) {
        this.anotherUserHeadImg = anotherUserHeadImg;
    }

    public String getAnotherUserHeadImg(int width, int height) {
        int min = Math.min(width, height);

        return anotherUserHeadImg.concat("?x-oss-process=image/resize,m_mfit,s_" + min + "/crop,w_" + width
                + ",h_" + height + ",g_center");
    }
}
