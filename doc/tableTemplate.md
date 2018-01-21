# 数据表存储模板   

应用场景：要将广东省所有学校的所有学院包括其所有专业显示出来。

方案一：通过学校id在学院表里查询对应的学院，拿到学院id后到专业表寻找对应的专业。数据量非常庞大，查询起来很慢。    

方案二：将专业和学院组成一个模板，与学校id对应存储成一个模板库。但对数据的显示js要求比较高。

相似的场景还有商城产品的规格参数：一个产品有多个规格组，每个规格组的规格项也是根据产品不同有所改变。    

本文主要将方案二：存储模板。

## 1.前端显示   

前端需要的效果就是：在树节点里面点击类别，如果参数存在，就提示已存在。如果不存在，出现动态添加参数的输入框。整个前端使用easyUI实现。

```html
<table cellpadding="5" style="margin-left: 30px" id="itemParamAddTable" class="itemParam">
	<tr>
		<td>商品类目:</td>
		<td><a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a> 
			<input type="hidden" name="cid" style="width: 280px;"></input>
		</td>
	</tr>
	<tr class="hide addGroupTr">
		<td>规格参数:</td>
		<td>
			<ul>
				<li><a href="javascript:void(0)" class="easyui-linkbutton addGroup">添加分组</a></li>
			</ul>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton submit">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton close">关闭</a>
		</td>
	</tr>
</table>
```

```javascript
		TAOTAO.initItemCat({
			fun:function(node){
				debugger;
				$(".addGroupTr").hide().find(".param").remove();
				//  判断选择的目录是否已经添加过规格
				$.getJSON("/item/param/query/itemcatid/" + node.id,function(data){
					if(data.status == 200 && data.data){
						$.messager.alert("提示", "该类目已经添加，请选择其他类目。", undefined, function(){
							$("#itemParamAddTable .selectItemCat").click();
						});
						return ;
					}
				});
				$(".addGroupTr").show();
			}
		});
```

从Controller判断该类目的参数是否存在，不存在就显示添加分组按钮。

```javascript
		$(".addGroup").click(function(){
			  var temple = $(".itemParamAddTemplate li").eq(0).clone();
			  $(this).parent().parent().append(temple);
			  temple.find(".addParam").click(function(){
				  var li = $(".itemParamAddTemplate li").eq(2).clone();
				  li.find(".delParam").click(function(){
					  $(this).parent().remove();
				  });
				  li.appendTo($(this).parentsUntil("ul").parent());
			  });
			  temple.find(".delParam").click(function(){
				  $(this).parent().remove();
			  });
		 });
```

添加分组按钮点击之后就可以添加分组，分组里面还可以添加参数。

![](../img/p16.png)  









4天视频8











不同商品不同规格，怎么描述？？

在讲什么内容？？

同一类商品，项目分组相同。规格项有的不一样，跟商品关联。不同商品，分组会变化。

要做的就是将商品对应的规格参数存储起来   



导入sql，研究表之间的关系   

解决方法：1.联三表查询     

（数据太多）

2.模板思路

一个商品对应一个模板。   

   





​   

实现，记录，feiman内容，发文

简单的部分快速跑过！





4天07