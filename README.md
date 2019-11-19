# smartbuf-dubbo

`smartbuf-dubbo`是一个基于`smartbuf`的`dubbo`序列化插件。

它内部封装了[`smartbuf`](https://github.com/smartbuf/smartbuf-java)序列化框架的`stream`模式，
通过自定义的`SmartbufSerialization`向`dubbo`暴露了一个名为`smartbuf`的序列化选项。

# 关于`smartbuf`

`smartbuf`是一种新颖、高效、智能、易用的跨语言序列化框架，它既拥有不亚于`protobuf`的高性能，
也拥有与`json`相仿的通用性、可扩展性、可调试性等。

它内部采用分区序列化将松散的对象序列化为若干个紧凑的分区，从而大幅提高编码效率，
具体细节请参考[`smartbuf`项目](https://github.com/smartbuf/smartbuf-java/blob/master/doc/index_zh.md)。

# 使用方式

`smartbuf-dubbo`内部实现非常简单，它只是简单地按照[`dubbo`官方文档](https://dubbo.apache.org/zh-cn/docs/dev/impls/serialize.html)提供了序列化插件，包括三个`class`:

 + [`SmartbufObjectInput`](https://github.com/smartbuf/smartbuf-dubbo/blob/master/src/main/java/com/github/smartbuf/dubbo/SmartbufObjectInput.java)
 + [`SmartbufObjectOutput`](https://github.com/smartbuf/smartbuf-dubbo/blob/master/src/main/java/com/github/smartbuf/dubbo/SmartbufObjectOutput.java)
 + [`SmartbufSerialization`](https://github.com/smartbuf/smartbuf-dubbo/blob/master/src/main/java/com/github/smartbuf/dubbo/SmartbufSerialization.java)

以及位于`src/main/resources/META-INF.dubbo/com.alibaba.dubbo.common.serialize.Serialization`的插件配置。

此插件已打包`deploy`至中心仓库，所以你可以直接通过以下`maven`坐标引入它：

```xml
<dependency>
    <groupId>com.github.smartbuf</groupId>
    <artifactId>smartbuf-dubbo</artifactId>
    <version>1.0.0</version>
</dependency>
```

当然也可以直接将以上提到的`class`和`resources`配置复制入自己的工程中，同时记得手动添加`smartbuf`依赖。

之后就可以按照官方文档的配置，在`protocol`中选择启用序列化插件，具体效果可能类似于：

```xml
<dubbo:protocol serialization="smartbuf" />
```

**警告⚠️**：此插件使用的`dubbo`的`groupId`是`com.alibaba`，如果您使用的是``

![dubbo-comparison-posts](./doc/smartbuf-posts.png)

![dubbo-comparison-user](./doc/smartbuf-user.png)

![dubbo-comparison-tiny](./doc/smartbuf-tiny.png)

kryo问题：
1. 不支持枚举
2. 不能有效地识别List，如SubList