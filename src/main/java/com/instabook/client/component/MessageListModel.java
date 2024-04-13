package com.instabook.client.component;

import com.instabook.client.model.dos.Message;

import javax.swing.*;
import java.util.List;

public class MessageListModel extends AbstractListModel<Message> {

    private final List<Message> messageList;

    public MessageListModel(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getSize() {
        return messageList.size();
    }

    @Override
    public Message getElementAt(int i) {
        return messageList.get(i);
    }

}
