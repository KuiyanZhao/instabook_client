package com.instabook.client.service;

import com.instabook.client.model.dos.UserApplication;

public interface UserApplicationService {
    void refreshUserApplicationList();

    UserApplication applyFriend(Long userId);

    UserApplication replyFriend(Long applicationId, int i);
}
