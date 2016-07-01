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
public class TestLesson_03_lists {
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
	public void a_lpush_rpush(){
		jedis.rpush("mylist1", "one");
		jedis.rpush("mylist1", "two");
		jedis.rpush("mylist1", "three");
		
		jedis.lpush("mylist1", "zero");
		System.out.println(jedis.lrange("mylist1", 0, -1));
	}
	
	@Test
	public void b_lpop(){
		System.out.println(jedis.lrange("mylist1", 0, -1));
		jedis.rpop("mylist1");
		jedis.lpop("mylist1");
		System.out.println(jedis.lrange("mylist1", 0, -1));
		
	}
	
	@Test
	public void c_llen_lindex(){
		jedis.rpush("mylist1", "one");
		jedis.rpush("mylist1", "two");
		jedis.rpush("mylist1", "three");
		System.out.println(jedis.lrange("mylist1", 0, -1));
		
		System.out.println("length:"+jedis.llen("mylist1"));
		System.out.println(jedis.lindex("mylist1", 0));
		
	}
	
	@Test
	public void d_lrem_ltrim(){
		jedis.rpush("mylist7", "one");
		jedis.rpush("mylist7", "two");
		jedis.rpush("mylist7", "two");
		jedis.rpush("mylist7", "three");
		jedis.rpush("mylist7", "three");
		jedis.rpush("mylist7", "three");
		System.out.println(jedis.lrange("mylist7", 0, -1));
		
		jedis.lrem("mylist7", 2, "three");
		System.out.println(jedis.lrange("mylist7", 0, -1));
		
		
		jedis.ltrim("mylist7", 0, -2);
		System.out.println(jedis.lrange("mylist7", 0, -1));
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
