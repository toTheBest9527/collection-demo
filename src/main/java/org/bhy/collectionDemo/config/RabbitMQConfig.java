package org.bhy.collectionDemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author EthanBrown
 * @Date 2025/6/1 20:30
 * @PackageName: org.bhy.collectionDemo.config
 * @ClassName: RabbitMQConfig
 * @Description: 配置exchange&routing key&queue的绑定关系
 * @Version 1.0
 */
@Configuration
public class RabbitMQConfig {
    // 交换机名称
    public static final String EXCHANGE_NAME = "batteryExchange";
    private final BatteryPropertiesConfig batteryPropertiesConfig;

    // 构造函数注入 BatteryConfig
    public RabbitMQConfig(BatteryPropertiesConfig batteryPropertiesConfig) {
        this.batteryPropertiesConfig = batteryPropertiesConfig;
    }

    // 创建并声明交换机，类型为 DirectExchange
    @Bean
    public DirectExchange batteryExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    // 声明队列并绑定交换机
    @Bean
    public List<Binding> declareQueuesAndBindings() {
        List<Binding> bindings = new ArrayList<>();

        // 遍历 IP 地址到队列名的映射
        for (String ip : batteryPropertiesConfig.getIpToQueueName().keySet()) {
            // 获取队列名
            String queueName = batteryPropertiesConfig.getIpToQueueName().get(ip);
            // 获取 routingKey
            String routingKey = batteryPropertiesConfig.getIpToRoutingKey().get(ip);

            if (queueName != null && routingKey != null) {
                // 声明队列，队列持久化，掉服务不消失
                Queue queue = new Queue(queueName, true);

                // 绑定队列到交换机，并指定精确路由（routing key）
                Binding binding = BindingBuilder.bind(queue)
                        .to(batteryExchange())
                        .with(routingKey);

                // 将绑定存储到列表中
                bindings.add(binding);
            }
        }
        return bindings;  // 返回所有绑定的列表
    }

    // 通过连接工厂创建 RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}

