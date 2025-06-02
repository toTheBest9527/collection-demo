package org.bhy.collectionDemo.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bhy.collectionDemo.mapper.BatteryDataMapper;
import org.bhy.collectionDemo.pojo.BatteryData;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author EthanBrown
 * @Date 2025/6/2 10:53
 * @PackageName: org.bhy.collectionDemo.consumer
 * @ClassName: BatteryDataConsumer
 * @Description: 消费者消费不同消息队列的消息，并存入数据库
 * @Version 1.0
 */

@Service
public class BatteryDataConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BatteryDataMapper batteryDataMapper;

    public BatteryDataConsumer(BatteryDataMapper batteryDataMapper) {
        this.batteryDataMapper = batteryDataMapper;
    }

    /**
     * @param 监听消息队列获得消息
     */
    @RabbitListener(queues = {
            "battery_group_queue_1",
            "battery_group_queue_2",
            "battery_group_queue_3",
            "battery_group_queue_4",
            "battery_group_queue_5",
            "battery_group_queue_6",
            "battery_group_queue_7",
            "battery_group_queue_8"
    })
    public void commonBatteryDataConsumer(Message message) {
        //获取消息体
        String messageBody = new String(message.getBody());
       /* //获取当前队列名称
        String consumerQueue = message.getMessageProperties().getConsumerQueue();
        System.out.println(consumerQueue + ":" + messageBody);*/

        //解析Json数组到BatteryData类型集合中
        try {
            List<BatteryData> batteryDataList = objectMapper.readValue(messageBody, new TypeReference<List<BatteryData>>() {
            });
            for (BatteryData batteryData : batteryDataList) {
                System.out.println(batteryData);
                batteryDataMapper.insert(batteryData);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Json转换对象List集合失败");
        }
    }
}
