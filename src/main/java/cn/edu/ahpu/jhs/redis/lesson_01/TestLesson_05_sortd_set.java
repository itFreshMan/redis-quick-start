package cn.edu.ahpu.jhs.redis.lesson_01;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@FixMethodOrder(MethodSorters.DEFAULT)  
public class TestLesson_05_sortd_set {
	private static JedisPool jedisPool;//非切片连接池
	private static Jedis jedis;//非切片额客户端连接
	@BeforeClass
	public static void  setup(){
		// 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxIdle(5); 
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(false); 
        
        jedisPool = new JedisPool(config,"192.168.220.129",6000);
	}
	
	@Before
	public void getJedis(){
		System.out.println("----------------------------------------------------");
		jedis = jedisPool.getResource();
		
	}
	
	@Test
	public void a_zadd_zrem(){
		jedis.del("myzset1");
		jedis.zadd("myzset1",1, "one");
		jedis.zadd("myzset1",2, "two");
		jedis.zadd("myzset1",2, "two");
		jedis.zadd("myzset1",3, "three");
		jedis.zadd("myzset1",3, "three");
		jedis.zadd("myzset1",3, "three");
		
		System.out.println(jedis.zrange("myzset1", 0, -1));
		jedis.zrem("myzset1","three");
		System.out.println(jedis.zrange("myzset1", 0, -1 ));
	}

	@After
	public void returnJedis(){
		if(jedis != null) {
			jedisPool.returnResourceObject(jedis);
		}
		System.out.println("----------------------------------------------------");
	}
	
	@AfterClass
	public static void teardown(){
		if(jedisPool != null){
			jedisPool.close();
		}	
	}
}
