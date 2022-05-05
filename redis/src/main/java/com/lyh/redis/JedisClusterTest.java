package com.lyh.redis;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import redis.clients.jedis.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisClusterTest {
    public static void main(String[] args) throws NoSuchFieldException {
        Set<HostAndPort> set =new HashSet<HostAndPort>();
        set.add(new HostAndPort("124.221.237.90",7000));
        set.add(new HostAndPort("124.221.237.90",7001));
        set.add(new HostAndPort("124.221.237.90",7002));
        set.add(new HostAndPort("124.221.237.90",7003));
        set.add(new HostAndPort("39.104.91.76",7000));
        set.add(new HostAndPort("39.104.91.76",7001));
        JedisCluster jedisCluster = new JedisCluster(set);
        System.out.println(jedisCluster.getClusterNodes());
        JedisPool jedisPool = jedisCluster.getClusterNodes().get("39.104.91.76:7001");
        System.out.println(jedisPool.getResource().clusterNodes());
        Jedis resource = jedisPool.getResource();
    }

}
