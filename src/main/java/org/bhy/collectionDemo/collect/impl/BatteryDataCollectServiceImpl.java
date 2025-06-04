package org.bhy.collectionDemo.collect.impl;

import org.bhy.collectionDemo.collect.BatteryDataCollectService;
import org.bhy.collectionDemo.config.BatteryPropertiesConfig;
import org.bhy.collectionDemo.mapping.BatteryGroupMapping;
import org.bhy.collectionDemo.pojo.BatteryData;
import org.bhy.collectionDemo.producer.BatteryDataProducer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author EthanBrown
 * @Date 2025/6/1 21:05
 * @PackageName: org.bhy.collectionDemo.collect
 * @ClassName: BatteryDataCollectServiceImpl
 * @Description: 实现定时任务和数据采集解析
 * @Version 1.0
 */
@Service
@EnableAsync
@EnableScheduling
public class BatteryDataCollectServiceImpl implements BatteryDataCollectService {
    private final BatteryPropertiesConfig batteryPropertiesConfig;
    private final BatteryDataProducer batteryDataProducer;
    private final BatteryGroupMapping batteryGroupMapping;

    public BatteryDataCollectServiceImpl(BatteryPropertiesConfig batteryPropertiesConfig, BatteryDataProducer batteryDataProducer, BatteryGroupMapping batteryGroupMapping) {
        this.batteryPropertiesConfig = batteryPropertiesConfig;
        this.batteryDataProducer = batteryDataProducer;
        this.batteryGroupMapping = batteryGroupMapping;
    }

    @Scheduled(fixedRate = 5000)
    @Override
    public void collectDataScheduled() {
        for (String ip : batteryPropertiesConfig.getIpToQueueName().keySet()) {
            collectDataTask(ip);
        }
    }

    @Async
    @Override
    public void collectDataTask(String ip) {
/*
 实际上，在这里就是具体的业务代码，数据采集+解析一并完成。会设计专门的数据结构来存储。
 假设我们要采集寄存器地址为1~400地址的数据，这些数据同属相同groupId，不同属cluster，要计算
*/
        HashMap<Integer, BatteryData> batteryDataHashMap = new HashMap<>();
        Integer groupId = batteryGroupMapping.getGroupIdByIp(ip);
        List<BatteryData> batteryDataList = new ArrayList<>();

        for (int address = 1; address <= 400; address++) {
            int clusterId = calculateClusterIdByRegisterAddress(address);
            double temperature = Math.random();
            double voltage = Math.random();

            // 判断该簇是否已经有对应的 BatteryData
            if (!batteryDataHashMap.containsKey(clusterId)) {
                BatteryData batteryData = new BatteryData();
                batteryData.setGroupId(groupId);
                batteryData.setClusterId(clusterId);
                batteryData.setTemperature(temperature);
                batteryData.setVoltage(voltage);

                // 添加到 list 和 map
                batteryDataList.add(batteryData);
                batteryDataHashMap.put(clusterId, batteryData);
            } else {
                // 更新现有的 BatteryData
                BatteryData batteryData = batteryDataHashMap.get(clusterId);
                batteryData.setTemperature(temperature);
                batteryData.setVoltage(voltage);
            }
        }
        // 发送所有的 BatteryData 到 RabbitMQ
        batteryDataProducer.sendBatteryData(ip, batteryDataList);
    }


    @Override
    public int calculateClusterIdByRegisterAddress(int address) {
        if (address >= 1 && address <= 100) {
            return 1; // 属于簇 1
        } else if (address >= 101 && address <= 200) {
            return 2; // 属于簇 2
        } else if (address >= 201 && address <= 300) {
            return 3; // 属于簇 3
        } else if (address >= 301 && address <= 400) {
            return 4; // 属于簇 4
        }
            return -1; // 无效地址
    }
}
