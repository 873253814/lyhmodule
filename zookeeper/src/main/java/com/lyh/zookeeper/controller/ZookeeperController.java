package com.lyh.zookeeper.controller;

import com.lyh.zookeeper.config.PropertiesCenter;
import com.lyh.zookeeper.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ZookeeperController {

    @Autowired
    private ZookeeperService zookeeperService;
    @Autowired
    private PropertiesCenter propertiesCenter;

    @GetMapping("/setConfig")
    public void setConfig() throws Exception {
        propertiesCenter.setProperties("name", "hello");
    }

    @GetMapping("/getConfig")
    public void getConfig() throws Exception {
        System.out.println(propertiesCenter.getProperties("name"));
    }


    @GetMapping("/getChild")
    public void getChild() {
        List<String> children = new ArrayList<>();
        try {
            children = zookeeperService.getChildren("/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(children);

    }
}
