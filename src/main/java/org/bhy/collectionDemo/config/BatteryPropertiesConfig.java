package org.bhy.collectionDemo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Author EthanBrown
 * @Date 2025/6/1 19:53
 * @PackageName: org.bhy.collectionDemo.config
 * @ClassName: BatteryConfig
 * @Description: 通过battery-group-ip.yml文件来动态加载ip、queue、routing key
 * @Version 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "battery")
@Data
@NoArgsConstructor
public class BatteryPropertiesConfig {
    private Map<String, String> ipToQueueName;
    private Map<String, String> ipToRoutingKey;
    private Map<String, Integer> ipToBatteryGroup;
}

