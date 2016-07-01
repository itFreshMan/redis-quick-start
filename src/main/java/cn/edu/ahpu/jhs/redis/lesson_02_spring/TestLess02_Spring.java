package cn.edu.ahpu.jhs.redis.lesson_02_spring;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-redis.xml"})
public class TestLess02_Spring {
	
	@Resource
	private JedisPool jedisPool ;
	
	private  Jedis jedis;//非切片额客户端连接
	
	@Before
	public void getJedis(){
		System.out.println("----------------------------------------------------");
		jedis = jedisPool.getResource();
	}
	
	@Test
	public void a_spring_get(){
		System.out.println(jedis.hgetAll("userinfo2"));
	}
	
	
	
	
	
	
	
	
	
	@After
	public void returnJedis(){
		if(jedis != null) {
			jedisPool.returnResourceObject(jedis);
		}
		System.out.println("----------------------------------------------------");
	}
}
