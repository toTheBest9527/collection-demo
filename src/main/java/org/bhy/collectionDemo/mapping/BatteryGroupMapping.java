package org.bhy.collectionDemo.mapping;

import org.bhy.collectionDemo.config.BatteryPropertiesConfig;
import org.springframework.stereotype.Component;

/**
 * @Author EthanBrown
 * @Date 2025/6/1 20:00
 * @PackageName: org.bhy.collectionDemo.mapping
 * @ClassName: BatteryGroupMapping
 * @Description: 获取ip、queue、routing key
 * @Version 1.0
 */

@Component
public class BatteryGroupMapping {

    private final BatteryPropertiesConfig batteryPropertiesConfig;

    public BatteryGroupMapping(BatteryPropertiesConfig batteryPropertiesConfig) {
        this.batteryPropertiesConfig = batteryPropertiesConfig;
    }

    //根据 IP 获取队列名
    public String getQueueNameByIp(String ip) {
        return batteryPropertiesConfig.getIpToQueueName().get(ip);
    }

    // 根据 IP 获取 routingKey
    public String getRoutingKeyByIp(String ip) {
        return batteryPropertiesConfig.getIpToRoutingKey().get(ip);
    }

    // 根据 IP 获取 groupId
    public Integer getGroupIdByIp(String ip) {
        return batteryPropertiesConfig.getIpToBatteryGroup().get(ip);
    }
}
