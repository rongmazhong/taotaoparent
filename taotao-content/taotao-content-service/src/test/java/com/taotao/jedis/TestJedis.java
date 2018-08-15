package com.taotao.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
    @Test
    public void testJedisCluster() throws IOException {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("10.0.0.71", 7000));
        nodes.add(new HostAndPort("10.0.0.71", 7001));
        nodes.add(new HostAndPort("10.0.0.71", 7002));
        nodes.add(new HostAndPort("10.0.0.71", 7003));
        nodes.add(new HostAndPort("10.0.0.71", 7004));
        nodes.add(new HostAndPort("10.0.0.71", 7005));
        nodes.add(new HostAndPort("10.0.0.71", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        jedisCluster.set("cluster-test", "hello from jedis cluster");
        String s = jedisCluster.get("cluster-test");
        System.out.println(s);
        jedisCluster.close();
    }
}
