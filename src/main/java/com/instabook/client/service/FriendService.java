package com.instabook.client.service;

import com.instabook.client.model.dos.UserRelationship;

public interface FriendService {

    void refreshFriendList();

    UserRelationship getRelationship(Long userId);
}
