# KindEditor图片上传

一般用户访问系统，使用上传图片功能，那么图片就上传到你的当前项目所在的tomcat服务器上，在/image下，上传成功后用户可以直接访问``http://ip:port/project/images/xxx.jpg``。这样做在用户少的时候是没有问题的，但是当用户访问量增大，系统就会变慢，这个时候就需要用集群来解决。

nginx作为静态资源服务器（像图片视频等的存储），tomcat仅存储图片路径。而将静态资源传输到nginx就需要用到ftp，本文使用的是vsftpd，主要介绍的是用java操作ftp上传文件，对Linux下搭建nginx并不做介绍。

**使用nginx的目的: **  

```
  动静态资源分离——运用Nginx的反向代理功能分发请求：所有动态资源的请求交给Tomcat，而静态资源的请求（例如图片、视频、CSS、JavaScript文件等）则直接由Nginx返回到浏览器，这样能大大减轻Tomcat的压力。

  负载均衡，当业务压力增大时，可能一个Tomcat的实例不足以处理，那么这时可以启动多个Tomcat实例进行水平扩展，而Nginx的负载均衡功能可以把请求通过算法分发到各个不同的实例进行处理
```

_我有很多不同的螺丝刀，本来我的螺丝刀放在工具箱里面，但是由于使用的人多了，大一群人挤着一个工具箱哪很不方便。所以我索性将螺丝刀全部规律放在一个工具架子上（nginx），而工具箱只放了一摞纸（记录索引的数据表），工人们只要通过纸上的位置，就可以顺利在工具架子上拿到自己想要的螺丝刀。因此解决了拥挤和小工具箱不够放的问题。_  

## 1.KindEditor文件上传条件    

1. linux中已经搭建好了nginx服务器，安装了vsftpd，并且处于启动状态。   

   能够用``虚拟机ip/images/图片名.jpg``顺利访问到上传到虚拟机www/images目录下的图片。     

   本文我的虚拟机ip为192.168.175.128 ，账号：ftpuser  ，密码：ftpuser    

   _有了放图片的架子_

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

   _购进运输图片的工具_   

3. pom文件引入joda-time的jar包  

   ```xml
   <joda-time.version>2.5</joda-time.version>
   ```

   ```xml
   			<!-- 时间操作组件 -->
   			<dependency>
   				<groupId>joda-time</groupId>
   				<artifactId>joda-time</artifactId>
   				<version>${joda-time.version}</version>
   			</dependency>
   ```

   _joda-time包装了一系列对时间处理的方法，可直接调用。_  

4. pom文件引入commons-fileupload和common-io的jar包

   ```xml
   <commons-io.version>1.3.2</commons-io.version>
   <commons-fileupload.version>1.3.1</commons-fileupload.version>
   ```

   ```xml
   			<dependency>
   				<groupId>commons-net</groupId>
   				<artifactId>commons-net</artifactId>
   				<version>${commons-net.version}</version>
   			</dependency>
   			<dependency>
   				<groupId>org.apache.commons</groupId>
   				<artifactId>commons-io</artifactId>
   				<version>${commons-io.version}</version>
   			</dependency>
   ```

   该插件可以简化文件上传的处理方式。

5. 搭建好EditKinder前端    

- 设置编辑器参数：

```javascript
	// 编辑器参数
	kingEditorParams : {
		//指定上传文件参数名称
		filePostName  : "uploadFile",
		//指定上传文件请求的url。
		uploadJson : '/pic/upload',
		//上传类型，分别为image、flash、media、file
		dir : "image"
	},
```

指定url和类型

- 初始化图片上传组件    

```javascript
    init : function(data){
    	// 初始化图片上传组件
    	this.initPicUpload(data);
    },
```

```javascript
    // 初始化图片上传组件
    initPicUpload : function(data){
    	$(".picFileUpload").each(function(i,e){
    		var _ele = $(e);
    		_ele.siblings("div.pics").remove();
    		_ele.after('\
    			<div class="pics">\
        			<ul></ul>\
        		</div>');
    		// 回显图片
        	if(data && data.pics){
        		var imgs = data.pics.split(",");
        		for(var i in imgs){
        			if($.trim(imgs[i]).length > 0){
        				_ele.siblings(".pics").find("ul").append("<li><a href='"+imgs[i]+"' target='_blank'><img src='"+imgs[i]+"' width='80' height='50' /></a></li>");
        			}
        		}
        	}
        	//给“上传图片按钮”绑定click事件
        	$(e).click(function(){
        		var form = $(this).parentsUntil("form").parent("form");
        		//打开图片上传窗口
        		KindEditor.editor(TT.kingEditorParams).loadPlugin('multiimage',function(){
        			var editor = this;
        			editor.plugin.multiImageDialog({
						clickFn : function(urlList) {
							var imgArray = [];
							KindEditor.each(urlList, function(i, data) {
								imgArray.push(data.url);
								form.find(".pics ul").append("<li><a href='"+data.url+"' target='_blank'><img src='"+data.url+"' width='80' height='50' /></a></li>");
							});
							form.find("[name=image]").val(imgArray.join(","));
							editor.hideDialog();
						}
					});
        		});
        	});
    	});
    },
```

点击按钮之后打开一个插件窗口，传入初始化参数  

_配置图片上传的插件：传什么类型，传到哪个Controller_      

- html的使用

  ```html
  	            <td>商品图片:</td>
  	            <td>
  	            	 <a href="javascript:void(0)" class="easyui-linkbutton picFileUpload">上传图片</a>
  	                 <input type="hidden" name="image"/>
  	            </td>
  ```

  添加class即可。   


  _有了该插件的帮忙才可以顺利将图片上传到后台，并且前端显示上传的情况_   


## 2.测试Java控制ftp上传代码   

```java
	public static void main(String[] args) throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.175.128",21);     //连接ftp,端口号默认21
		ftpClient.login("ftpuser", "ftpuser");   //登陆账号密码
         //读取到io流
		FileInputStream inputStream = new FileInputStream(new File("D:\\20171108b.jpg"));
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images"); //设置上传路径
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);    //修改上传文件的格式
		ftpClient.storeFile("testftpImg.jpg", inputStream);  //服务器端文件名，io流
		inputStream.close();
		ftpClient.logout();
	}
```

使用FTPClient对象，就可以将它作为ftp工具使用了。

## 3.包装ftp上传代码为工具类     

由于  ftp上传  可能在多个项目中使用到，所以将代码改造成通用的工具类，放在common工程中。

因为这是一段可以直接使用的代码，原理与上方相似，这里直接提供源码：【[ftp上传工具类](../Tools/FtpUtil.java)】。

### 1.测试ftp上传工具类   

```java
	@Test
	public void testFtpUtil() throws Exception{
		FileInputStream inputStream = new FileInputStream(new File("D:\\A.jpg"));
		FtpUtil.uploadFile("192.168.175.128", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "2017/12/06", "hai.jpg", inputStream);	
	}
```

_工具类已经把相应的固定代码封装起来，只要传入会变的参数就可以了。_    

## 4.KindEdit插件实现上传    

【条件】：   

1. KindEdit上传的文件对象为：MultipartFile

   _知道文件对象才可以获取到上传的文件的信息，像名字等。_    

2. KindEidt上传后返回给插件的数据格式为：

```xml
//成功时
{
        "error" : 0,
        "url" : "http://www.example.com/path/to/file.ext"
}
//失败时
{
        "error" : 1,
        "message" : "错误信息"
}
```

​	_告诉插件后台的上传情况，好让其显示成功或者失败。_

### 1.编写Service和其实现类    

1. Service接口    

   ```java
   public interface PictureService {
   	Map uploadPicture(MultipartFile uploadFile);
   }
   ```

2. 加载配置文件    

   - spring的dao.xml文件

   ```xml
   	<!-- 加载配置文件 -->
   	<context:property-placeholder location="classpath:resource/*.properties" />
   ```

   ​	将resource目录下的所有properties文件加载到Spring容器中。     

   - properties文件

   ```xml
   #ftp相关配置   
   #ftp的ip地址
   FTP_ADDRESS=192.168.175.128
   FTP_PORT=21
   FTP_USERNAME=ftpuser
   FTP_PASSWORD=ftpuser
   FTP_BASE_PATH=/home/ftpuser/www/images/
   #图片服务器相关配置
   #图片服务器基础url
   IMAGE_BASE_URL=http://192.168.175.128/images
   ```

   ​	properties的文件格式为简单的**键=值**对

   _将这些配置信息写进配置文件的目的是因为这些信息都是可能多次改动的，而当程序打包之后就不方便进行改动，因为配置文件不被程序打包，所以使用读取配置文件的方式。_   

3. Service的实现类    

   ```java
   	@Value("${FTP_ADDRESS}")
   	private String FTP_ADDRESS;
   	@Value("${FTP_PORT}")
   	private Integer FTP_PORT;    //会自动转化类型
   	@Value("${FTP_USERNAME}")
   	private String FTP_USERNAME;
   	@Value("${FTP_PASSWORD}")
   	private String FTP_PASSWORD;
   	@Value("${FTP_BASE_PATH}")
   	private String FTP_BASE_PATH;
   	@Value("${IMAGE_BASE_URL}")
   	private String IMAGE_BASE_URL;

   	@Override
   	public Map uploadPicture(MultipartFile uploadFile){
   		Map resultMap = new HashMap<>();
   		try {
   			//生成新的文件名
   			//取原文件名
   			String oldName = uploadFile.getOriginalFilename();
   			//生成新的文件名
   			String newName = IDUtils.genImageName();
   			newName = newName + oldName.substring(oldName.lastIndexOf("."));
   			//图片上传
   			String imagePath = new DateTime().toString("/yyyy/MM/dd");
   			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, 
   					FTP_BASE_PATH, imagePath, newName, uploadFile.getInputStream());//使用joda处理时间
   			if(!result){
   				resultMap.put("error", 1);
   				resultMap.put("message", "文件上传失败");
   				return resultMap;
   			}
   			resultMap.put("error", 0);
   			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" +newName);
   			return resultMap;
   		} catch (IOException e) {
   			resultMap.put("error", 1);
   			resultMap.put("message", "文件上传发生异常");
   			return resultMap;
   		} 
   	}
   ```

   1. 使用MultipartFile的方法获取文件名：

      ```java
      String oldName = uploadFile.getOriginalFilename();
      ```

   2. 使用uuid为文件重命名   

      因为原本的文件名可能会导致重复，所以使用uuid的命名方式，并且用日期作为文件夹层次进行分类管理。   

      ```java
      			//取原文件名
      			String oldName = uploadFile.getOriginalFilename();
      			//生成新的文件名
      			String newName = IDUtils.genImageName();
      			newName = newName + oldName.substring(oldName.lastIndexOf("."));
      			//图片上传
      			String imagePath = new DateTime().toString("/yyyy/MM/dd");
      ```

      - 由于后缀不同，所以采用原文件的后缀。uuid的使用需要用到工具类【[uuid工具类](../Tools/IDUtils.java)】,由于该工具类可以通用，这里不做过多介绍。   
      - 这里的``DateTime().toString``来源于**joda**的使用。  

   3. 使用FtpUtil的uploadFile上传方法将文件上传到服务器       

      ```java
      boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, 
      					FTP_BASE_PATH, imagePath, newName, uploadFile.getInputStream());
      ```

   4. 对上传结果进行判断，返回KindEdit规定好的数据格式。

      ```java
      			if(!result){
      				resultMap.put("error", 1);
      				resultMap.put("message", "文件上传失败");
      				return resultMap;
      			}
      			resultMap.put("error", 0);
      			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" +newName);
      			return resultMap;
      ```

      _由于url由  （基础路径 和 图片路径 和 文件名）构成，所以这里需要进行组装。_   

   ### 2.配置多部件解析器    

   SpringMVC 用的是的MultipartFile来进行文件上传 所以我们首先要配置MultipartResolver:用于处理表单中的file。

   1. 在springmvc.xml中配置上传解析器    

      添加下方代码：

      ```xml
      <!-- 定义文件上传解析器 -->
      	<bean id="multipartResolver"
      		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
      		<!-- 设定默认编码 -->
      		<property name="defaultEncoding" value="UTF-8"></property>
      		<!-- 设定文件上传的最大值5MB，5*1024*1024 -->
      		<property name="maxUploadSize" value="5242880"></property>
      	</bean>
      ```

      设置文件上传的编码，文件的大小。    


   ### 3.处理兼容问题    

   ```java
   		Map result= pictureService.uploadPicture(uploadFile);
   		//为了兼容火狐，将java对象转成json字符串
   		String json = JsonUtils.objectToJson(result);
   		return json;
   ```

因为直接返回java对象，chrome会自动将其将会为json，而火狐不会。所以为了兼容火狐浏览器，在Controller层将对象转化为json字符串再进行返回。

这里的对象转化为json字符串使用到【[json转化](../Tools/JsonUtils.java)】工具类（工具类可通用，不做介绍）   

![](../img/p11.png)  

