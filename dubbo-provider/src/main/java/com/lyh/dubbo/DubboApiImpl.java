package com.lyh.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

import java.net.InetAddress;

@DubboService
public class DubboApiImpl implements DubboApi {

    @Override
    public String sayHello() {
        return "Hello World";
    }

    @Override
    public String dubboTest() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return localHost.getHostAddress() + "Dubbo Test";
    }
}
