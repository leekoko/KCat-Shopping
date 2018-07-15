# 详情展示页面   

### 展示数据提取

Z：在rest服务中对数据进行提取，形成接口

```java
	@Override
	public TaotaoResult getItemParam(long itemId) {
		
		try {
			//添加缓存逻辑
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//从缓存取信息，根据id提取
			if(!StringUtils.isBlank(json)){
				//json转pojo
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return TaotaoResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//根据商品id查询规格参数
		//设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);    //查询大文本
		if(list != null && list.size()>0){
			TbItemParamItem paramItem = list.get(0);
			
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return TaotaoResult.ok(paramItem);
		}
		return TaotaoResult.build(400,"无此商品规格");
	}
```

D：为什么设置redis缓存要使用这种命名方式？

```java
jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
```

Z：方便管理，当使用 A:B:C:01 的形式设置键，用redis工具查看的时候会自动生成多级文件夹。

D：``return TaotaoResult.ok(paramItem);``返回的具体是什么东西？

Z：一个TaotaoResult对象，也就是它本身

```java
    public static TaotaoResult ok(Object data) {
        return new TaotaoResult(data);
    }
    public TaotaoResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }
```

D：TaotaoResult.java类是在common包，在本包怎么调用到的呢？

Z：从pom文件看出，rest —依赖—> mapper —依赖—> manager —依赖—>common。   

D：``List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);   ``跟普通的查询有什么区别呢？  

Z：当有大字段到的时候，就会出现WithBLOBs。将大字段当独附加。（继承了BaseResultMap）

```xml
  <resultMap id="ResultMapWithBLOBs" type="com.taotao.pojo.TbItemDesc" extends="BaseResultMap" >
    <result column="item_desc" property="itemDesc" jdbcType="LONGVARCHAR" />
  </resultMap>
```

D：``return TaotaoResult.build(400,"无此商品规格");``build是怎么处理错误信息的呢？

Z：也是生成TotaoResule对象，不过改为传状态码和信息，不传数据而已

```java
    public static TaotaoResult build(Integer status, String msg) {
        return new TaotaoResult(status, msg, null);
    }
```

D：接口已经有了，怎么使用接口数据进行页面展示呢？

Z：portal项目是用来展示页面的项目，



















程序原型

10视频 

第十天1-3