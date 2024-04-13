package com.instabook.client.component;

import com.instabook.client.model.dos.UserRelationship;

import javax.swing.*;
import java.util.List;

public class FriendListModel extends AbstractListModel<UserRelationship> {

    private final List<UserRelationship> friendList;

    public FriendListModel(List<UserRelationship> friendList) {
        this.friendList = friendList;
    }

    @Override
    public int getSize() {
        return friendList.size();
    }

    @Override
    public UserRelationship getElementAt(int i) {
        return friendList.get(i);
    }
}
