package com.lyh.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


@Component
public class PropertiesCenter {
    /**
     * 配置中心
     */
    Properties properties = new Properties();
    CuratorFramework client = null;
    TreeCache treeCache = null;

    @Value("${zookeeper.address}")
    private String address;

    private final String CONFIG_NAME = "/config-center";

    public PropertiesCenter() {
    }

    private void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(0, 3);
        client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        treeCache = new TreeCache(client, CONFIG_NAME);
    }

    /**
     * 设置属性
     * @param key
     * @param value
     * @throws Exception
     */
    public void setProperties(String key, String value) throws Exception {
        String propertiesKey = CONFIG_NAME + "/" + key;
        Stat stat = client.checkExists().forPath(propertiesKey);
        if(stat == null) {
            client.create().forPath(propertiesKey);
        }
        client.setData().forPath(propertiesKey, value.getBytes());
    }

    /**
     * 获取属性
     * @param key
     * @return
     */
    public String getProperties(String key) {
        return properties.getProperty(key);
    }

    @PostConstruct
    public void loadProperties() {
        try {
            init();
            client.start();
            treeCache.start();

            // 从zk中获取配置放入本地配置中
            Stat stat = client.checkExists().forPath(CONFIG_NAME);
            if(stat == null) {
                client.create().forPath(CONFIG_NAME);
            }
            List<String> configList = client.getChildren().forPath(CONFIG_NAME);
            for (String configName : configList) {
                byte[] value = client.getData().forPath(CONFIG_NAME + "/" + configName);
                properties.setProperty(configName, new String(value));
            }

            // 监听属性值变更
            treeCache.getListenable().addListener(new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                    if (Objects.equals(treeCacheEvent.getType(), TreeCacheEvent.Type.NODE_ADDED) ||
                            Objects.equals(treeCacheEvent.getType(), TreeCacheEvent.Type.NODE_UPDATED)) {
                        String updateKey = treeCacheEvent.getData().getPath().replace(CONFIG_NAME + "/", "");
                        properties.setProperty(updateKey, new String(treeCacheEvent.getData().getData()));
                        System.out.println("数据更新: "+treeCacheEvent.getType()+", key:"+updateKey+",value:"+new String(treeCacheEvent.getData().getData()));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
