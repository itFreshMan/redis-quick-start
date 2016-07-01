package cn.edu.ahpu.jhs.redis.lesson_04_spring_data_redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-srping-data-redis.xml"})
public class TestLess04_spring_data_redis {
	
	@Autowired
	 private StringRedisTemplate stringRedisTemplate;  
	
	@Test
	public void a_set(){
		stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set("j_name".getBytes(), "jianghs".getBytes());
				return 1l;
			}  
			
		});
	}	
	
	@Test
	public void b_get(){
		final String key = "j_name";
		String val = stringRedisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				 return new String(connection.get(key.getBytes()));
			}  
			
		});
		
		System.out.println(key+":"+val);
	}
	
	@Test
	public void c_del(){
		Long delNum = stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				Long returnval = 0l;
				for(int i = 0 ; i < 100 ; i++){
					String tempKey = "TestLess03_2_SharedSpring_"+i;
					if(connection.exists(tempKey.getBytes())){
						connection.del(tempKey.getBytes());
						
						returnval++;
					}
				}
				return returnval;
			}  
		});
		
		System.out.println("É¾³ýÁË:"+delNum);
	}
}
