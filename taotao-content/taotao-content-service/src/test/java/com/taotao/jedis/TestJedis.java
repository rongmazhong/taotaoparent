package com.taotao.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 〈test Jedis〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/13
 */
public class TestJedis {
    @Test
    public void testJedis() {
        // 创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("jedis-key", "1234");
        String s = jedis.get("jedis-key");
        System.out.println(s);
    }
}
