package com.lyh.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class KafkaConsumer {
    //建议看一下KafkaListener的源码 很多api 我们也可以指定分区消费消息
// topicPartitions ={@TopicPartition(topic = "topic1", partitions = { "0", "1" })}
    @KafkaListener(topics = "test2", groupId = "consumer-group")
    public void listen(List<String> messages, Acknowledgment ack) {
        log.info("num:" + messages.size() + " consume");
        List<String> msgList = new ArrayList<>();
        //Object value = record.value();
        System.out.println(messages);
        for (String record : messages) {
            Optional<?> kafkaMessage = Optional.ofNullable(record);
            // 获取消息
            kafkaMessage.ifPresent(o -> msgList.add(o.toString()));
        }

        if (msgList.size() > 0) {
            for (String msg : msgList) {
                log.info("consume" + msg);
            }
            // 更新索引
            // updateES(messages);
        }
        //手动提交offset
        ack.acknowledge();
        msgList.clear();
        log.info("end");
    }

    /**
     * 批量消费
     */
    public void batchConsumer(List<ConsumerRecord<String, String>> records) {
        records.forEach(this::consumer);
    }

    /**
     * 单条消费
     */
    public void consumer(ConsumerRecord<String, String> record) {
        log.debug("主题:{}, 内容: {}", record.topic(), record.value());
    }
}
