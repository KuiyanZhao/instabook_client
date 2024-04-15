package com.instabook.client.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.instabook.client.constant.ApiConstants;
import com.instabook.client.context.StorageContext;
import com.instabook.client.handler.FormatResponseHandler;
import com.instabook.client.model.dos.User;
import com.instabook.client.service.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getList(String username) {
        if (username == null || username.length() == 0) {
            return new ArrayList<>();
        }
        try (HttpResponse response = HttpUtil.createGet(ApiConstants.userListUrl + "?userName=" + username)
                .header("Authorization", StorageContext.user.getToken())
                .execute()) {
            return FormatResponseHandler.handleListResponse(response.body(), User.class);
        }
    }

    @Override
    public User uploadHeadImg(File selectedFile) {
        try (HttpResponse response = HttpRequest.put(ApiConstants.userHeadImgUrl)
                .form("file", selectedFile)
                .header("Authorization", StorageContext.user.getToken())
                .execute()) {
            return FormatResponseHandler.handleResponse(response.body(), User.class);
        }
    }
}
