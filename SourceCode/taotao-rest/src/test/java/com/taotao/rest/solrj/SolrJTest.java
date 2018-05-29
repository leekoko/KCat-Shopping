package com.taotao.rest.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	@Test
	public void addDocument() throws SolrServerException, IOException {
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.175.129:8080/solr");
		//创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "001");
		document.addField("item_title", "测试商品2");
		document.addField("item_price", 88888);
		//文档对象写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws SolrServerException, IOException{
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.175.129:8080/solr");
//		solrServer.deleteById("001");   //通过id删除
		solrServer.deleteByQuery("*:*");  //通过语句删除
		solrServer.commit();  
	}


}
