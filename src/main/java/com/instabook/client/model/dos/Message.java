package com.instabook.client.model.dos;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Message {

    private Long messageId;

    /**
     * user id
     */
    private Long userId;

    private String userName;

    private String userHeadImg;

    /**
     * another one
     */
    private Long anotherUserId;

    private String anotherUserName;

    private String anotherUserHeadImg;

    /**
     * chat id
     */
    private String chatId;

    /**
     * message object
     */
    private JSONObject message;

    /**
     * 0:text 1:img 2:video 3:file
     */
    private Integer type;

    /**
     * create time
     */
    private Date createTime;

    /**
     * user del it
     */
    private Integer delFlag;

    /**
     * another one del it
     */
    private Integer anotherDelFlag;


    /**
     * use to mark a request
     * for data synchronization in different endpoints or result reply(timeout, not friend, blocked etc.)
     */
    private String requestId;

    private Integer done;

    private Integer error;

    public String getContent() {
        return this.message != null ? this.message.getString("content") : null;
    }

    public void setContent(int messageType, String content) {
        this.type = messageType;
        // Initialize 'message' if it's null
        if (this.message == null) {
            this.message = new JSONObject();
        }
        this.message.put("content", content);
    }

    public void setContent(String content) {
        setContent(0, content);
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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

    public String getUserHeadImg(int width, int height) {
        int min = Math.min(width, height);

        return userHeadImg.concat("?x-oss-process=image/resize,m_mfit,s_" + min + "/crop,w_" + width
                + ",h_" + height + ",g_center");
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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public JSONObject getMessage() {
        return message;
    }

    public void setMessage(JSONObject message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getAnotherDelFlag() {
        return anotherDelFlag;
    }

    public void setAnotherDelFlag(Integer anotherDelFlag) {
        this.anotherDelFlag = anotherDelFlag;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
