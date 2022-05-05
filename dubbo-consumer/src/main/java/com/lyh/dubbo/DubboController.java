package com.lyh.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@RequestMapping("/demo")
public class DubboController {
    @Autowired
    private DubboApiRpc rpc;

    /**
     * 测试方法，浏览器访问 /demo/index 可以看到响应结果了
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return rpc.sayHello();
    }

    @RequestMapping(value = "/index1", method = RequestMethod.GET)
    @ResponseBody
    public String dubbo() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "currentHost:" + localHost + "--------" + rpc.dubboTest();
    }
}
