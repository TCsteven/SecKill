package com.ygc.miaosha.serivce;

import com.alibaba.fastjson.JSON;
import com.ygc.miaosha.redis.KeyPrefix;
import com.ygc.miaosha.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {


    @Autowired
    JedisPool jedisPool;

    /**
     * 获取一个对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix,String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            return stringToBean(str,clazz);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 设置对象
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix,String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            int expireSeconds = prefix.expireSeconds();
            if (expireSeconds <= 0){
                jedis.set(realKey,beanToString(value));
            }else {
                jedis.setex(realKey,expireSeconds,beanToString(value));
            }
            return true;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @return
     */
    public boolean exist(KeyPrefix prefix,String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }


    public Long incr(KeyPrefix prefix,String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public Long decr(KeyPrefix prefix,String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }


    private <T> String beanToString(T value) {
        if (null == value){
            return null;
        }
        Class<?> clz = value.getClass();

        if(clz.equals(int.class) || clz.equals(Integer.class) || clz.equals(long.class) || clz.equals(Long.class)){
            return "" + value;
        }else if(clz.equals(String.class)){
            return (String)value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T stringToBean(String str,Class<T> clz) {
        if (StringUtil.isBlank(str) || clz == null){
            return null;
        }

        if(clz.equals(int.class) || clz.equals(Integer.class)){
            return (T) Integer.valueOf(str);
        }else if(clz.equals(String.class)){
            return (T) str;
        }else if(clz.equals(long.class) || clz.equals(Long.class)){
            return (T) Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clz);
        }
    }


}
