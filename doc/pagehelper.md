# pageHelper插件   

pagehelper是一个分页插件，其工作原理就是利用mybatis拦截器，在查询数据库的时候拦截下sql，修改sql使其实现分页效果。   

## 1.pagehelper使用条件   

1. 在parent工程的pom.xml文件中添加了maven依赖    

   ```xml
   <properties>	
   	<pagehelper.version>3.4.2-fix</pagehelper.version>
     ...
   </properties>  
   ```

   ```xml
   			<dependency>
   				<groupId>com.github.pagehelper</groupId>
   				<artifactId>pagehelper</artifactId>
   				<version>${pagehelper.version}</version>
   			</dependency>
   ```

## 2.测试pagehelper

### 1.配置拦截器插件   

在mybatis的xml（也就是SqlMapConfig.xml）里面配置(放在< configuration > 里)     

```xml
	<!-- 配置分页插件 -->
	<plugins>
		<plugin interceptor="com.github.pagehelper.PageHelper">
			<!-- 设置方言 -->
			<property name="dialect" value="mysql"/>			
		</plugin>
	</plugins>
```

_把拦截sql的权限交给了pagehelper，并且告诉它：你要拦截的sql是mysql类型的。_    

### 2.测试pagehelper   

使用Junit测试pagehelper：

```java
	@Test
	public void testPageHelper() {
		//创建一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//获取Mapper代理对象
		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
		//执行查询并分页
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(1, 10);
		List<TbItem> list = mapper.selectByExample(example);
		//取商品列表   
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());
		}
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		System.out.println("总共有商品信息条数："+total);
	}
```

1. 首先加载spring容器，spring容器里面有许多mapper映射类（由xxxMapper.xml转化而成），取出要使用的代理对象。

   ```java
   TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
   ```

2. 告诉pagehelper从哪一行开始，多少行一页   

   ```java
   PageHelper.startPage(1, 10);
   ```

3. 调用映射类的selectByExample方法查询所有的数据

   ```java
   List<TbItem> list = mapper.selectByExample(example);
   ```

   其example对象可用来限定查询条件，example为空，表示查询所有。   

4. PageInfo只要输入了所有的数据，它就可以对数据进行分页处理，通过PageInfo可以直接拿到数据的条数等信息。   

_使用pagehelper就省去了原来编写新的sql语句，只需要告诉pagehelper从哪一行开始，多少行一页，它就会将其分好页，直接调用即可。_    

## 3.实战pageHelper   





v    12   3min

