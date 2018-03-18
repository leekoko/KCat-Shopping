package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
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
	
	@Test
	public void testJedisCluster(){
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.0.129", 7001));
		nodes.add(new HostAndPort("192.168.0.129", 7002));
		nodes.add(new HostAndPort("192.168.0.129", 7003));
		nodes.add(new HostAndPort("192.168.0.129", 7004));
		nodes.add(new HostAndPort("192.168.0.129", 7005));
		nodes.add(new HostAndPort("192.168.0.129", 7006));
		
		JedisCluster cluster = new JedisCluster(nodes);
		
		cluster.set("key3", "888");
		String string = cluster.get("key3");
		System.out.println(string);
		
		cluster.close();
	}
	/**
	 * spring & redis
	 * 测试单redis
	 */
	@Test
	public void testSingle(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String str = jedis.get("key1");
		System.out.println(str);
		jedis.close();
		pool.close();
	}
	
	/**
	 * spring & redis
	 * 集群测试redis
	 */
	@Test
	public void testCluster(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
		String str = jedisCluster.get("key3");
		System.out.println(str);
		jedisCluster.close();
	}
	
}
