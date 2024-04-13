package com.instabook.client.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
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
        try (HttpResponse response = HttpUtil.createGet(ApiConstants.applicationPage)
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
}
