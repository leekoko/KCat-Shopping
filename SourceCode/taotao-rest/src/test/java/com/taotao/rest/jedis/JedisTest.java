package com.taotao.rest.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	@Test
	public void testJedisPool(){
		//创建jedis连接池
		JedisPool pool = new JedisPool("192.168.0.105",6379);
		//从连接池获取jedis对象
		Jedis jedis = pool.getResource();
		jedis.set("key2", "hello jedis hehe");
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jedis对象
		jedis.close();
		pool.close();
	}

}
