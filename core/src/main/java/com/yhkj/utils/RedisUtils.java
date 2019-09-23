package com.yhkj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    //Redis服务器IP
//    private static String ADDR = "192.168.32.222";
    private static String ADDR = "182.61.39.89";

    //Redis的端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH = "123456";

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            logger.info("JedisPool连接池报错");
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        RedisUtils.setString("test","testValue");
        System.out.println(RedisUtils.getString("test"));
        logger.info("成功");
    }
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public static void setString(String key ,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            logger.error("setString设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
        } finally {
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public static void setString(String key ,String value , int expireTime){
        String result = "OK";
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
            if(result.equals("OK")) {
                jedis.expire(key.getBytes(), expireTime);
            }
        } catch (Exception e) {
            logger.error("setString设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
        } finally {
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public static String getString(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            logger.error("getString获取redis键值异常:key=" + key + " cause:" + e.getMessage());
        } finally {
            jedis.close();
        }
        return null;
    }


    /**
     * 删除key
     */
    public static Long delkeyObject(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public static Boolean existsObject(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }
}
