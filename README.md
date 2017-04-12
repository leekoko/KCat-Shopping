# taotaoShopping
黑马程序员视频笔记

#第一天
简历可以写：
淘淘网上商城是一个综合性的B2C平台，类似京东商城、天猫商城。会员可以在商城浏览商品、下订单，以及参加各种活动。
管理员、运营可以在平台后台管理系统中管理商品、订单、会员等。
客服可以在后台管理系统中处理用户的询问以及投诉。

1.parent工程
1.使用配置好的工作区环境
2.拷贝.m2本地仓库到当前用户的文件夹（maven插件仓库的默认位置）
3.创建maven工程  所有工程的父工程，pom工程
4.修改pom文件  所有jar包的版本信息   pom.xml  （由积累所得，集中定义依赖的版本号）
定义的东西并不实际依赖，只是定义版本号进行管理
java编译插件作用：解决每次编译版本变化

2.common工程
1.创建通用的common工程，继承与parent工程，打包成jar包被其他工程引用
2.把要依赖的jar包引入到pom文件中，版本就不用了（已经被集中管理了）

3.manager工程
1.添加模块：pojo   不依赖任何jar包，所以不需要修改pom文件
2.添加模块：
搭建聚合工程，在manager中把pojo，mapper，service打包到controller的war包中

4.运行聚合工程
新建jsp测试页面，manager中配置tomcat插件
运行前将parent，common用install 安装到本地仓库，再用maven build运行:   clean tomcat7:run

5.使用svn
trunk主干，branches分支（定版之后的小更新）
svn账号密码：xn     1105
上传项目应该忽略掉setting，project，target
下载工程，导入模块，转化为maven工程

