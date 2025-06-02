package org.bhy.collectionDemo;

import org.bhy.collectionDemo.mapper.BatteryDataMapper;
import org.bhy.collectionDemo.pojo.BatteryData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CollectionDemoApplicationTests {
    @Autowired
    private BatteryDataMapper batteryDataMapper;

    @Test
    void contextLoads() {

        BatteryData batteryData = new BatteryData(1, 1, 25.0, 220.0);
        batteryDataMapper.insert(batteryData);
    }

}
