# 项目日志  

### 1.环境搭建  

1. 替换掉本地仓库m2  
2. 创建parent的maven父工程   

不用模板，组织id为com.taotao  

Artifact id:    taotao-parent  

父工程为pom工程，不用继承于谁    

3. 配置pom文件的版本信息 

集中配置版本号，管理积累的jar包  

4. 创建用来管理通用工具的common工程  

还要继承父工程，同一个组，修改其pom，jar包从parent中找（版本号就不需要定义了）    

5. 新建manager聚合pom工程，继承于parent  

修改pom文件，依赖于common  

在里面把pojo（jar），mapper，service，controller（war）聚合在一起   

6. 创建四个模块  

创建模块的方式：在聚合工程上新建maven module   

pojo不依赖任何jar包  

mapper配置需要的依赖  

service设置依赖

web（controller）使用的是war，设置依赖，因为结构不完整，所以会报错  

（需要在src/main/webapp下创建WEB-INF/web.xml）  

7. 运行测试  

运行的对象是manager聚合工程，需要用到tomcat插件。所以要在manager的pom文件中配置tomcat插件  

运行maven需要使用命令clean tomcat7:run

（注意：要把taotao-parent&common安装到本地仓库）  

### 2.使用svn  

1. 新建仓库，选择创建简单的仓库结构  
2. 开发过程一般在主干中开发，定板之后小修改才在分支上开发  
3. 在eclipse中team-share-svn（leekoko6lyb）   

选择文件上传到trunk上

4. 选择要提交的内容进行提交

把setting和.project   .classpath   target忽略掉 

因为一些文件服务器不存在，所以不能忽略，需要先提交一部分才可以忽略  

5. 冲突：因为本地和服务器上传后会冲突，所以需要右键进行更新再提交  
6. 可以从svn导入maven工程，在从存在的maven project导入manager项目，manager项目可以转化为maven项目  

### 3.创建数据库    

数据表未导入，到02-10min





