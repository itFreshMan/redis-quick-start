package cn.edu.ahpu.jhs.redis.lesson_03_shared_spring;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestLess03_1_Shared_NoSpring {
	static ShardedJedisPool pool;

    static{
        JedisPoolConfig config =new JedisPoolConfig();//Jedis池配置
        config.setMaxIdle(1000 * 60);//对象最大空闲时间
        config.setTestOnBorrow(true);
        JedisShardInfo infoA = new JedisShardInfo("192.168.220.129", 6000);
        JedisShardInfo infoB = new JedisShardInfo("192.168.220.129", 7001);
        JedisShardInfo infoC= new JedisShardInfo("192.168.220.129", 8002);
        List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>(3);
        jdsInfoList.add(infoA);
        jdsInfoList.add(infoB);
        jdsInfoList.add(infoC);
        pool =new ShardedJedisPool(config, jdsInfoList);
     }

    
    public static void main(String[] args) {
    	for(int i=0; i<100; i++){
               String key = generateKey();
               ShardedJedis jds = null;	
               try {
                   jds = pool.getResource();
                   System.out.println(key+":"+jds.getShard(key).getClient().getPort());
                   System.out.println(jds.set(key,Math.random()+""));
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                   pool.returnResource(jds);
               }
           }
    	   
    	   ShardedJedis  jedis = pool.getResource();
    	   System.out.println(jedis.hgetAll("userinfo2"));
	}
    
    private static int index = 1;
    public static String generateKey(){
        return String.valueOf(Thread.currentThread().getId())+"_"+(index++);
    }
}
