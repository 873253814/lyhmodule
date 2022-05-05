package com.lyh.zookeeper.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
@Slf4j
public class ZookeeperConfig {
    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.timeout}")
    private int timeout;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                    System.out.println("Path:" + watchedEvent.getPath() + "Status:" + watchedEvent.getState());
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        countDownLatch.countDown();
                    }
                    log.info("【Watcher监听事件】={}",watchedEvent.getState());
                    log.info("【监听路径为】={}",watchedEvent.getPath());
                    log.info("【监听的类型为】={}",watchedEvent.getType()); //  三种监听类型： 创建，删除，更新
                }
            });
            countDownLatch.await();
            System.out.println("zk链接成功");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }
}
