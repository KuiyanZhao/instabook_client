package com.instabook.client.service;

import com.instabook.client.model.dos.User;

import java.io.File;
import java.util.List;

public interface UserService {
    List<User> getList(String username);

    User uploadHeadImg(File selectedFile);
}
