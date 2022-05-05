package com.lyh.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@SpringBootTest
class CommonApplicationTests {
	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	void contextLoads() {

	}

}
