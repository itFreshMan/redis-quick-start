package cn.edu.ahpu.jhs.redis.lesson_01;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class TestLesson_02_hashes {
	private static JedisPool jedisPool;//����Ƭ���ӳ�
	private static Jedis jedis;//����Ƭ��ͻ�������
	@BeforeClass
	public static void  setup(){
		// �ػ������� 
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
	public void a_hset(){
		jedis.hset("userinfo", "name", "����˫");
		jedis.hset("userinfo", "age", "18");
		jedis.hset("userinfo", "sex", "��");
		jedis.hset("userinfo", "birth", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		System.out.println("���� userinfo[name] :"+jedis.hget("userinfo","name"));
	}
	
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Test
	public void b_hmset(){
		
		Map userinfoMap = new HashMap();
		userinfoMap.put( "name", "����˫2");
		userinfoMap.put("age", "182");
		userinfoMap.put("sex", "��2");
		jedis.hmset("userinfo2", userinfoMap);
		System.out.println("���� userinfo2[name,age��sex] :"+jedis.hmget("userinfo2","name","age","sex"));
	}
	
	@Test
	public void c_hincrby(){
		System.out.print("���� userinfo[age]  :"+jedis.hget("userinfo","age")+" - 8 = ");
		jedis.hincrBy("userinfo", "age", -8l);
		System.out.print(jedis.hget("userinfo","age"));
	}
	
	
	@Test
	public void d_otherCommond(){
		/*jedis.hlen("userinfo");
		jedis.hdel("userinfo", "age");
		jedis.hexists("userinfo", "name");*/
		
		
		System.out.println(jedis.hkeys("userinfo"));
		System.out.println(jedis.hvals("userinfo"));
		System.out.println(jedis.hgetAll("userinfo"));
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
