package com.github.smartbuf.dubbo.model;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
@DefaultSerializer(CompatibleFieldSerializer.class)
public class UserModel implements Serializable {
    private int    id;
    private String token;
    private String nickname;
    private String loginIp;
    private long   loginTime;
    private long   createTime;
    private long   updateTime;
}