package com.instabook.client.model.dos;

import java.util.Date;

public class MessageFile {

    private Long fileId;

    /**
     * 1:head_img 2:message
     */
    private Integer channel;

    /**
     * url
     */
    private String url;

    /**
     * path
     */
    private String path;

    /**
     * outer id
     */
    private String outerId;

    /**
     * create time
     */
    private Date createTime;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
