package com.github.smartbuf.dubbo;

import com.github.smartbuf.dubbo.impl.PostModel;
import com.github.smartbuf.dubbo.impl.UserModel;

import java.util.List;

/**
 * @author sulin
 * @since 2019-11-17 15:43:53
 */
public interface UserService {

    String login(String username, String password);

    UserModel getUser(Integer userId);

    List<PostModel> queryPost(String keyword);

}
