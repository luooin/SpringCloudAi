# SpringCloudAi
介绍本地快速集成搭建Spring Cloud Alibaba AI的DashScope灵积模型

# 简介
Spring AI 是 Spring 官方社区项目，旨在简化 Java AI 应用程序开发，让 Java 开发者像使用 Spring 开发普通应用一样开发 AI 应用。

Spring Cloud Alibaba AI 以 Spring AI 为基础，并在此基础上提供阿里云通义系列大模型全面适配，让用户在 5 分钟内开发基于通义大模型的 Java AI 应用。

# 快速搭建

想写搭建流程查看http://t.csdnimg.cn/1evUV


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


## 搭建好之后即可运行

> 这个是通译千问模式，即文字问答模式
![image](https://github.com/luooin/SpringCloudAi/assets/85004172/bbea5aa8-f5f9-4573-9be8-12dff68de2c0)


> 还有一个是文生图模式

![image](https://github.com/luooin/SpringCloudAi/assets/85004172/741697d2-f632-44e4-a52e-c774ed0bf26e)

> 更多模式参考通译千问官网，很多开源的模型

# 总结
最实质的就是在本地提供了后端的自定义api接口，开发人员可以利用这个进行个性化开发，即真实的体验ai大模型接入自己的应用中。

想要了解更多，最直观的是直接查看github中的源码或者直接访问阿里云官网，其中还提供了图片模型、Llama3大语言模型等，有些的开源的，可以免费玩，上手也简单。


[计算机类毕设项目合集点击跳转](http://t.csdnimg.cn/qQgGv)

