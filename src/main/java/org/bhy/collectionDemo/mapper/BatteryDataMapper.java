package org.bhy.collectionDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bhy.collectionDemo.pojo.BatteryData;

@Mapper
public interface BatteryDataMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(BatteryData record);

    int insertSelective(BatteryData record);

    BatteryData selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKeySelective(BatteryData record);

    int updateByPrimaryKey(BatteryData record);
}