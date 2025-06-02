package generator;

import java.io.Serializable;
import lombok.Data;

/**
 * battery_data
 */
@Data
public class BatteryData implements Serializable {
    private Integer groupId;

    private Integer clusterId;

    private Double temperature;

    private Double voltage;

    private static final long serialVersionUID = 1L;
}