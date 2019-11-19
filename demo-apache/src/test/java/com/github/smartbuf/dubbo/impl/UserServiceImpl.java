package com.github.smartbuf.dubbo.impl;

import com.github.smartbuf.dubbo.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author sulin
 * @since 2019-11-17 15:44:13
 */
public class UserServiceImpl implements UserService {

    private UserModel       user;
    private List<PostModel> posts = new ArrayList<>();

    public UserServiceImpl() {
        for (int i = 0; i < 20; i++) {
            UserModel user = new UserModel();
            user.setId(RandomUtils.nextInt(10000, 99999));
            user.setNickname(RandomStringUtils.randomAlphanumeric(10));
            user.setLoginIp("127.0.0.1");
            user.setLoginTime(10);
            user.setCreateTime(System.currentTimeMillis());
            user.setUpdateTime(System.currentTimeMillis());
            user.setToken(UUID.randomUUID().toString());
            if (i == 0) {
                this.user = user;
            } else {
                this.user.getFriends().add(user);
            }
        }

        for (int i = 0; i < 100; i++) {
            PostModel post = PostModel.random();
            for (int j = 0; j < 5; j++) {
                post.getTopics().add(TopicModel.getFromPool());
            }
            posts.add(post);
        }
    }

    public String login(String username, String password) {
        return user.getToken();
    }

    public UserModel getUser(Integer userId) {
        return user;
    }

    @Override
    public List<PostModel> queryPost(String keyword) {
        return posts;
    }

}
