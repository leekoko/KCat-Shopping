# Java ftp文件上传

一般用户访问系统，使用上传图片功能，那么图片就上传到你的当前项目所在的tomcat服务器上，在/image下，上传成功后用户可以直接访问``http://ip:port/project/images/xxx.jpg``。这样做在用户少的时候是没有问题的，但是当用户访问量增大，系统就会变慢，这个时候就需要用集群来解决。

nginx作为静态资源服务器（像图片视频等的存储），tomcat仅存储图片路径。而将静态资源传输到nginx就需要用到ftp，本文使用的是vsftpd，主要介绍的是用java操作ftp上传文件，对Linux下搭建nginx并不做介绍。

**使用nginx的目的: **  

```
  动静态资源分离——运用Nginx的反向代理功能分发请求：所有动态资源的请求交给Tomcat，而静态资源的请求（例如图片、视频、CSS、JavaScript文件等）则直接由Nginx返回到浏览器，这样能大大减轻Tomcat的压力。

  负载均衡，当业务压力增大时，可能一个Tomcat的实例不足以处理，那么这时可以启动多个Tomcat实例进行水平扩展，而Nginx的负载均衡功能可以把请求通过算法分发到各个不同的实例进行处理
```

_我有很多不同的螺丝刀，本来我的螺丝刀放在工具箱里面，但是由于使用的人多了，大一群人挤着一个工具箱哪很不方便。所以我索性将螺丝刀全部规律放在一个工具架子上（nginx），而工具箱只放了一摞纸（记录索引的数据表），工人们只要通过纸上的位置，就可以顺利在工具架子上拿到自己想要的螺丝刀。因此解决了拥挤和小工具箱不够放的问题。_  

## 1.Java ftp文件上传条件    

1. linux中已经搭建好了nginx服务器，安装了vsftpd，并且处于启动状态。   

   能够用``虚拟机ip/images/图片名.jpg``顺利访问到上传到虚拟机www/images目录下的图片。     

   本文我的虚拟机ip为192.168.25.133 ，账号：ftpuser  ，密码：ftpuser    

2. pom文件引入commons-net的jar包    

   ```xml
   		<commons-net.version>3.3</commons-net.version>
   ```

   ```xml
   			<dependency>
   				<groupId>commons-net</groupId>
   				<artifactId>commons-net</artifactId>
   				<version>${commons-net.version}</version>
   			</dependency>
   ```

   Commons net包中的ftp工具类能够帮助我们轻松实现Ftp方式的文件上传/下载。（相当于ftp上传工具）   

   ​

​    common-net          io        upload    的关系



## 2.测试Java控制ftp上传代码   











不用客户端，用java代码访问     

使用Apache工具包common-net

引包   

编写测试类   

封装成工具类，直接被别的项目使用。直接用      

工具类的使用，做Test     

# 前端KindEdit插件使用

使用KindEdit上传图片，查看api文档详情   

编写service

​    

虚拟机查看ifconfig

我的虚拟机ip     http://192.168.175.128/        ftpuser    ftpuser     

一直都访问不了



编写service     使用uuid，ftpUtil

读取prpertices注入变量使用   

KindEdit上传所需的json数据  



Controller调用   传来文件类型   



报错：配置多部件解析器      springmvc中配置，定义数据

上传组件	



插件火狐不兼容     

把Map转化为json字符串返回解决问题，JsonUtil



主题：插件使用+附加nginx使用+datagrid使用+工具类重点介绍      







图片上传：   实战+专题编写











第三天   08视频  20min   快速看



KindEdit文件上传     富文本框编辑器