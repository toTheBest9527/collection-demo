package org.bhy.collectionDemo.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bhy.collectionDemo.config.RabbitMQConfig;
import org.bhy.collectionDemo.mapping.BatteryGroupMapping;
import org.bhy.collectionDemo.pojo.BatteryData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author EthanBrown
 * @Date 2025/6/1 20:59
 * @PackageName: org.bhy.collectionDemo.producer
 * @ClassName: BatteryDataProducer
 * @Description: 发送生产消息到RabbitMQ
 * @Version 1.0
 */
@Component
public class BatteryDataProducer {
    private final RabbitTemplate rabbitTemplate;
    private final BatteryGroupMapping batteryGroupMapping;
    private final ObjectMapper objectMapper;  // Jackson用于将对象转换成JSON

    @Autowired
    public BatteryDataProducer(RabbitTemplate rabbitTemplate, BatteryGroupMapping batteryGroupMapping, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.batteryGroupMapping = batteryGroupMapping;
        this.objectMapper = objectMapper;
    }

    // 发送多个 BatteryData 到对应的队列
    public void sendBatteryData(String ip, List<BatteryData> batteryDataList) {
        // 获取队列名和 routingKey
        String queueName = batteryGroupMapping.getQueueNameByIp(ip);
        String routingKey = batteryGroupMapping.getRoutingKeyByIp(ip);

        if (queueName != null && routingKey != null) {
            try {
                // 将 BatteryData 列表转换为 JSON 字符串
                String message = objectMapper.writeValueAsString(batteryDataList);
                // 发送数据到 RabbitMQ
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, message);
                System.out.println("Data sent to queue: " + queueName);
            } catch (Exception e) {
                System.out.println("Error converting BatteryData to JSON: " + e.getMessage());
            }
        } else {
            // 没有匹配的消息队列
            System.out.println("No matching queue or routingKey found for IP: " + ip);
        }
    }
}
