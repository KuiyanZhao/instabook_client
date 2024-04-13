package com.instabook.client.component;

import com.instabook.client.model.dos.UserApplication;

import javax.swing.*;
import java.util.List;

public class UserApplicationListModel extends AbstractListModel<UserApplication> {

    private final List<UserApplication> userApplicationList;

    public UserApplicationListModel(List<UserApplication> userApplicationList) {
        this.userApplicationList = userApplicationList;
    }

    @Override
    public int getSize() {
        return userApplicationList.size();
    }

    @Override
    public UserApplication getElementAt(int i) {
        return userApplicationList.get(i);
    }
}
