package com.instabook.client.model.dos;

import com.instabook.client.context.StorageContext;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private String chatId;

    private List<Message> messages;

    private User anotherUser;

    private Integer type;

    public Chat(Message message) {
        this.chatId = message.getChatId();
        this.messages = List.of(message);
        this.anotherUser = new User();
        this.type = message.getType();
        if (StorageContext.user.getUserId().equals(message.getUserId())) {
            this.anotherUser.setUserName(message.getAnotherUserName());
            this.anotherUser.setHeadImg(message.getAnotherUserHeadImg());
            this.anotherUser.setUserId(message.getAnotherUserId());
        } else {
            this.anotherUser.setUserName(message.getUserName());
            this.anotherUser.setHeadImg(message.getUserHeadImg());
            this.anotherUser.setUserId(message.getUserId());
        }
    }

    public Chat(UserRelationship userRelationship) {
        this.chatId = userRelationship.getChatId();
        this.messages = new ArrayList<>();
        this.anotherUser = new User();
        this.anotherUser.setUserName(userRelationship.getAnotherUserName());
        this.anotherUser.setHeadImg(userRelationship.getAnotherUserHeadImg());
        this.anotherUser.setUserId(userRelationship.getAnotherUserId());
    }

    public Chat(User user) {
        this.chatId = user.getChatId();
        this.messages = new ArrayList<>();
        this.anotherUser = user;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public User getAnotherUser() {
        return anotherUser;
    }

    public void setAnotherUser(User anotherUser) {
        this.anotherUser = anotherUser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
