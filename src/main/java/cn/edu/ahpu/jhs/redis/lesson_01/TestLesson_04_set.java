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
public class TestLesson_04_set {
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
	public void a_sadd(){
		jedis.del("myset1");
		jedis.sadd("myset1", "one");
		jedis.sadd("myset1", "two");
		jedis.sadd("myset1", "two");
		jedis.sadd("myset1", "three");
		jedis.sadd("myset1", "three");
		jedis.sadd("myset1", "three");
		
		System.out.println(jedis.smembers("myset1"));
	}
	
	@Test
	public void b_srem(){
		jedis.srem("myset1", "two","three");
		System.out.println(jedis.smembers("myset1"));
	}
	
	@Test
	public void c_sdiff_sinter_sunion(){
		jedis.sadd("myset2", "hi");
		jedis.sadd("myset2", "jim");
		
		jedis.sadd("myset3", "hello");
		jedis.sadd("myset3", "jim");
		
		System.out.println("myset2"+jedis.smembers("myset2"));
		System.out.println("myset3"+jedis.smembers("myset3"));
		
		System.out.println("diff:"+jedis.sdiff("myset2","myset3"));
		System.out.println("sinter:"+jedis.sinter("myset2","myset3"));
		System.out.println("sunion:"+jedis.sunion("myset2","myset3"));
	}
	
	@Test
	public void d_scard(){
		jedis.del("myset1");
		jedis.sadd("myset1", "one");
		jedis.sadd("myset1", "two");
		jedis.sadd("myset1", "two");
		jedis.sadd("myset1", "three");
		jedis.sadd("myset1", "three");
		jedis.sadd("myset1", "three");
		System.out.println(jedis.scard("myset1"));
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
