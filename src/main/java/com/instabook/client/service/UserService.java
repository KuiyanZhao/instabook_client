package com.instabook.client.service;

import com.instabook.client.model.dos.User;

import java.util.List;

public interface UserService {
    List<User> getList(String username);
}
