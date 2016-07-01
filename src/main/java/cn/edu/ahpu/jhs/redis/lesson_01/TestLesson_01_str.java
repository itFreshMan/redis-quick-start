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
public class TestLesson_01_str {
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
	public void a_set(){
		String name = "jhs";
		jedis.set("name",name );
		System.out.println("���� name :"+name);
	}
	
	@Test
	public void b_get(){
		String name = jedis.get("name");
		System.out.println("��ȡ name :"+name);
	}
	
	@Test
	public void c_del(){
		String key = "name";
		System.out.println("ɾ��ǰ��ȡ "+key+" :"+jedis.get(key));
		jedis.del(key);
		System.out.println("ɾ����>>>>��ȡ "+key+" :"+jedis.get(key));
	}
	
	@Test
	public void e_exists(){
		String key = "name";
		System.out.println("���� "+key+" :"+jedis.exists(key));
	}
	
	@Test
	public void f_expire(){
		String key = "name";
		System.out.println("expireǰ��ȡ 	"+key+" :"+jedis.get(key));
		jedis.expire(key,1);
		System.out.println("expire��>>>>��ȡ "+key+" :"+jedis.get(key));
	}
	
	@Test
	public void g_expireAt(){
		String key = "name";
		System.out.println("expireAtǰ��ȡ "+key+" :"+jedis.get(key));
		jedis.expireAt(key,System.currentTimeMillis()+2000);
		System.out.println("expireAt��>>>>��ȡ "+key+" :"+jedis.get(key));
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
