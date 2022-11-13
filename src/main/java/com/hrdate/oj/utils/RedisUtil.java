package com.hrdate.oj.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    private static final String ENV_NAME_KEY = "spring.profiles";
    private static final String APPLICATION_NAME_PREFIX = "test-";
    private static final String KEY_PREFIX;

    static {
        KEY_PREFIX = APPLICATION_NAME_PREFIX + ENV_NAME_KEY + ":";
    }

    /** ----------------------------key操作(不全)---------------------------- **/


    /**
     * 删除key
     * @param key 后缀key
     */
    public void delete(String key) {
        key = KEY_PREFIX + key;
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys 后缀key
     */
    public void delete(Collection<String> keys) {
        List<String> list = new ArrayList<>(keys.size());
        for (String key : keys) {
            key = KEY_PREFIX + key;
            list.add(key);
        }
        redisTemplate.delete(keys);
    }

    /**
     * 是否存在key
     *
     * @param key 后缀key
     */
    public Boolean hasKey(String key) {
        key = KEY_PREFIX + key;
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key 后缀key
     * @param timeout 过期时间
     * @param unit 过期时间单位
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        key = KEY_PREFIX + key;
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern 匹配字
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 游标检索模糊匹配keys，每次scan 1000条
     * @param matchKey 匹配字
     */
    public Set<String> scan(String matchKey) {
        Set<String> keys = redisTemplate.execute((RedisCallback<? extends Set<String>>)  connection -> {
            Set<String> keySet = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match("*" + matchKey + "*").count(1000).build());
            while (cursor.hasNext()) {
                keySet.add(new String(cursor.next()));
            }
            return keySet;
        });
        return keys;
    }

    /** ----------------------------简单String操作(不全)---------------------------- **/

    /**
     * 设置指定 key 的值
     * @param key 后缀key
     * @param value 存储值
     */
    public void set(String key, Object value) {
        key = KEY_PREFIX + key;
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的值
     * @param key 后缀key
     */
    public Object get(String key) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key 后缀key
     * @param value 存储值
     * @return 之前已经存在返回false,不存在返回true
     */
    public boolean setIfAbsent(String key, Object value) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 按delta递增
     *
     * @param key   key值
     * @param delta delta值
     * @return 返回递增后结果
     */
    Long incr(String key, long delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 获取自增1后的 值
     *
     * @param key  key值
     * @param time 过期时间
     * @return 返回递增后结果
     */
    public Long incrExpire(String key, long time) {
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count != null && count == 1) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return count;
    }

    /** ----------------------------hash操作(不全)---------------------------- **/

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key 后缀key
     * @param field hash key
     */
    public Object hGet(String key, String field) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key 后缀key
     */
    public Map<Object, Object> hGetAll(String key) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key 后缀key
     * @param fields hash keys
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    public void hPut(String key, String hashKey, Object value) {
        key = KEY_PREFIX + key;
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void hPutAll(String key, Map<String, Object> maps) {
        key = KEY_PREFIX + key;
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key 后缀key
     * @param hashKey hash的key
     * @param value hash的value值
     */
    public Boolean hPutIfAbsent(String key, String hashKey, Object value) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key 后缀key
     * @param fields hash key值
     */
    public Long hDelete(String key, Object... fields) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key  后缀key
     * @param field hash字段
     */
    public boolean hExists(String key, String field) {
        key = KEY_PREFIX + key;
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key 后缀key
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key 后缀key
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key 后缀key
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key 后缀key
     * @param options scan参数项
     */
    public Cursor<Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }
}
