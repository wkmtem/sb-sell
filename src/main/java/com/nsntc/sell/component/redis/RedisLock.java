package com.nsntc.sell.component.redis;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Method Name: lock
     * Description: 加锁
     * Create DateTime: 2017/12/9 上午3:14
     * @param key productId
     * @param value 时间戳:当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        /** SETNX命令 */
        if (this.stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            /** 可设置, 并设置后锁定 */
            log.info("【redis分布式锁】加锁成功，value={}", value);
            return true;
        }
        /** 以下代码，解决死锁 */
        /** 获取redis当前key的值 */
        String currentValue = this.stringRedisTemplate.opsForValue().get(key);
        /** 锁已过期 */
        if (StringUtils.isNotEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            log.info("【redis分布式锁】锁已过期，currentValue={}", currentValue);
            /** 获取上个锁的时间, 即置换锁中的value GETSET命令*/
            String preValue = this.stringRedisTemplate.opsForValue().getAndSet(key, value);
            if (StringUtils.isNotEmpty(preValue) && StringUtils.equals(preValue, currentValue)) {
                log.info("【redis分布式锁】重新加锁，value={}", value);
                return true;
            }
        }
        return false;
    }

    /**
     * Method Name: unlock
     * Description: 解锁
     * Create DateTime: 2017/12/9 上午3:39
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = this.stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(currentValue) && StringUtils.equals(currentValue, value)) {
                /** 删除key */
                this.stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("【redis分布式锁】解锁异常，{}", e);
        }
    }
}
