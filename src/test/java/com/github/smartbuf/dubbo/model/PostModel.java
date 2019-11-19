package com.github.smartbuf.dubbo.model;

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
public class PostModel implements Serializable {
    private int         postId;
    private int         authorId;
    private Integer     prePostId;
    private String      title;
    private String      description;
    private ContentType contentType;
    private Visibility  visibility;
    private long        createTime;

    private List<Integer>    mentions = new ArrayList<>();
    private List<TopicModel> topics   = new ArrayList<>();

    public static PostModel random() {
        PostModel post = new PostModel();
        post.setPostId(RandomUtils.nextInt(1000000, 2000000));
        post.setAuthorId(RandomUtils.nextInt(1000000, 1001000));
        post.setPrePostId(RandomUtils.nextInt(1000000, 1001000));
        post.setTitle(RandomStringUtils.randomAlphanumeric(12));
        post.setDescription(RandomStringUtils.randomAlphanumeric(40));
        post.setCreateTime(System.currentTimeMillis());
        post.setContentType(ContentType.values()[RandomUtils.nextInt(0, 5)]);
        post.setVisibility(Visibility.values()[RandomUtils.nextInt(0, 3)]);
        for (int i = 0; i < 20; i++) {
            post.getMentions().add(RandomUtils.nextInt(1000000, 2000000));
        }
        return post;
    }

    @DefaultSerializer(CompatibleFieldSerializer.class)
    public enum ContentType {
        TEXT,
        PHOTO,
        MUSIC,
        VIDEO,
        FILE,
    }

    @DefaultSerializer(CompatibleFieldSerializer.class)
    public enum Visibility {
        PUBLIC,
        FRIEND_OBLY,
        PRIVATE
    }

}
