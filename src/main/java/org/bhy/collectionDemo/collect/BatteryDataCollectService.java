package org.bhy.collectionDemo.collect;

/**
 * @Author EthanBrown
 * @Date 2025/6/1 21:06
 * @PackageName: org.bhy.collectionDemo.collect
 * @ClassName: BatteryDataCollectService
 * @Description: TODO
 * @Version 1.0
 */
public interface BatteryDataCollectService {

    //定时调度方法
    void collectDataScheduled();

    //真正的采集业务方法
    void collectDataTask(String ip);

    //计算采集数据所属的簇
    int calculateClusterIdByRegisterAddress(int address);
}
