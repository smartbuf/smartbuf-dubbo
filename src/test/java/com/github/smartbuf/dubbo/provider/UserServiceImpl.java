package com.github.smartbuf.dubbo.provider;

import com.github.smartbuf.dubbo.model.UserModel;
import com.github.smartbuf.dubbo.UserService;

import java.util.List;
import java.util.UUID;

/**
 * @author sulin
 * @since 2019-11-17 15:44:13
 */
public class UserServiceImpl implements UserService {

    public String login(String username, String password) {
        return UUID.randomUUID().toString();
    }

    public UserModel getUser(int userId) {
        return null;
    }

    public List<UserModel> getFriends(int userId) {
        return null;
    }

}
