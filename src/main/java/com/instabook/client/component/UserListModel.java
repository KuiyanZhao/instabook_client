package com.instabook.client.component;

import com.instabook.client.model.dos.User;

import javax.swing.*;
import java.util.List;

public class UserListModel extends AbstractListModel<User> {

    private final List<User> userList;

    public UserListModel(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int getSize() {
        return userList.size();
    }

    @Override
    public User getElementAt(int i) {
        return userList.get(i);
    }
}
