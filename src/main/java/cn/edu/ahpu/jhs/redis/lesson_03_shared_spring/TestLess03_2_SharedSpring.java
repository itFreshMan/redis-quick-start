package cn.edu.ahpu.jhs.redis.lesson_03_shared_spring;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-shared-redis.xml"})
public class TestLess03_2_SharedSpring {
	
	@Resource
	private ShardedJedisPool shardedJedisPool ;
	
	private  ShardedJedis jedis;//非切片额客户端连接
	
	@Before
	public void getJedis(){
		System.out.println("----------------------------------------------------");
		jedis = shardedJedisPool.getResource();
	}
	
	@Test
	public void a_spring_get(){
		System.out.println(jedis.hgetAll("userinfo2"));
	}
	
	
	
	
	
	
	@After
	public void returnJedis(){
		if(jedis != null) {
			shardedJedisPool.returnResourceObject(jedis);
		}
		System.out.println("----------------------------------------------------");
	}
	
}
