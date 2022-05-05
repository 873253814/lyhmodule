package com.lyh.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/send/{message}")
    public String sendMsg(@PathVariable String message) {
        //建议看一下KafkaTemplate的源码 很多api 我们可以指定分区发送消息
        kafkaTemplate.send("test2", message); //使用kafka模板发送信息
        String res = "Success" + message;
        log.info(res);
        return res;
    }
}
