package com.taotao.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author rongmahzong@outlook.com
 * @package com.taotao.jedis
 * @date Create in 下午 9:39 8.15/015
 */
public class JedisSpringTest {

	@Test
	public void testSpringJedis() {
		// 初始化容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		// 从容器中获取JedisClient对象
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		// 使用JedisClient对象操作redis
		jedisClient.set("redisClient", "mytest");
		String result = jedisClient.get("redisClient");
		System.out.println(result);
	}
}
