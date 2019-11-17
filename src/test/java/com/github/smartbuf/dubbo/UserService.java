package com.github.smartbuf.dubbo;

import com.github.smartbuf.dubbo.model.UserModel;

import java.util.List;

/**
 * @author sulin
 * @since 2019-11-17 15:43:53
 */
public interface UserService {

    String login(String username, String password);

    UserModel getUser(int userId);

    List<UserModel> getFriends(int userId);

}
