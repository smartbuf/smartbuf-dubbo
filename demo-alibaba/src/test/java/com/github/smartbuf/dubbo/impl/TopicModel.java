package com.github.smartbuf.dubbo.impl;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sulin
 * @since 2019-11-19 10:20:59
 */
@Data
@DefaultSerializer(CompatibleFieldSerializer.class)
public class TopicModel implements Serializable {

    private static final List<TopicModel> pools = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            TopicModel topic = new TopicModel();
            topic.setId(RandomUtils.nextInt(10000, 50000));
            topic.setName(RandomStringUtils.randomAlphanumeric(16));
            topic.setDescription(RandomStringUtils.randomAlphanumeric(64));
            topic.setPortrait("http://helloworld.com/img/default-portrait.png");
            topic.setBackground("http://helloworld.com/img/default-background.png");
            topic.setPostNum(RandomUtils.nextInt(1000, 2000));
            topic.setFollowerNum(RandomUtils.nextInt(5000, 8000));
            topic.setCreateTime(System.currentTimeMillis());
            pools.add(topic);
        }
    }

    public static TopicModel getFromPool() {
        TopicModel model = pools.get(RandomUtils.nextInt(0, pools.size()));
        return new TopicModel(model);
    }

    private int    id;
    private String name;
    private String portrait;
    private String background;
    private String description;
    private int    postNum;
    private int    followerNum;
    private long   createTime;

    public TopicModel() {
    }

    TopicModel(TopicModel model) {
        this.id = model.id;
        this.name = model.name;
        this.portrait = model.portrait;
        this.background = model.background;
        this.description = model.description;
        this.postNum = model.postNum;
        this.followerNum = model.followerNum;
        this.createTime = model.createTime;
    }

}