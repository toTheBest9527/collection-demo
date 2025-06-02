package org.bhy.collectionDemo.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * battery_data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryData implements Serializable {
    private Integer groupId;

    private Integer clusterId;

    private Double temperature;

    private Double voltage;

    private static final long serialVersionUID = 1L;
}