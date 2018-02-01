# 前后端分离      

本文主要介绍前台和后台分离的方式，各自搭建一个工程，可以减低耦合度，灵活地进行分布式部署。而由于是不同的工程，服务之间通过接口通信，开发工作量提高。    

这里使用两个项目来搭建前台，分别是服务层 & 表现层。   

## 1.创建工程   

### 1.创建服务层   

#### 1.创建war工程   

#### 2.继承于parent工程   

#### 3.使用到的技术：Mybatis, Spring, SpringMVC   

#### 4.依赖jar包

直接依赖整个模块，把相关的jar包都继承过来。

```xml
  <dependencies>
  	<dependency>
  		<groupId>com.taotao</groupId>
  		<artifactId>taotao-manager-mapper</artifactId>
  		<version>0.0.1-SNAPSHOT</version>
  	</dependency>
  </dependencies>
```

依赖dao层。   

​    

















分层：  web工程   +   表现层



新建工程：taotao-reset    

属于服务层    

使用ssm发布服务

添加pom，添加项目的依赖

添加web.xml   ，伪静态化

添加配置文件，参考之前   

安装到本地仓库   

修改端口，启动执行新建的模块   

修改配置文件   

配置tomcat

添加jsp页面，其他资源

处理返回的编码问题，两种方式     





怎么处理多个tomcat启动   







5天03天10min



































feiman关注重要一个点：关注前后端分离，其他实现即可。学这个原因因为kcat用到

feiman老少     发布



