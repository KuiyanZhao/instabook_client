package com.instabook.client.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.instabook.client.constant.ApiConstants;
import com.instabook.client.context.StorageContext;
import com.instabook.client.handler.FormatResponseHandler;
import com.instabook.client.model.dos.UserRelationship;
import com.instabook.client.service.FriendService;

import javax.swing.*;

public class FriendServiceImpl implements FriendService {
    @Override
    public void refreshFriendList() {
        //get friend list
        try (HttpResponse response = HttpUtil.createGet(ApiConstants.userRelationshipListUrl)
                .header("Authorization", StorageContext.user.getToken())
                .execute()) {
            StorageContext.friends = FormatResponseHandler.handleListResponse(response.body(), UserRelationship.class);
        }
    }

    @Override
    public UserRelationship getRelationship(Long userId) {
        try (HttpResponse response = HttpUtil.createGet(ApiConstants.userRelationshipUrl + "?userId=" + userId)
                .header("Authorization", StorageContext.user.getToken())
                .execute()) {
            return FormatResponseHandler.handleResponse(response.body(), UserRelationship.class);
        }
    }
}
