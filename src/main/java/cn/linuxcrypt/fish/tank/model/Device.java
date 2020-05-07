package cn.linuxcrypt.fish.tank.model;

import lombok.Data;

/**
 * @author clibing
 */
@Data
public class Device {
    private String clientId;
    private ChipType chip;
    private String ip;
    private String mac;
    private String wifi;

    private DeviceStatus status;
    private ApplicationType application;



}
