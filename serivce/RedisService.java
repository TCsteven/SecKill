package com.ygc.miaosha.serivce;

import com.alibaba.fastjson.JSON;
import com.ygc.miaosha.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {


    @Autowired
    JedisPool jedisPool;

    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            return stringToBean(str,clazz);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }
    public <T> boolean set(String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,beanToString(value));
            return true;
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
