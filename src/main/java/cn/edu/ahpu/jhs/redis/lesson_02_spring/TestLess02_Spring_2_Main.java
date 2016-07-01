package cn.edu.ahpu.jhs.redis.lesson_02_spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestLess02_Spring_2_Main {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
		 JedisPool pool = (JedisPool) context.getBean("jedisPool");
		 Jedis jedis = pool.getResource();
		 
		 System.out.println(jedis.hgetAll("userinfo2"));
		 pool.returnResource(jedis);
	
	}
}
