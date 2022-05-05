package com.lyh.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WatcherService implements Watcher {

    @Override
    public void process(WatchedEvent event) {

    }
}
