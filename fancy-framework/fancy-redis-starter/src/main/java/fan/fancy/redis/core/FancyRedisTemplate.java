package fan.fancy.redis.core;

import org.redisson.api.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 相关操作类, 封装 {@link RedisTemplate} 和 {@link RedissonClient}.
 *
 * @author Fan
 */
public class FancyRedisTemplate<V> {

    private final RedisTemplate<String, V> redisTemplate;

    private final RedissonClient redissonClient;

    public FancyRedisTemplate(RedisTemplate<String, V> redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }

    /**
     * 判断 Key 是否存在.
     *
     * @param key {@link String}
     * @return {@link Boolean}
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除 Key.
     *
     * @param key {@link String}
     * @return {@link Boolean}
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 获取 Key 的过期时间, 默认单位秒.
     *
     * @param key {@link String}
     * @return {@link Long}
     */
    public Long getExpire(String key) {
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 获取 Key 的过期时间, 指定时间单位.
     *
     * @param key  {@link String}
     * @param unit {@link TimeUnit}
     * @return {@link Long}
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 设置 Key 的过期时间, 默认单位秒.
     *
     * @param key     {@link String}
     * @param timeout 过期时间
     * @return {@link Boolean}
     */
    public Boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置 Key 的过期时间, 指定时间单位.
     *
     * @param key     {@link String}
     * @param timeout 过期时间
     * @param unit    {@link TimeUnit}
     * @return {@link Boolean}
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * String, 设置 Key-Value.
     *
     * @param key   {@link String}
     * @param value 值
     */
    public void set(String key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * String, 设置 Key-Value, 同时设置过期时间, 默认单位秒.
     *
     * @param key     {@link String}
     * @param value   值
     * @param timeout 过期时间
     */
    public void set(String key, V value, long timeout) {
        set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * String, 设置 Key-Value, 同时设置过期时间, 指定时间单位.
     *
     * @param key     {@link String}
     * @param value   值
     * @param timeout 过期时间
     * @param unit    {@link TimeUnit}
     */
    public void set(String key, V value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * String, 获取 Key 的值.
     *
     * @param key {@link String}
     * @return 值
     */
    public V get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Hash, 设置 Key-HashKey-HashValue.
     *
     * @param key     {@link String}
     * @param hashKey {@link String}
     * @param value   值
     */
    public void hSet(String key, String hashKey, V value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * Hash, 获取 Key-HashKey 的值.
     *
     * @param key     {@link String}
     * @param hashKey {@link String}
     * @return {@link Object}
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * Hash, 获取 Key 的值.
     *
     * @param key {@link String}
     * @return {@link Map}
     */
    public Map<Object, Object> hEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Hash, 增加 Key-HashKey 指定值.
     *
     * @param key       {@link String}
     * @param hashKey   {@link String}
     * @param increment 值
     */
    public void hIncrement(String key, String hashKey, long increment) {
        redisTemplate.opsForHash().increment(key, hashKey, increment);
    }

    /**
     * Hash, 删除 Key-HashKey.
     *
     * @param key      {@link String}
     * @param hashKeys HashKeys
     */
    public void hDel(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * List, 设置 Key-Value, 在头部添加元素.
     *
     * @param key   {@link String}
     * @param value 值
     */
    public void lPush(String key, V value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * List, 设置 Key-Value, 在尾部添加元素.
     *
     * @param key   {@link String}
     * @param value 值
     */
    public void rPush(String key, V value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * List, 获取 Key 的值, 移除并获取头部的元素.
     *
     * @param key {@link String}
     * @return 值
     */
    public V lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * List, 获取 Key 的值, 移除并获取尾部的元素.
     *
     * @param key {@link String}
     * @return 值
     */
    public V rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * Set, 判断 Key 是否包含指定值.
     *
     * @param key   {@link String}
     * @param value 值
     * @return {@link Boolean}
     */
    public Boolean sIsMember(String key, V value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Set, 设置 Key-Value, 添加元素.
     *
     * @param key    {@link String}
     * @param values 元素
     */
    @SafeVarargs
    public final void sAdd(String key, V... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Set, 获取 Key 的值.
     *
     * @param key {@link String}
     * @return {@link Set}
     */
    public Set<V> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }


    /**
     * Set, 在 Key 中删除值并返回删除数量.
     *
     * @param key    {@link String}
     * @param values 元素
     * @return {@link Long}
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * Sorted set, 设置 Key-Value, 添加元素.
     *
     * @param key   {@link String}
     * @param value 值
     * @param score 评分
     */
    public void zAdd(String key, V value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * Sorted set, 从 Key 中获取 start 和 end 之间的元素, score 从低到高.
     *
     * @param key   {@link String}
     * @param start 起始
     * @param end   结束
     * @return {@link Set}
     */
    public Set<V> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * Sorted set, 获取 Key 中指定元素的排名, score 从低到高.
     *
     * @param key   {@link String}
     * @param value 值
     * @return {@link Long}
     */
    public Long zRank(String key, V value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 获取 {@link RLock}.
     *
     * @param lockKey {@link String}
     * @return {@link RLock}
     */
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 获取分布式锁, 阻塞等待, 不会自动释放.
     *
     * @param lockKey {@link String}
     */
    public void lock(String lockKey) {
        RLock lock = getLock(lockKey);
        lock.lock();
    }

    /**
     * 尝试获取分布式锁, 不等待, 不会自动释放.
     *
     * @param lockKey {@link String}
     * @return {@code boolean}
     */
    public boolean tryLock(String lockKey) {
        RLock lock = getLock(lockKey);
        return lock.tryLock();
    }

    /**
     * 尝试获取分布式锁, 可以等待一段时间, 不会自动释放.
     *
     * @param lockKey {@link String}
     * @param time    超时时间
     * @param unit    {@link TimeUnit}
     * @return {@code boolean}
     */
    public boolean tryLock(String lockKey, long time, TimeUnit unit) {
        RLock lock = getLock(lockKey);
        try {
            return lock.tryLock(time, unit);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 尝试获取分布式锁, 可以等待一段时间, 到期后自动释放.
     *
     * @param lockKey   {@link String}
     * @param waitTime  等待时间
     * @param leaseTime 持有时间
     * @param unit      {@link TimeUnit}
     * @return {@code boolean}
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 释放分布式锁.
     *
     * @param lockKey {@link String}
     */
    public void unlock(String lockKey) {
        RLock lock = getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    /**
     * 获取原子类 {@link RAtomicLong}.
     *
     * @param name {@link String}
     * @return {@link RAtomicLong}
     */
    public RAtomicLong getAtomicLong(String name) {
        return redissonClient.getAtomicLong(name);
    }

    /**
     * 获取原子类 {@link RAtomicDouble}.
     *
     * @param name {@link String}
     * @return {@link RAtomicDouble}
     */
    public RAtomicDouble getAtomicDouble(String name) {
        return redissonClient.getAtomicDouble(name);
    }

    /**
     * 获取布隆过滤器 {@link RBloomFilter}.
     *
     * @param name {@link String}
     * @return {@link RBloomFilter}
     */
    public <T> RBloomFilter<T> getBloomFilter(String name) {
        return redissonClient.getBloomFilter(name);
    }
}