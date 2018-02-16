# 前后端分离      

本文主要介绍前台和后台分离的方式，各自搭建一个工程，可以减低耦合度，灵活地进行分布式部署。而由于是不同的工程，服务之间通过接口通信，开发工作量提高。    

这里使用两个项目来搭建前台，分别是服务层 & 表现层。   

## 1.创建后端工程(服务层)   

使用到的技术：Mybatis, Spring, SpringMVC

- 创建war工程
- 继承于parent工程

### 1.pom添加依赖   

直接依赖整个模块，把相关的jar包都继承过来。

1. dao层：

```xml
  <dependencies>
  	<dependency>
  		<groupId>com.taotao</groupId>
  		<artifactId>taotao-manager-mapper</artifactId>
  		<version>0.0.1-SNAPSHOT</version>
  	</dependency>
  </dependencies>
```

2. service层：依赖Spring    

```xml
  		<!-- Spring -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-beans</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-jdbc</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-aspects</artifactId>
		<version>${spring.version}</version>
	</dependency>
```

3. controller层   

```xml
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>servlet-api</artifactId>
		<version>${servlet-api.version}</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>jsp-api</artifactId>
		<version>${jsp-api.version}</version>
		<scope>provided</scope>
	</dependency>
```

### 2.添加web.xml    

使用之前的web.xml修改springmvc拦截路径：只要是改路径下的都会被springmvc拦截    

```xml
	<servlet-mapping>
		<servlet-name>taotao-manager</servlet-name>
		<url-pattern>/rest/*</url-pattern>
	</servlet-mapping>
```

### 3.框架整合

添加resources 。

1. 在applicationContext中因为dao层使用同一个，所以还是扫描：``com.taotao.mapper`` 。

2. service扫描的是  ``com.taotao.rest.service`` 。      

3. 事务需要指向新的包：

   ```xml
   	<!-- 切面 -->
   	<aop:config>
   		<aop:advisor advice-ref="txAdvice"
   			pointcut="execution(* com.taotao.rest.service.*.*(..))" />
   	</aop:config>
   ```

4. springmvc扫描的包重新指向：

   ```xml
   	<context:component-scan base-package="com.taotao.rest.controller" />
   ```

![](../img/p19.png)   

### 4.配置tomcat

1. 使用不同的端口号：8081    

### 5.静态资源的位置

![](../img/p21.png) 

为了静态资源  

## 2.创建前端工程(表现层)

使用到的技术：Spring, SpringMVC, jstl, jQuery, httpClient    

客户端和服务端之间没有直接依赖关系，完全独立。

- 创建war工程
- 继承于parent工程

### 1.pom添加依赖

1. service层：依赖taotao-common，Spring  ，前端jar包  

```xml
  		<!-- Spring -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-beans</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-jdbc</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-aspects</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<!-- JSP相关 -->
	<dependency>
		<groupId>jstl</groupId>
		<artifactId>jstl</artifactId>
		<version>${jstl.version}</version>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>servlet-api</artifactId>
		<version>${servlet-api.version}</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>jsp-api</artifactId>
		<version>${jsp-api.version}</version>
		<scope>provided</scope>
	</dependency>
```

把common和所需的jar包依赖过来。

### 2.添加web.xml

修改前端控制器&过滤拦截：html伪静态化

```xml
	<servlet-mapping>
		<servlet-name>taotao-portal</servlet-name>
		<url-pattern>*.html</url-pattern>
	</servlet-mapping>
...
	<!-- springmvc的前端控制器 -->
	<servlet>
		<servlet-name>taotao-portal</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<!-- contextConfigLocation不是必须的， 如果不配置contextConfigLocation， springmvc的配置文件默认在：WEB-INF/servlet的name+"-servlet.xml" -->
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:spring/springmvc.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
```

### 3.框架整合

添加resources 。

1. 不需要访问dao，不添加applicationContext-dao。

2. 修改applicationContext-service的配置文件

   ```xml
   	<!-- 加载配置文件 -->
   	<context:property-placeholder location="classpath:resource/*.properties" />
   	<!-- 扫描包加载Service实现类 -->
   	<context:component-scan base-package="com.taotao.portal.service"></context:component-scan>
   ```

3. 不需要事务，不添加trans。

4. springmvc扫描的包重新指向：资源上传和文件映射不需要

```xml
...
	<context:component-scan base-package="com.taotao.portal.controller" />
	<mvc:annotation-driven />
	<bean
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/jsp/" />
		<property name="suffix" value=".jsp" />
	</bean>
...
```

![](../img/p20.png)   

### 4.配置tomcat

1. 使用不同的端口号，HTTP , AJP ，才能进行启动。   


### 5.跨域问题   

js不能进行跨域请求，js设计时为了安全考虑。域名相同，端口相同才不算跨域。

可以使用jsonp解决跨域问题。js跨域请求数据不行，js请求js脚本可以。   

将json数据变成js语句（在json前面添加category.getDateService(...)），然后追加到请求中。    

## 3.查询数据   

### 1.新建pojo   

D:

```java
public class CatNode {
	
	@JsonProperty("n")
	private String name;
	@JsonProperty("u")
	private String url;
	@JsonProperty("i")
	private List<?> item;

	public String getName() {
      ...
```

M:@JsonProperty("i")在这里的作用是什么？

Z:@JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty(value="name")。   

M:也就是说它就是用来说明json的key部分内容。

D:还有一个pojo

```java
public class CatResult {
	private List<?> data;
  ...
```

M:这个POJO用来干嘛？

Z:因为json的 n，u，i外层还包了一个data

### 2.编写Service   

D:

```java
public interface ItemCatService {
	CatResult getItemCatList();
}
```

```java
	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		//查询分类列表
		catResult.setData(getCatList(0));
		return catResult;
	}
```

```java
	private List<?> getCatList(long parentId){
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//返回值list
		List resultList = new ArrayList<>();
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			//判断是否为叶子节点
			if(tbItemCat.getIsParent()){
				CatNode catNode = new CatNode();
				if(parentId == 0){    //第一层
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else{
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				count ++;
				//第一级只取11条
				if(parentId == 0 && count >= 14){
					break;
				}
			}else{
				resultList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return resultList;
	}
```

M:最后一个方法parentId是什么？

Z:parentId是该类别的等级，它传进去0说明获取第一等级的数据。

M:那  ``if(tbItemCat.getIsParent()){`` 的作用是什么？

Z:getIsParent()是tbItemCat的一个方法，这主要是由于tbItemCat表中有该属性。它的类型为tinyint，在 MySql 中还没有严格的 bool 类型，但使用 TINYINT(1) 隐式用作 bool 类型，零作为false，而非零值（包括负数）作为true。   

M:那这里就是做出了区分，如果没有子类，就直接显示名字（最大的类目，下图A）。有子类的话...CatNode是干嘛用的？

Z:CatNode是一个专门为json格式创建的pojo对象。它的属性主要有以下三个：

```java
	@JsonProperty("n")
	private String name;
	@JsonProperty("u")
	private String url;
	@JsonProperty("i")
	private List<?> item;
```

M:为什么setName有分两种

Z:parentId为0说明还有子类目，则使用带a标签(中大的类目，下图B)，否则直接显示文字（具体的类目C）。

M: ``if(parentId == 0 && count >= 14){``  的作用是什么？

Z:对第一级数目限制在14个以内。为了详细说明三种分类，图片如下：

![](../img/p22.png)  

M:``					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");``  下面已经setUrl，为什么在name中还要添加< a >标签。    

Z:这个可能得等跳转功能实现之后再说了。

D:这里的前后端分离只要在于Controller的传输：

```java
	@RequestMapping(value="/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback){
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
```

M:这里的CatResult是什么？

Z:CatResult就只是一个List，因为json数据的格式需要所以创建的pojo。

M:  ``MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);``  作用是什么？

Z:  针对显式注解 @ResponseBody 的方法 (我们本来就是直接响应JSON的)，而由于跨域问题，需要使用 MappingJacksonValue 进行封装处理。   

M: 这里就是将获取到的List数据作为json封装进一个方法里。     ``mappingJacksonValue.setJsonpFunction(callback);``  作用又是什么？

Z:  callback的内容是  category.getDataService。而在js调用里面就有对应的category.getDataService方法。    

M: 那也就是说，调用  /itemcat/list 的方法之后，他就会把  数据 + 调用方法名  一起包装成一个 Object 方法返回。而前端拿到Object方法之后，就会找到该调用方法名，把数据拿进去处理，进行显示。由于前端处理那一部分写得比较乱，就不麻烦两位前辈了。目前实现了如下效果的目录显示：

![](../img/p23.png)          