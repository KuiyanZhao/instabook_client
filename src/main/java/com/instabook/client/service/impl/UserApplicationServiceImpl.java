package com.instabook.client.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.instabook.client.constant.ApiConstants;
import com.instabook.client.context.StorageContext;
import com.instabook.client.handler.FormatResponseHandler;
import com.instabook.client.model.Page;
import com.instabook.client.model.dos.UserApplication;
import com.instabook.client.service.UserApplicationService;

public class UserApplicationServiceImpl implements UserApplicationService {


    @Override
    public void refreshUserApplicationList() {
        //get friend list
        try (HttpResponse response = HttpUtil.createGet(ApiConstants.applicationPage + "?pageSize=50")
                .header("Authorization", StorageContext.user.getToken())
                .execute()) {
            Page<UserApplication> userApplicationPage = FormatResponseHandler
                    .handlePageResponse(response.body(), UserApplication.class);
            if (userApplicationPage == null) {
                return;
            }
            StorageContext.userApplications = userApplicationPage.getRecords();
        }
    }

    @Override
    public UserApplication applyFriend(Long userId) {
        try (HttpResponse response = HttpUtil.createPost(ApiConstants.applyFriendUrl + "/" + userId)
                .header("Authorization", StorageContext.user.getToken())
                .execute()) {
            return FormatResponseHandler.handleResponse(response.body(), UserApplication.class);
        }
    }

    @Override
    public UserApplication replyFriend(Long applicationId, int status) {
        try (HttpResponse response = HttpUtil.createRequest(Method.PUT,
                        ApiConstants.replyFriendUrl + "/"
                        + applicationId + "/" + status)
                .header("Authorization", StorageContext.user.getToken())
                .execute()) {
            return FormatResponseHandler.handleResponse(response.body(), UserApplication.class);
        }
    }
}
