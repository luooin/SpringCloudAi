# SpringCloudAi
介绍本地快速集成搭建Spring Cloud Alibaba AI的DashScope灵积模型

# 简介
Spring AI 是 Spring 官方社区项目，旨在简化 Java AI 应用程序开发，让 Java 开发者像使用 Spring 开发普通应用一样开发 AI 应用。

Spring Cloud Alibaba AI 以 Spring AI 为基础，并在此基础上提供阿里云通义系列大模型全面适配，让用户在 5 分钟内开发基于通义大模型的 Java AI 应用。

# 快速搭建
> jdk以17为基准

整体的项目结构如下，所有的资源来源于阿里云官网，教程来自https://sca.aliyun.com/docs/2023/user-guide/ai/quick-start/

代码来源于https://github.com/alibaba/spring-cloud-alibaba/tree/2023.x/spring-cloud-alibaba-examples/ai-example/spring-cloud-ai-example
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/cfff96d318864466868717e575cce33f.png)

## 引入依赖
文章以Maven项目为测试用例，首先在pom.xml中引入依赖。
```xml
<dependencyManagement>
    <dependencies>
         <dependency>
             <groupId>com.alibaba.cloud</groupId>
             <artifactId>spring-cloud-alibaba-dependencies</artifactId>
             <version>2023.0.1.0</version>
             <type>pom</type>
             <scope>import</scope>
         </dependency>
     </dependencies>
 </dependencyManagement>

 <dependencies>
     <dependency>
         <groupId>com.alibaba.cloud</groupId>
         <artifactId>spring-cloud-starter-alibaba-ai</artifactId>
     </dependency>
 </dependencies>
```

## 申请api-key

api-key的申请需要前往阿里云官网，[点击跳转](https://help.aliyun.com/zh/dashscope/developer-reference/activate-dashscope-and-create-an-api-key)

然后application.yml配置文件中加入api-key
```yml
spring:
  cloud:
    ai:
      tongyi:
        api-key: sk-69******** #这里的api-key需要去阿里云官网申请
```


申请好之后替换application.yml的api-key

## 代码部分
> 代码部分分为controller层、service层、serviceImpl层和Model层

### controller层
controller层还有一个类，用于对外提供接口
```java
@RestController
@RequestMapping("/ai")
@CrossOrigin
public class TongYiController {

    @Autowired
    @Qualifier("tongYiSimpleServiceImpl")
    private TongYiService tongYiSimpleService;

    @GetMapping("/example")
    public String completion(
            @RequestParam(value = "message", defaultValue = "Tell me a joke")
            String message
    ) {

        return tongYiSimpleService.completion(message);
    }
}
```

### service层
service层用于对controller提供服务

```java
public interface TongYiService {

    /**
     * Hello World example.
     *
     * @param message conversation content question.
     * @return AI answer.
     */
    String completion(String message);
}
```

### serviceImpl层
serviceImpl层是对service的实现，包括`AbstractTongYiServiceImpl`和`TongYiSimpleServiceImpl`

**AbstractTongYiServiceImpl**
```java
public abstract class AbstractTongYiServiceImpl implements TongYiService {

    private static final String INFO_PREFIX = "please implement ";
    private static final String INFO_SUFFIX = "() method.";

    @Override
    public String completion(String message) {

        throw new RuntimeException(INFO_PREFIX + Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
```
**TongYiSimpleServiceImpl**
```java
@Service
@Slf4j
public class TongYiSimpleServiceImpl extends AbstractTongYiServiceImpl {

    private final ChatClient chatClient;

    private final StreamingChatClient streamingChatClient;

    @Autowired
    public TongYiSimpleServiceImpl(ChatClient chatClient, StreamingChatClient streamingChatClient) {
        this.chatClient = chatClient;
        this.streamingChatClient = streamingChatClient;
    }
}    
```

### model层

**ActorsFilms**
```java
public class ActorsFilms {
    private String actor;
    private List<String> movies;
    public ActorsFilms() {}
    public String getActor() {return actor;}
    public void setActor(String actor) {this.actor = actor;}
    public List<String> getMovies() {return movies;}
    public void setMovies(List<String> movies) {this.movies = movies;}
    @Override
    public String toString() {
        return "ActorsFilms{" + "actor='" + actor + '\'' + ", movies=" + movies + '}';
    }
}
```

**Completion**

```java
public class Completion {
    private final String completion;
    public Completion(String completion) {this.completion = completion;}
    public String getCompletion() {return completion;}
}
```

# 运行测试

## api访问方式
> http://localhost:8080/ai/example?message=西瓜

可以放在浏览器或者api接口测试工具
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/29f1ab84e4f14750839366e83abe7f2a.png)


## 页面访问
> http://localhost:8080/index.html

配置好之后即可运行，其中阿里云github官网里面有前端页面资源，即resource里面的static静态资源，想要的可以直接去github代码地址里拷贝
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/f2665ad1343449ba9570dd6396d75f26.png)
仅拷贝static资源的步骤：
1. 在当前页面点击键盘上的逗号，这样就会跳转到github内嵌的vscode
2. 然后在想要的资源上右击下载即可下载到本地，然后将其放在项目的resource资源文件下static
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/dd3423f8ddba4e518409cf7a0f172923.png)
用此方法可以通过页面进行访问，即访问刚才从github下载的static静态资源的页面，将链接放在浏览器就到了下面的页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/bac468f81624468c8b8247108c7dc775.png)
# 总结
最实质的就是在本地提供了后端的自定义api接口，开发人员可以利用这个进行个性化开发，即真实的体验ai大模型接入自己的应用中。

想要了解更多，最直观的是直接查看github中的源码或者直接访问阿里云官网，其中还提供了图片模型、Llama3大语言模型等，有些的开源的，可以免费玩，上手也简单。
# 关于我
我是热爱学习的小伙子


[计算机类毕设项目合集点击跳转](http://t.csdnimg.cn/qQgGv)

------
![在这里插入图片描述](https://img-blog.csdnimg.cn/ea02a507be4b4998a091f7e145c06df4.gif#pic_center)
