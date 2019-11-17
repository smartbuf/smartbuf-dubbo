package com.github.smartbuf.dubbo.model;

import lombok.Data;

@Data
public class UserModel {
    private int    id;
    private String token;
    private String nickname;
    private String loginIp;
    private long   loginTime;
    private long   createTime;
    private long   updateTime;
}