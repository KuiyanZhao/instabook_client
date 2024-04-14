package com.instabook.client.context;

import com.instabook.client.frame.ChatFrame;
import com.instabook.client.frame.MenuFrame;
import com.instabook.client.frame.UserSearchFrame;
import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.User;
import com.instabook.client.model.dos.UserApplication;
import com.instabook.client.model.dos.UserRelationship;

import java.util.List;
import java.util.Map;

public class StorageContext {

    public static User user;

    public static int screenWidth;

    public static int screenHeight;

    public static List<UserRelationship> friends;

    public static List<UserApplication> userApplications;

    public static MenuFrame menuFrame;

    public static Map<String, ChatFrame> chatFrameMap;

    public static int connectTimes = 0;
    public static UserSearchFrame userSearchFrame
            ;
}
