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



07-22min

